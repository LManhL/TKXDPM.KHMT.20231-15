package views.screen.ordermanagement;

import controller.OrderManagementAdminController;
import entity.order.Order;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Pagination;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.Configs;
import utils.Utils;
import views.screen.BaseScreenHandler;
import views.screen.home.HomeScreenHandler;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class OrderManagementAdminScreenHandler extends BaseScreenHandler implements Initializable, ItemOrderScreenHandler.OnClickItemOrder {

    public static Logger LOGGER = Utils.getLogger(HomeScreenHandler.class.getName());

    @FXML
    private VBox vboxItems;

    @FXML
    private Pagination pagination;

    private ArrayList<Order> orderList = new ArrayList<>();

    private final int itemsPerPage = 30;


    public OrderManagementAdminScreenHandler(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setBController(new OrderManagementAdminController());
        initUI();
    }

    private void initUI() {
        pagination.currentPageIndexProperty().addListener((observable, oldValue, newValue) -> {
            updatePagination((Integer) newValue);
        });
        updatePagination(0);
    }

    public OrderManagementAdminController getBController() {
        return (OrderManagementAdminController) super.getBController();
    }

    @Override
    public void onClick(int id) throws IOException, SQLException {
        OrderDetailScreenHandler orderDetailScreenHandler = new OrderDetailScreenHandler(this.stage, Configs.ORDER_DETAIL_ADMIN_PATH, id);
        orderDetailScreenHandler.show();
    }

    private void updatePagination(int pageIndex) {
        vboxItems.getChildren().clear();
        try {
            ArrayList<Order> orderArrayList = getBController().getOrderByPage(pageIndex, itemsPerPage);
            if(orderList != null){
                orderList.clear();
            }
            else orderList = new ArrayList<>();
            orderList.addAll(orderArrayList);
            System.out.println(orderList.size());
        } catch (SQLException e) {
            LOGGER.info("Errors occured: " + e.getMessage());
            e.printStackTrace();
        }

        for (int i = 0; i < orderList.size(); i++) {
            try {
                ItemOrderScreenHandler itemOrderScreenHandler = new ItemOrderScreenHandler(Configs.ITEM_ORDER_PATH, orderList.get(i), i + 1, this);
                vboxItems.getChildren().add(itemOrderScreenHandler.getContent());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
