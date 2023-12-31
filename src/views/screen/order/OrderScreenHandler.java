//import java.io.IOException;
//
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//import views.screen.BaseScreenHandler;
//
//public class OrderScreenHandler extends BaseScreenHandler {
//    private Stage stage;
//    private Scene scene;
//    private OrderController controller;
//
//    public OrderScreenHandler(Stage stage, String screenPath) throws IOException {
//        super(stage, screenPath);
//    }
//
//    public void show() throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("Order.fxml"));
//        Parent root = loader.load();
//        this.controller = loader.getController();
//        this.scene = new Scene(root);
//        this.stage.setScene(this.scene);
//        this.stage.show();
//    }
//
//    public OrderController getController() {
//        return this.controller;
//    }
//}