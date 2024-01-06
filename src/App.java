import java.io.IOException;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import utils.Configs;
import views.screen.ChooseRoleScreenHandler;
import views.screen.home.HomeScreenHandler;
import views.screen.ordermanagement.OrderManagementAdminScreenHandler;
import views.seller_screen.ManageProductScreenHandler;

public class App extends Application {

	@FXML
	ImageView logo;

	@Override
	public void start(Stage primaryStage) {
		try {

			// initialize the scene
			StackPane root = (StackPane) FXMLLoader.load(getClass().getResource(Configs.SPLASH_SCREEN_PATH));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();

			// Load splash screen with fade in effect
			FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), root);
			fadeIn.setFromValue(0);
			fadeIn.setToValue(1);
			fadeIn.setCycleCount(1);

			// Finish splash with fade out effect
			FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), root);
			fadeOut.setFromValue(1);
			fadeOut.setToValue(0);
			fadeOut.setCycleCount(1);

			// After fade in, start fade out
			fadeIn.play();
			fadeIn.setOnFinished((e) -> {
				fadeOut.play();
			});

			// After fade out, load actual content
			fadeOut.setOnFinished((e) -> {
				try {
					ChooseRoleScreenHandler roleScreenHandler = new ChooseRoleScreenHandler(primaryStage, Configs.SELLER_OR_USER_PATH);
					roleScreenHandler.setScreenTitle("Path choosing screen");
					roleScreenHandler.show();
//					HomeScreenHandler homeScreenHandler = new HomeScreenHandler(primaryStage, Configs.HOME_PATH);
//					homeScreenHandler.setScreenTitle("Home Screen");
//					homeScreenHandler.setImage();
//					homeScreenHandler.show();
					OrderManagementAdminScreenHandler orderManagementAdminScreenHandler = new OrderManagementAdminScreenHandler(primaryStage, Configs.ORDER_MANAGEMENT_ADMIN_PATH);
					orderManagementAdminScreenHandler.show();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
	
}
