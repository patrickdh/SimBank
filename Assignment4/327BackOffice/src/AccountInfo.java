/**
 * Created by stuartbourne on 2016-11-15.
 */
public class AccountInfo {

    private int accountBalance;
    private String accountName;

    public AccountInfo(int accountBalance, String accountName){

        setAccountBalance(accountBalance);
        setAccountName(accountName);
    }


    public int getAccountBalance(){
        return accountBalance;
    }

    public String getAccountName(){
        return accountName;
    }


    private void setAccountBalance(int accountBalance){
        //perform checks
        this.accountBalance = accountBalance;
    }

    private void setAccountName(String accountName){
        //perform checks
        this.accountName = accountName;
    }

    public void adjustBalance(int balance){
        accountBalance += balance;
    }

}
