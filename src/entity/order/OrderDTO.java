package entity.order;

public class OrderDTO {
    private int id;
    private String email;
    private String address;
    private String phone;
    private int userID;
    private int shipping_fee;
    private int state;
    private String province;
    private String time;
    private String shipping_instruction;
    private String rush_shipping_instruction;
    private int is_rush_shipping;

    public OrderDTO(int id, String email, String address, String phone, int userID,
                    int shipping_fee, int state, String province, String time, String shipping_instruction,
                    String rush_shipping_instruction, int is_rush_shipping) {
        this.id = id;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.userID = userID;
        this.shipping_fee = shipping_fee;
        this.state = state;
        this.province = province;
        this.time = time;
        this.shipping_instruction = shipping_instruction;
        this.rush_shipping_instruction = rush_shipping_instruction;
        this.is_rush_shipping = is_rush_shipping;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getShipping_instruction() {
        return shipping_instruction;
    }

    public void setShipping_instruction(String shipping_instruction) {
        this.shipping_instruction = shipping_instruction;
    }

    public String getRush_shipping_instruction() {
        return rush_shipping_instruction;
    }

    public void setRush_shipping_instruction(String rush_shipping_instruction) {
        this.rush_shipping_instruction = rush_shipping_instruction;
    }

    public int getIs_rush_shipping() {
        return is_rush_shipping;
    }

    public void setIs_rush_shipping(int is_rush_shipping) {
        this.is_rush_shipping = is_rush_shipping;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getShipping_fee() {
        return shipping_fee;
    }

    public void setShipping_fee(int shipping_fee) {
        this.shipping_fee = shipping_fee;
    }
}
