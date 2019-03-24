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
	private float caloriesTotal;
	private float carbsTotal;
	private float fatsTotal;
	private float proteinsTotal;

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
		float calories=0;
		for (DietLogEntry entry: getBreakfast()) {
			calories += entry.getEntry().getCalories();
		}
		for (DietLogEntry entry: getSnack1()) {
			calories += entry.getEntry().getCalories();
		}
		for (DietLogEntry entry: getLunch()) {
			calories += entry.getEntry().getCalories();
		}
		for (DietLogEntry entry: getSnack2()) {
			calories += entry.getEntry().getCalories();
		}
		for (DietLogEntry entry: getDinner()) {
			calories += entry.getEntry().getCalories();
		}
		for (DietLogEntry entry: getSnack3()) {
			calories += entry.getEntry().getCalories();
		}

		System.out.println(calories);
		this.setCaloriesTotal(calories);
		return this.caloriesTotal;
	}

	public float getCarbsTotal() {
		float carbs=0;
		for (DietLogEntry entry: getBreakfast()) {
			carbs += entry.getEntry().getCarbs();
		}
		for (DietLogEntry entry: getSnack1()) {
			carbs += entry.getEntry().getCarbs();
		}
		for (DietLogEntry entry: getLunch()) {
			carbs += entry.getEntry().getCarbs();
		}
		for (DietLogEntry entry: getSnack2()) {
			carbs += entry.getEntry().getCarbs();
		}
		for (DietLogEntry entry: getDinner()) {
			carbs += entry.getEntry().getCarbs();
		}
		for (DietLogEntry entry: getSnack3()) {
			carbs += entry.getEntry().getCarbs();
		}

		setCarbsTotal(carbs);
		return this.carbsTotal;
	}

	public float getFatsTotal() {
		float fats=0;
		for (DietLogEntry entry: getBreakfast()) {
			fats += entry.getEntry().getFats();
		}
		for (DietLogEntry entry: getSnack1()) {
			fats += entry.getEntry().getFats();
		}
		for (DietLogEntry entry: getLunch()) {
			fats += entry.getEntry().getFats();
		}
		for (DietLogEntry entry: getSnack2()) {
			fats += entry.getEntry().getFats();
		}
		for (DietLogEntry entry: getDinner()) {
			fats += entry.getEntry().getFats();
		}
		for (DietLogEntry entry: getSnack3()) {
			fats += entry.getEntry().getFats();
		}

		setFatsTotal(fats);
		return this.fatsTotal;
	}

	public float getProteinsTotal() {
		float proteins=0;
		for (DietLogEntry entry: getBreakfast()) {
			proteins += entry.getEntry().getProteins();
		}
		for (DietLogEntry entry: getSnack1()) {
			proteins += entry.getEntry().getProteins();
		}
		for (DietLogEntry entry: getLunch()) {
			proteins += entry.getEntry().getProteins();
		}
		for (DietLogEntry entry: getSnack2()) {
			proteins += entry.getEntry().getProteins();
		}
		for (DietLogEntry entry: getDinner()) {
			proteins += entry.getEntry().getProteins();
		}
		for (DietLogEntry entry: getSnack3()) {
			proteins += entry.getEntry().getProteins();
		}

		setProteinsTotal(proteins);
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