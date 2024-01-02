package common.exception;

public class InvalidRefundRequestDataException extends RefundException{
    public InvalidRefundRequestDataException() {
        super("ERROR: INVALID REFUND REQUEST DATA");
    }
}
