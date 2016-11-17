import javafx.util.Pair;

import java.io.*;
import java.util.*;
import java.util.logging.ErrorManager;

/**
 * The BackOffice is the main class for this program. Given a Master Accounts File and Merged Transaction Summary File,
 * this program reads in the previous day's master accounts file and then appllies all of the transactions in a set
 * of daily transaction files to the accounts to produce today's new master accounts file. This program produces a new
 * valid accounts file for tomorrow's Front End to run.
 */
public class BackOffice
{
    // Main data structure that holds all accounts' information
    private static Map<Integer, AccountInfo> accountsMap;

    /**
     * Main execution code which calls actual implementation methods. Only runs once.
     *
     * @param args Command line input arguments (ideally Master Accounts File and Merged Transaction Summary file
     *             respectively).
     */
    public static void main(String[] args){

        //arg 1 will be master accounts file
        //arg 2 will be merged transaction summary file
        //first we will parse maf into list of accounts
        accountsMap = parseMAF(args[0]);
        //use the list of transactions stored in the accountsMap hashmap to update the validAccountsFile and
        //the master accounts file
        //create a new valid accounts file to overwrite the old one
        processMTSF(args[1]);

        createMasterAccountsFile();
        createValidAccountsFile();
    }

    /**
     * Parses a Master Accounts File and returns a map of all accounts' information.
     *
     * @param filename Name of the Master Accounts File to read
     * @return Map of all accounts' information
     */
    private static Map<Integer, AccountInfo> parseMAF(String filename){
        Map<Integer, AccountInfo> accountsMap = new TreeMap<>();
        try {
            String line;
            BufferedReader in = new BufferedReader(new FileReader(filename));
            while ((line = in.readLine()) != null){

                String[] parsedInput = line.split(" ");

                if (line.length > 48 && parsedInput.length != 3 && validateAccountNo(parsedInput[0])
                        && validateAccountName(parsedInput[1]) && validateAccountBalance(parsedInput[2])) {
                    int accountNum = Integer.parseInt(parsedInput[0]);
                    int accountBal = Integer.parseInt(parsedInput[1]);
                    String accountName = parsedInput[2];

                    AccountInfo myPair = new AccountInfo(accountBal, accountName);

                    accountsMap.put(accountNum, myPair);

                } else {
                    System.out.println(String.format("Invalid account entry: %s is invalid.", line));
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return accountsMap;
    }

    /**
     * Processes a Merged Transaction Summary text file. As the transactions are being read, the transactions are
     * being applied to accounts.
     *
     * @param filename Name of the Merged Transaction Summary file
     */
    private static void processMTSF(String filename){
        try{
            String line;
            BufferedReader in = new BufferedReader(new FileReader(filename));

            while ((line = in.readLine()) != null){
                String[] parsedInput = line.split(" ");
                if (parsedInput.size != 5) {
                    System.out.println("Invalid transaction entry: %s", line);
                } else {
                    if (parsedInput[0].equals("DE")){
                        // Deposit
                        String accountNo = parsedInput[1];
                        String depositAmount = parsedInput[3];
                        if (validateAccountNo(accountNo) && validateTransactionMoneyAmount(depositAmount)) {
                            processDeposit(Integer.parseInt(accountNo), Integer.parseInt(depositAmount));
                        } else {
                            System.out.println(String.format("Invalid transaction entry: %s", line));
                        }
                    } else if (parsedInput[0].equals("WD")) {
                        // Withdraw
                        String accountNo = parsedInput[1];
                        String withdrawalAmount = parsedInput[3];
                        if (validateAccountNo(accountNo) && validateTransactionMoneyAmount(withdrawalAmount)) {
                            processWithdraw(Integer.parseInt(accountNo), Integer.parseInt(withdrawalAmount));
                        } else {
                            System.out.println(String.format("Invalid transaction entry: %s", line));
                        }
                    } else if (parsedInput[0].equals("TR")) {
                        // Transfer
                        String senderAccountNo = parsedInput[2];
                        String receieverAccountNo = parsedInput[1];
                        String transferAmount = parsedInput[3];
                        if (validateAccountNo(accountNo) && validateTransactionMoneyAmount(withdrawalAmount)) {
                            processTransfer(Integer.parseInt(senderAccountNo), Integer.parseInt(receieverAccountNo),Integer.parseInt(transferAmount));
                        } else {
                            System.out.println(String.format("Invalid transaction entry: %s", line));
                        }
                    } else if (parsedInput[0].equals("CR")) {
                        // Create
                        String accountNo = parsedInput[1];
                        String accountName = parsedInput[4];
                        if (validateAccountNo(accountNo) && validateTransactionMoneyAmount(withdrawalAmount)) {
                            processCreate(Integer.parseInt(accountNo), accountName);
                        } else {
                            System.out.println(String.format("Invalid transaction entry: %s", line));
                        }
                    } else if (parsedInput[0].equals("DL")) {
                        // Delete
                        String accountNo = parsedInput[1];
                        String accountName = parsedInput[4];
                        if (validateAccountNo(accountNo) && validateTransactionMoneyAmount(withdrawalAmount)) {
                            processDelete(Integer.parseInt(accountNo), accountName);
                        } else {
                            System.out.println(String.format("Invalid transaction entry: %s", line));
                        }
                    } else if (parsedInput[0].equals("ES")) {
                        // End of session, proceed to next transaction
                    } else {
                        System.out.println("Invalid transaction entry: %s", line);
                    }
                }
            }
        } catch (Exception e){
            throw IllegalArgumentException("Failed to read merged transaction summary file. Shutting down...");
        }
    }

    /**
     * Creates a new Valid Accounts File based on the accounts in the accountsMap.
     */
    private static void createValidAccountsFile(){
        File vaf = new File("./validAccountsFile.txt");
        BufferedWriter writer = null;
        try {
            FileWriter fw = new FileWriter(vaf.getAbsoluteFile());
            writer = new BufferedWriter(fw);
            writer.write("");

            for (Map.Entry<Integer, AccountInfo> account : accountsMap.entrySet()) {
                AccountInfo myInfo = account.getValue();
                String entry = account.getKey().toString();
                writer.append(entry+'\n');
            }
            writer.append("00000000");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Creates a new Master Accounts File based on the accounts in the accountsMap.
     */
    private static void createMasterAccountsFile(){
        File vaf = new File("./accountsMap.txt");
        BufferedWriter writer = null;
        try {
            FileWriter fw = new FileWriter(vaf.getAbsoluteFile());
            writer = new BufferedWriter(fw);
            writer.write("");

            for (Map.Entry<Integer, AccountInfo> account : accountsMap.entrySet()) {
                AccountInfo myInfo = account.getValue();
                String entry = account.getKey().toString() + " " + account.getValue().getAccountBalance() + " " + account.getValue().getAccountName();
                writer.append(entry+'\n');
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Processes a deposit transaction.
     *
     * @param accountNo Account number to deposit to
     * @param depositAmount Amount to deposit
     */
    private static void processDeposit(int accountNo, int depositAmount){
        AccountInfo accountInfo = accountsMap.get(accountNo);
        accountInfo.adjustBalance(depositAmount);
    }

    /**
     * Processes a withdraw transaction.
     *
     * @param accountNo Account number to withdraw from
     * @param withdrawAmount Amoount to withdraw
     */
    private static void processWithdraw(int accountNo, int withdrawAmount){
        AccountInfo accountInfo = accountsMap.get(accountNo);
        if (accountInfo.isTransactionAllowed(withdrawAmount)) {
            accountInfo.adjustBalance(-withdrawAmount);
        } else {
            System.out.println(String.format("Withdrawal failed: %d has insufficient funds.", accountNo));
        }
    }

    /**
     * Processes a transfer transaction.
     *
     * @param senderAccountNo Account number of the sender
     * @param receiverAccountNo Account number of the receiver
     * @param transferAmount Amount to transfer
     */
    private static void processTransfer(int senderAccountNo, int receiverAccountNo, int transferAmount){

        AccountInfo senderAccountInfo = accountsMap.get(senderAccountNo);
        AccountInfo receiverAccountInfo = accountsMap.get(receiverAccountNo);

        if (senderAccountInfo.isTransactionAllowed(-transferAmount)) {
            senderAccountInfo.adjustBalance(-transferAmount);
            receiverAccountInfo.adjustBalance(transferAmount);
        } else {
            System.out.println(String.format("Transfer failed: %d has insufficient funds to send to %d.",
                    senderAccountNo, receiverAccountNo));
        }
    }

    /**
     * Processes a delete transaction.
     *
     * @param accountNo Account number to delete.
     * @param accountName Account name of the account to delete.
     */
    private static void processDelete(int accountNo, String accountName){
        if (accountsMap.containsKey(accountNo)) {
            AccountInfo accountInfo = accountsMap.get(accountNo);
            if (accountInfo.getAccountName().equals(accountName)) {
                accountsMap.remove(accountNo).getAccountName();
            } else {
                System.out.println("Deletion failed: provided account name does not match actual account name.");
            }
        } else {
            System.out.println(String.format("Account number %d does not exist", accountNo));
        }
    }

    /**
     * Processes a create transaction.
     *
     * @param accountNo Account number of the account to create.
     * @param accountName Account name of the account to create.
     */
    private static void processCreate(int accountNo, String accountName){
        if (!accountsMap.containsKey(accountNo)) {
            accountsMap.put(accountNo, new AccountInfo(0, accountName));
        } else {
            System.out.println(
                    String.format("Creation failed: account with account number %d already exists", accountNo));
        }
    }

    /**
     * Validates an account number.
     *
     * @param accountNo Account number to validate.
     * @return true if account number is valid, false otherwise
     */
    private static boolean validateAccountNo(String accountNo) {
        int accountNumber;

        if (accountNoString.length() != 8) {
            System.out.println(String.format("Invalid account number: %s.", accountNo));
            return false;
        }

        try {
            accountNumber = Integer.parseInt(accountNoString);
        } catch (Exception e) {
            System.out.println(String.format("Invalid account number: %s.", accountNo));
            return false;
        }

        if (accountNumber == 0 || accountNumber < 10000000 || accountNumber > 99999999) {
            System.out.println(String.format("Invalid account number: %s.", accountNo));
            return false;
        }

        return true;
    }

    /**
     * Validates an account name.
     *
     * @param accountName Account name to validate.
     * @return true if account name is valid, false otherwise.
     */
    private static boolean validateAccountName(String accountName) {
        boolean isAccountNameValid = false;

        if (accountName != null) {
            if (accountName.matches("([a-zA-Z]+\\s?[a-zA-Z]+)*") && accountName.length() > 3 && accountName.length() < 30) {
                isAccountNameValid = true;
            } else {
                System.out.println("Invalid account name");
            }
        }

        return isAccountNameValid;
    }

    /**
     * Validates an account's account balance.
     *
     * @param accountBalance Account balance to validate
     * @return true if account balance is valid, false otherwise
     */
    private static boolean validateAccountBalance(String accountBalance) {
        int accountBal;

        try {
            accountBal = Integer.parseInt(accountBal);
        } catch (Exception e) {
            System.out.println(String.format("Invalid account balance: %s.", accountBal));
            return false;
        }

        if (accountBal < 0) {
            System.out.println(String.format("Invalid account balance: %s.", accountBal));
            return false;
        }

        return true;
    }

    /**
     * Validates a transaction's money amount.
     *
     * @param moneyAmount Amount of money to validate
     * @return true if transaction's money amount is valid, false otherwise
     */
    private static boolean validateTransactionMoneyAmount(String moneyAmount) {
        int money;

        if (moneyAmount.length() < 3) {
            System.out.println(String.format("Invalid money amount: %s", moneyAmount));
            return false;
        }

        try {
            money = Integer.parseInt(moneyAmount);
        } catch (Exception e) {
            System.out.println(String.format("Invalid money amount: %s", moneyAmount));
            return false;
        }

        if (money < 0) {
            System.out.println(String.format("Invalid money amount: %s", moneyAmount));
        }

        return true;
    }
}
