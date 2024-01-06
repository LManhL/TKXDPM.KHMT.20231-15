package views.screen.ordermanagement;

import controller.OrderManagementAdminController;
import entity.order.Order;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.Configs;
import utils.Utils;
import views.screen.BaseScreenHandler;
import views.screen.home.HomeScreenHandler;
import views.seller_screen.ManageProductScreenHandler;

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

    @FXML
    private ImageView pending;

    @FXML
    private ImageView delivering;

    @FXML
    private ImageView delivered;

    @FXML
    private ImageView declined;

    @FXML
    private ImageView home_img;

    @FXML
    private Label lbPending;

    @FXML
    private Label lbDelivering;

    @FXML
    private Label lbDelivered;

    @FXML
    private Label lbDeclined;

    private int state;



    private ArrayList<Order> orderList = new ArrayList<>();

    private final int itemsPerPage = 30;


    public OrderManagementAdminScreenHandler(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setBController(new OrderManagementAdminController());
        state = 0;
        refreshOpacity();
        pending.setStyle("-fx-opacity: 1;");
        lbPending.setStyle("-fx-opacity: 1;");
        initUI();
    }

    private void initUI() {
        pagination.currentPageIndexProperty().addListener((observable, oldValue, newValue) -> {
            updatePagination((Integer) newValue);
        });
        updatePagination(0);

        pending.setOnMouseClicked(mouseEvent -> {
            state = 0;
            updatePagination(0);
            refreshOpacity();
            pending.setStyle("-fx-opacity: 1;");
            lbPending.setStyle("-fx-opacity: 1;");
        });

        delivering.setOnMouseClicked(mouseEvent -> {
            state = 1;
            updatePagination(0);
            refreshOpacity();
            delivering.setStyle("-fx-opacity: 1;");
            lbDelivering.setStyle("-fx-opacity: 1;");
        });

        delivered.setOnMouseClicked(mouseEvent -> {
            state = 2;
            updatePagination(0);
            refreshOpacity();
            delivered.setStyle("-fx-opacity: 1;");
            lbDelivered.setStyle("-fx-opacity: 1;");
        });

        declined.setOnMouseClicked(mouseEvent -> {
            state = 3;
            updatePagination(0);
            refreshOpacity();
            declined.setStyle("-fx-opacity: 1;");
            lbDeclined.setStyle("-fx-opacity: 1;");
        });

        home_img.setOnMouseClicked(mouseEvent -> {
            ManageProductScreenHandler manageProductScreenHandler = null;
            try {
                manageProductScreenHandler = new ManageProductScreenHandler(stage, Configs.SELLER_HOMEPAGE_PATH);
            } catch (IOException e) {
                e.printStackTrace();
            }
            manageProductScreenHandler.show();
        });
    }

    public OrderManagementAdminController getBController() {
        return (OrderManagementAdminController) super.getBController();
    }

    @Override
    public void goToOrderDetail(int id) throws IOException, SQLException {
        OrderDetailScreenHandler orderDetailScreenHandler = new OrderDetailScreenHandler(this.stage, Configs.ORDER_DETAIL_ADMIN_PATH, id);
        orderDetailScreenHandler.show();
    }

    private void refreshOpacity(){
        pending.setStyle("-fx-opacity: 0.5;");
        lbPending.setStyle("-fx-opacity: 0.5;");
        delivering.setStyle("-fx-opacity: 0.5;");
        lbDelivering.setStyle("-fx-opacity: 0.5;");
        delivered.setStyle("-fx-opacity: 0.5;");
        lbDelivered.setStyle("-fx-opacity: 0.5;");
        declined.setStyle("-fx-opacity: 0.5;");
        lbDeclined.setStyle("-fx-opacity: 0.5;");
    }

    private void updatePagination(int pageIndex) {
        vboxItems.getChildren().clear();
        try {
            ArrayList<Order> orderArrayList = getBController().getOrderByPage(pageIndex, itemsPerPage, state);
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
        displayOrderList();
    }
    private void displayOrderList(){
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
