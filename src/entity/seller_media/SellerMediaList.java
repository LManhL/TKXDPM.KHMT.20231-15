package entity.seller_media;

import java.util.ArrayList;
import java.util.List;

import entity.cart.CartMedia;

public class SellerMediaList {
	private List<CartMedia> sellerMediaList;
	private static SellerMediaList sellerMediaListIntance;
	
	private SellerMediaList() {
		this.sellerMediaList = new ArrayList<CartMedia>();
	}
	
	public static SellerMediaList getSellerMediaList() {
		if (sellerMediaListIntance == null) {
			sellerMediaListIntance = new SellerMediaList();
		}
		return sellerMediaListIntance;
	}
	
	public void addSellerMedia(CartMedia cartMedia) {
		this.sellerMediaList.add(cartMedia);
		return;
	}
	
	public void deleteSellerMedia(CartMedia cartMedia) {
		this.sellerMediaList.remove(cartMedia);
		return;
	}
	
	public void changeSellerMedia(CartMedia cartMedia) {
		// find the media in the back-end with its id(or its number in the database)
		// and then fix the item
		return;
	}
	
	public List<CartMedia> getSellerMedia(CartMedia cartMedia) {
		return this.sellerMediaList;
	}
}
