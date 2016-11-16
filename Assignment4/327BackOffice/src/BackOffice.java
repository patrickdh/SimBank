import javafx.util.Pair;

import java.io.*;
import java.util.*;
import java.util.logging.ErrorManager;

/**
 * Created by stuartbourne on 2016-11-15.
 */
public class BackOffice
{
    private static TreeMap<Integer, AccountInfo> masterAccountsFile;



    public static void main(String[] args){

        //arg 1 will be master accounts file
        //arg 2 will be merged transaction summary file
        //first we will parse maf into list of accounts
        masterAccountsFile = parseMAF(args[0]);
        //use the list of transactions stored in the masterAccountsFile hashmap to update the validAccountsFile and
        //the master accounts file
        //create a new valid accounts file to overwrite the old one
        processMTSF(args[1]);

        createMasterAccountsFile();
        createValidAccountsFile();
    }

    private static TreeMap<Integer, AccountInfo> parseMAF(String fileName){
        TreeMap<Integer, AccountInfo> myMaf = new TreeMap<>();
        try {
            String line;
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            while ((line = in.readLine()) != null){
                String[] parsedInput = line.split(" ");
                int accountNum = Integer.parseInt(parsedInput[0]);
                int accountBal = Integer.parseInt(parsedInput[1]);
                String accountName = parsedInput[2];

                AccountInfo myPair = new AccountInfo(accountBal, accountName);

                myMaf.put(accountNum, myPair);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return myMaf;
    }

    private static void processMTSF(String fileName){
        try{
            String line;
            BufferedReader in = new BufferedReader(new FileReader(fileName));

            while ((line = in.readLine()) != null){
                String[] parsedInput = line.split(" ");

                if (parsedInput[0].equals("DE")){
                    //deposit
                    processDeposit(Integer.parseInt(parsedInput[1]), Integer.parseInt(parsedInput[3]));
                } else if (parsedInput[0].equals("WD")) {
                    //withdrawal
                    processWithdraw(Integer.parseInt(parsedInput[1]), Integer.parseInt(parsedInput[3]));
                } else if (parsedInput[0].equals("TR")) {
                    //transfer
                    processTransfer(Integer.parseInt(parsedInput[2]), Integer.parseInt(parsedInput[1]),Integer.parseInt(parsedInput[3]));
                } else if (parsedInput[0].equals("CR")) {
                    //create
                    processCreate(Integer.parseInt(parsedInput[1]), parsedInput[4]);
                } else if (parsedInput[0].equals("DL")) {
                    //delete
                    processDelete(Integer.parseInt(parsedInput[1]));
                } else if (parsedInput[0].equals("ES")) {
                    //end of file
                } else {
                    //not recognized
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void createValidAccountsFile(){
        File vaf = new File("./validAccountsFile.txt");
        BufferedWriter writer = null;
        try {
            FileWriter fw = new FileWriter(vaf.getAbsoluteFile());
            writer = new BufferedWriter(fw);
            writer.write("");

            for (Map.Entry<Integer, AccountInfo> account : masterAccountsFile.entrySet()) {
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

    private static void createMasterAccountsFile(){
        File vaf = new File("./masterAccountsFile.txt");
        BufferedWriter writer = null;
        try {
            FileWriter fw = new FileWriter(vaf.getAbsoluteFile());
            writer = new BufferedWriter(fw);
            writer.write("");

            for (Map.Entry<Integer, AccountInfo> account : masterAccountsFile.entrySet()) {
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


    private static void processDeposit(int accountNo, int depositAmount){
        AccountInfo myAccountInfo = masterAccountsFile.get(accountNo);
        myAccountInfo.adjustBalance(depositAmount);
        //check to see if it goes over
    }


    private static void processWithdraw(int accountNo, int withdrawAmount){
        AccountInfo myAccountInfo = masterAccountsFile.get(accountNo);
        myAccountInfo.adjustBalance(-withdrawAmount);
        //check to see if it goes over or under
    }


    private static void processTransfer(int accountTransferring, int accountRecieving, int transferAmount){

        AccountInfo transferringAccountInfo = masterAccountsFile.get(accountTransferring);
        AccountInfo receivingAccountInfo = masterAccountsFile.get(accountRecieving);

        transferringAccountInfo.adjustBalance(-transferAmount);
        receivingAccountInfo.adjustBalance(transferAmount);
        //perform adequate checks
    }

    private static void processDelete(int accountNo){
        masterAccountsFile.remove(accountNo).getAccountName();
        //check to see if account is there
    }

    private static void processCreate(int accountNo, String accountName){
        masterAccountsFile.put(accountNo, new AccountInfo(0, accountName));
        //check to see if account is there
    }
}
