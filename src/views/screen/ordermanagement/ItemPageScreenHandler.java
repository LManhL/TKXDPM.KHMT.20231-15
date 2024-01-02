package views.screen.ordermanagement;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import views.screen.FXMLScreenHandler;

import java.io.IOException;

public class ItemPageScreenHandler extends FXMLScreenHandler {

    @FXML
    Label page;

    public ItemPageScreenHandler(String screenPath, int index) throws IOException {
        super(screenPath);
        page.setText(String.valueOf(index));
    }
}
