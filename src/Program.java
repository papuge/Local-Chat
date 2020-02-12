import java.util.Scanner;

public class Program {

    static String str = "Start chatting:\n" +
            "\u001b[32m[groupName]\u001b[0m your message here\n" +
            "\u001b[32m(user)\u001b[0m your message here\n" +
            "\u001b[32m\\exit\u001b[0m (exit from chat)\n";

    public static void main(String[] args) {
        System.out.print("Welcome, enter your nick: ");
        Scanner scanner = new Scanner(System.in);
        String username = scanner.nextLine();
        Client client = new Client(username);
        System.out.print(str);
        Thread t = new Thread(client);
        t.start();
        while (true) {
            String input = scanner.nextLine();
            if(input.equals("\\exit")) {
                System.out.print("Buy!\n");
                break;
            }
            System.out.print("You said: ");
            if (!client.handleInput(input)) {
                System.out.print("not a message\n");
            }
        }
    }
}
