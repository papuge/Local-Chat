import java.net.InetAddress;

public class Dialog implements Chat, Runnable {

    private String name;
    private InetAddress address;
    private int port;

    public Dialog(String name, InetAddress address, int port) {
        this.name = name;
        this.address = address;
        this.port = port;
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
    public void join() {

    }

    @Override
    public void leave() {

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
