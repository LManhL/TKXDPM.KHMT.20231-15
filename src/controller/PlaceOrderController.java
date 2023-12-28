package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import entity.cart.Cart;
import entity.cart.CartMedia;
import common.exception.InvalidDeliveryInfoException;
import entity.invoice.Invoice;
import entity.order.Order;
import entity.order.OrderMedia;
import views.screen.popup.PopupScreen;

/**
 * This class controls the flow of place order usecase in our AIMS project
 * @author nguyenlm
 */
public class PlaceOrderController extends BaseController{

    /**
    *LSP
    * Co the co loi trong tuong lai khi dung phuong thuc cua lop cha 
    */

    /**
     * Just for logging purpose
     */
    private static Logger LOGGER = utils.Utils.getLogger(PlaceOrderController.class.getName());

    /**
     * This method checks the avalibility of product when user click PlaceOrder button
     * @throws SQLException
     */
    public void placeOrder() throws SQLException{
        Cart.getCart().checkAvailabilityOfProduct();
    }

    /**
     * This method creates the new Order based on the Cart
     * @return Order
     * @throws SQLException
     */
    /*Content coupling: Do order.getlstOrderMedia().add(orderMedia) 
     đang can thiệp trực tiếp đến giá trị thuộc tính của lớp Order*/
     // The createOrder should be placed in the Cart class,
     // This controller class should be only control the flow of the application
     // not where to write the command code
     // Violate the single responsibility principle
    // public Order createOrder() throws SQLException{
    //     Order order = new Order();
    //     for (Object object : Cart.getCart().getListMedia()) {
    //         CartMedia cartMedia = (CartMedia) object;
    //         OrderMedia orderMedia = new OrderMedia(cartMedia.getMedia(), 
    //                                                cartMedia.getQuantity(), 
    //                                                cartMedia.getPrice());    
    //         order.getlstOrderMedia().add(orderMedia);
    //     }
    //     return order;
    // }

    /**
     * This method creates the new Invoice based on order
     * @param order
     * @return Invoice
     */
    public Invoice createInvoice(Order order) {
        return new Invoice(order);
    }

    /**
     * This method takes responsibility for processing the shipping info from user
     * @param info
     * @throws InterruptedException
     * @throws IOException
     */
    public void processDeliveryInfo(HashMap info) throws InterruptedException, IOException{
        LOGGER.info("Process Delivery Info");
        LOGGER.info(info.toString());
        validateDeliveryInfo(info);
    }
    
    /**
   * The method validates the info
   * @param info
   * @throws InterruptedException
   * @throws IOException
   */
    public void validateDeliveryInfo(HashMap<String, String> info) throws InterruptedException, IOException{
    	
    }
    
    public boolean validatePhoneNumber(String phoneNumber) {
    	// TODO: your work
    	return false;
    }
    
    public boolean validateName(String name) {
    	// TODO: your work
    	return false;
    }
    
    public boolean validateAddress(String address) {
    	// TODO: your work
    	return false;
    }
    

    /**
     * This method calculates the shipping fees of order
     * @param order
     * @return shippingFee
     */
    // This function should be place in Order class 
    // (this violate the single responsibility principle)
    public int calculateShippingFee(Order order){
        Random rand = new Random();
        int fees = (int)( ( (rand.nextFloat()*10)/100 ) * order.getAmount() );
        LOGGER.info("Order Amount: " + order.getAmount() + " -- Shipping Fees: " + fees);
        return fees;
    }
}
