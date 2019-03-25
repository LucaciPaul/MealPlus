package lucacipaul.mealplus.backend;

import java.util.*;

public class Customer extends User {

	private Adviser adviser;
	private DietLog dietLog;
	private ArrayList<Report> reports = new ArrayList<Report>();
	private ArrayList<Food> ownedFood = new ArrayList<Food>();
	private ArrayList<Recipe> ownedRecipes = new ArrayList<Recipe>();
	private int age;
	private float size;
	private float weight;
	private Gender gender;
	private ActivityLevel activityLevel;
	private Goal goal;

	// Check end of file for more details
	private boolean pendingRequest;

	// Current nutritional values
	private float carbsPerDay;
	private float proteinsPerDay;
	private float fatsPerDay;
	private float caloriesPerDay;
	// These values CAN be changed if Customer's Adviser(if there is any)
	// decides to change Customer's Nutritional Settings.
	// These nutritional values WILL be passed to the newly generated report if the
	// DietLog will be closed.

	private ArrayList<DietLogEntry> dislikedItems = new ArrayList<>();
	private HashMap<Items, Integer> frequentlyEaten = new HashMap<>();

	public Customer() {}

	public int getAge() {
		return this.age;
	}

	/**
	 * 
	 * @param age
	 */
	public void setAge(int age) {
		this.age = age;
	}

	public float getSize() {
		return this.size;
	}

	/**
	 * 
	 * @param size
	 */
	public void setSize(float size) {
		this.size = size;
	}

	public float getWeight() {
		return this.weight;
	}

	/**
	 * 
	 * @param weight
	 */
	public void setWeight(float weight) {
		this.weight = weight;
	}

	public Gender getGender() {
		return this.gender;
	}

	/**
	 * 
	 * @param gender
	 */
	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public ActivityLevel getActivityLevel() {
		return this.activityLevel;
	}

	/**
	 * 
	 * @param activityLevel
	 */
	public void setActivityLevel(ActivityLevel activityLevel) {
		this.activityLevel = activityLevel;
	}

	public Goal getGoal() {
		return this.goal;
	}

	/**
	 * 
	 * @param goal
	 */
	public void setGoal(Goal goal) {
		this.goal = goal;
	}

	public boolean getPendingRequest() {
		return this.pendingRequest;
	}

	/**
	 * 
	 * @param pendingRequest
	 */
	public void setPendingRequest(boolean pendingRequest) {
		this.pendingRequest = pendingRequest;
	}

	public float getCarbsPerDay() {
		return this.carbsPerDay;
	}

	/**
	 * 
	 * @param carbsPerDay
	 */
	public void setCarbsPerDay(float carbsPerDay) {
		this.carbsPerDay = carbsPerDay;
	}

	public float getProteinsPerDay() {
		return this.proteinsPerDay;
	}

	/**
	 * 
	 * @param proteinsPerDay
	 */
	public void setProteinsPerDay(float proteinsPerDay) {
		this.proteinsPerDay = proteinsPerDay;
	}

	public float getFatsPerDay() {
		return this.fatsPerDay;
	}

	/**
	 * 
	 * @param fatsPerDay
	 */
	public void setFatsPerDay(float fatsPerDay) {
		this.fatsPerDay = fatsPerDay;
	}

	public float getCaloriesPerDay() {
		return this.caloriesPerDay;
	}

	/**
	 * 
	 * @param caloriesPerDay
	 */
	public void setCaloriesPerDay(float caloriesPerDay) {
		this.caloriesPerDay = caloriesPerDay;
	}

	public void setDefaultNutritionalValues() {

		// Formula calculated by the Harris-Benedict equation (https://www.livestrong.com/article/178764-caloric-intake-formula/)
		if(this.gender == Gender.Female) {
			this.caloriesPerDay = (int) (655.1 + 9.563 * this.getWeight() + 1.850 * this.getSize() - 4.676 * this.getAge());
		} else {
			this.caloriesPerDay = (int)(66.5 + 13.75*this.getWeight() + 5.003*this.getSize()-6.755*this.getAge());
		}

		if(this.goal == Goal.GainWeight) this.caloriesPerDay *= 1.1;
		else if(this.goal == Goal.LoseWeight) this.caloriesPerDay *= 0.9;

		// Calculated according to https://en.m.wikipedia.org/wiki/Harris%E2%80%93Benedict_equation
		if(this.activityLevel == ActivityLevel.High) this.caloriesPerDay *= 2.25;
		if(this.activityLevel == ActivityLevel.Medium) this.caloriesPerDay *= 1.76;
		if(this.activityLevel == ActivityLevel.Low) this.caloriesPerDay *= 1.53;

		// Carbs - %50 of calories divided by 4.
		// Fats - %30 of calories divided by 8.
		// Proteins - %20 of calories divided by 4.
		// All values are rounded up to 1 decimal place.
		this.setCarbsPerDay((float)Math.round(this.caloriesPerDay*0.5/4*10)/10);
		this.setFatsPerDay((float)Math.round(this.caloriesPerDay*0.3/8*10)/10);
		this.setProteinsPerDay((float)Math.round(this.caloriesPerDay*0.2/4*10)/10);
		this.setCaloriesPerDay((float)Math.round(this.caloriesPerDay*10)/10);
	}

