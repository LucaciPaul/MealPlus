package lucacipaul.mealplus.backend;

import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.Date;

public class Dummy {
    public static ArrayList<Customer> customers = new ArrayList<Customer>();
	public static ArrayList<Adviser> advisers = new ArrayList<Adviser>();
	public static ArrayList<Admin> admins = new ArrayList<Admin>();
	public static ArrayList<Food> foods = new ArrayList<Food>();
	public static ArrayList<Food> unPublishedFoods = new ArrayList<Food>();
	public static ArrayList<Recipe> recipes = new ArrayList<Recipe>();
    public static ArrayList<Recipe> unPublishedRecipes = new ArrayList<Recipe>();
    public static ArrayList<DietLog> dietLogs = new ArrayList<DietLog>();
    public static ArrayList<Report> reports = new ArrayList<>();

    public static boolean magicHappenedOnce = false;

    public static void set() {

    	// Avoid calling this method more than once to avoid having duplicate dummies
    	magicHappenedOnce = true;

		//Foods
		Food food = new Food();

		ArrayList<Types> types = new ArrayList<Types>(); types.add(Types.LowSugar); types.add(Types.Meat);
		ArrayList<Amenities> amenities = new ArrayList<Amenities>(); amenities.add(Amenities.Cooker);
		ArrayList<Sellpoints> sellpoints = new ArrayList<Sellpoints>(); sellpoints.add(Sellpoints.Sainsbury); sellpoints.add(Sellpoints.Tesco);
		food.setName("Eggs"); food.setPublic(true); food.setTypes(types); food.setAmenities(amenities); food.setSellpoints(sellpoints);
		food.setCalories(100); food.setCarbs(10); food.setProteins(20); food.setFats(30);
		foods.add(food);

		food = new Food();
		types = new ArrayList<Types>(); types.add(Types.Vegan); types.add(Types.Vegetables);
		sellpoints = new ArrayList<Sellpoints>(); sellpoints.add(Sellpoints.Tesco);
		food.setName("Avocado Toast"); food.setPublic(true); food.setTypes(types); food.setAmenities(null); food.setSellpoints(sellpoints);
		food.setCalories(200); food.setCarbs(15); food.setProteins(22); food.setFats(30);
		foods.add(food);

		//Recipes
		Recipe recipe = new Recipe();

		types = new ArrayList<Types>(); types.add(Types.Vegan); types.add(Types.Vegetables);
		amenities = new ArrayList<Amenities>(); amenities.add(Amenities.Cooker);
		ArrayList<String> ingredients = new ArrayList<String>(); ingredients.add("1x Bread"); ingredients.add("1/4x Avocado");
		recipe.setFoodProduct(food);
		recipe.setName("Avocado Toast"); recipe.setFoodProduct(foods.get(1)); recipe.setPublic(true); recipe.setTypes(types); recipe.setAmenities(amenities);
		recipe.setTutorial("Lorem Ipsum\nlorem ipsum\nlOrem iPsum"); recipe.setIngredients(ingredients);
		recipe.setCalories(210); recipe.setCarbs(40); recipe.setProteins(50); recipe.setFats(60);
		recipes.add(recipe);

		//DietLogs
		DietLog dietLog = new DietLog();
		DietLogEntry dietLogEntry = new DietLogEntry();

		dietLogEntry.setFood(foods.get(0));
		dietLogEntry.setQuantity(100);

		dietLog.addMealEntry(dietLogEntry, Meal.Breakfast);
		dietLog.addMealEntry(dietLogEntry, Meal.SnackOne);
		dietLog.addMealEntry(dietLogEntry, Meal.Lunch);
		dietLog.addMealEntry(dietLogEntry, Meal.SnackTwo);
		dietLog.addMealEntry(dietLogEntry, Meal.Dinner);
		dietLog.addMealEntry(dietLogEntry, Meal.SnackThree);

		// Dummy values, they would only have to be set once when the Customer clicks the "Confirm" button from ViewItem.java
		dietLog.setCaloriesTotal(((dietLogEntry.getQuantity()*dietLogEntry.getEntry().getCalories())/100)*6);
		dietLog.setCarbsTotal(((dietLogEntry.getQuantity()*dietLogEntry.getEntry().getCarbs())/100)*6);
		dietLog.setProteinsTotal(((dietLogEntry.getQuantity()*dietLogEntry.getEntry().getProteins())/100)*6);
		dietLog.setFatsTotal(((dietLogEntry.getQuantity()*dietLogEntry.getEntry().getFats())/100)*6);

		dietLogs.add(dietLog);

		//Customers
    	Customer c = new Customer();

    	c.setFirstName("Paul"); c.setLastName("Lucaci"); c.setEmail("p@email.com"); c.setRegistrationDate(new Date()); c.setPwd(DataManager.getInstance().hashPassword(c, "password"));
    	c.setGender(Gender.Male); c.setActivityLevel(ActivityLevel.Medium); c.setGoal(Goal.LoseWeight); c.setSize(170); c.setWeight(70); c.setAge(20);
    	c.setDefaultNutritionalValues();
    	c.addFrequentlyEaten(new DietLogEntry(food)); c.addFrequentlyEaten(new DietLogEntry(food)); c.addFrequentlyEaten(new DietLogEntry(food));
    	dietLogs.get(0).setCustomer(c);
    	c.setDietLog(dietLogs.get(0));
    	customers.add(c);
    	
    	c = new Customer();

    	dietLog = new DietLog();
		c.setFirstName("Sadonis"); c.setLastName("Ignas"); c.setEmail("s@email.com"); c.setRegistrationDate(new Date()); c.setPwd(DataManager.getInstance().hashPassword(c, "password"));
		c.setGender(Gender.Male); c.setActivityLevel(ActivityLevel.High); c.setGoal(Goal.LoseWeight); c.setSize(170); c.setWeight(65); c.setAge(19);
		c.setDefaultNutritionalValues();
		dietLogs.add(dietLog);
		c.setDietLog(dietLogs.get(1));
		customers.add(c);
    	
    	// Advisers
    	Adviser adv = new Adviser();
    	
    	adv.setFirstName("Kevin"); adv.setLastName("Horeau"); adv.setEmail("k@email.com"); adv.setRegistrationDate(new Date()); adv.setPwd(DataManager.getInstance().hashPassword(adv, "password"));
    	advisers.add(adv);
    	
    	adv = new Adviser();

    	adv.setFirstName("Viktorija"); adv.setLastName("Kolesnikova"); adv.setEmail("v@email.com"); adv.setRegistrationDate(new Date()); adv.setPwd(DataManager.getInstance().hashPassword(adv, "password"));
    	adv.getAssociatedCustomers().add(customers.get(0));
    	customers.get(0).setAdviser(adv); customers.get(0).setPendingRequest(false);
    	advisers.add(adv);
    }
}