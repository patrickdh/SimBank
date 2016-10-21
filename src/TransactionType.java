/**
 * Created by stuartbourne on 2016-10-19.
 */
public enum TransactionType
{
    CREATE("CR"),
    DELETE("DL"),
    DEPOSIT("DE"),
    TRANSFER("TR"),
    WITHDRAW("WD");

    String transactionCode;

    TransactionType(String transactionCode)
    {
        this.transactionCode = transactionCode;
    }

    public String getTransactionCode()
    {
        return transactionCode;
    }
}
