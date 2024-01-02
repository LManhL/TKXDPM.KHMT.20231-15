package entity.order;

import entity.db.AIMSDB;
import entity.media.Media;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class OrderMedia {

    private Media media;
    private int price;
    private int quantity;

    public OrderMedia(Media media, int quantity, int price) {
        this.media = media;
        this.quantity = quantity;
        this.price = price;
    }

    public OrderMedia() {

    }

    @Override
    public String toString() {
        return "{" +
                "  media='" + media + "'" +
                ", quantity='" + quantity + "'" +
                ", price='" + price + "'" +
                "}";
    }

    public Media getMedia() {
        return this.media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int calculatePrice() {
        return getMedia().getPrice() * getQuantity();
    }

    public static ArrayList<OrderMedia> getAllOrderMediaByOrderId(int orderId) throws SQLException {
        String sql = "SELECT OrderMedia.*, Media.id, Media.title, Media.category, Media.imageUrl, Media.price as mediaPrice, Media.type "
                + "FROM OrderMedia "
                + "INNER JOIN Media ON OrderMedia.mediaID = Media.id "
                + "WHERE OrderMedia.orderID = " + orderId + ";";

        Statement stm = AIMSDB.getConnection().createStatement();
        ResultSet res = stm.executeQuery(sql);

        ArrayList<OrderMedia> orderMediaArrayList = new ArrayList<>();

        while (res.next()) {
            OrderMedia orderMedia = new OrderMedia();

            Media media = new Media()
                    .setId(res.getInt("id"))
                    .setTitle(res.getString("title"))
                    .setCategory(res.getString("category"))
                    .setMediaURL(res.getString("imageUrl"))
                    .setPrice(res.getInt("mediaPrice"))
                    .setType(res.getString("type"));
            int quantity = res.getInt("quantity");
            int price = res.getInt("price");

            orderMedia.setMedia(media);
            orderMedia.setPrice(price);
            orderMedia.setQuantity(quantity);

            orderMediaArrayList.add(orderMedia);
        }
        return orderMediaArrayList;
    }

    public static void createOrderMedia(OrderMedia orderMedia, int orderId) throws SQLException {
        try {
            String sql = "INSERT INTO OrderMedia (mediaID, orderID, price, quantity) VALUES (?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = AIMSDB.getConnection().prepareStatement(sql)) {
                preparedStatement.setInt(1, orderMedia.getMedia().getId());
                preparedStatement.setInt(2, orderId);
                preparedStatement.setDouble(3, orderMedia.getPrice());
                preparedStatement.setInt(4, orderMedia.getQuantity());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
