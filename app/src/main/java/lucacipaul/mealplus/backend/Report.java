package lucacipaul.mealplus.backend;

import android.os.Parcel;
import android.os.Parcelable;

public class Report implements Parcelable {

	private Customer customer;
	private DietLog dietLog;
	private float caloriesPerDay;
	private float carbsPerDay;
	private float fatsPerDay;
	private float proteinsPerDay;

	public Report() {}

	protected Report(Parcel in) {
		customer = in.readParcelable(Customer.class.getClassLoader());
		dietLog = in.readParcelable(DietLog.class.getClassLoader());
		caloriesPerDay = in.readFloat();
		carbsPerDay = in.readFloat();
		fatsPerDay = in.readFloat();
		proteinsPerDay = in.readFloat();
	}

	public static final Creator<Report> CREATOR = new Creator<Report>() {
		@Override
		public Report createFromParcel(Parcel in) {
			return new Report(in);
		}

		@Override
		public Report[] newArray(int size) {
			return new Report[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(customer, flags);
		dest.writeParcelable(dietLog, flags);
		dest.writeFloat(caloriesPerDay);
		dest.writeFloat(carbsPerDay);
		dest.writeFloat(fatsPerDay);
		dest.writeFloat(proteinsPerDay);
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