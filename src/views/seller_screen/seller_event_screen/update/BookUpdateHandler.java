package views.seller_screen.seller_event_screen.update;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import entity.db.AIMSDB;
import entity.media.Book;
import entity.media.Media;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.Configs;
import utils.Utils;
import views.screen.BaseScreenHandler;
import views.seller_screen.MediaProductHandler;
import views.seller_screen.seller_event_screen.create.CreateCommonInfo;

public class BookUpdateHandler extends BaseScreenHandler implements Initializable {

	public static Logger LOGGER = Utils.getLogger(BookUpdateHandler.class.getName());

	@FXML
	private ComboBox<String> category;

	@FXML
	private TextField author;

	@FXML
	private TextField cover_type;

	@FXML
	private TextField publisher;

	@FXML
	private DatePicker publish_date;

	@FXML
	private TextField number_pages;

	@FXML
	private TextField language;

	@FXML
	private TextField title;

	@FXML
	private TextField value;

	@FXML
	private TextField price;

	@FXML
	private TextField weight;

	@FXML
	private Spinner<Integer> quantity;

	@FXML
	private Button create;

	@FXML
	private ComboBox<String> image_url;

	private CreateCommonInfo createCommonInfo;
	private Media media;

	public BookUpdateHandler(Stage stage, String screenPath, Media media) throws IOException, SQLException {
		super(stage, screenPath);
		// TODO Auto-generated constructor stub
		this.media = media;
		setMediaInfo();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		quantity.setValueFactory(
				new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 1)
		);

		category.getItems().addAll(
				"Horror",
				"Science fiction",
				"Western",
				"Action",
				"Drama",
				"Comedy",
				"Thriller",
				"Romance"
		);

		image_url.getItems().addAll(
				"assets/images/book/book1.jpg",
				"assets/images/book/book2.jpg",
				"assets/images/book/book3.jpg",
				"assets/images/book/book4.jpg",
				"assets/images/book/book5.jpg",
				"assets/images/book/book6.jpg",
				"assets/images/book/book7.jpg",
				"assets/images/book/book8.jpg",
				"assets/images/book/book9.jpg",
				"assets/images/book/book10.jpg",
				"assets/images/book/book11.jpg",
				"assets/images/book/book12.jpg"
		);

		create.setOnMouseClicked(event -> {
			if (checkFillInformation()) {
				try {
					updateBookQuery();
					this.stage.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	public void setMediaInfo() throws SQLException {
		LOGGER.info("Id of the media: " + this.media);
		Book targetMedia = (Book) new Book().getMediaById(media.getId());
		category.setValue(targetMedia.getCategory());
		author.setText(targetMedia.getAuthor());
		cover_type.setText(targetMedia.getCoverType());
		publisher.setText(targetMedia.getPublisher());
		publish_date.setValue(LOCAL_DATE(targetMedia.getPublishDate().toString()));
		number_pages.setText("" + targetMedia.getNumOfPages());
		language.setText(targetMedia.getLanguage());
		title.setText(targetMedia.getTitle());
		value.setText("" + targetMedia.getValue());
		price.setText("" + targetMedia.getPrice());
		weight.setText("" + targetMedia.getWeight());
		quantity.getValueFactory().setValue(targetMedia.getQuantity());
		image_url.setValue(targetMedia.getImageURL());
	}

	public LocalDate LOCAL_DATE (String dateString){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse(dateString, formatter);
		return localDate;
	}

	public boolean checkFillInformation() {
		String comboBoxText = category.getValue();
		String authorText = author.getText();
		String coverTypeText = cover_type.getText();
		String publisherText = publisher.getText();
		String publishDateText = publish_date.getValue().toString();
		String numOfPages = number_pages.getText();
		String languageText = language.getText();
		String imageUrl = image_url.getValue();
		String titleText = title.getText();
		String valueText = value.getText();
		String priceText = price.getText();
		String weightText = weight.getText();
		int quantityText = quantity.getValue();
		return comboBoxText.length() > 0 &&
				authorText.length() > 0 &&
				imageUrl.length() > 0 &&
				coverTypeText.length() > 0 &&
				publisherText.length() > 0 &&
				publishDateText.length() > 0 &&
				numOfPages.length() > 0 &&
				languageText.length() > 0 &&
				titleText.length() > 0 &&
				valueText.length() > 0 &&
				priceText.length() > 0 &&
				weightText.length() > 0 &&
				quantityText > 0;
	}

	public void updateBookQuery() throws SQLException {
		String bookSQL = "UPDATE Book "
				+ "SET "
				+ "author='" + author.getText() + "',"
				+ "coverType='" + cover_type.getText() + "',"
				+ "publisher='" + publisher.getText() + "',"
				+ "publishDate='" + publish_date.getValue().toString() + "',"
				+ "numOfPages='" + number_pages.getText() + "',"
				+ "language='" + language.getText() + "',"
				+ "bookCategory='" + category.getValue() + "'"
				+ " WHERE "
				+ "id = " + this.media.getId() + ";";

		LOGGER.info("" + bookSQL);

		String mediaSQL = "UPDATE Media "
				+ "SET "
				+ "title='" + title.getText() + "',"
				+ "category='" + category.getValue() + "',"
				+ "price='" + price.getText() + "',"
				+ "value='" + value.getText() + "',"
				+ "quantity=" + quantity.getValue() + ","
				+ "weight='" + weight.getText() + "',"
				+ "imageURL='" + image_url.getValue() + "'"
				+ " WHERE "
				+ "id = " + this.media.getId() + ";";

		LOGGER.info("" + mediaSQL);
		Statement stm = AIMSDB.getConnection().createStatement();
		stm.executeUpdate(bookSQL);
		stm.executeUpdate(mediaSQL);
	}
}