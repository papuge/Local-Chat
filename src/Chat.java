import java.net.InetAddress;
import java.util.ArrayList;

public interface Chat {

    String getName();

    InetAddress getAddress();

    int getPort();

    void sendMessage(String message);
}
