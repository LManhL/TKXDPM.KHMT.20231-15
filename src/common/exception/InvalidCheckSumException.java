package common.exception;

public class InvalidCheckSumException extends RefundException{
    public InvalidCheckSumException() {
        super("ERROR: INVALID CHECK SUM");
    }
}
