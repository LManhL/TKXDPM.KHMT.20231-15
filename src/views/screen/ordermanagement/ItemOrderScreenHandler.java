package views.screen.ordermanagement;

import entity.order.Order;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import views.screen.FXMLScreenHandler;

import java.io.IOException;

public class ItemOrderScreenHandler extends FXMLScreenHandler {

    @FXML
    private Label totalPrice;

    @FXML
    private Label email;

    @FXML
    private Label address;

    @FXML
    private Label phone;

    @FXML
    private Label isRushShipping;

    @FXML
    private Button detail;

    @FXML
    private Label stt;


    public ItemOrderScreenHandler(String screenPath) throws IOException {
        super(screenPath);
    }

    public void setOrder(Order order){

    }
    private void setOrderInformation(){

    }
}
