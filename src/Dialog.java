import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;

public class Dialog implements Chat, Runnable {

    private MulticastSocket mSocket;

    private String name;
    private InetAddress address;
    private int port;

    public Dialog(String name, InetAddress address, int port) {
        this.name = name;
        this.address = address;
        this.port = port;

        try {
            mSocket = new MulticastSocket(this.port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public InetAddress getAddress() {
        return address;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public void sendMessage(String message) {

    }

    public void respondMessageReceived() {

    }

    @Override
    public void run() {

    }
}
