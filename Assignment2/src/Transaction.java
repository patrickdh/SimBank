import java.util.List;
import java.util.Scanner;

/**
 * Transaction object for the SimBank banking system.
 */
public class Transaction {

    private TransactionType type;
    private AccountNo recipientAccountNo;
    private AccountNo senderAccountNo;
    private String accountName;
    private int money;

    private static Scanner sc = new Scanner(System.in);

    private Transaction(TransactionType transactionType, AccountNo recipientAccountNo, AccountNo senderAccountNo, String
            accountName, int money) {

        type = transactionType;
        this.recipientAccountNo = recipientAccountNo;
        this.senderAccountNo = senderAccountNo;
        this.accountName = accountName;
        this.money = money;
    }

    /**
     * Constructs a 'create' transaction based off user input.
     *
     * @param validAccountNoList valid account numbers list to validate account number(s)
     * @return Transaction object for a 'create' transaction
     */
    public static Transaction constructCreateTransaction(List<AccountNo> validAccountNoList)
    {
        AccountNo accountNo = null;
        String accountName = "";
        String userInput;

        while (accountNo == null) {
            System.out.println("Please enter the account number of the account you wish to create.");
            userInput = sc.next().toLowerCase();
            if (userInput.equals("cancel")) {
                System.out.println("You have cancelled the account creation.");
                return null;
            } else {
                try {
                    accountNo = new AccountNo(userInput);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    continue;
                }
                accountNo = validateAccountNoInput(userInput);
                if (validAccountNoList.contains(accountNo)) {
                    System.out.println("Invalid account number: this account number already exists.");
                    accountNo = null;
                }
            }
        }

        while (!validateAccountName(accountName)) {
            System.out.println("Please enter the account's name.");
            userInput = sc.next().toUpperCase();
            if (userInput.equals("cancel")) {
                System.out.println("You have cancelled the account creation.");
                return null;
            } else {
                accountName = userInput;
            }
        }

        return new Transaction(TransactionType.CREATE, accountNo, null, accountName, -1);
    }

    /**
     * Constructs a 'delete' transaction based off user input.
     *
     * @param validAccountNoList valid account numbers list to validate account number(s)
     * @return
     */
    public static Transaction constructDeleteTransaction(List<AccountNo> validAccountNoList)
    {
        AccountNo accountNo = null;
        String accountName = "";
        String userInput;

        while (accountNo == null) {
            System.out.println("Please enter the account number of the account you wish to delete.");
            userInput = sc.next().toLowerCase();
            if (userInput.equals("cancel")) {
                System.out.println("You have cancelled the delete.");
                return null;
            } else {
                try {
                    accountNo = new AccountNo(userInput);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    continue;
                }
                if (validAccountNoList.contains(accountNo)) {
                    accountNo = validateAccountNoInput(userInput);
                } else {
                    System.out.println("This account does not exist");
                    accountNo = null;
                }
            }
        }

        while (!validateAccountName(accountName)) {
            System.out.println("Please enter the account name of the account you wish to delete.");
            accountName = sc.next().toUpperCase();
        }
        validAccountNoList.remove(accountNo);

        return new Transaction(TransactionType.DELETE, accountNo, null, accountName, -1);
    }

    /**
     * Constructs a 'deposit' transaction based off user input.
     *
     * @param validAccountNoList valid account numbers list to validate account number(s)
     * @return
     */
    public static Transaction constructDepositTransaction(List<AccountNo> validAccountNoList)
    {
        AccountNo accountNo = null;
        String userInput;
        int depositAmount = -1;

        while (accountNo == null) {
            System.out.println("Please enter the account number of the account you wish to deposit to.");
            userInput = sc.next().toLowerCase();
            if (userInput.equals("cancel")) {
                System.out.println("You have cancelled the deposit.");
                return null;
            } else {
                try {
                    accountNo = new AccountNo(userInput);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    continue;
                }
                if (validAccountNoList.contains(accountNo)) {
                    accountNo = validateAccountNoInput(userInput);
                } else {
                    System.out.println("This account does not exist");
                    accountNo = null;
                }
            }
        }

        while (depositAmount < 0) {
            System.out.println("Please enter the amount you would like to deposit (in number of cents).");
            userInput = sc.next().toLowerCase();
            if (userInput.equals("cancel")) {
                System.out.println("You have cancelled the deposit.");
                return null;
            } else {
                try {
                    int amount = Integer.parseInt(userInput);
                    if (amount > -1) {
                        depositAmount = amount;
                    } else {
                        System.out.println("Invalid money amount.");
                    }
                } catch(Exception e) {
                    System.out.println("Invalid money amount.");
                }
            }
        }

        return new Transaction(TransactionType.DEPOSIT, accountNo, null, null, depositAmount);
    }

