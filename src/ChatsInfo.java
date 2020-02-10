import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ChatsInfo implements ChatsDetails {

    private final static String CHATS_FILE_NAME = "chats_data.json";

    private final static String USERS_FILE_NAME = "users_data.json";

    private File chatFile;
    private File usersFile;

    private ArrayList<Chat> chats = new ArrayList<>();
    private ArrayList<User> users = new ArrayList<>();

    private JSONParser parser = new JSONParser();

    public ChatsInfo() {
        chatFile = new File(CHATS_FILE_NAME);
        usersFile = new File(USERS_FILE_NAME);
        try {
            if (chatFile.createNewFile()) {
                String json = Files.readString(Paths.get(CHATS_FILE_NAME));
                chatsFromJson(json);
            }
            if (usersFile.createNewFile()) {
                String json = Files.readString(Paths.get(USERS_FILE_NAME));
                usersFromJson(json);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void storeData() {
        chatsToJson();
        usersToJson();
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void addChat(Chat chat) {
        chats.add(chat);
    }

    @Override
    public boolean isChatExists(Chat chat) {
        return false;
    }

    @Override
    public void createChat(String name, String address) {

    }

    private void usersToJson() {
        JSONArray jsonArray = new JSONArray();

        for(User user: users) {
            JSONObject obj = new JSONObject();
            obj.put("name", user.getUsername());
            obj.put("address", user.getAddress());
            obj.put("port", user.getPort());
            jsonArray.add(obj);
        }

        try {
            FileWriter fw = new FileWriter(usersFile,false);
            fw.write(jsonArray.toJSONString());
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void usersFromJson(String json) {
        try {
            Object objArray = parser.parse(json);
            JSONArray jsonArray = (JSONArray) objArray;

            for (Object o : jsonArray) {
                JSONObject obj = (JSONObject) o;
                String name = (String) obj.get("name");
                InetAddress address = (InetAddress) obj.get("address");
                int port = (int) obj.get("port");
                users.add(new User(name, address, port));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void chatsToJson() {
        JSONArray jsonArray = new JSONArray();

        for(Chat chat: chats) {
            JSONObject obj = new JSONObject();
            obj.put("name", chat.getName());
            obj.put("address", chat.getAddress());
            obj.put("port", chat.getPort());
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
                String name = (String) obj.get("name");
                InetAddress address = (InetAddress) obj.get("address");
                int port = (int) obj.get("port");
                chats.add(new GroupChat(name, address, port));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
