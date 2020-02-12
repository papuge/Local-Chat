import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Client implements Runnable {

    private final static String GROUPS_NAME_FILE = "local_groups.json";
    private final static String NAME_KEY = "name";

    private byte[] buf = new byte[256];
    private MulticastSocket socket;
    private LocalChatsInfo localChatsInfo;
    private String username;
    private ArrayList<String> clientGroupsNames = new ArrayList<>();

    private File groupsFile;

    public Client(String username) {
        this.username = username;
        try {
            socket = new MulticastSocket(4446);
        } catch (IOException e) {
            e.printStackTrace();
        }
        groupsFile = new File(GROUPS_NAME_FILE);
        try {
            groupsFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        readClientGroups();
        localChatsInfo = LocalChatsInfo.getInstance();
        try {
            for (Chat chat : localChatsInfo.getChats()) {
                if (socket != null) {
                    boolean isUserDialog = !chat.getName().startsWith(username + "|")
                            && !chat.getName().endsWith("|" + username);
                    boolean isDialog = chat.getName().contains("|");
                    if(isDialog && isUserDialog) {
                        continue;
                    }
                    if(!isDialog && !clientGroupsNames.contains(chat.getName())) {
                        continue;
                    }
                    socket.joinGroup(InetAddress.getByName(chat.getChatIp()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean handleInput(String input) {
        if (input.isEmpty()) {
            return false;
        }

        String message = null;
        String ip = null;

        if (input.startsWith("(")) {
            int pos = input.indexOf(")");
            if (pos != -1) {
                String toUserName = input.substring(1, pos);
                ip = localChatsInfo.findDialogIpByName(toUserName, username);
                message = "\u001b[34;1m" + "(" + toUserName + "|" + username + ")"
                        + "\u001b[0m" + input.substring(pos + 1);
            }
        } else if (input.startsWith("[")) {
            int pos = input.indexOf("]");
            if (pos != -1) {
                String chatName = input.substring(1, pos);
                ip = localChatsInfo.findChatIpByName(chatName);
                message = "\u001b[32;1m" + input.substring(0, pos + 1) + "\u001b[0m" + input.substring(pos + 1);
            }
        }

        if (message != null && ip != null) {
            sendMessage(message, ip);
            return true;
        }

        return false;
    }

    private void sendMessage(String message, String ip) {
        byte[] buf = message.getBytes();

        try {
            DatagramPacket packet = new DatagramPacket(buf, buf.length, InetAddress.getByName(ip), 4446);
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readClientGroups() {
        try {
            if(groupsFile.exists()) {
                String json = Files.readString(Paths.get(GROUPS_NAME_FILE));
                if(json.isEmpty()) {
                    return;
                }
                try {
                    JSONParser parser = new JSONParser();
                    Object objArray = parser.parse(json);
                    JSONArray jsonArray = (JSONArray) objArray;

                    for (Object o : jsonArray) {
                        JSONObject obj = (JSONObject) o;
                        String name = (String) obj.get(NAME_KEY);
                        clientGroupsNames.add(name);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String received = new String(
                    packet.getData(), 0, packet.getLength());

            handleInput(received);
            System.out.println(received);
        }
    }
}
