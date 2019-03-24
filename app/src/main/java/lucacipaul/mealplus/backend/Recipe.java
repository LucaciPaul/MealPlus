package lucacipaul.mealplus.backend;

import java.util.*;

public class Recipe extends Items {

	private Food foodProduct;
	private ArrayList<String> ingredients;
	private String tutorial;

	public Recipe() {}

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