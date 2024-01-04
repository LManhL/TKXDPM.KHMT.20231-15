package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import entity.cart.Cart;
import entity.media.Media;
import views.screen.home.MediaHandler;

/**
 * This class controls the flow of events in homescreen
 * @author nguyenlm
 */
public class HomeController extends BaseController{

    /**
    *LSP
    * Co the co loi trong tuong lai khi dung phuong thuc cua lop cha 
    */



    /**
     * this method gets all Media in DB and return back to home to display
     * @return List[Media]
     * @throws SQLException
     */
    public List getAllMedia() throws SQLException{
        return new Media().getAllMedia();
    }

    public List<Media> search(String searchText) throws SQLException{
    	
    	return new Media().searchMedia(searchText);
    }
    public void sortTitle(List<MediaHandler> list){
    	Comparator<MediaHandler> mediaTitleComparator = new Comparator<MediaHandler>() {
            @Override
            public int compare(MediaHandler m1, MediaHandler m2) {
                return m1.getMedia().getTitle().compareToIgnoreCase(m2.getMedia().getTitle());
            }
        };
        list.sort(mediaTitleComparator);
    }
    public void sortPrice(List<MediaHandler> list) {
    	Comparator<MediaHandler> mediaTitleComparator = new Comparator<MediaHandler>() {
            @Override
            public int compare(MediaHandler m1, MediaHandler m2) {
            	int priceComparison = m1.getMedia().getPrice() - m2.getMedia().getPrice();
                if (priceComparison != 0) {
                    return priceComparison;
                }
                return 0;
            }
        };
        list.sort(mediaTitleComparator);
    }

}
