package entity.payment;

import entity.db.AIMSDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentTransaction {
    private int orderId;
    private String errorCode;
    private String transactionId;
    private String transactionNum;
    private String transactionContent;
    private String createdAt;
    private String message;
    private int amount;

    public PaymentTransaction() {
        super();
    }

    public PaymentTransaction(int orderId, String transactionId, String transactionContent, int amount, String createdAt, String transactionNum) {
        super();
        this.orderId = orderId;
        this.transactionId = transactionId;
        this.transactionNum = transactionNum;
        this.transactionContent = transactionContent;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    public PaymentTransaction(int orderId, String errorCode, String transactionId, String transactionContent, int amount, String createdAt, String transactionNum) {
        super();
        this.orderId = orderId;
        this.errorCode = errorCode;
        this.transactionId = transactionId;
        this.transactionNum = transactionNum;
        this.transactionContent = transactionContent;
        this.amount = amount;
        this.createdAt = createdAt;
        if ("00".equals(getErrorCode())) {
            message = "Successful transaction";
        } else {
            message = "Transaction failed";
        }
    }

    public String getTransactionNum() {
        return transactionNum;
    }

    public void setTransactionNum(String transactionNum) {
        this.transactionNum = transactionNum;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getTransactionContent() {
        return transactionContent;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getMessage() {
        return message;
    }

    public int getAmount() {
        return amount;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public static void saveTransaction(PaymentTransaction transaction) throws SQLException {
        String sql = "INSERT INTO `Transaction` (orderID, createAt, content, transaction_id, transaction_num, amount) VALUES (?, ?, ?, ?, ?, ?)";
        Connection connection = AIMSDB.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setInt(1, transaction.getOrderId());
        preparedStatement.setString(2, transaction.getCreatedAt());
        preparedStatement.setString(3, transaction.getTransactionContent());
        preparedStatement.setString(4, transaction.getTransactionId());
        preparedStatement.setString(5, transaction.getTransactionNum());
        preparedStatement.setInt(6, transaction.getAmount());

        preparedStatement.executeUpdate();
    }

    public static PaymentTransaction getPaymentTransactionByOrderId(int orderId) throws SQLException {
        String sql = "SELECT * FROM `Transaction` WHERE orderID = ?";
        Connection connection = AIMSDB.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, orderId);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            int orderID = resultSet.getInt("orderID");
            String createdAt = resultSet.getString("createAt");
            String content = resultSet.getString("content");
            String transactionId = resultSet.getString("transaction_id");
            String transactionNum = resultSet.getString("transaction_num");
            int amount = resultSet.getInt("amount");
            // Tạo đối tượng PaymentTransaction và trả về
            return new PaymentTransaction(orderId, transactionId, content, amount, createdAt, transactionNum);
        } else {
            // Không tìm thấy giao dịch với orderId tương ứng
            return null;
        }
    }


}
