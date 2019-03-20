package lucacipaul.mealplus.backend;

import android.os.Parcel;
import android.os.Parcelable;

public class DietLogEntry implements Parcelable {

	private Recipe recipe;
	private Food food;
	private float quantity;

	public DietLogEntry() {}
	public DietLogEntry(Items item) {
		if(item instanceof Recipe) recipe = (Recipe)item;
		else if(item instanceof Food) food = (Food)item;
	}

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
}