package ca.mcgill.ecse321.GSSS.model;

@Entity
public class Order extends Purchase {
	private OrderType orderType;
	private OrderStatus orderStatus;
	private int id;
	
	@Id
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}
	
	public OrderType getOrderType() {
		return this.orderType;
	}
	
	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	public OrderStatus getOrderStatus() {
		return this.orderStatus;
	}
	
	
}
