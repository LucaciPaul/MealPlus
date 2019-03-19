package lucacipaul.mealplus.backend;

public class DietLogEntry {

	private Recipe recipe;
	private Food food;
	private float quantity;

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