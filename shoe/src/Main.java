import java.net.*;
import java.io.*;
import java.util.*;

import javax.swing.JFileChooser;
public class Main {
  public static void main(String args[]){
       String nextLine;
       URL url = null;
       String nextUrl = null;
       URLConnection urlConn = null;
       InputStreamReader  inStream = null;
       BufferedReader buff = null;
       
       PrintWriter out = null;
       
		JFileChooser fc = new JFileChooser();						//used to let user pick file to save shoe info
		fc.showSaveDialog(fc);										
		String saveFile = fc.getSelectedFile().getAbsolutePath();	
																	
	try {
		out = new PrintWriter(new FileWriter(saveFile));
		System.out.println("got the file");
	} catch (IOException e2) {
		// TODO Auto-generated catch block
		System.out.println("can't reach file");
		e2.printStackTrace();
	}
       String www = "www.zappos.com";
       String add = null;
       String b = null;
       String prodName = null;
       float price;
       String p = "0";
       String r = "0";
       ArrayList<Shoe> shoeArr = new ArrayList<Shoe>(); 
       int page = 1;
       int totalPages =0;
       

       
       try{
          // Create the URL obect that points
          // at the default file index.html
    	  //get all of the mens shoes
      	 for(int i=0; i == totalPages; i++){			//gets all of the pages of men's shoes
     		
    		 System.out.println("i is: " +i);
    		 if(i == 0){
    			 url  = new URL("http://www.zappos.com/mens-shoes~ov?zfcTest=pd%3A0");	//first page
    			 totalPages += 1;
    		 }
    		 else{
    			 url = new URL("http://www.zappos.com" + nextUrl);	//subsequent pages
    		 
    			 int stringBreak = nextUrl.indexOf('=');										//get the index of the = to find the page number
    			 String pageNum = nextUrl.substring(stringBreak+1,nextUrl.length()).toString(); //put the page number into a string
    			 totalPages = Integer.parseInt(pageNum)+1;										//parse the number from the string
    		 
    		
    		 }
    		 
          urlConn = url.openConnection();
          inStream = new InputStreamReader(urlConn.getInputStream());
          buff= new BufferedReader(inStream);
        
           System.out.println("current url is: "+ url);
           
       // Read the shoes from html
        while (true){
            nextLine = buff.readLine();  
            if (nextLine !=null){
            	//get the next page for the shoes
            	if(nextLine.contains("link rel=\"next\"")){		//find the next page in the html code
            		nextUrl = nextLine;
            		nextUrl = nextUrl.replace("href=\"", "@");	//parse the code to get just the url
            		int temp = nextUrl.indexOf("@")+1;			//parse the code to get just the url
    				int temp2 = nextUrl.indexOf("/>")-1;		//parse the code to get just the url
    				nextUrl = nextUrl.substring(temp, temp2);	//parse the code to get just the url
    				temp = nextUrl.indexOf('"');				//parse the code to get just the url
    				nextUrl = nextUrl.substring(0, temp);		//parse the code to get just the url
    				
    				
            		System.out.println("next url is: " + nextUrl );
            	}
            	if(nextLine.contains("document.getElementById('searchResults').className")){	//start reading the shoes
            		nextLine = buff.readLine();			//skip ahead to where first shoe entry starts
            		nextLine = buff.readLine();			//skip ahead to where first shoe entry starts
            		nextLine = buff.readLine();			//skip ahead to where first shoe entry starts
            		while(true){
            			//stop after getting all the shoes
            			if(nextLine.contains("/div")){
            				break;
            			}
            			
            			//breaks the shoes apart
            			if(nextLine.equals("</a>")){
            				//System.out.println("NEW SHOE");
            				Shoe s = new Shoe(add, b, prodName, p, r);	//create a Shoes s and add to an arrayList
            				shoeArr.add(s);
            				nextLine = buff.readLine(); 
            			}
            			
            			//get the url of the shoe
            			if(nextLine.contains("href")){
            				add = nextLine;	//the address
            				add = add.replace("<a href=\"", "");	//parse through garbage info
            				int tmp = add.indexOf('"');				//parse through garbage info
            				add = add.substring(0, tmp);			//url
            				add = www.concat(add);
            				//System.out.println(add);
            			}
            			
            			// get the brand of shoe
            			if(nextLine.contains("img")){				
            				b = nextLine;	
            				int temp = b.indexOf("alt=\"");	//parse to where the brand name is
            				int temp2 = b.indexOf('&');		//parse to where the brand name is
            				b = b.substring(temp, temp2);	//parse to where the brand name is
            				temp = b.indexOf('"')+1;		//parse to where the brand name is
            				temp2 = b.length();				//parse to where the brand name is
            				b = b.substring(temp, temp2);	//parse to where the brand name is
            				b = b.trim();
            				//System.out.println(b);
            			}
            			
            			//get the product name of the shoe
            			if(nextLine.contains("img")){
            				prodName = nextLine;	
            				int temp = prodName.indexOf(';')+1;			//parse to where the product name is
            				int temp2 = prodName.indexOf("class")-2;	//parse to where the product name is
            				prodName = prodName.substring(temp, temp2);	//parse to where the product name is
            				if(prodName.contains("&")){					//parse to where the product name is
            					temp = prodName.indexOf('&');			//parse to where the product name is
                			prodName = prodName.substring(0, temp);		//parse to where the product name is
                			
            				}
            				//System.out.println(prodName);
            				prodName = prodName.substring(1, prodName.length());
            			}
            			
            			//get the price of the shoe
            			if(nextLine.contains("price")){
            				p = nextLine;	
            				int temp = p.indexOf('$')+1;		//parse to price
            				int temp2 = p.indexOf('/')-1;		//parse to price
            				p = p.substring(temp, temp2);		//parse to price
            				//System.out.println(p);
            			}
            			
            			//get the user rating of the shoes
            			if(nextLine.contains("stars rating")){
            				r = nextLine;
            				int temp = r.indexOf(':')+1;		//parse to rating
            				int temp2 = r.indexOf("stars!")-1;	//parse to rating
            				r = r.substring(temp, temp2);		//parse to rating
            				r = r.replace(" ", "");
            				//System.out.println(r);
            			}
            			
            				
            			nextLine = buff.readLine();	//get next line
            			
            		}
            	}
            }
            else{

            	//if got all data break loop
               break;
            } 
        }// end of reading from website
    	 }
        for(int i=0; i< shoeArr.size(); i++){
        	
        	if(shoeArr.get(i).getBrand().toUpperCase().equals("NIKE")) 	//if statement start
        	{
        		Nike n =  new Nike();
        		n.setAddress(shoeArr.get(i).getAddress());			  	// just to show that you can put all of one type of shoe into different subclasses
        		n.setBrand(shoeArr.get(i).getBrand());
        		n.setProdName(shoeArr.get(i).getProdName());
        		n.setPrice(shoeArr.get(i).getPrice());
        		n.setRating(shoeArr.get(i).getRating());
        		//n.print(n); //can print if desired
        	}															//if end
        	
        	out.println(shoeArr.get(i).getAddress()+ "	" + shoeArr.get(i).getBrand()+ "	" +shoeArr.get(i).getProdName() +  "	" + shoeArr.get(i).getPrice()+ "	" +  shoeArr.get(i).getRating());
        	
        } //end for
        out.close();
        //catch errors
     } catch(MalformedURLException e){
       System.out.println("Please check the URL:" + 
                                           e.toString() );
     } catch(IOException  e1){
      System.out.println("Can't read  from the Internet: "+ 
                                          e1.toString() ); 
  }
 }
}