public interface MentalChannel {
    void receiveThought();
    void sendThought(String sender);
    void sendThought(String message, String sender);
}