	/**
	 * 
	 * @param adviser
	 */
	public void setAdviser(Adviser adviser) {
		if(adviser == null) {
			getAdviser().getAssociatedCustomers().remove(this);
		}

		this.adviser = adviser;
	}

	public Adviser getAdviser() {
		return this.adviser;
	}

	public ArrayList<Report> getReports() {
		return this.reports;
	}

	/**
	 * 
	 * @param report
	 */
	public void addReport(Report report) {
		this.reports.add(report);
	}

	/**
	 * 
	 * @param dietLog
	 */
	public void setDietLog(DietLog dietLog) {
		this.dietLog = dietLog;
	}

	public DietLog getDietLog() {
		return this.dietLog;
	}

	public ArrayList<Food> getOwnedFood() {
		return this.ownedFood;
	}

	public ArrayList<Recipe> getOwnedRecipes() {
		return this.ownedRecipes;
	}

	/**
	 * 
	 * @param ownedFood
	 */
	public void submitFoodForReview(Food ownedFood) {
		ownedFood.setPublic(false); // Make sure the item is not approved.
		Dummy.unPublishedFoods.add(ownedFood); // Add to global foods as well, will be used globally only once it gets approved (setPublic).
	}

	/**
	 * 
	 * @param ownedRecipe
	 */
	public void submitRecipeForReview(Recipe ownedRecipe) {
		ownedRecipe.setPublic(false); // Make sure the item is not approved.
		Dummy.unPublishedRecipes.add(ownedRecipe); // Add to global recipes as well, will be used globally only once it gets approved (setPublic).
	}

	public ArrayList<DietLogEntry> getDislikedItems() {
		return this.dislikedItems;
	}

	/**
	 *
	 * @param dislikedFood
	 */
	public void dislikeFood(Food dislikedFood) {
		DietLogEntry entry = new DietLogEntry();
		entry.setFood(dislikedFood);
		this.dislikedItems.add(entry);
	}

	/**
	 *
	 * @param dislikedRecipe
	 */
	public void dislikeRecipe(Recipe dislikedRecipe) {
		DietLogEntry entry = new DietLogEntry();
		entry.setRecipe(dislikedRecipe);
		this.dislikedItems.add(entry);
	}

	/**
	 * 
	 * @param createdFood
	 */
	public void createFood(Food createdFood) {
		createdFood.setPublic(false); // Make sure the item is not approved.
		this.ownedFood.add(createdFood); // Add to personal food array.
	}

	/**
	 * 
	 * @param createdRecipe
	 */
	public void createRecipe(Recipe createdRecipe) {
		createdRecipe.setPublic(false); // Make sure the item is not approved.
		this.ownedRecipes.add(createdRecipe); // Add to personal recipe array.
	}

	public ArrayList<DietLogEntry> getFrequentlyEaten() {
		int defaultSize = frequentlyEaten.size() > 5 ? 5 : frequentlyEaten.size();
		return getFrequentlyEaten(defaultSize);
	}
	public ArrayList<DietLogEntry> getFrequentlyEaten(int count) {
		Object[] sorted = sortByValue(this.frequentlyEaten).entrySet().toArray();
		ArrayList<DietLogEntry> entries = new ArrayList<DietLogEntry>(count);

		for(int i = 0; i < count; i++)
			entries.add(new DietLogEntry(((Map.Entry<Items, Integer>)sorted[i]).getKey()));

		return entries;
	}

	private static HashMap<Items, Integer> sortByValue(HashMap<Items, Integer> hm)
	{
		// Create a list from elements of HashMap
		List<Map.Entry<Items, Integer> > list =
				new LinkedList<Map.Entry<Items, Integer> >(hm.entrySet());

		// Sort the list
		Collections.sort(list, new Comparator<Map.Entry<Items, Integer> >() {
			public int compare(Map.Entry<Items, Integer> o1,
							   Map.Entry<Items, Integer> o2)
			{
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});

		// put data from sorted list to hashmap
		HashMap<Items, Integer> temp = new LinkedHashMap<Items, Integer>();
		for (Map.Entry<Items, Integer> aa : list) {
			temp.put(aa.getKey(), aa.getValue());
		}
		return temp;
	}

	/**
	 * All foods that the customer eats should be called on this. This function will then
	 * count how often this food is eaten and determine by that if it's a frequently eaten food or not.
	 * To get the request we will use 'getFrequentlyEaten'.
	 * @param frequentlyEaten
	 */
	public void addFrequentlyEaten(DietLogEntry frequentlyEaten) {
		Items key = frequentlyEaten.getEntry();
		if(this.frequentlyEaten.containsKey(key))
			// If the item is in the array (previously eaten),
			// then we just increment the counter.
			this.frequentlyEaten.put(key, this.frequentlyEaten.get(key) + 1);
		else
			// The item is not in the array (never eaten before),
			// therefore add a new entry with the value 1 (eaten once).
			this.frequentlyEaten.put(key, 1);
	}

	public int adviserStatus() {
		if(this.getAdviser() != null &&
				this.getPendingRequest() == false) return 1; // Adviser is associated
		else if(this.getAdviser() != null &&
				this.getPendingRequest() == true) return 0; // An invitation is pending an answer
		return -1; // Adviser position is vacant for this Customer
	}
}