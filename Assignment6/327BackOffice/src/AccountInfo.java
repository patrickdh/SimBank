/**
 * Account information for an account number. This class holds an account's balance and the account holder's name.
 */
public class AccountInfo {

    // Balance of the account
    private int accountBalance;
    // Name of the account holder
    private String accountName;

    /**
     * Constructor for AccountInfo class
     *
     * @param accountBalance Balance of the account
     * @param accountName Name of the account holder
     */
    public AccountInfo(int accountBalance, String accountName){
        this.accountBalance = accountBalance;
        this.accountName = accountName;
    }

    /**
     * Getter for accountBalance.
     *
     * @return Account's balance
     */
    public int getAccountBalance(){
        return accountBalance;
    }


    /**
     * Getter for accountName.
     *
     * @return Account holder's name
     */
    public String getAccountName(){
        return accountName;
    }

    /**
     * Adjust the account's balance by a specified amount. Does not adjust if the resulting account balance is less
     * than 0.
     *
     * @param adjustAmount Amount to adjust the account's balance by.
     */
    public void adjustBalance(int adjustAmount){
        if (accountBalance + adjustAmount < 0) {
            System.out.println("Transaction cancelled: account balance is less than 0.");
        } else {

        }
        accountBalance += adjustAmount;
    }

    /**
     * Checks if an account's balance will be valid after applying a transaction's money amount.
     *
     * @param adjustAmount Amount to adjust the account's balance by.
     * @return true if the account balance will be greater than 0, false otherwise
     */
    public boolean isTransactionAllowed(int adjustAmount) {
        return accountBalance + adjustAmount >= 0;
    }

}
