package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import entity.order.Order;
import entity.payment.PaymentTransaction;
import entity.payment.RefundTransaction;
import subsystem.IPaymentSubsystem;
import subsystem.PaymentSubsystem;
import subsystem.vnpay.VNPaySubsystemController;

public class ViewOrderController extends BaseController {

    private Order order;
    private IPaymentSubsystem vnPay;

    public ViewOrderController() {
        this.order = new Order();
        this.vnPay = new PaymentSubsystem(new VNPaySubsystemController());
    }

    public List getAllOrders() throws SQLException {
        return Order.getAllOrders();
    }

    public void deleteOrder(int id) {
        try {
            Order.deleteOrderById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public RefundTransaction refund(Order order) throws IOException, SQLException {
        PaymentTransaction paymentTransaction = PaymentTransaction.getPaymentTransactionByOrderId(order.getId());
        return this.vnPay.refund(paymentTransaction);
    }
}
