package entity.media;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import entity.db.AIMSDB;
import utils.Utils;

/**
 * The general media class, for another media it can be done by inheriting this class
 *
 * @author nguyenlm
 */
public class Media {

    private static Logger LOGGER = Utils.getLogger(Media.class.getName());

    protected Statement stm;
    protected int id;
    protected String title;
    protected String category;
    protected int value; // the real price of product (eg: 450)
    protected int price; // the price which will be displayed on browser (eg: 500)
    protected int quantity;
    protected float weight;
    protected String type;
    protected String imageURL;

    public Media() throws SQLException {
        stm = AIMSDB.getConnection().createStatement();
    }

    public Media(int id, String title, String category, int price, int quantity, String type) throws SQLException {
        this.id = id;
        this.title = title;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.type = type;
        //stm = AIMSDB.getConnection().createStatement();
    }

    public Media (int id, String title, String category, int price, int value,
                  int quantity, float weight, String type, String imageURL) throws SQLException{
        this.id = id;
        this.title = title;
        this.category = category;
        this.price = price;
        this.value = value;
        this.quantity = quantity;
        this.weight = weight;
        this.type = type;
        this.imageURL = imageURL;
    }

    public int getQuantity() throws SQLException {
        int updated_quantity = getMediaById(id).quantity;
        this.quantity = updated_quantity;
        return updated_quantity;
    }

    /* Các hàm getAllMedia(),getMediaById(),getAllMedia(), updateMediaFieldById() vi phạm nguyên tắc OCP.
     * Bởi vì khi Sqlite thay đổi cách tổ chức dữ liệu, hoặc khi muốn lấy entity khác như book hay cd, hoặc là cần sửa đổi hoặc thêm mới các hàm get, update, delete
     * thì lại cần phải sửa dổi code ở trong class Media.
     * Cách sửa lại:
     *  - Cách 1: Định nghĩa một interface có các hàm get, update, delete,... để đại diện cho quy trình lấy dữ liệu từ cơ sở dữ liệu mà không cần biết chi tiết về
     * cách nó được thực hiện. Sau đó implement nó ở trong các class con.
     *  - Cách 2: Chuyển Media thành abtract class sau đó để các hàm tương tác với Database là các abtract function dể lớp con override lại
     */
    
    public List<Media> searchMedia(String title) throws SQLException{
    	String sql = "SELECT * FROM Media where title like '%" + title + "%';";
        Statement stm = AIMSDB.getConnection().createStatement();
        ResultSet res = stm.executeQuery(sql);
        List<Media> list = new ArrayList<Media>();
        while (res.next()) {

            Media m = new Media()
                    .setId(res.getInt("id"))
                    .setTitle(res.getString("title"))
                    .setQuantity(res.getInt("quantity"))
                    .setCategory(res.getString("category"))
                    .setMediaURL(res.getString("imageUrl"))
                    .setPrice(res.getInt("price"))
                    .setType(res.getString("type"))
                    .setWeight(res.getFloat("weight"));
            list.add(m);
        }
        return list;
    }

    public Media getMediaById(int id) throws SQLException {
        String sql = "SELECT * FROM Media ;";
        Statement stm = AIMSDB.getConnection().createStatement();
        ResultSet res = stm.executeQuery(sql);
        if (res.next()) {

            return new Media()
                    .setId(res.getInt("id"))
                    .setTitle(res.getString("title"))
                    .setQuantity(res.getInt("quantity"))
                    .setCategory(res.getString("category"))
                    .setMediaURL(res.getString("imageUrl"))
                    .setPrice(res.getInt("price"))
                    .setType(res.getString("type"));
        }
        return null;
    }

    public List getAllMedia() throws SQLException {
        Statement stm = AIMSDB.getConnection().createStatement();
        ResultSet res = stm.executeQuery("select * from Media");
        ArrayList medium = new ArrayList<>();
        while (res.next()) {
            Media media = new Media()
                    .setId(res.getInt("id"))
                    .setTitle(res.getString("title"))
                    .setQuantity(res.getInt("quantity"))
                    .setCategory(res.getString("category"))
                    .setMediaURL(res.getString("imageUrl"))
                    .setPrice(res.getInt("price"))
                    .setType(res.getString("type"))
                    .setWeight(res.getFloat("weight"));
            medium.add(media);
        }
        return medium;
    }

    public void createMedia() throws SQLException {
        // String title, String category, int price, int quantity, String type
        StringBuilder queryValues = new StringBuilder();
        queryValues.append("(")
                .append(title).append(",")
                .append(category).append(",")
                .append(price).append(",")
                .append(quantity).append(",")
                .append(type).append(")");
        String sql = "INSERT INTO aims.Media "
                + "(title, category, price, quantity, type)"
                + "VALUES "
                + queryValues.toString() + ";";
        Statement stm = AIMSDB.getConnection().createStatement();
        ResultSet res = stm.executeQuery(sql);
    }

    public void updateMediaFieldById(String tbname, int id, String field, Object value) throws SQLException {
        Statement stm = AIMSDB.getConnection().createStatement();
        if (value instanceof String) {
            value = "\"" + value + "\"";
        }
        stm.executeUpdate(" update " + tbname + " set" + " "
                + field + "=" + value + " "
                + "where id=" + id + ";");
    }

    public void deleteMediaFieldById(int id) throws SQLException {
        Statement stm = AIMSDB.getConnection().createStatement();
        stm.executeUpdate("DELETE FROM " + "Media" + " WHERE id = " + id + ";");
    }

    // getter and setter 
    public int getId() {
        return this.id;
    }

    public Media setId(int id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public Media setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getCategory() {
        return this.category;
    }

    public Media setCategory(String category) {
        this.category = category;
        return this;
    }

    public int getPrice() {
        return this.price;
    }

    public Media setPrice(int price) {
        this.price = price;
        return this;
    }

    public float getWeight() {
        return this.weight;
    }

    public Media setWeight(float weight) {
        this.weight = weight;
        return this;
    }

    public int getValue() {
        return this.value;
    }

    public Media setValue(int value) {
        this.value = value;
        return this;
    }

    public String getImageURL() {
        return this.imageURL;
    }

    public Media setMediaURL(String url) {
        this.imageURL = url;
        return this;
    }

    public Media setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public String getType() {
        return this.type;
    }

    public Media setType(String type) {
        this.type = type;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + id + "'" +
                ", title='" + title + "'" +
                ", category='" + category + "'" +
                ", price='" + price + "'" +
                ", quantity='" + quantity + "'" +
                ", type='" + type + "'" +
                ", imageURL='" + imageURL + "'" +
                "}";
    }

}
