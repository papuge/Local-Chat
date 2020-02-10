import java.net.InetAddress;
import java.util.ArrayList;

public interface Chat {

    public String getName();

    public InetAddress getAddress();

    public int getPort();

    public void join();

    public void leave();

    public void sendMessage(String message);
}
