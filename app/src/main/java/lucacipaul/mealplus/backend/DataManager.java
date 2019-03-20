package lucacipaul.mealplus.backend;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.nio.charset.StandardCharsets;

public class DataManager {

	private static DataManager instance = null;

	private static User loggedUser = null;

	private DataManager() {	}

	public static DataManager getInstance() {
		if(instance == null) {
			instance = new DataManager();
		}
		return instance;
	}
	
	public static User getLoggedUser() {
		return loggedUser;
	}
	
	/**
	 * 
	 * @param email
	 */
	public ArrayList<User> searchAccount(String email) {
		ArrayList<User> users = new ArrayList<User>();
		
		for (User usr : Dummy.customers) {
			if(usr.getEmail().contains(email))
					users.add(usr);
		}
		for (User usr : Dummy.advisers) {
			if(usr.getEmail().contains(email))
					users.add(usr);
		}
		for (User usr : Dummy.admins) {
			if(usr.getEmail().contains(email))
					users.add(usr);
		}
		return users;
	}

	private ArrayList<Items> filterItems(ArrayList<Items> items, ArrayList<Amenities> amenities, ArrayList<Types> types, ArrayList<Sellpoints> sellpoints) {
		ArrayList<Items> entries = new ArrayList<Items>();
		for(Items item : items) {

			// Special sellpoint check if item is Food
			// (because only Food has sellpoints).
			if(item instanceof Food) {
				if(!DataManager.ContainsArrayItem(sellpoints, ((Food) item).getSellpoints())) continue;
			}
			
			// Do a general filter on items.
			if(DataManager.ContainsArrayItem(amenities, item.getAmenities()) &&
			   DataManager.ContainsArrayItem(types, item.getTypes()) ) {
				entries.add(item);
			}
		}
		return entries;
	}

	private static <T> boolean ContainsArrayItem(ArrayList<T> i, ArrayList<T> o) {
		if(i == null) return true;
		if(o == null) return false;

		for (T t : o)
			if(i.contains(t))
				return true;
		return false;
	}

	private static <T extends Items> void ConcatItems(ArrayList<Items> original, ArrayList<T> add) {
		for(T a : add)
			original.add(a);
	}

	private static ArrayList<Items> DietLogEntriesToItems(ArrayList<DietLogEntry> entries) {
		ArrayList<Items> items = new ArrayList<Items>();
		for(DietLogEntry entry : entries)
			items.add(entry.getEntry());
		return items;
	}

	private static ArrayList<DietLogEntry> ItemsToDietLogEntries(ArrayList<Items> items) {
		ArrayList<DietLogEntry> entries = new ArrayList<DietLogEntry>();

		for(Items item : items) {
			DietLogEntry entry = new DietLogEntry();
			if(item instanceof Food) {
				Food food = (Food)item;
				entry.setFood(food);
			}
			if(item instanceof Recipe) {
				Recipe recipe = (Recipe)item;
				entry.setRecipe(recipe);
			}
			entries.add(entry);
		}

		return entries;
	}

	private static ArrayList<Items> RemoveDuplicates(ArrayList<Items> items) {
		ArrayList<Items> newArray = new ArrayList<Items>();

		for(Items item : items) {
			if(!newArray.contains(item)) newArray.add(item);
		}

		return newArray;
	}

	private static <E> void RemoveFromArrayByArray(ArrayList<E> items, ArrayList<E> toRemove) {
		for(E e : toRemove)
			items.remove(e);
	}

	/**
	 * 
	 * @param token
	 * @param amenities
	 * @param types
	 * @param sellpoints
	 * @param searchFrequentlyEaten
	 * @param searchSelfMade
	 * @param recommend
	 */
	public ArrayList<DietLogEntry> searchItems(Customer customer,
			String token,
			ArrayList<Amenities> amenities, ArrayList<Types> types, ArrayList<Sellpoints> sellpoints,
			boolean searchFrequentlyEaten, boolean searchSelfMade, boolean recommend)
	{

		// All items that need to be filtered need to be stored in this array,
		// this will be then filtered and placed into 'filtered'.
		ArrayList<Items> unfiltered = new ArrayList<Items>();

		// Add items to unfiltered depending on settings.
		if(searchSelfMade) {
			ConcatItems(unfiltered, customer.getOwnedFood());
			ConcatItems(unfiltered, customer.getOwnedRecipes());
		}
		if(searchFrequentlyEaten) {
			ConcatItems(unfiltered, DietLogEntriesToItems(customer.getFrequentlyEaten()));
		}
		if(!searchSelfMade && !searchFrequentlyEaten) {
			ConcatItems(unfiltered, Dummy.foods);
			ConcatItems(unfiltered, Dummy.recipes);
		}
		
		
		/**
		 * 1. X How does the code from above avoid duplicates into unfiltered? self-made items can also be under frequently-eaten.
		 * 2. TODO: TEST What happens if the self-made and frequently-eaten are empty arrays?
		 */

		// Filter items by token and other settings.
		ArrayList<Items> tokenSearchedItems = searchUnpublishedItems(token, true, unfiltered);
		ArrayList<Items> filtered = filterItems(tokenSearchedItems, amenities, types, sellpoints);

		RemoveFromArrayByArray(filtered, DietLogEntriesToItems(customer.getDislikedItems()));

		// TODO: implement the frequently-eaten
		// TODO: recommendations.

		return ItemsToDietLogEntries(RemoveDuplicates(filtered));
	}