    /**
     * Constructs a 'transfer' transaction based off user input.
     *
     * @param validAccountNoList valid account numbers list to validate account number(s)
     * @param currentTransferAmount
     * @param mode
     * @return
     */
    public static Transaction constructTransferTransaction(List<AccountNo> validAccountNoList, int currentTransferAmount, SessionMode mode)
    {
        AccountNo recipientAccountNo = null;
        AccountNo senderAccountNo = null;
        int transferAmount = -1;
        String userInput;

        while (recipientAccountNo == null) {
            System.out.println("Please enter the account number of the account you wish to transfer FROM.");
            userInput = sc.next().toLowerCase();
            if (userInput.equals("cancel")) {
                System.out.println("You have cancelled the transfer.");
                return null;
            } else {
                try {
                    recipientAccountNo = new AccountNo(userInput);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    continue;
                }
                if (validAccountNoList.contains(recipientAccountNo)) {
                    recipientAccountNo = validateAccountNoInput(userInput);
                } else {
                    System.out.println("This account does not exist");
                    recipientAccountNo = null;
                }
            }
        }

        while (senderAccountNo == null) {
            System.out.println("Please enter the account number of the account you wish to transfer TO.");
            userInput = sc.next().toLowerCase();
            if (userInput.equals("cancel")) {
                System.out.println("You have cancelled the transfer.");
                return null;
            } else {
                try {
                    senderAccountNo = new AccountNo(userInput);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    continue;
                }
                if (validAccountNoList.contains(senderAccountNo)) {
                    senderAccountNo = validateAccountNoInput(userInput);
                } else {
                    System.out.println("This account does not exist");
                    senderAccountNo = null;
                }
            }
        }

        while (transferAmount < 0) {
            System.out.println("Please enter the amount you would like to transfer (in number of cents).");
            userInput = sc.next().toLowerCase();
            if (userInput.equals("cancel")) {
                System.out.println("You have cancelled the transfer.");
                return null;
            } else {
                try{
                    int amount = Integer.parseInt(userInput);
                    if (amount > -1 && mode == SessionMode.ATM && (currentTransferAmount + amount) > SessionMode.ATM.getMaxTransfer()) {
                        System.out.println("Could not complete transaction due to surpassing transfer limit.");
                        return null;
                    } else if (mode == SessionMode.AGENT && (currentTransferAmount + amount) > SessionMode.AGENT.getMaxTransfer()) {
                        System.out.println("Could not complete transaction due to surpassing transfer limit.");
                        return null;
                    } else {
                        transferAmount = amount;
                    }
                    if (amount > -1) {
                        transferAmount = amount;
                    } else {
                        System.out.println("Invalid money amount.");
                    }
                } catch(Exception e)
                {
                    System.out.println("Invalid money amount.");
                }
            }
        }

        return new Transaction(TransactionType.TRANSFER, recipientAccountNo, senderAccountNo, null, transferAmount);

    }

