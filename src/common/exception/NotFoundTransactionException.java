package common.exception;

public class NotFoundTransactionException extends RefundException{
    public NotFoundTransactionException() {
        super("ERROR: NOT FOUND TRANSACTION");
    }
}
