package views.screen.shipping;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;

import controller.PlaceOrderController;
import common.exception.InvalidDeliveryInfoException;
import entity.invoice.Invoice;
import entity.order.Order;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utils.Configs;
import views.screen.BaseScreenHandler;
import views.screen.invoice.InvoiceScreenHandler;
import views.screen.popup.PopupScreen;

public class ShippingScreenHandler extends BaseScreenHandler implements Initializable {

    @FXML
    private Label screenTitle;

    @FXML
    private TextField name;

    @FXML
    private TextField phone;

    @FXML
    private TextField address;

    @FXML
    private TextField instructions;

    @FXML
    private Label labelTime;

    @FXML
    private Label labelRushShippingInstr;

    @FXML
    private TextField time;

    @FXML
    private TextField email;

    @FXML
    private TextField rushShippingInstr;

    @FXML
    private RadioButton chooseShip;

    @FXML
    private ComboBox<String> province;

    private Order order;

    public ShippingScreenHandler(Stage stage, String screenPath, Order order) throws IOException {
        super(stage, screenPath);
        this.order = order;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        final BooleanProperty firstTime = new SimpleBooleanProperty(true); // Variable to store the focus on stage load
        name.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue && firstTime.get()) {
                content.requestFocus(); // Delegate the focus to container
                firstTime.setValue(false); // Variable value changed for future references
            }
        });
        this.province.getItems().addAll(Configs.PROVINCES);
        updateRushShippingView(false);

        chooseShip.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
                if (isNowSelected) {
                    updateRushShippingView(true);
                } else {
                    updateRushShippingView(false);
                }
            }
        });
    }

    private void updateRushShippingView(boolean check) {
        labelTime.setVisible(check);
        labelRushShippingInstr.setVisible(check);
        time.setVisible(check);
        rushShippingInstr.setVisible(check);
    }

    @FXML
    void submitDeliveryInfo(MouseEvent event) throws IOException, InterruptedException, SQLException {

        // add info to messages

        HashMap messages = new HashMap<>();
        messages.put("name", name.getText());
        messages.put("phone", phone.getText());
        messages.put("address", address.getText());
        messages.put("instructions", instructions.getText());
        if(province.getValue() != null){
            messages.put("province", province.getValue());
        }
        else notifyError("Empty province");
        messages.put("email", email.getText());

        if (chooseShip.isSelected()) {
            messages.put("isRushShipping", "Yes");
            messages.put("time", time.getText());
            messages.put("rushShippingInstruction", rushShippingInstr.getText());
        } else {
            messages.put("isRushShipping", "No");
        }
        if(!validateShippingInformation(messages)){
            return;
        }

        try {
            getBController().processDeliveryInfo(messages);
        } catch (InvalidDeliveryInfoException e) {
            throw new InvalidDeliveryInfoException(e.getMessage());
        }

        // calculate shipping fees
        int shippingFees = getBController().calculateShippingFee(order);
        order.setShippingFees(shippingFees);
        order.setDeliveryInfo(messages);

        // create invoice screen
        Invoice invoice = getBController().createInvoice(order);
        BaseScreenHandler InvoiceScreenHandler = new InvoiceScreenHandler(this.stage, Configs.INVOICE_SCREEN_PATH, invoice);
        InvoiceScreenHandler.setPreviousScreen(this);
        InvoiceScreenHandler.setHomeScreenHandler(homeScreenHandler);
        InvoiceScreenHandler.setScreenTitle("Invoice Screen");
        InvoiceScreenHandler.setBController(getBController());
        InvoiceScreenHandler.show();
    }

    public PlaceOrderController getBController() {
        return (PlaceOrderController) super.getBController();
    }

    public void notifyError(String error) throws IOException {
        PopupScreen.error(error);
    }
    private boolean validateShippingInformation(HashMap<String,String> deliveryInfor) throws IOException {
        String res = getBController().validateDeliveryInfo(deliveryInfor);
        if(res.equals("Valid")){
            return true;
        }
        notifyError(res);
        return false;
    }

}
