import java.security.InvalidParameterException;

/**
 * Account Number class for the SimBank system.
 */
public class AccountNo {

    private int accountNumber;

    public AccountNo(String accountNoString) {
        int accountNumber;

        try {
            accountNumber = Integer.parseInt(accountNoString);
        } catch (Exception e) {
            throw new InvalidParameterException("You have entered an invalid account number. Valid account numbers are between 1000000 and 9999999.");
        }
        if (accountNumber < 1000000 || accountNumber > 99999999) {
            throw new InvalidParameterException("You have entered an invalid account number. Valid account numbers are between 1000000 and 9999999.");
        }

        this.accountNumber = accountNumber;
    }

    private int getAccountNo() {
        return accountNumber;
    }

    @Override
    public String toString() {
        return Integer.toString(accountNumber);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!AccountNo.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final AccountNo other = (AccountNo) obj;
        if (this.accountNumber != other.getAccountNo()) {
            return false;
        }
        return true;
    }
}
