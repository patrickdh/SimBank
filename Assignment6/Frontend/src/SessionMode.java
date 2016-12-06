/**
 * Session Mode Enum.
 */
public enum SessionMode {
    ATM (100000, 100000, 100000),
    AGENT (99999999, 99999999, 99999999);

    int maxWithdraw;
    int maxTransfer;
    int maxDeposit;

    SessionMode(int maxWithdraw, int maxTransfer, int maxDeposit) {
        this.maxWithdraw = maxWithdraw;
        this.maxTransfer = maxTransfer;
        this.maxDeposit = maxDeposit;
    }

    public int getMaxWithdraw() {
        return maxWithdraw;
    }

    public int getMaxTransfer() {
        return maxTransfer;
    }

    public int getMaxDeposit() {
        return maxDeposit;
    }
}
