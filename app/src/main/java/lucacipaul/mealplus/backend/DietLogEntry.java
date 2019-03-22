package lucacipaul.mealplus.backend;

import android.os.Parcel;
import android.os.Parcelable;

public class DietLogEntry implements Parcelable {

	private Recipe recipe;
	private Food food;
	private float quantity;
	private Meal meal = null;

	public DietLogEntry() {}
	public DietLogEntry(Items item) {
		if(item instanceof Recipe) recipe = (Recipe)item;
		else if(item instanceof Food) food = (Food)item;
	}

	protected DietLogEntry(Parcel in) {
		recipe = in.readParcelable(Recipe.class.getClassLoader());
		food = in.readParcelable(Food.class.getClassLoader());
		quantity = in.readFloat();
		meal = (Meal) in.readSerializable();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(recipe, flags);
		dest.writeParcelable(food, flags);
		dest.writeFloat(quantity);
		dest.writeSerializable(meal);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<DietLogEntry> CREATOR = new Creator<DietLogEntry>() {
		@Override
		public DietLogEntry createFromParcel(Parcel in) {
			return new DietLogEntry(in);
		}

		@Override
		public DietLogEntry[] newArray(int size) {
			return new DietLogEntry[size];
		}
	};

	public Food getFood() {
		return this.food;
	}

	/**
	 * 
	 * @param food
	 */
	public void setFood(Food food) {
		this.food = food;
	}

	public Recipe getRecipe() {
		return this.recipe;
	}

	/**
	 * 
	 * @param recipe
	 */
	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	public float getQuantity() {
		return this.quantity;
	}

	/**
	 * 
	 * @param quantity
	 */
	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}

	public Items getEntry() {
		if(food != null) return food;
		return recipe;
	}

	public Meal getMeal() {
		return this.meal;
	}

	public void setMeal(Meal meal) {
		this.meal = meal;
	}
}