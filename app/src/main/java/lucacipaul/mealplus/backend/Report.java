package lucacipaul.mealplus.backend;

public class Report {

	private Customer customer;

	// The closed DietLog
	private DietLog dietLog;

	// The nutritional values that once used to be Customer's.
	// Future implementations could allow comparing these values with
	// Customer's current ones and potentially draw charts.

	private float caloriesPerDay;
	private float carbsPerDay;
	private float fatsPerDay;
	private float proteinsPerDay;
	// These values CAN NO LONGER be changed if Customer's Adviser(if there is any)
	// decides to change Customer's Nutritional Settings.
	// However, if an Adviser changed Customer's Nutritional Settings and then the DietLog is closed,
	// these values will appear in the report the way the Adviser changed them, but they can't be changed
	// once the report is generated.

	public Report() {}

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

	public DietLog getDietLog() {
		return this.dietLog;
	}

	/**
	 * 
	 * @param dietLog
	 */
	public void setDietLog(DietLog dietLog) {
		this.dietLog = dietLog;
	}

	/**
	 * 
	 * @param caloriesPerDay
	 */
	public void setCaloriesPerDay(float caloriesPerDay) {
		this.caloriesPerDay = caloriesPerDay;
	}

	/**
	 * 
	 * @param carbsPerDay
	 */
	public void setCarbsPerDay(float carbsPerDay) {
		this.carbsPerDay = carbsPerDay;
	}

	/**
	 * 
	 * @param fatsPerDay
	 */
	public void setFatsPerDay(float fatsPerDay) {
		this.fatsPerDay = fatsPerDay;
	}

	/**
	 * 
	 * @param proteinsPerDay
	 */
	public void setProteinsPerDay(float proteinsPerDay) {
		this.proteinsPerDay = proteinsPerDay;
	}

	public float getCaloriesPerDay() {
		return this.caloriesPerDay;
	}

	public float getCarbsPerDay() {
		return this.carbsPerDay;
	}

	public float getFatsPerDay() {
		return this.fatsPerDay;
	}

	public float getProteinsPerDay() {
		return this.proteinsPerDay;
	}
}