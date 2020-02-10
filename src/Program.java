import java.net.InetAddress;
import java.net.UnknownHostException;

public class Program {

    public static void main(String[] args) {
        ChatsInfo chatsInfo = new ChatsInfo();
        try {
            chatsInfo.addChat(new GroupChat("yyy", InetAddress.getLocalHost(), 404));
            chatsInfo.addChat(new GroupChat("123.456.89.0", InetAddress.getLocalHost(), 404));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        try {
            chatsInfo.addUser(new User("grispy", InetAddress.getLocalHost(), 404));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        chatsInfo.storeData();
    }
}