	/**
	 * 
	 * @param dietLog
	 * @param toggle
	 */
	public void toggleDietLog(DietLog dietLog, boolean toggle) {
		dietLog.setClosed(toggle);
	}

	/**
	 * 
	 * @param dietLog
	 */
	public Report generateReport(DietLog dietLog) {
		Report report = new Report();
		dietLog.setClosed(true);
		report.setDietLog(dietLog);
		
		// compute nutritional values - yet corrupt
		
		return report;
	}

	/**
	 * 
	 * @param email
	 * @param pwd
	 */
	public User login(String email, String pwd) { // works

		// No need to sanitise parameters, as if they do not
		// match then it will just return false.
		ArrayList<User> users = searchAccount(email);
		if(users.size() == 1 && users.get(0).getEmail().equalsIgnoreCase(email)) {
			User usr = users.get(0);
			if(usr.getPwd().equals(hashPassword(usr, pwd))) { // Critical bug fixed here, make sure to have { } @Paul.
				loggedUser = usr;
				return usr;
			}
		}
		// Email was not found in the users array.
		return null;
	}

	/**
	 * 
	 * @param user
	 */
	public boolean register(User user) {

		// Sanity checks for user inputted data.
		if(user == null) return false;
		if(!user.getEmail().contains("@") || !sanityCheckInputField(user.getEmail(), false)) return false;
		if(!sanityCheckInputField(user.getFirstName(), false) || !sanityCheckInputField(user.getLastName(), false)) return false;
		if(!sanityCheckInputField(user.getPwd(), true)) return false;

		// Sanity checks to make sure the email
		// does not exist.
		ArrayList<User> users = searchAccount(user.getEmail());
		for(User u:users)
			System.out.println(u.getEmail());
		if(!users.isEmpty()) {
			return false;
		}

		if(user instanceof Customer) return Dummy.customers.add((Customer) user);
		if(user instanceof Adviser) return Dummy.advisers.add((Adviser) user);
		return false;
	}

	/**
	 * 
	 * @param email
	 * @param approved
	 */
	public ArrayList<Adviser> searchAdviserAccounts(String email, boolean approved) {
		ArrayList<Adviser> advisers = new ArrayList<Adviser>();
		
		for(Adviser adviser : advisers){
			if(adviser.getApproved() == approved) {
				if(adviser.getEmail().contains(email)) {
					advisers.add(adviser);
				}
			}
		}
		
		return advisers;
	}

	/**
	 * 
	 * @param token
	 * @param isPublic
	 */
	public ArrayList<Items> searchUnpublishedItems(String token, boolean isPublic) {
		ArrayList<Items> all = new ArrayList<Items>();

		for(Items item : Dummy.foods) all.add(item);
		for(Items item : Dummy.recipes) all.add(item);

		return searchUnpublishedItems(token, isPublic, all);
	}
	public ArrayList<Items> searchUnpublishedItems(String token, boolean isPublic, ArrayList<Items> all) {
		ArrayList<Items> items = new ArrayList<Items>();

		for(Items item : all) {
			if(item.isPublic() == isPublic)
				if(item.getName().toLowerCase().contains(token.toLowerCase())) {
					if(item instanceof Food) items.add(item);
					if (item instanceof Recipe) {
						Recipe recipe = (Recipe) item;
						if(tokenMatch(token, recipe.getName())     ||
						   tokenMatch(token, recipe.getTutorial()) ||
						   productsTokenMatch(token, recipe.getIngredients()))
							items.add(item);
					}
				}
		}

		return items;
	}

	/**
	 * 
	 * @param dietLog
	 */
	public void removeReport(DietLog dietLog) {
		// TODO - implement DataManager.removeReport
		throw new UnsupportedOperationException();
	}

	public String hashPassword(User user, String password) {
		// SHA-256 is virtually uncrackable, however due to it's
		// relatively fast computation times it's not ideal to store passwords with,
		// as rainbow table attacks could easily "crack" common password.
		// To combat this we will be using salt to encrypt the password.
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			// If we cannot use SHA-256 then crash the program,
			// as we cannot run without having this critical function.
			throw new RuntimeException("SHA-256 algorithm is required, however was not found.");
		}

		// Salt password in order to protect it from rainbow table attacks.
		String saltedPassword = user.getLastName() + password + user.getLastName() + user.getRegistrationDate().toString();
		byte[] hashedBytes = md.digest(saltedPassword.getBytes(StandardCharsets.UTF_8));
		
		// Convert hash bytes back to a string, as User contains
		// a string, and return it.
		return new String(hashedBytes, StandardCharsets.UTF_8);
	}

	private static boolean sanityCheckInputField(String input, boolean password) {
		if(input == null ||
		   input.length() <= (password?6:1) || // Password must be at least 6 characters long for security purposes.
		   input.length() > 512) return false;
		return true;
	}

	private static boolean productsTokenMatch(String token, ArrayList<String> products) {
		for(String s : products) {
			if(s.toLowerCase().contains(token.toLowerCase()))
				return true;
		}
		return false;
	}

	private static boolean tokenMatch(String token, String item) {
		return item != null && item.toLowerCase().contains(token.toLowerCase());
	}

}