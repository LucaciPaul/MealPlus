package lucacipaul.mealplus.backend;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.*;

public class Customer extends User implements Parcelable {

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
	private boolean pendingRequest;
	private float carbsPerDay;
	private float proteinsPerDay;
	private float fatsPerDay;
	private float caloriesPerDay;
	private ArrayList<DietLogEntry> dislikedItems = new ArrayList<DietLogEntry>();
	private ArrayList<DietLogEntry> frequentlyEaten = new ArrayList<DietLogEntry>();

	public Customer() {}

	protected Customer(Parcel in) {
		super(in);
		adviser = in.readParcelable(Adviser.class.getClassLoader());
		dietLog = in.readParcelable(DietLog.class.getClassLoader());
		reports = in.createTypedArrayList(Report.CREATOR);
		ownedFood = in.createTypedArrayList(Food.CREATOR);
		ownedRecipes = in.createTypedArrayList(Recipe.CREATOR);
		age = in.readInt();
		size = in.readFloat();
		weight = in.readFloat();
		gender = (Gender) in.readSerializable();
		activityLevel = (ActivityLevel) in.readSerializable();
		goal = (Goal) in.readSerializable();
		pendingRequest = in.readByte() != 0;
		carbsPerDay = in.readFloat();
		proteinsPerDay = in.readFloat();
		fatsPerDay = in.readFloat();
		caloriesPerDay = in.readFloat();
		dislikedItems = in.createTypedArrayList(DietLogEntry.CREATOR);
		frequentlyEaten = in.createTypedArrayList(DietLogEntry.CREATOR);
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		super.writeToParcel(dest, flags);
		dest.writeParcelable(adviser, flags);
		dest.writeParcelable(dietLog, flags);
		dest.writeTypedList(reports);
		dest.writeTypedList(ownedFood);
		dest.writeTypedList(ownedRecipes);
		dest.writeInt(age);
		dest.writeFloat(size);
		dest.writeFloat(weight);
		dest.writeSerializable(gender);
		dest.writeSerializable(activityLevel);
		dest.writeSerializable(goal);
		dest.writeByte((byte) (pendingRequest ? 1 : 0));
		dest.writeFloat(carbsPerDay);
		dest.writeFloat(proteinsPerDay);
		dest.writeFloat(fatsPerDay);
		dest.writeFloat(caloriesPerDay);
		dest.writeTypedList(dislikedItems);
		dest.writeTypedList(frequentlyEaten);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<Customer> CREATOR = new Creator<Customer>() {
		@Override
		public Customer createFromParcel(Parcel in) {
			return new Customer(in);
		}

		@Override
		public Customer[] newArray(int size) {
			return new Customer[size];
		}
	};

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
		// TODO - implement Customer.setDefaultNutritionalValues
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param adviser
	 */
	public void setAdviser(Adviser adviser) {
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
		return this.frequentlyEaten;
	}

	/**
	 * 
	 * @param frequentlyEaten
	 */
	public void addFrequentlyEaten(DietLogEntry frequentlyEaten) {
		this.frequentlyEaten.add(frequentlyEaten);
	}
}