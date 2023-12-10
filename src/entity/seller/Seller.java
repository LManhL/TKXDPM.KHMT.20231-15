package entity.seller;

public class Seller {
	private String sellerName;
	private String sellerRole;
	private String password;
	
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	
	public String getSellerRole() {
		return sellerRole;
	}
	public void setSellerRole(String sellerRole) {
		if (sellerRole == "default") {
			this.sellerRole = "admin";
			return;
		}
		this.sellerRole = sellerRole;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		if (password == "default") {
			this.password = "123456789";
			return;
		}
		this.password = password;
	}
}
