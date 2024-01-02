package entity.payment;

public class RefundTransaction {
    private String id;
    private String message;
    private String errorCode;
    private int amount;
    private String content;

    public RefundTransaction(String id, String message, String errorCode, int amount, String content) {
        this.id = id;
        this.message = message;
        this.errorCode = errorCode;
        this.amount = amount;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
