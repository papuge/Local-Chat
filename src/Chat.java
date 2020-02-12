public class Chat {

    private String name;
    private String chatIp;

    public String getName() {
        return this.name;
    }

    public String getChatIp() {
        return this.chatIp;
    }

    public Chat(String name, String address) {
        this.name = name;
        this.chatIp = address;
    }
}
