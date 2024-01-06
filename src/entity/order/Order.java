package entity.order;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import entity.db.AIMSDB;
import utils.Configs;

public class Order {

    private int id;
    private int shippingFees;
    private List lstOrderMedia;
    private HashMap<String, String> deliveryInfo;
    private OrderState state;

    public Order() {
        this.lstOrderMedia = new ArrayList<>();
    }

    public Order(OrderDTO orderDTO) {
        this.id = orderDTO.getId();
        this.shippingFees = orderDTO.getShipping_fee();
        this.deliveryInfo = new HashMap<>();
        this.lstOrderMedia = new ArrayList();
        deliveryInfo.put("email", orderDTO.getEmail());
        deliveryInfo.put("phone", orderDTO.getPhone());
        deliveryInfo.put("address", orderDTO.getAddress());
        deliveryInfo.put("province", orderDTO.getProvince());
        deliveryInfo.put("time", orderDTO.getTime());
        if (orderDTO.getIs_rush_shipping() == 1) {
            deliveryInfo.put("isRushShipping", "Yes");
        } else {
            deliveryInfo.put("isRushShipping", "No");
        }
        deliveryInfo.put("rushShippingInstruction", orderDTO.getRush_shipping_instruction());
        deliveryInfo.put("instructions", orderDTO.getShipping_instruction());
        switch (orderDTO.getState()) {
            case 0:
                this.state = OrderState.WAITING;
                break;
            case 1:
                this.state = OrderState.DELIVERING;
                break;
            case 2:
                this.state = OrderState.DELIVERED;
                break;
            case 3:
                this.state = OrderState.DECLINED;
                break;
        }
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return getDeliveryInfo().get("phone");
    }

    public Order(List lstOrderMedia) {
        this.lstOrderMedia = lstOrderMedia;
    }

    public void addOrderMedia(OrderMedia om) {
        this.lstOrderMedia.add(om);
    }

    public void removeOrderMedia(OrderMedia om) {
        this.lstOrderMedia.remove(om);
    }


    public List<OrderMedia> getlstOrderMedia() {
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

    public HashMap<String, String> getDeliveryInfo() {
        return deliveryInfo;
    }

    public void setDeliveryInfo(HashMap deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
    }

    public int getAmount() {
        double amount = 0;
        for (Object object : lstOrderMedia) {
            OrderMedia om = (OrderMedia) object;
            amount += om.getPrice();
        }
        return (int) (amount + (Configs.PERCENT_VAT / 100) * amount);
    }

    public static ArrayList<Order> getOrdersByPage(int startRow, int pageSize, int state) throws SQLException {
        String sql = "SELECT * FROM `Order` WHERE state = ? LIMIT ?, ?";

        Connection connection = AIMSDB.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, state);
        preparedStatement.setInt(2, startRow); // startRow là giá trị bắt đầu của trang
        preparedStatement.setInt(3, pageSize); // pageSize là kích thước của mỗi trang

        ResultSet resultSet = preparedStatement.executeQuery();

        ArrayList<OrderDTO> orderDTOArrayList = new ArrayList<>();
        while (resultSet.next()) {
            OrderDTO orderDTO = new OrderDTO(
                    resultSet.getInt("id"),
                    resultSet.getString("email"),
                    resultSet.getString("address"),
                    resultSet.getString("phone"),
                    resultSet.getInt("userID"),
                    resultSet.getInt("shipping_fee"),
                    resultSet.getInt("state"),
                    resultSet.getString("province"),
                    resultSet.getString("time"),
                    resultSet.getString("shipping_instruction"),
                    resultSet.getString("rush_shipping_instruction"),
                    resultSet.getInt("is_rush_shipping"));
            orderDTOArrayList.add(orderDTO);
        }

        ArrayList<Order> orderArrayList = new ArrayList<>();
        for (OrderDTO orderDTO : orderDTOArrayList) {
            orderArrayList.add(new Order(orderDTO));
        }
        return orderArrayList;
    }

