import java.net.InetAddress;

public class User {

    private String username;
    public String getUsername() {
        return username;
    }

    private InetAddress address;
    public InetAddress getAddress() {
        return address;
    }

    private int port;
    public int getPort() {
        return port;
    }


    public User(String username, InetAddress address, int port) {
        this.username = username;
        this.address = address;
        this.port = port;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }

        if(!(obj instanceof User)) {
            return false;
        }

        User another = (User) obj;

        return this.username.equals(another.username)
                && this.address.equals(another.address)
                && this.port == another.port;
    }

    @Override
    public int hashCode() {
        return this.username.hashCode() + this.address.hashCode() + this.port;
    }
}
