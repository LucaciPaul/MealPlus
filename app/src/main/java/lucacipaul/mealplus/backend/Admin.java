package lucacipaul.mealplus.backend;

public class Admin extends User {

	/**
	 * 
	 * @param adviser
	 * @param allow
	 */
	public void reviewAdviser(Adviser adviser, boolean allow) {
		adviser.setApproved(allow);
	}

	/**
	 * 
	 * @param recipe
	 * @param allow
	 */
	public void reviewRecipe(Recipe recipe, boolean allow) {
		recipe.setPublic(allow);
	}

	/**
	 * 
	 * @param food
	 * @param allow
	 */
	public void reviewFood(Food food, boolean allow) {
		food.setPublic(allow);
	}

	/**
	 * 
	 * @param user
	 */
	public void changeUser(User user) {
		// TODO - implement Admin.changeUser
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param food
	 */
	public void removeFood(Food food) {
		Dummy.foods.remove(food);
		// TODO: remove all recipes that have this food?
	}

	/**
	 * 
	 * @param recipe
	 */
	public void removeRecipe(Recipe recipe) {
		Dummy.recipes.remove(recipe);
		// TODO: prob need to clear this somewhere else as well.
	}

	/**
	 * 
	 * @param food
	 */
	public void changeFood(Food food) {
		// TODO - implement Admin.changeFood
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param recipe
	 */
	public void changeRecipe(Recipe recipe) {
		// TODO - implement Admin.changeRecipe
		throw new UnsupportedOperationException();
	}

}