package lucacipaul.mealplus.backend;

import java.util.*;

public class Food extends Items {

	private ArrayList<Sellpoints> sellpoints;

	public ArrayList<Sellpoints> getSellpoints() {
		return this.sellpoints;
	}

	/**
	 * 
	 * @param sellpoints
	 */
	public void setSellpoints(ArrayList<Sellpoints> sellpoints) {
		this.sellpoints = sellpoints;
	}

}