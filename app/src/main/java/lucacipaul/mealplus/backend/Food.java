package lucacipaul.mealplus.backend;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.*;

public class Food extends Items implements Parcelable {

	private ArrayList<Sellpoints> sellpoints = new ArrayList<Sellpoints>();

	public Food() { }

	protected Food(Parcel in) {
		super(in);
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		super.writeToParcel(dest, flags);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<Food> CREATOR = new Creator<Food>() {
		@Override
		public Food createFromParcel(Parcel in) {
			return new Food(in);
		}

		@Override
		public Food[] newArray(int size) {
			return new Food[size];
		}
	};

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