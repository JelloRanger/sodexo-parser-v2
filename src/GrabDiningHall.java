import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/*import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;*/

// parse a specific dining halls menu items according to provided attributes
public class GrabDiningHall {

	// attributes used to select food items
	private String diningHallURL; 	// link to rpi sodexo menu page
	private String dayOfWeek; 	  	// day of week meal is being served
	private String mealTime; 		// meal time meal is being served (breakfast, lunch, dinner)
	private String station; 		// station meal is being served at
	private String attr; 			// vegetarian/vegan/mindful item
	
	// list of food items that meet the above attributes' criteria
	private ArrayList<FoodItem> foodItems;
	
	
	
	// tester main function
	/*public static void main(String args[]) throws IOException {
		GrabDiningHall gdh = new GrabDiningHall("https://rpi.sodexomyway.com/images/WeeklyMenuRSDH%2012-1-14_tcm1068-29436.htm", "thursday", "brk", null, "vegetarian");
		gdh.parsePage();
	}*/
	
	// constructor given all attributes (dining hall, day of week, meal, station, vegan/vegetarian/mindful item)
	public GrabDiningHall(String diningHallURL, String dayOfWeek, String mealTime, String station, String attr) { 
		this.diningHallURL = diningHallURL;
		this.dayOfWeek = dayOfWeek;
		this.station = station;
		this.attr = attr;
		
		if (mealTime.equalsIgnoreCase("breakfast")) this.mealTime = "brk";
		else if (mealTime.equalsIgnoreCase("lunch")) this.mealTime = "lun";
		else if (mealTime.equalsIgnoreCase("dinner")) this.mealTime = "din";
		else this.mealTime = mealTime;
	}
	
	// parse the provided URL given the provided attributes
	public boolean parsePage() throws IOException {
		
		// connect to the dining hall menu webpage
		Document doc = Jsoup.connect(diningHallURL).userAgent("Mozilla/5.0").timeout(30000).get();
		
		Elements meals = doc.select("*");
		
		// select based on dayOfWeek
		if (dayOfWeek != null) {
			meals = meals.select("td[id=" + dayOfWeek + "]");
		}
		
		// select based on meal time
		if (mealTime != null) {
			meals = meals.select("tr[class=" + mealTime + "]");
		}
		
		
		// select based on attr
		/*if (attr != null) {
			meals = meals.select("tr img[alt=" + attr + "]");
		}*/
		
		
		// grab meal names
		meals = meals.select("div[class=menuitem]");
		
		// select based on station
		//todo
		
		System.out.println(meals.size());
		
		for (Element meal : meals) {
			System.out.println(meal.text());
		}
		
		return true;
	}
	
}
