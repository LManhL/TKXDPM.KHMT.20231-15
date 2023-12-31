package controller;

import java.sql.SQLException;
import java.util.List;

import entity.order.Order;

public class ViewOrderController extends BaseController {

    private Order order;

    public ViewOrderController() {
        this.order = new Order();
    }

    public List getAllOrders() throws SQLException {
      return this.order.getAllOrders();
    }

    // Method to create a new order
    public void createOrder(Order order) {
        try {
            this.order.createOrder("email@email.com", "Ha Noi", "0379334098", 1, 1000);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to delete an order
    public void deleteOrder(String phone) {
        try {
            order.deleteOrder(phone);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
