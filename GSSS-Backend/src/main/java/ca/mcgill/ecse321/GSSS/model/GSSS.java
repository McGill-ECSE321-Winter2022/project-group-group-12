package ca.mcgill.ecse321.GSSS.model;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * The system class that composes all of the other classes. Has a OneToMany
 * association with all other classes in the system and an id to use as a PK.
 * 
 * @author Wassim Jabbour
 *
 */

@Entity
public class GSSS {

	// Attributes
	Owner owner;
	Set<Employee> employees;
	Set<Customer> customers;
	Set<Address> addresses;
	Set<Purchase> purchases;
	Set<Order> orders;
	Set<QuantityOrdered> quantitiesOrdered;
	Set<Item> items;
	Set<ItemCategory> itemCategories;
	Set<Shift> shifts;
	Set<BusinessHour> businessHours;
	String id; // Since none of the above attributes can be used as a primary key

	@Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@OneToOne(cascade = { CascadeType.ALL })
	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	@OneToMany(cascade = { CascadeType.ALL })
	public Set<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(Set<Employee> employees) {
		this.employees = employees;
	}

	@OneToMany(cascade = { CascadeType.ALL })
	public Set<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(Set<Customer> customers) {
		this.customers = customers;
	}

	@OneToMany(cascade = { CascadeType.ALL })
	public Set<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}

	@OneToMany(cascade = { CascadeType.ALL })
	public Set<Purchase> getPurchases() {
		return purchases;
	}

	public void setPurchases(Set<Purchase> purchases) {
		this.purchases = purchases;
	}

	@OneToMany(cascade = { CascadeType.ALL })
	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	@OneToMany(cascade = { CascadeType.ALL })
	public Set<QuantityOrdered> getQuantitiesOrdered() {
		return quantitiesOrdered;
	}

	public void setQuantitiesOrdered(Set<QuantityOrdered> quantitiesOrdered) {
		this.quantitiesOrdered = quantitiesOrdered;
	}

	@OneToMany(cascade = { CascadeType.ALL })
	public Set<Item> getItems() {
		return items;
	}

	public void setItems(Set<Item> items) {
		this.items = items;
	}

	@OneToMany(cascade = { CascadeType.ALL })
	public Set<ItemCategory> getItemCategories() {
		return itemCategories;
	}

	public void setItemCategories(Set<ItemCategory> itemCategories) {
		this.itemCategories = itemCategories;
	}

	@OneToMany(cascade = { CascadeType.ALL })
	public Set<Shift> getShifts() {
		return shifts;
	}

	public void setShifts(Set<Shift> shifts) {
		this.shifts = shifts;
	}

	@OneToMany(cascade = { CascadeType.ALL })
	public Set<BusinessHour> getBusinessHours() {
		return businessHours;
	}

	public void setBusinessHours(Set<BusinessHour> businessHours) {
		this.businessHours = businessHours;
	}

}