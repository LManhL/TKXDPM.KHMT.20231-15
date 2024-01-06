package views.screen.ordermanagement;

import entity.order.Order;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import utils.Utils;
import views.screen.FXMLScreenHandler;

import java.io.IOException;
import java.sql.SQLException;

public class ItemOrderScreenHandler extends FXMLScreenHandler {

    @FXML
    private Label email;

    @FXML
    private Label address;

    @FXML
    private Label phone;

    @FXML
    private Button detail;

    @FXML
    private Label stt;

    @FXML
    private Label shippingfee;

    @FXML
    private AnchorPane orderItem;

    @FXML
    private Label state;


    public ItemOrderScreenHandler(String screenPath, Order order, int index, OnClickItemOrder onClickItemOrder ) throws IOException {
        super(screenPath);
        setOrderInformation(order, index);
        detail.setOnMouseClicked(mouseEvent -> {
            try {
                onClickItemOrder.goToOrderDetail(order.getId());
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void setOrderInformation(Order order, int index){
        this.email.setText(order.getDeliveryInfo().get("email"));
        this.phone.setText(order.getDeliveryInfo().get("phone"));
        this.address.setText(order.getDeliveryInfo().get("address"));
        this.stt.setText(String.valueOf(index));
        this.shippingfee.setText(Utils.getCurrencyFormat(order.getShippingFees()));
        this.state.setText(order.getStateString());
    }
    interface OnClickItemOrder {
        public void goToOrderDetail(int id) throws IOException, SQLException;
    }
}
