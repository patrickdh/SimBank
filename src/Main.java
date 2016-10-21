import java.util.Scanner;
import java.util.jar.Pack200;

/**
 * Created by stuartbourne on 2016-10-19.
 */
public class Main
{
    public static void main(String[] args)
    {
        System.out.println("Welcome to SimBank! Please enter 'login' to use SimBank.");
        Session session = null;
        Scanner sc = new Scanner(System.in);
        String userInput;

        while(true)
        {
            if (session != null) {
                System.out.println("Please enter one of these commands: ");
                if (session.getSessionMode() == SessionMode.AGENT) {
                    System.out.println("'create', 'delete', 'deposit', 'transfer', 'withdraw', 'logout'");
                } else {
                    System.out.println("'deposit', 'transfer', 'withdraw', 'logout'");
                }
                userInput = sc.next().toLowerCase();
                if (userInput.equals("logout")) {
                    session.logout();
                    session = null;
                } else {
                    session.processTransaction(userInput);
                }
            } else {
                userInput = sc.next().toLowerCase();
                if (userInput.equals("login")) {
                    session = Session.login("test.txt");
                } else {
                    System.out.println("Please login before entering a command.");
                }
            }
        }
    }
}
