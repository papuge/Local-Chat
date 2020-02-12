import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class LocalChatsInfo {

    private final static String CHATS_FILE_NAME = "chats_data.json";
    private final static String NAME_KEY = "name";
    private final static String CHAT_IP_KEY = "chatIp";

    private static volatile LocalChatsInfo instance;

    private File chatFile;

    private ArrayList<Chat> chats = new ArrayList<>();

    public ArrayList<Chat> getChats() {
        return this.chats;
    }

    private JSONParser parser = new JSONParser();

    private LocalChatsInfo() {
        chatFile = new File(CHATS_FILE_NAME);
        try {
            if (!chatFile.createNewFile()) {
                String json = Files.readString(Paths.get(CHATS_FILE_NAME));
                chatsFromJson(json);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static LocalChatsInfo getInstance() {
        if (instance == null) {
            instance = new LocalChatsInfo();
        }
        return instance;
    }

    public String findChatIpByName(String name) {
        for (Chat chat : chats) {
            if (chat.getName().equals(name)) {
                return chat.getChatIp();
            }
        }
        return null;
    }

    public String findDialogIpByName(String name, String username) {
        for (Chat chat : chats) {
            if ((chat.getName().contains(name + "|") || chat.getName().contains("|" + name)) &&
                    (chat.getName().contains(username + "|") || chat.getName().contains("|" + username))) {
                return chat.getChatIp();
            }
        }
        return null;
    }

    private void chatsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Chat chat : chats) {
            JSONObject obj = new JSONObject();
            obj.put(NAME_KEY, chat.getName());
            obj.put(CHAT_IP_KEY, chat.getChatIp());
            jsonArray.add(obj);
        }

        try {
            FileWriter fw = new FileWriter(chatFile, false);
            fw.write(jsonArray.toJSONString());
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void chatsFromJson(String json) {
        try {
            Object objArray = parser.parse(json);
            JSONArray jsonArray = (JSONArray) objArray;

            for (Object o : jsonArray) {
                JSONObject obj = (JSONObject) o;
                String name = (String) obj.get(NAME_KEY);
                String address = (String) obj.get(CHAT_IP_KEY);
                chats.add(new Chat(name, address));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
