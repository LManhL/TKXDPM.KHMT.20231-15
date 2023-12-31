import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import controller.ViewOrderController;
import entity.order.Order;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import views.screen.BaseScreenHandler;

public class OrderScreenHandler extends BaseScreenHandler {

    private ViewOrderController controller;

    public OrderScreenHandler(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);
        this.controller = new ViewOrderController();
    }

    @Override
    public void show() throws SQLException {
        super.show();
        VBox vbox = new VBox();
        List<Order> orders = controller.getAllOrders();
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
