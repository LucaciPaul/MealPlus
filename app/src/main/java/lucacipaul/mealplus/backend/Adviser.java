package lucacipaul.mealplus.backend;

import java.util.*;

public class Adviser extends User {

	private ArrayList<Customer> customers = new ArrayList<>();
	private String regNo;
	private String occupation;
	private String addr1;
	private String addr2;
	private String postCode;
	private String phoneNo;
	private boolean approved;

	public Adviser() {}

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
	public void sendAssociationRequest(Customer customer) {
		customer.setAdviser(this);
		customer.setPendingRequest(true);
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