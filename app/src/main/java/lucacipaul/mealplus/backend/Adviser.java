package lucacipaul.mealplus.backend;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.*;

public class Adviser extends User implements Parcelable {

	private ArrayList<Customer> customers;
	private String regNo;
	private String occupation;
	private String addr1;
	private String addr2;
	private String postCode;
	private String phoneNo;
	private boolean approved;

	public Adviser() {}

	protected Adviser(Parcel in) {
		super(in);
		customers = in.createTypedArrayList(Customer.CREATOR);
		regNo = in.readString();
		occupation = in.readString();
		addr1 = in.readString();
		addr2 = in.readString();
		postCode = in.readString();
		phoneNo = in.readString();
		approved = in.readByte() != 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		super.writeToParcel(dest, flags);
		dest.writeTypedList(customers);
		dest.writeString(regNo);
		dest.writeString(occupation);
		dest.writeString(addr1);
		dest.writeString(addr2);
		dest.writeString(postCode);
		dest.writeString(phoneNo);
		dest.writeByte((byte) (approved ? 1 : 0));
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<Adviser> CREATOR = new Creator<Adviser>() {
		@Override
		public Adviser createFromParcel(Parcel in) {
			return new Adviser(in);
		}

		@Override
		public Adviser[] newArray(int size) {
			return new Adviser[size];
		}
	};

	public String getRegNo() {
		return this.regNo;
	}

	/**
	 * 
	 * @param regNo
	 */
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public String getOccupation() {
		return this.occupation;
	}

	/**
	 * 
	 * @param occupation
	 */
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getAddr1() {
		return this.addr1;
	}

	/**
	 * 
	 * @param addr1
	 */
	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}

	public String getAddr2() {
		return this.addr2;
	}

	/**
	 * 
	 * @param addr2
	 */
	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}

	public String getPostCode() {
		return this.postCode;
	}

	/**
	 * 
	 * @param postCode
	 */
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getPhoneNo() {
		return this.phoneNo;
	}

	/**
	 * 
	 * @param phoneNo
	 */
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public ArrayList<Customer> getAssociatedCustomers() {
		return this.customers;
	}

	/**
	 * 
	 * @param customer
	 */
	public void addCustomer(Customer customer) {
		this.customers.add(customer);
	}

	/**
	 * 
	 * @param customer
	 */
	public int sendAssociationRequest(Customer customer) {
		if(customer.getAdviser() != null &&
				customer.getPendingRequest() == false) return -1;
		else if(customer.getAdviser() != null &&
				customer.getPendingRequest() == true) return 0;

		customer.setAdviser(this);
		customer.setPendingRequest(true);
		return 1;
	}

	public boolean getApproved() {
		return this.approved;
	}

	/**
	 * 
	 * @param approved
	 */
	public void setApproved(boolean approved) {
		this.approved = approved;
	}

}