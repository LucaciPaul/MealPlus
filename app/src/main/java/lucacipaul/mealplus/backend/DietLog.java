package lucacipaul.mealplus.backend;

import java.io.Serializable;
import java.util.*;

public class DietLog {

	private String email;
	private Date date;
	private boolean closed;
	private ArrayList<DietLogEntry> breakfast = new ArrayList<DietLogEntry>();
	private ArrayList<DietLogEntry> snack1 = new ArrayList<DietLogEntry>();
	private ArrayList<DietLogEntry> lunch = new ArrayList<DietLogEntry>();
	private ArrayList<DietLogEntry> snack2 = new ArrayList<DietLogEntry>();
	private ArrayList<DietLogEntry> dinner = new ArrayList<DietLogEntry>();
	private ArrayList<DietLogEntry> snack3 = new ArrayList<DietLogEntry>();
	private float caloriesTotal = 0;
	private float carbsTotal = 0;
	private float fatsTotal = 0;
	private float proteinsTotal = 0;

	public DietLog() {
	    date = new Date();
    }


	public Date getDate() {
		return this.date;
	}

	/**
	 * 
	 * @param date
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	public boolean isClosed() {
		return this.closed;
	}

	/**
	 * 
	 * @param closed
	 */
	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	public ArrayList<DietLogEntry> getBreakfast() {
		return this.breakfast;
	}

	public ArrayList<DietLogEntry> getSnack1() {
		return this.snack1;
	}

	public ArrayList<DietLogEntry> getLunch() {
		return this.lunch;
	}

	public ArrayList<DietLogEntry> getSnack2() {
		return this.snack2;
	}

	public ArrayList<DietLogEntry> getDinner() {
		return this.dinner;
	}

	public ArrayList<DietLogEntry> getSnack3() {
		return this.snack3;
	}

	public String getCustomer() {
		return this.email;
	}

	/**
	 * 
	 * @param email
	 */
	public void setCustomer(String email) {
		this.email = email;
	}

	/**
	 * 
	 * @param entry
	 * @param meal
	 */
	public void addMealEntry(DietLogEntry entry, Meal meal) {
		getMealArray(meal).add(entry);
	}

	public float getCaloriesTotal() {
		return this.caloriesTotal;
	}

	public float getCarbsTotal() {
		return this.carbsTotal;
	}

	public float getFatsTotal() {
		return this.fatsTotal;
	}

	public float getProteinsTotal() {
		return this.proteinsTotal;
	}

	/**
	 * 
	 * @param caloriesTotal
	 */
	public void setCaloriesTotal(float caloriesTotal) {
		this.caloriesTotal = caloriesTotal;
	}

	/**
	 * 
	 * @param carbsTotal
	 */
	public void setCarbsTotal(float carbsTotal) {
		this.carbsTotal = carbsTotal;
	}

	/**
	 * 
	 * @param fatsTotal
	 */
	public void setFatsTotal(float fatsTotal) {
		this.fatsTotal = fatsTotal;
	}

	/**
	 * 
	 * @param proteinsTotal
	 */
	public void setProteinsTotal(float proteinsTotal) {
		this.proteinsTotal = proteinsTotal;
	}

	public float getCaloriesForMeal(Meal meal) {
		float total = 0;
		ArrayList<DietLogEntry> items = getMealArray(meal);

		for(DietLogEntry item : items)
			total += item.getEntry().getCalories();

		return total;
	}

	private ArrayList<DietLogEntry> getMealArray(Meal meal) {
		switch (meal) {
			case Breakfast: return this.breakfast;
			case Lunch: return this.lunch;
			case Dinner: return this.dinner;
			case SnackOne: return this.snack1;
			case SnackTwo: return this.snack2;
			case SnackThree: return this.snack3;
			default: throw new IllegalArgumentException("Meal type not implemented in getMealArray().");
		}
	}

	public boolean isEmpty(){

		if(breakfast.isEmpty() &&
				snack1.isEmpty() &&
				lunch.isEmpty() &&
				snack2.isEmpty() &&
				dinner.isEmpty() &&
				snack3.isEmpty()) {
			return true;
		}

		return false;
	}
}