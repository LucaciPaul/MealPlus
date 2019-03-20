package lucacipaul.mealplus.backend;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.*;

public class Items implements Parcelable {

	private String name;
	private User author;
	private boolean isPublic;
	private ArrayList<Types> types;
	private ArrayList<Amenities> amenities;
	private float calories;
	private float carbs;
	private float fats;
	private float proteins;

	public Items() {}

	protected Items(Parcel in) {
		author = in.readParcelable(User.class.getClassLoader());
		name = in.readString();
		isPublic = in.readByte() != 0;
		calories = in.readFloat();
		carbs = in.readFloat();
		fats = in.readFloat();
		proteins = in.readFloat();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(author, flags);
		dest.writeString(name);
		dest.writeByte((byte) (isPublic ? 1 : 0));
		dest.writeFloat(calories);
		dest.writeFloat(carbs);
		dest.writeFloat(fats);
		dest.writeFloat(proteins);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<Items> CREATOR = new Creator<Items>() {
		@Override
		public Items createFromParcel(Parcel in) {
			return new Items(in);
		}

		@Override
		public Items[] newArray(int size) {
			return new Items[size];
		}
	};

	public String getName() {
		return this.name;
	}

	/**
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	public User getAuthor() {
		return this.author;
	}

	/**
	 * 
	 * @param author
	 */
	public void setAuthor(User author) {
		this.author = author;
	}

	public ArrayList<Types> getTypes() {
		return this.types;
	}

	public ArrayList<Amenities> getAmenities() {
		return this.amenities;
	}

	/**
	 * 
	 * @param types
	 */
	public void setTypes(ArrayList<Types> types) {
		this.types = types;
	}

	/**
	 * 
	 * @param amenities
	 */
	public void setAmenities(ArrayList<Amenities> amenities) {
		this.amenities = amenities;
	}

	public float getCalories() {
		return this.calories;
	}

	/**
	 * 
	 * @param calories
	 */
	public void setCalories(float calories) {
		this.calories = calories;
	}

	public float getCarbs() {
		return this.carbs;
	}

	/**
	 * 
	 * @param carbs
	 */
	public void setCarbs(float carbs) {
		this.carbs = carbs;
	}

	public float getFats() {
		return this.fats;
	}

	/**
	 * 
	 * @param fats
	 */
	public void setFats(float fats) {
		this.fats = fats;
	}

	public float getProteins() {
		return this.proteins;
	}

	/**
	 * 
	 * @param proteins
	 */
	public void setProteins(float proteins) {
		this.proteins = proteins;
	}

	public boolean isPublic() {
		return this.isPublic;
	}

	/**
	 * 
	 * @param isPublic
	 */
	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

}