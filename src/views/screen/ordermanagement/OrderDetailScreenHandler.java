package views.screen.ordermanagement;

import common.exception.ProcessInvoiceException;
import controller.OrderManagementAdminController;
import entity.order.Order;
import entity.order.OrderMedia;
import entity.payment.RefundTransaction;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.Configs;
import utils.Utils;
import views.screen.BaseScreenHandler;
import views.screen.invoice.MediaInvoiceScreenHandler;

import java.io.IOException;
import java.sql.SQLException;

public class OrderDetailScreenHandler extends BaseScreenHandler {

    @FXML
    private Label name;

    @FXML
    private Label email;

    @FXML
    private Label phone;

    @FXML
    private Label address;

    @FXML
    private Label shippingInstr;

    @FXML
    private Label shippingTime;

    @FXML
    private Label rushShippingInstr;

    @FXML
    private Label VAT;

    @FXML
    private Label noVAT;

    @FXML
    private Label state;

    @FXML
    private Label shippingFees;

    @FXML
    private Label total;

    @FXML
    private Label province;

    @FXML
    private Button confirm;

    @FXML
    private Button decline;

    @FXML
    private VBox vboxItems;

    @FXML
    private ImageView back;


    public OrderDetailScreenHandler(Stage stage, String screenPath, int orderId) throws IOException, SQLException {
        super(stage, screenPath);
        setBController(new OrderManagementAdminController());
        Order order = getBController().getOrderDetail(orderId);
        initUI(order);
    }
    public OrderManagementAdminController getBController() {
        return (OrderManagementAdminController) super.getBController();
    }
    private void initUI(Order order){
        this.email.setText(order.getDeliveryInfo().get("email"));
        this.phone.setText(order.getDeliveryInfo().get("phone"));
        this.address.setText(order.getDeliveryInfo().get("address"));
        this.shippingInstr.setText(order.getDeliveryInfo().get("instructions"));
        this.province.setText(order.getDeliveryInfo().get("province"));

        if(order.getDeliveryInfo().get("isRushShipping").equals("Yes")){
            this.rushShippingInstr.setText(order.getDeliveryInfo().get("rushShippingInstruction"));
            this.shippingTime.setText(order.getDeliveryInfo().get("time"));
        }
        else{
            this.rushShippingInstr.setVisible(false);
            this.shippingTime.setVisible(false);
        }

        this.state.setText(order.getStateString());
        this.VAT.setText(Utils.getCurrencyFormat(order.calculateTotalProductIncludeVAT()));
        this.noVAT.setText(Utils.getCurrencyFormat(order.calculateTotalProductNoVAT()));
        this.shippingFees.setText(Utils.getCurrencyFormat(order.calculateShippingFees()));
        this.total.setText(Utils.getCurrencyFormat(order.calculateTotalPrice()));

        order.getlstOrderMedia().forEach(orderMedia -> {
            try {
                MediaInvoiceScreenHandler mis = new MediaInvoiceScreenHandler(Configs.INVOICE_MEDIA_SCREEN_PATH);
                mis.setOrderMedia((OrderMedia) orderMedia);
                vboxItems.getChildren().add(mis.getContent());
            } catch (IOException | SQLException e) {
                System.err.println("errors: " + e.getMessage());
                throw new ProcessInvoiceException(e.getMessage());
            }
        });

        decline.setOnMouseClicked(mouseEvent -> {
            showYesNoAlert(order);
        });

        confirm.setOnMouseClicked(mouseEvent -> {
            try {
                if(order.getState() == Order.OrderState.WAITING){
                    getBController().acceptOrder(order.getId());
                    showAlert("Order confirmation successful");
                }
                else if(order.getState() == Order.OrderState.DELIVERING){
                    getBController().confirmDelivered(order.getId());
                    showAlert("Successful shipping confirmed");
                }
                goBack();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        back.setOnMouseClicked(mouseEvent -> {
            goBack();
        });

        switch (order.getState()) {
            case WAITING:
                confirm.setText("ACCEPT");
                break;
            case DELIVERING:
                confirm.setText("CONFIRM SHIPPING");
                break;
            case DECLINED:
            case DELIVERED:
                confirm.setVisible(false);
                decline.setVisible(false);
        }
    }
    private void goBack(){
        OrderManagementAdminScreenHandler orderManagementAdminScreenHandler = null;
        try {
            orderManagementAdminScreenHandler = new OrderManagementAdminScreenHandler(stage, Configs.ORDER_MANAGEMENT_ADMIN_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        orderManagementAdminScreenHandler.show();
    }
    private void showAlert(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alert");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void showYesNoAlert(Order order){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Order cancellation includes refund to user");
        alert.setContentText("Are you sure want to refund?");

        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        alert.showAndWait().ifPresent(response -> {
            if (response == buttonTypeYes) {
                try {
                    getBController().declineOrder(order.getId());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                try {
                    RefundTransaction refundTransaction = getBController().refund(order);
                    RefundResultScreenHandler refundResultScreenHandler = new RefundResultScreenHandler(this.stage, Configs.REFUND_RESULT_PATH, refundTransaction);
                    refundResultScreenHandler.show();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else if (response == buttonTypeNo) {
                System.out.println("User click no");
            }
        });
    }
}
