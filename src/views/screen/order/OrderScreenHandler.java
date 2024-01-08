package views.screen.order;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import controller.ViewOrderController;
import entity.order.Order;
import entity.payment.RefundTransaction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import utils.Configs;
import utils.Utils;
import views.screen.BaseScreenHandler;
import views.screen.ordermanagement.RefundResultScreenHandler;
import views.screen.popup.PopupScreen;

public class OrderScreenHandler extends BaseScreenHandler {

    private ViewOrderController controller;

    @FXML
    private ListView<Order> orderList;

    @FXML
    private ImageView aimsImage;

    @FXML
    private Button deleteOrder;

    public OrderScreenHandler(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);
    }

    @FXML
    public void initialize() throws SQLException, IOException {
        this.controller = new ViewOrderController();
        deleteOrder.setOnAction(e -> {
            Order selectedOrder = orderList.getSelectionModel().getSelectedItem();
            if (selectedOrder != null) {
                showConfirmationAlert(selectedOrder);
            } else {
                try {
                    PopupScreen.error("No item selected");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        // fix relative image path caused by fxml
        File file = new File("assets/images/Logo.png");
        Image im = new Image(file.toURI().toString());
        aimsImage.setImage(im);

        // on mouse clicked, we back to home
        aimsImage.setOnMouseClicked(e -> {
            homeScreenHandler.show();
        });
    }

    @Override
    public void show() {
        super.show();
        ListView<Order> orderListView = (ListView<Order>) this.content.lookup("#orderList");
        List<Order> orders = null;
        try {
            orders = controller.getAllOrders();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (orders != null) {
            ObservableList<Order> observableList = FXCollections.observableArrayList(orders);
            orderListView.setItems(observableList);
            orderListView.setCellFactory(param -> new ListCell<Order>() {
                @Override
                protected void updateItem(Order item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText("ID : " + item.getId() + " - " +
                                " PHONE: " + item.getPhone() + " - " +
                                "Shipping fee: " + Utils.getCurrencyFormat(item.calculateShippingFees()) + " - " +
                                "State: " + item.getStateString());
                    }
                }
            });
        } else {
            orderListView.getItems().add(new Order());
        }
    }

    private void showConfirmationAlert(Order order) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure want to cancel this order?");

        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        alert.showAndWait().ifPresent(response -> {
            if (response == buttonTypeYes) {
                controller.deleteOrder(order.getId());
                orderList.getItems().remove(order);
                try {
                    if(order.getState() == Order.OrderState.WAITING || order.getState() == Order.OrderState.DELIVERING){
                        RefundTransaction refundTransaction = controller.refund(order);
                        showResult("DELETE SUCCESSFULLY - Your money will be refunded as soon as possible");
                    }
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

    private void showResult(String message) throws IOException {
        PopupScreen.success(message);
    }
}