    public static ArrayList<Order> getAllOrders() throws SQLException {
        String sql = "SELECT * FROM `Order`";

        Connection connection = AIMSDB.getConnection();
        ResultSet resultSet = connection.createStatement().executeQuery(sql);

        ArrayList<OrderDTO> orderDTOArrayList = new ArrayList<>();
        while (resultSet.next()) {
            OrderDTO orderDTO = new OrderDTO(
                    resultSet.getInt("id"),
                    resultSet.getString("email"),
                    resultSet.getString("address"),
                    resultSet.getString("phone"),
                    resultSet.getInt("userID"),
                    resultSet.getInt("shipping_fee"),
                    resultSet.getInt("state"),
                    resultSet.getString("province"),
                    resultSet.getString("time"),
                    resultSet.getString("shipping_instruction"),
                    resultSet.getString("rush_shipping_instruction"),
                    resultSet.getInt("is_rush_shipping"));
            orderDTOArrayList.add(orderDTO);
        }

        ArrayList<Order> orderArrayList = new ArrayList<>();
        for (OrderDTO orderDTO : orderDTOArrayList) {
            orderArrayList.add(new Order(orderDTO));
        }
        return orderArrayList;
    }

    public static Order getOrderById(int id) throws SQLException {
        String sql = "SELECT * FROM `Order` WHERE id = " + id;
        Statement stm = AIMSDB.getConnection().createStatement();
        ResultSet res = stm.executeQuery(sql);
        ArrayList<OrderMedia> orderMediaArrayList = OrderMedia.getAllOrderMediaByOrderId(id);
        if (res.next()) {
            OrderDTO orderDTO = new OrderDTO(res.getInt("id"),
                    res.getString("email"),
                    res.getString("address"),
                    res.getString("phone"),
                    res.getInt("userID"),
                    res.getInt("shipping_fee"),
                    res.getInt("state"),
                    res.getString("province"),
                    res.getString("time"),
                    res.getString("shipping_instruction"),
                    res.getString("rush_shipping_instruction"),
                    res.getInt("is_rush_shipping"));
            Order order = new Order(orderDTO);
            order.setlstOrderMedia(orderMediaArrayList);
            return order;
        }
        return null;
    }

