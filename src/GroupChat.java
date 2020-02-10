import java.net.InetAddress;
import java.net.MulticastSocket;

public class GroupChat implements Chat, Runnable {

    private MulticastSocket mSocket;

    private String name;
    private InetAddress address;
    private int port;

    public GroupChat(String name, InetAddress address, int port) {
        this.name = name;
        this.address = address;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public InetAddress getAddress() {
        return this.address;
    }

    @Override
    public int getPort() {
        return this.port;
    }

    @Override
    public void join() {

    }

    @Override
    public void leave() {

    }

    @Override
    public void sendMessage(String message) {

    }

    // Receiving new messages
    @Override
    public void run() {

    }
}
