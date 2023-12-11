package views.screen.ordermanagement;

import common.exception.ProcessInvoiceException;
import entity.order.Order;
import entity.order.OrderMedia;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.Configs;
import utils.Utils;
import views.screen.BaseScreenHandler;
import views.screen.home.HomeScreenHandler;
import views.screen.invoice.MediaInvoiceScreenHandler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class OrderManagementAdminHandler extends BaseScreenHandler {

    public static Logger LOGGER = Utils.getLogger(HomeScreenHandler.class.getName());

    @FXML
    private VBox vboxItems;

    private ArrayList<Order> orderList = new ArrayList<>();

    public OrderManagementAdminHandler(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);
        orderList.add(new Order());
        orderList.add(new Order());
        orderList.add(new Order());
        orderList.add(new Order());
        orderList.add(new Order());
        initUI();
    }
    private void initUI(){
        orderList.forEach(order -> {
            try {
                ItemOrderScreenHandler itemOrderScreenHandler = new ItemOrderScreenHandler(Configs.ITEM_ORDER_PATH);
                vboxItems.getChildren().add(itemOrderScreenHandler.getContent());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
