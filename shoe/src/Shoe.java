
public class Shoe {
    String address = null;
    String brand;
    String prodName;
    String price;
    String rating;
    
    //constructors 
    public Shoe(String add, String b, String prod, String price, String r){
    	this.address = add;
    	this.brand = b;
    	this.prodName = prod;
    	this.price = price;
    	this.rating =r; 	
    }
    
    public Shoe(){
    	this.address = "";
    	this.brand = "";
    	this.prodName = "";
    	this.price = "";
    	this.rating = ""; 	
    }

    //getters and setters
    
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}
	
	public float getFloatPrice(){
		return Float.parseFloat(price);
	}
	
	public int getIntRating(){
		return Integer.parseInt(rating);
	}
	
	public void print(Shoe s){
		System.out.println(s.getAddress() + " " + s.getBrand() + " "+ s.getProdName() + " " + s.getFloatPrice() + " " + s.getIntRating());
	}
}
