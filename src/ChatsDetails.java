public interface ChatsDetails {

    public boolean isChatExists(Chat chat);

    public void createChat(String name, String address);

    public boolean isMemberOfChat(User user, Chat chat);
}
