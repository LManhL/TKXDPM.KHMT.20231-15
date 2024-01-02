package controller;

import entity.order.Order;
import entity.payment.PaymentTransaction;
import entity.payment.RefundTransaction;
import subsystem.IVNPaySubsystem;
import subsystem.VNPaySubsystemSubsystem;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderManagementAdminController extends BaseController {

    private IVNPaySubsystem vnPay;

    public OrderManagementAdminController() {
        this.vnPay = new VNPaySubsystemSubsystem();
    }

    public ArrayList<Order> getOrderByPage(int curPage, int pageSize) throws SQLException {
        int start = curPage * pageSize;
        return Order.getOrdersByPage(start, pageSize);
    }

    public Order getOrderDetail(int id) throws SQLException {
        return Order.getOrderById(id);
    }

    public void acceptOrder(int orderId) throws SQLException {
        Order.acceptOrderById(orderId);
    }

    public void declineOrder(int orderId) throws SQLException {
        Order.declineOrderById(orderId);
    }

    public void confirmDelivered(int orderId) throws SQLException {
        Order.confirmDeliveredOrderById(orderId);
    }

    public RefundTransaction refund(Order order) throws IOException, SQLException {
        PaymentTransaction paymentTransaction = PaymentTransaction.getPaymentTransactionByOrderId(order.getId());
        return this.vnPay.refund(paymentTransaction);
    }
}
