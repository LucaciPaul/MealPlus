package lucacipaul.mealplus.backend;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.*;

public class Recipe extends Items implements Parcelable {

	private Food foodProduct;
	private ArrayList<String> ingredients;
	private String tutorial;

	public Recipe() {}

	protected Recipe(Parcel in) {
		super(in);
		foodProduct = in.readParcelable(Food.class.getClassLoader());
		ingredients = in.createStringArrayList();
		tutorial = in.readString();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		super.writeToParcel(dest, flags);
		dest.writeParcelable(foodProduct, flags);
		dest.writeStringList(ingredients);
		dest.writeString(tutorial);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
		@Override
		public Recipe createFromParcel(Parcel in) {
			return new Recipe(in);
		}

		@Override
		public Recipe[] newArray(int size) {
			return new Recipe[size];
		}
	};

	public ArrayList<String> getIngredients() {
		return this.ingredients;
	}

	/**
	 * 
	 * @param ingredients
	 */
	public void setIngredients(ArrayList<String> ingredients) {
		// Is this all we need to do here? Strange that this was not automatically completed as a getter and setter, am I missing something here?
		this.ingredients = ingredients;
	}

	public String getTutorial() {
		return this.tutorial;
	}

	/**
	 * 
	 * @param tutorial
	 */
	public void setTutorial(String tutorial) {
		this.tutorial = tutorial;
	}

	/**
	 * 
	 * @param food
	 */
	public void setFoodProduct(Food food) {
		this.foodProduct = food;
	}

	public Food getFoodProduct() {
		return this.foodProduct;
	}


}