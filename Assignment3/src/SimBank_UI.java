import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * SimBank - The future of banking.
 * The intention of this program is to act as the user interface for SimBank. This the entry-point
 * for SimBank. This program will a "valid accounts" text file and a "transaction summary" file. The
 * "valid accounts" file will be read once the user logs in. The program will output a transaction summary
 * file after a session has been ended.
 *
 * The program is intended to be ran with a text file "valid-accounts-file.txt" and "_tsf.txt" along with user input
 * via command line, but if given a textfile of valid commands as third parameter the program will also run as intended.
 */
public class SimBank_UI {

    public static Scanner sc;

    public static void main(String[] args)
    {
        String validAccountsFilename = "";
        String transactionsSummaryFilename = "";
        String commandsFilename;

        if (args.length == 2) {
            validAccountsFilename = args[0];
            transactionsSummaryFilename = args[1];
            sc = new Scanner(System.in);
        } else if (args.length == 3) {
            validAccountsFilename = args[0];
            transactionsSummaryFilename = args[1];
            commandsFilename = args[2];
            try {
                sc = new Scanner(new File(commandsFilename));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("You must provide a 'valid accounts' text file and a 'transactions summary' text file.");
            System.exit(-1);
        }

        Session session = null;
        String userInput;
        System.out.println("Welcome to SimBank! Please enter 'login' to use SimBank.");

        while (true)
        {
            // If session is active (user has typed 'login')
            if (session != null) {
                System.out.println("Please enter one of these commands: ");
                if (session.getSessionMode() == SessionMode.AGENT) {
                    System.out.println("'create', 'delete', 'deposit', 'transfer', 'withdraw', 'logout'");
                } else {
                    System.out.println("'deposit', 'transfer', 'withdraw', 'logout'");
                }
                userInput = sc.next().toLowerCase();
                if (userInput.equals("logout")) {
                    session.logout(transactionsSummaryFilename);
                    session = null;
                    System.out.println("Session ended. Goodbye! Type 'login' to start another session.");
                } else {
                    session.processTransaction(userInput);
                }
            // If session is not active (user has not type 'login')
            } else {
                userInput = sc.next().toLowerCase();
                if (userInput.equals("login")) {
                    session = Session.login(validAccountsFilename);
                } else if (userInput.equals("exit")) {
                    System.exit(0);
                } else {
                    System.out.println("Please login before entering a command.");
                }
            }
        }
    }
}
