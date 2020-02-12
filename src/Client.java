import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Client implements Runnable {

    protected byte[] buf = new byte[256];

    private MulticastSocket socket;

    private LocalChatsInfo localChatsInfo;

    private String username;

    public Client(String username) {
        this.username = username;
        try {
            socket = new MulticastSocket(4446);
        } catch (IOException e) {
            e.printStackTrace();
        }
        localChatsInfo = LocalChatsInfo.getInstance();
        try {
            for (Chat chat : localChatsInfo.getChats()) {
                if (socket != null) {
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
