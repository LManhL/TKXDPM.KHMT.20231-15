package views.screen.order;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.text.html.ListView;

import controller.ViewOrderController;
import entity.order.Order;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import views.screen.BaseScreenHandler;

public class OrderScreenHandler extends BaseScreenHandler {

    private ViewOrderController controller;

    @FXML
    private ListView<Order> orderList;

    @FXML
    private Button deleteOrder;

    public OrderScreenHandler(Stage stage, String screenPath) throws IOException {
		super(stage, screenPath);
        this.controller = new ViewOrderController();
    }

    @FXML
    public void initialize() {
        deleteOrder.setOnAction(e -> {
            Order selectedOrder = orderList.getSelectionModel().getSelectedItem();
            if (selectedOrder != null) {
                try {
                    controller.deleteOrder(selectedOrder.getPhone());
                    orderList.getItems().remove(selectedOrder);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    @Override
    public void show() {
        super.show();
        VBox vbox = new VBox();
        List<Order> orders;
		try {
			orders = controller.getAllOrders();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        for (Order order : orders) {
            Button button = new Button("Order : " + order.getId());
            button.setOnAction(e -> {
                try {
                    controller.deleteOrder(order.getPhone());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            });
            vbox.getChildren().add(button);
        }
        this.content.getChildren().add(vbox);
    }
}
