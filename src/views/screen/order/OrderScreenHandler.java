package views.screen.order;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import controller.ViewOrderController;
import entity.order.Order;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import views.screen.BaseScreenHandler;
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
    public void initialize() throws SQLException {
        this.controller = new ViewOrderController();
        deleteOrder.setOnAction(e -> {
            Order selectedOrder = orderList.getSelectionModel().getSelectedItem();
            if (selectedOrder != null) {
                controller.deleteOrder(selectedOrder.getPhone());
				orderList.getItems().remove(selectedOrder);
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
        VBox vbox = new VBox();
        List<Order> orders = null;
		try {
			orders = controller.getAllOrders();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (orders == null) {
		    Label label = new Label("No order found");
		    vbox.getChildren().add(label);
		} else {
	        for (Order order : orders) {
	            Button button = new Button("Order : " + order.getId());
	            button.setOnAction(e -> {
	                controller.deleteOrder(order.getPhone());
	            });
	            vbox.getChildren().add(button);
	        }
	        this.content.getChildren().add(vbox);
		}
    }
}
