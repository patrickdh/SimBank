/**
 * Transaction Type Enum.
 */
public enum TransactionType {
    CREATE("CR"),
    DELETE("DL"),
    DEPOSIT("DE"),
    TRANSFER("TR"),
    WITHDRAW("WD");

    private String transactionCode;

    TransactionType(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    /**
     * Returns the transaction code of the transaction type.
     *
     * @return transaction code string
     */
    public String getTransactionCode() {
        return transactionCode;
    }
}
