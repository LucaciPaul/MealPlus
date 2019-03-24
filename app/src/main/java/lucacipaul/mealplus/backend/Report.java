package lucacipaul.mealplus.backend;

public class Report {

	private Customer customer;
	private DietLog dietLog;
	private float caloriesPerDay;
	private float carbsPerDay;
	private float fatsPerDay;
	private float proteinsPerDay;

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