    public static int createOrder(Order order) throws SQLException {
        String sql = "INSERT INTO `Order` (email, address, phone, userID, shipping_fee, state, province, time, shipping_instruction, rush_shipping_instruction, is_rush_shipping) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        String email = order.getDeliveryInfo().get("email");
        String address = order.getDeliveryInfo().get("address");
        String phone = order.getDeliveryInfo().get("phone");
        int userID = 1;
        double shippingFee = order.calculateShippingFees();
        int state = 0;
        String province = order.getDeliveryInfo().get("province");
        String time = order.getDeliveryInfo().get("time");
        int isRushShipping = 0;
        if (order.deliveryInfo.get("isRushShipping").equals("Yes")) {
            isRushShipping = 1;
        }
        String shippingInstruction = order.getDeliveryInfo().get("instructions");
        String rushShippingInstruction = order.getDeliveryInfo().get("rushShippingInstruction");
        Connection connection = AIMSDB.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        // Đặt giá trị cho các tham số
        preparedStatement.setString(1, email);
        preparedStatement.setString(2, address);
        preparedStatement.setString(3, phone);
        preparedStatement.setInt(4, userID);
        preparedStatement.setDouble(5, shippingFee);
        preparedStatement.setInt(6, state);
        preparedStatement.setString(7, province);
        preparedStatement.setString(8, time);
        preparedStatement.setString(9, shippingInstruction);
        preparedStatement.setString(10, rushShippingInstruction);
        preparedStatement.setInt(11, isRushShipping);

        int rowsAffected = preparedStatement.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("Bản ghi đã được thêm thành công!");
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int orderID = generatedKeys.getInt(1);

                    // Gọi phương thức createOrderMedia với orderID
                    for (OrderMedia orderMedia : order.getlstOrderMedia()) {
                        OrderMedia.createOrderMedia(orderMedia, orderID);
                    }
                    System.out.println("Đã thêm Order và OrderMedia thành công!");
                    return orderID;
                }
            }
        } else {
            System.out.println("Thêm bản ghi không thành công!");
            return -1;
        }
        return -1;
    }

    public static void acceptOrderById(int orderId) throws SQLException {
        Statement stm = AIMSDB.getConnection().createStatement();
        stm.executeUpdate("UPDATE `Order` SET" + " state = " + 1
                + " WHERE id=" + orderId);
    }

    public static void declineOrderById(int orderId) throws SQLException {
        Statement stm = AIMSDB.getConnection().createStatement();
        stm.executeUpdate("UPDATE `Order` SET" + " state = " + 3
                + " WHERE id=" + orderId);
    }

    public static void confirmDeliveredOrderById(int orderId) throws SQLException {
        Statement stm = AIMSDB.getConnection().createStatement();
        stm.executeUpdate("UPDATE `Order` SET" + " state = " + 2
                + " WHERE id=" + orderId);
    }

    public static void deleteOrderById(int orderId) throws SQLException {
        String sql = "DELETE FROM `Order` WHERE id = ?";
        Connection connection = AIMSDB.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, orderId);
        preparedStatement.executeUpdate();
    }

    public String getStateString() {
        switch (getState()) {
            case WAITING:
                return "Pending approval";
            case DELIVERING:
                return "Delivering";
            case DECLINED:
                return "Refuse approval";
            case DELIVERED:
                return "Shipped successfully";
        }
        return "";
    }

    public int calculateShippingFees() {

        if (calculateTotalProductIncludeVAT() > 100000) {
            return 0;
        }

        double baseCost = 0;
        double baseWeight = 0;
        double additionalCostPerHalfKg = 0;

        if (isUrban()) {
            baseCost = 22000;
            baseWeight = 3;
        } else {
            baseCost = 30000;
            baseWeight = 0.5;
        }
        additionalCostPerHalfKg = 2500;

        double rushShippingCost = 0;

        if (isRushShipping()) rushShippingCost = 10000 * getNumberOfRushShippingProduct();

        double regularShippingCost = 0;

        if (getMaxWeight() <= baseWeight) {
            regularShippingCost = baseCost;
        } else {
            regularShippingCost = baseCost + Math.ceil((getMaxWeight() - baseWeight) * 2) * additionalCostPerHalfKg;
        }
        setShippingFees((int) (rushShippingCost + regularShippingCost));
        return (int) (rushShippingCost + regularShippingCost);
    }

    public boolean isRushShipping() {
        String isRushShipping = deliveryInfo.get("isRushShipping");
        return isRushShipping.equals("Yes");
    }

    public boolean isUrban() {
        String address = deliveryInfo.get("address");
        return address.toLowerCase().contains("hà nội") || address.toLowerCase().contains("hồ chí minh");
    }

    public int calculateTotalProductIncludeVAT() {
        double amount = 0;
        for (Object object : getlstOrderMedia()) {
            OrderMedia om = (OrderMedia) object;
            amount += om.getMedia().getPrice() * om.getQuantity() ;
        }
        return (int) (amount + (Configs.PERCENT_VAT / 100) * amount);
    }

    public int calculateTotalProductNoVAT() {
        double amount = 0;
        for (Object object : getlstOrderMedia()) {
            OrderMedia om = (OrderMedia) object;
            amount += om.getMedia().getPrice() * om.getQuantity() ;
        }
        return (int) (amount);
    }

    public int getNumberOfRushShippingProduct() {
        return getlstOrderMedia().size();
    }

    public double getMaxWeight() {
        int max = 0;
        for (Object object : getlstOrderMedia()) {
            OrderMedia om = (OrderMedia) object;
            if (om.getMedia().getPrice() > max) max = om.getMedia().getPrice();
        }
        return max / 30000.0;
    }

    public int calculateTotalPrice() {
        return calculateTotalProductIncludeVAT() + calculateShippingFees();
    }

    public enum OrderState {
        WAITING,
        DELIVERING,
        DECLINED,
        DELIVERED
    }
}
