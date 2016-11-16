/**
 * Session Mode Enum.
 */
public enum SessionMode {
    ATM (100000, 100000),
    AGENT (99999999, 99999999);

    int maxWithdraw;
    int maxTransfer;

    SessionMode(int maxWithdraw, int maxTransfer)
    {
        this.maxWithdraw = maxWithdraw;
        this.maxTransfer = maxTransfer;
    }

    public int getMaxWithdraw()
    {
        return maxWithdraw;
    }

    public int getMaxTransfer()
    {
        return maxTransfer;
    }
}
