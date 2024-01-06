package controller;

import java.sql.SQLException;
import java.util.List;

import entity.media.Media;

public class SellerHomeController extends BaseController {

	public List<Media> getAllMedia() throws SQLException{
        return new Media().getAllMedia();
    }
}
