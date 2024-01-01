package entity.order;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import entity.db.AIMSDB;
import utils.Configs;

public class Order {
    
    private int shippingFees;
    private List lstOrderMedia;
    private HashMap<String, String> deliveryInfo;
    private String email;
    private String address;
    private String phone;
    private int userID;
    private int shipping_fee;

    public Order() {
        this.lstOrderMedia = new ArrayList<>();
    }

    public Order(String email, String address, String phone, int userID, int shipping_fee) {
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.userID = userID;
        this.shipping_fee = shipping_fee;
        this.lstOrderMedia = new ArrayList<>();
    }

    public Order(List lstOrderMedia) {
        this.lstOrderMedia = lstOrderMedia;
    }

    public String getId() {
        return this.phone;
    }

    public String getPhone() {
        return this.phone;
    }

    public HashMap<String, Object> getOrderDetail() {
        HashMap<String, Object> orderDetail = new HashMap<>();
        orderDetail.put("email", this.email);
        orderDetail.put("address", this.address);
        orderDetail.put("phone", this.phone);
        orderDetail.put("userID", this.userID);
        orderDetail.put("shipping_fee", this.shipping_fee);
        orderDetail.put("orderMediaList", this.lstOrderMedia);
        return orderDetail;
    }    

    public void addOrderMedia(OrderMedia om){
        this.lstOrderMedia.add(om);
    }

    public void removeOrderMedia(OrderMedia om){
        this.lstOrderMedia.remove(om);
    }

    public List getlstOrderMedia() {
        return this.lstOrderMedia;
    }

    public void setlstOrderMedia(List lstOrderMedia) {
        this.lstOrderMedia = lstOrderMedia;
    }

    public void setShippingFees(int shippingFees) {
        // there should be a function to calculate the shipping fees
        // not place that function in the controller class
        this.shippingFees = shippingFees;
    }

    public int getShippingFees() {
        return shippingFees;
    }

    public HashMap getDeliveryInfo() {
        return deliveryInfo;
    }

    public void setDeliveryInfo(HashMap deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
    }

    public int getAmount(){
        double amount = 0;
        for (Object object : lstOrderMedia) {
            OrderMedia om = (OrderMedia) object;
            amount += om.getPrice();
        }
        return (int) (amount + (Configs.PERCENT_VAT/100)*amount);
    }

    public void createOrder(String email, String address, String phone, int userID, int shipping_fee) throws SQLException {
        String sql = "INSERT INTO aims.Order (email, address, phone, userID, shipping_fee) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pstmt = AIMSDB.getConnection().prepareStatement(sql);
        pstmt.setString(1, email);
        pstmt.setString(2, address);
        pstmt.setString(3, phone);
        pstmt.setInt(4, userID);
        pstmt.setInt(5, shipping_fee);
        pstmt.executeUpdate();
    }

    public List<Order> getAllOrders() throws SQLException {
        String sql = "SELECT * FROM aims.Order";
        Statement stmt = AIMSDB.getConnection().createStatement();
        ResultSet res = stmt.executeQuery(sql);
        List<Order> orders = new ArrayList<>();
        while(res.next()) {
            Order order = new Order(
                res.getString("email"), res.getString("address"),
                res.getString("phone"), res.getInt("userId"),
                res.getInt("shipping_fee"));
            orders.add(order);
        }
        return orders;
    }
    

    public void deleteOrder(String phone) throws SQLException {
        String sql = "DELETE FROM aims.Order WHERE phone = ?";
        PreparedStatement pstmt = AIMSDB.getConnection().prepareStatement(sql);
        pstmt.setString(1, phone);
        pstmt.executeUpdate();
    }

}
