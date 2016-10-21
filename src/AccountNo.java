import java.security.InvalidParameterException;

/**
 * Created by stuartbourne on 2016-10-19.
 */
public class AccountNo {
    private int accountNo;

    public AccountNo(String accountNoString) {
        int accountNumber;
        try {
            accountNumber = Integer.parseInt(accountNoString);
            if (accountNumber < 1000000 && accountNumber > 99999999) {
                throw new InvalidParameterException();
            }
        } catch (Exception e) {
            throw new InvalidParameterException("You have entered an invalid account number. Valid account numbers are between 1000000 and 9999999.");
        }
        accountNo = accountNumber;
    }

    public String getAccountNoName() {
        return Integer.toString(accountNo);
    }
}
