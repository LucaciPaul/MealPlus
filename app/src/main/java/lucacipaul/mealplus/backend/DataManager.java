package lucacipaul.mealplus.backend;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Comparator;

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
	 * @param customersOnly
	 */
	public ArrayList<User> searchAccount(String email, boolean customersOnly) {
		ArrayList<User> users = new ArrayList<User>();
		
		for (User usr : Dummy.customers) {
			if(usr.getEmail().contains(email.toLowerCase()))
					users.add((Customer)usr);
		}
		if(!customersOnly) {
			for (User usr : Dummy.advisers) {
				if (usr.getEmail().contains(email.toLowerCase()))
					users.add((Adviser) usr);
			}
			for (User usr : Dummy.admins) {
				if (usr.getEmail().contains(email.toLowerCase()))
					users.add((Admin) usr);
			}
		}
		return users;
	}
	public User searchSpecificAccount(String email) {

        ArrayList<User> users = searchAccount(email, false);
        if(users.isEmpty()) return null;
        return users.get(0);
    }

	private ArrayList<Items> filterItems(ArrayList<Items> items, ArrayList<Amenities> amenities, ArrayList<Types> types, ArrayList<Sellpoints> sellpoints) {
		ArrayList<Items> entries = new ArrayList<Items>();
		for(Items item : items) {

			// Special sellpoint check if item is Food or Recipe.
			// (because only Food has sellpoints).
			if(item instanceof Food) if(!DataManager.hasFilter(sellpoints, ((Food) item).getSellpoints())) continue;
			if(item instanceof Recipe) if(sellpoints != null && sellpoints.size() > 0) continue;
			
			// Do a general filter on items.
			if(DataManager.hasFilter(amenities, item.getAmenities()) &&
			   DataManager.hasFilter(types, item.getTypes()) ) {
				entries.add(item);
			}
		}
		return entries;
	}

	private static <T> boolean hasFilter(ArrayList<T> selectedFilters, ArrayList<T> itemFilters) {
		if(selectedFilters == null || selectedFilters.size() == 0) return true;
		if(itemFilters == null) return false;

		for (T t : itemFilters)
			if(selectedFilters.contains(t))
				return true;
		return false;
	}

	private static <T extends Items> void concatItems(ArrayList<Items> original, ArrayList<T> add) {
		for(T a : add)
		    if(!original.contains(a))
			    original.add(a);
	}

	private static ArrayList<Items> dietLogEntriesToItems(ArrayList<DietLogEntry> entries) {
		ArrayList<Items> items = new ArrayList<Items>();
		for(DietLogEntry entry : entries)
			items.add(entry.getEntry());
		return items;
	}

	private static ArrayList<DietLogEntry> itemsToDietLogEntries(ArrayList<Items> items) {
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

	private static ArrayList<Items> removeDuplicates(ArrayList<Items> items) {
		ArrayList<Items> newArray = new ArrayList<Items>();

		for(Items item : items) {
			if(!newArray.contains(item)) newArray.add(item);
		}

		return newArray;
	}

	private static <E> void removeDislikedItems(ArrayList<E> items, ArrayList<E> toRemove) {
		for(E e : toRemove)
			items.remove(e);
	}

	private static float getCaloriesForMealPercentage(Meal meal) {
		switch (meal){
			case Breakfast: return 0.15f;
			case Lunch: return 0.25f;
			case Dinner: return 0.15f;
			default: return 0.15f; // Snack1/2/3.
		}
	}

	private static void filterRecommendations(ArrayList<Items> items, int caloriesAvailable, int carbsAvailable, int fatsAvailable, int proteinsAvailable) {
		// Remove items that are over the calorie budget.
		for (int i = items.size() - 1; i >= 0; i--) {
			Items item = items.get(i);
			// Do not show items that exceed the available limits.
			if((item.getCalories() > caloriesAvailable) ||
                item.getCarbs() > carbsAvailable        ||
                item.getFats() > fatsAvailable          ||
                item.getProteins() > proteinsAvailable) items.remove(item);
		}

		// Once the items that are over the calorie budget removed, then we
		// can sort everything by how close it is to our calorie budget.
		// (should be sort by highest)
		Collections.sort(items, new Comparator<Items>(){
			public int compare(Items o1, Items o2)
			{ return (int)(
                    (o2.getCalories() + o2.getCarbs() + o2.getFats() + o2.getProteins())
                    -
                    (o1.getCalories() + o2.getCarbs() + o2.getFats() + o2.getProteins()));
			}
		});
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
	public ArrayList<DietLogEntry> searchItems(Customer customer, Meal meal,
			String token,
			ArrayList<Amenities> amenities, ArrayList<Types> types, ArrayList<Sellpoints> sellpoints,
			boolean searchFrequentlyEaten, boolean searchSelfMade, boolean recommend)
	{

		// All items that need to be filtered need to be stored in this array,
		// this will be then filtered and placed into 'filtered'.
		ArrayList<Items> unfiltered = new ArrayList<Items>();

		// Add items to unfiltered depending on settings.
		if(searchSelfMade) {
            concatItems(unfiltered, customer.getOwnedFood());
            concatItems(unfiltered, customer.getOwnedRecipes());
		}
		if(searchFrequentlyEaten) {
            concatItems(unfiltered, dietLogEntriesToItems(customer.getFrequentlyEaten()));
		}
		if(!searchSelfMade && !searchFrequentlyEaten) {
            concatItems(unfiltered, Dummy.foods);
            concatItems(unfiltered, Dummy.recipes);
		}

		// Filter items by token and other settings.
		ArrayList<Items> tokenSearchedItems = searchUnpublishedItems(token, true, unfiltered);
		ArrayList<Items> filtered = filterItems(tokenSearchedItems, amenities, types, sellpoints);

        removeDislikedItems(filtered, dietLogEntriesToItems(customer.getDislikedItems()));

        if(recommend) {

			int caloriesForMeal = (int)((customer.getCaloriesPerDay()-customer.getDietLog().getCaloriesTotal()) * getCaloriesForMealPercentage(meal));
            int carbsForMeal = (int)((customer.getCarbsPerDay()-customer.getDietLog().getCarbsTotal()) * getCaloriesForMealPercentage(meal));
            int fatsForMeal = (int)((customer.getFatsPerDay()-customer.getDietLog().getFatsTotal()) * getCaloriesForMealPercentage(meal));
            int proteinsForMeal = (int)((customer.getProteinsPerDay()-customer.getDietLog().getProteinsTotal()) * getCaloriesForMealPercentage(meal));


            filterRecommendations(filtered, caloriesForMeal, carbsForMeal, fatsForMeal, proteinsForMeal);
		}

		return itemsToDietLogEntries(removeDuplicates(filtered));
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
		User user = searchSpecificAccount(email);
		if(user != null) {
			if(user.getPwd().equals(hashPassword(user, pwd))) { // Critical bug fixed here, make sure to have { } @Paul.
				if(user instanceof Customer) {
					loggedUser = (Customer)user;
					return (Customer)user;
				} else if(user instanceof Adviser) {
					loggedUser = (Adviser)user;
					return (Adviser)user;
				}else if(user instanceof Admin) {
					loggedUser = (Admin)user;
					return (Admin)user;
				}
			}
		}
		// Email was not found in the users array.
		return null;
	}

	public void logout() {
		loggedUser = null;
	}

	/**
	 * 
	 * @param user
	 */
	public boolean register(User user) {

		// Sanity checks for user inputted data.
		if(user == null) return false;
		if(!user.getEmail().contains("@") || !sanityCheckInputField(user.getEmail(), false))
		    return false;
		if(!sanityCheckInputField(user.getFirstName(), false) || !sanityCheckInputField(user.getLastName(), false))
		    return false;
		if(!sanityCheckInputField(user.getPwd(), true)) return false;

		// Sanity checks for advisers.
		if(user instanceof Adviser) {
			Adviser adviser = (Adviser)user;
			if(!sanityCheckAdviser(adviser)) return false; // Registration number or Phone number already exists.
		}

		// Sanity checks to make sure the email
		// does not exist.
		User users = searchSpecificAccount(user.getEmail());
		if(users != null) {
			return false;
		}

		user.setPwd(hashPassword(user, user.getPwd())); // Hash password before adding it to the database.
		if(user instanceof Customer) {
			loggedUser = (Customer)user;
			return Dummy.customers.add((Customer) user);
		}
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
				if(adviser.getEmail().contains(email.toLowerCase())) {
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
				if((token == null || token.isEmpty()) || item.getName().toLowerCase().contains(token.toLowerCase())) {
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
		   input.length() <= (password?5:0) || // Password must be at least 6 characters long for security purposes.
		   input.length() > 512) return false;
		return true;
	}

	private static boolean productsTokenMatch(String token, ArrayList<String> products) {
		for(String s : products) {
			if((token == null || token.isEmpty()) || s.toLowerCase().contains(token.toLowerCase()))
				return true;
		}
		return false;
	}

	private static boolean tokenMatch(String token, String item) {
		return item != null && ((token == null || token.isEmpty()) || item.toLowerCase().contains(token.toLowerCase()));
	}

	// Checks if registrationNumber and phoneNumber are unique to this new adviser.
	// true if unique, false otherwise.
	private static boolean sanityCheckAdviser(Adviser adviser) {
		for (Adviser other : Dummy.advisers) {
			if(adviser.getRegNo().equals(other.getRegNo()) || adviser.getPhoneNo().equals(other.getPhoneNo()))
				return false;
		}
		return true;
	}

	public static ArrayList<String> parseItemNames(ArrayList<DietLogEntry> items) {
		ArrayList<String> itemNames = new ArrayList<String>();

		for(DietLogEntry item : items) {
			itemNames.add(item.getFood() != null? "Food: " + item.getEntry().getName():"Recipe: " + item.getEntry().getName());
		}

		return itemNames;
	}

}