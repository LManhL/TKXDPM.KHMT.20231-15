package views.screen;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import utils.Configs;
import views.screen.home.HomeScreenHandler;
import views.seller_screen.ManageProductScreenHandler;

public class ChooseRoleScreenHandler extends BaseScreenHandler implements Initializable{
	
	@FXML
	private RadioButton seller;
	
	@FXML
	private RadioButton buyer;
	
	@FXML
	private Button confirm_btn;
	
	private HomeScreenHandler homeHandler;
	private ManageProductScreenHandler manageProductScreenHandler;

    public ChooseRoleScreenHandler(Stage stage, String screenPath) throws IOException {
		super(stage, screenPath);
		// TODO Auto-generated constructor stub
	}

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    	final ToggleGroup group = new ToggleGroup();
    	// add radio buttons into a group
    	seller.setToggleGroup(group);
    	buyer.setToggleGroup(group);
    	seller.setSelected(true);

    	confirm_btn.setOnMouseClicked(event -> {
    		if (seller.isSelected()) {
    			try {
    				manageProductScreenHandler = new ManageProductScreenHandler(this.stage, Configs.SELLER_HOMEPAGE_PATH);
    				manageProductScreenHandler.setScreenTitle("Seller Screen");
    				manageProductScreenHandler.show();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
    		} else {
				try {
					homeHandler = new HomeScreenHandler(this.stage, Configs.HOME_PATH);
					homeHandler.setScreenTitle("Home Screen");
					homeHandler.setImage();
					homeHandler.show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    	});
    }
}