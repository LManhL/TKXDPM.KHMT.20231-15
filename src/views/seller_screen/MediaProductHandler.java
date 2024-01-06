package views.seller_screen;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

import entity.media.Media;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.Configs;
import utils.Utils;
import views.screen.FXMLScreenHandler;
import views.seller_screen.seller_event_screen.update.BookUpdateHandler;

// This class is used for handle the administrators/seller action 
// to each media product in the AdminHome page
public class MediaProductHandler extends FXMLScreenHandler {
	
	public static Logger LOGGER = Utils.getLogger(MediaProductHandler.class.getName());
	// media product's Image
	@FXML
	protected ImageView item_img;
	// media product's name
	@FXML
	protected Text item_name;
	// media product's quantity
	@FXML
	protected Text item_quantity;
	// media product's type
	@FXML
	protected Text item_type;
	// media product's Delete button
	@FXML
	protected Text item_price;
	
	@FXML
	protected Button delete_btn;
	// media product's Change button
	@FXML
	protected Button update_btn;

	private Media media;
	private int id;
	private BookUpdateHandler bookUpdateHandler;

	public MediaProductHandler(String screenPath, Media media) throws IOException, SQLException {
		super(screenPath);
		// TODO Auto-generated constructor stub
		this.media = media;
		this.id = media.getId();
		setMediaInfo();
	}

	// handle display Media item (with adding event to those buttons)
	private void setMediaInfo() throws SQLException {
		// set the cover image of media
		File file = new File(media.getImageURL());
		Image image = new Image(file.toURI().toString());
		item_img.setFitHeight(160);
		item_img.setFitWidth(152);
		item_img.setImage(image);
		
		item_name.setText(media.getTitle());
		item_quantity.setText(Integer.toString((int) media.getQuantity()));
		item_type.setText(media.getTitle());
		item_price.setText("" + media.getPrice());

		update_btn.setOnMouseClicked(event -> {
			try {
				bookUpdateHandler = new BookUpdateHandler(new Stage(), Configs.SELLER_UPDATE_BOOK_PATH, this.media);
				bookUpdateHandler.setScreenTitle("Book Update Dialog");
				bookUpdateHandler.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		delete_btn.setOnMouseClicked(event -> {
			try {
				this.media.deleteMediaFieldById(this.id);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
}
