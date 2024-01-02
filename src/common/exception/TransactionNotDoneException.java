package common.exception;

public class TransactionNotDoneException extends PaymentException {
    public TransactionNotDoneException() {
        super("ERROR: Giao dịch chưa hoàn tất!");
    }
}