    /**
     * Constructs a 'withdraw' transaction based off user input.
     *
     * @param validAccountNoList valid account numbers list to validate account number(s)
     * @param currentWithdrawalAmount
     * @param mode
     * @return
     */
    public static Transaction constructWithdrawTransaction(List<AccountNo> validAccountNoList, int currentWithdrawalAmount, SessionMode mode)
    {
        AccountNo accountNo = null;
        int withdrawalAmount = -1;
        String userInput;

        while (accountNo == null) {
            System.out.println("Please enter the account number of the account you wish to withdraw from.");
            userInput = sc.next().toLowerCase();
            if (userInput.equals("cancel")) {
                System.out.println("You have cancelled the withdrawal.");
                return null;
            } else {
                try {
                    accountNo = new AccountNo(userInput);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    continue;
                }
                if (validAccountNoList.contains(accountNo)) {
                    accountNo = validateAccountNoInput(userInput);
                } else {
                    System.out.println("This account does not exist");
                    accountNo = null;
                }
            }
        }

        while(withdrawalAmount < 0) {
            System.out.println("Please enter the amount of money you would like to withdraw");
            userInput = sc.next();
            if (userInput.equals("cancel")) {
                System.out.println("You have cancelled the withdrawal.");
                return null;
            } else {
                try {
                    int amount = Integer.parseInt(userInput);
                    if (mode == SessionMode.ATM && (currentWithdrawalAmount + amount) > SessionMode.ATM.getMaxWithdraw()) {
                        //could not create due to overwithdrawal
                        System.out.println("Could not complete transaction due to overwithdrawl");
                        return null;
                    } else if (mode == SessionMode.AGENT && (currentWithdrawalAmount + amount) > SessionMode.AGENT.getMaxWithdraw()) {
                        //could not create due to overwithdrawal
                        System.out.println("Could not complete transaction due to overwithdrawl");
                        return null;
                    } else {
                        withdrawalAmount = amount;
                    }
                } catch (Exception e) {
                    System.out.println("Invalid money amount.");
                }
            }
        }

        return new Transaction(TransactionType.WITHDRAW, accountNo, null, null, withdrawalAmount);
    }

    /**
     * Builds a transaction summary file entry.
     *
     * @return Transation summary file entry string.
     */
    public String getTransactionSummaryEntry()
    {
        String toAccountCode;
        String fromAccountCode;
        String accountMoneyCode;
        String accountNameCode;

        String accountCode = type.getTransactionCode();

        if (senderAccountNo == null) {
            toAccountCode = "***";
        } else {
            toAccountCode = senderAccountNo.toString();
        }

        if (recipientAccountNo == null) {
            fromAccountCode = "***";
        } else {
            fromAccountCode = recipientAccountNo.toString();
        }

        if (money <= -1) {
            accountMoneyCode = "000";
        } else if (money < 100) {
            String parsedMoney = Integer.toString(money);

            if (parsedMoney.length() == 1) {
                accountMoneyCode = "00" + parsedMoney;
            } else {
                accountMoneyCode = "0" + parsedMoney;
            }
        } else {
            accountMoneyCode = Integer.toString(money);
        }

        if (accountName == null) {
            accountNameCode = "***";
        } else {
            accountNameCode = accountName;
        }

        return accountCode + toAccountCode + fromAccountCode + accountMoneyCode + accountNameCode;
    }

    /**
     * Getter for a transaction's money.
     *
     * @return Amount of money involved in the transaction.
     */
    public int getMoney() {
        return money;
    }

    /**
     * Helper function to validate an account number.
     *
     * @param input Account number to validate.
     * @return AccountNo object of a valid account number. Returns null otherwise.
     */
    private static AccountNo validateAccountNoInput(String input) {
        AccountNo accountNo = null;

        try {
            accountNo = new AccountNo(input);
        } catch (Exception e) {
            System.out.println("Invalid account number");
        }

        return accountNo;
    }

    /**
     * Helper function to validate an account name.
     *
     * @param input Account name to validate
     * @return true if the account name is valid, false otherwise.
     */
    public static boolean validateAccountName(String input) {
        boolean isAccountNameValid = false;

        if (input != null) {
            if (input.matches("([a-zA-Z]+\\s?[a-zA-Z]+)*") && input.length() > 3 && input.length() < 30) {
                isAccountNameValid = true;
            } else {
                System.out.println("Invalid account name");
            }
        }

        return isAccountNameValid;
    }
}
