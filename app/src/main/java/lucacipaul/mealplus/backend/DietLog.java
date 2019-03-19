package lucacipaul.mealplus.backend;

import java.util.*;

public class DietLog {

	private Customer customer;
	private String date;
	private boolean closed;
	private ArrayList<DietLogEntry> breakfast;
	private ArrayList<DietLogEntry> snack1;
	private ArrayList<DietLogEntry> lunch;
	private ArrayList<DietLogEntry> snack2;
	private ArrayList<DietLogEntry> dinner;
	private ArrayList<DietLogEntry> snack3;
	private float caloriesTotal;
	private float carbsTotal;
	private float fatsTotal;
	private float proteinsTotal;

	public String getDate() {
		return this.date;
	}

	/**
	 * 
	 * @param date
	 */
	public void setDate(String date) {
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

	public Customer getCustomer() {
		return this.customer;
	}

	/**
	 * 
	 * @param customer
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
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
}