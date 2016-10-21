import lombok.Data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * Created by stuartbourne on 2016-10-19.
 */
@Data
public class Session {

    private SessionMode sessionMode;
    private Date timestamp;
    private List<AccountNo> deletedAccountNoList;
    private List<AccountNo> validAccountNoList;
    private List<Transaction> transactions;
    private int currentWithdrawnAmount;
    private int currentTransferAmount;

    private static Scanner sc = new Scanner(System.in);

    public Session(SessionMode sessionMode, List<AccountNo> validAccountNoList) {
        if (sessionMode == null || validAccountNoList == null) {
            throw new InvalidParameterException("Error: Invalid session is being created - session creation aborted.");
        } else {
            this.sessionMode = sessionMode;
            this.validAccountNoList = validAccountNoList;
            deletedAccountNoList = new ArrayList<>();
            transactions = new ArrayList<>();
            currentTransferAmount = 0;
            currentWithdrawnAmount = 0;
            timestamp = new Date();
        }
    }

    public SessionMode getSessionMode() {
        return sessionMode;
    }

    public void processTransaction(String input) {
        switch (input) {
            case "create": {
                if (sessionMode == SessionMode.AGENT) {
                    System.out.println("You have chosen to create an account. (Type 'cancel' to cancel the transaction)");
                    Transaction newTransaction = Transaction.constructCreateTransaction(validAccountNoList);
                    if (newTransaction != null) {
                        transactions.add(newTransaction);
                        System.out.println("Account has been successfully created.");
                    } else {
                        System.out.println("Creating account unsuccessful.");
                    }
                } else {
                    System.out.println("Invalid command.");
                }
                break;
            } case "delete": {
                if (sessionMode == SessionMode.AGENT) {
                    System.out.println("You have chosen to delete an account. (Type 'cancel' to cancel the transaction)");
                    Transaction newTransaction = Transaction.constructDeleteTransaction(validAccountNoList);
                    if (newTransaction != null) {
                        transactions.add(newTransaction);
                        System.out.println("Account has been successfully deleted.");
                    } else {
                        System.out.println("Deleting account unsuccessful.");
                    }
                } else {
                    System.out.println("Invalid command.");
                }
                break;
            } case "deposit": {
                System.out.println("You have chosen to deposit. (Type 'cancel' to cancel the transaction)");
                Transaction newTransaction = Transaction.constructDepositTransaction(validAccountNoList);
                if (newTransaction != null) {
                    transactions.add(newTransaction);
                    System.out.println("Deposit successful.");
                } else {
                    System.out.println("Deposit unsuccessful.");
                }
                break;
            } case "transfer": {
                System.out.println("You have chosen to transfer. (Type 'cancel' to cancel the transaction)");
                Transaction newTransaction = Transaction.constructTransferTransaction(validAccountNoList, currentTransferAmount, sessionMode);
                if (newTransaction != null) {
                    transactions.add(newTransaction);
                    System.out.println("Transfer successful.");
                } else {
                    System.out.println("Transfer unsuccessful.");
                }
                break;
            } case "withdraw": {
                System.out.println("You have chosen to withdraw. (Type 'cancel' to cancel the transaction)");
                Transaction newTransaction = Transaction.constructWithdrawTransaction(validAccountNoList, currentWithdrawnAmount, sessionMode);
                if (newTransaction != null) {
                    currentWithdrawnAmount += newTransaction.getMoney();
                    System.out.println("Withdrawal successful.");
                    transactions.add(newTransaction);
                } else {
                    System.out.println("Withdrawal unsuccessful.");
                }
                break;
            } default: {
                System.out.println("Invalid command.");
            }
        }
    }

    public static Session login(String file){

        SessionMode sessionMode = null;
        List<AccountNo> validAccountNoList = new ArrayList<>();
        String userInput;

        while (sessionMode == null) {
            System.out.println("Please enter 'atm' or 'agent'.");
            userInput = sc.next();
            if (userInput.equals("logout")) {
                return null;
            } else if (userInput.equals("atm")) {
                sessionMode = SessionMode.ATM;
            } else if (userInput.equals("agent")) {
                sessionMode = SessionMode.AGENT;
            } else {
                System.out.println("Invalid session mode.");
            }
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    AccountNo accountNo = new AccountNo(line);
                    validAccountNoList.add(accountNo);
                } catch (Exception e) {
                    System.out.println("Invalid account number in Valid Accounts file. Shutting down.");
                    System.exit(0);
                }
            }
        } catch (Exception e) {
            System.out.println("Unable to read Valid Accounts file. Shutting down.");
            e.printStackTrace();
            System.exit(0);
        }

        return new Session(sessionMode, validAccountNoList);
    }

    public boolean logout() {
        System.out.println(timestamp.toString());
        return true;
        /*
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(timestamp. + "_tsf.txt"));
            for (Transaction transaction : transactions) {
                writer.write(transaction.getTransactionSummaryEntry() + '\n');
            }
            writer.write("ES");
            writer.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        */
    }
}
