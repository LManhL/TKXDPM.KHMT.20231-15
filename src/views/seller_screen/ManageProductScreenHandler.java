package views.seller_screen;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import controller.HomeController;
import controller.SellerHomeController;
import entity.media.Book;
import entity.media.Media;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.Configs;
import utils.Utils;
import views.screen.BaseScreenHandler;
import views.screen.home.HomeScreenHandler;
import views.seller_screen.seller_event_screen.ChooseTypeMediaCreate;

public class ManageProductScreenHandler extends BaseScreenHandler implements Initializable {

	public static Logger LOGGER = Utils.getLogger(ManageProductScreenHandler.class.getName());

	@FXML
	private ScrollPane scroll_pane;

	@FXML
	private VBox vboxes;

	@FXML
	private Button add_btn;
	
	@FXML
	private Button reset;

	@FXML
	private ImageView order_img;

	@FXML
	private Button exit_btn;

	private ChooseTypeMediaCreate chooseTypeMediaCreate;
	private List<?> allTheMedia;

	public SellerHomeController getBController() {
		return (SellerHomeController) super.getBController();
	}

	public ManageProductScreenHandler(Stage stage, String screenPath) throws IOException {
		super(stage, screenPath);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void show() {
		super.show();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setBController(new SellerHomeController());

		add_btn.setOnMouseClicked(event -> {
			try {
				chooseTypeMediaCreate = new ChooseTypeMediaCreate(new Stage(), Configs.CHOOSE_TYPE_CREATE_PATH);
				chooseTypeMediaCreate.setScreenTitle("Choose Media Type");
				chooseTypeMediaCreate.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		reset.setOnMouseClicked(event -> {
			try {
				this.vboxes.getChildren().clear();
				this.allTheMedia = getBController().getAllMedia();
				List<?> convertMediaList = convertMediaToRenderItem(allTheMedia);
				renderMediaToScreen(convertMediaList);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		try{
			this.vboxes.getChildren().clear();
			this.allTheMedia = getBController().getAllMedia();
			List<?> convertMediaList = convertMediaToRenderItem(allTheMedia);
			renderMediaToScreen(convertMediaList);
		} catch (Error | SQLException e){
			e.printStackTrace();
		}
	}

	public List<MediaProductHandler> convertMediaToRenderItem(List<?> MediaList) throws SQLException {
		List<MediaProductHandler> renderList = new ArrayList<MediaProductHandler>();
		try {
			for (Object item: MediaList) {
				MediaProductHandler mediaProductHandler = new MediaProductHandler(Configs.SELLER_ITEM_VIEW_PATH, (Media) item);
				renderList.add(mediaProductHandler);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return renderList;
	}

	public void renderMediaToScreen(List<?> MediaList) {
		if (MediaList.size() == 0) return;
		int numberOfLastRow = MediaList.size() % 3;
		int numberOfRows = -1;
		if (numberOfLastRow > 0) {
			numberOfRows = (int) Math.floor(MediaList.size() / 3) + 1;
		} else {
			numberOfRows = (int) Math.floor(MediaList.size() / 3);
		}
		for (int rowIndex = 0; rowIndex < numberOfRows; rowIndex++) {
			HBox hbox = new HBox();
			if (rowIndex == numberOfRows - 1 && numberOfLastRow > 0) {
				for (int index = 0; index < numberOfLastRow; index++) {
					hbox.getChildren().add(((MediaProductHandler) MediaList.get(rowIndex * 3 + index)).getContent());
				}
			} else {
				for (int index = 0; index < 3; index++) {
					hbox.getChildren().add(((MediaProductHandler) MediaList.get(rowIndex * 3 + index)).getContent());
				}
			}
			this.vboxes.getChildren().add(hbox);
		}
	}
}
