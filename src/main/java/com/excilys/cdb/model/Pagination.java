package com.excilys.cdb.model;

public class Pagination {
	
	private int maxItems = 10;
	private int numPage = 1;
	private int totalItems = 0;
	private OrderBy orderBy = OrderBy.ID;
	private Order order = Order.ASC;
	
	public Pagination() {
		
	}
	
	public Pagination(int totalItems) {
		this.totalItems = totalItems;
	}
	
	public Pagination(int maxItems, int numPage, int totalItems) {
		this.maxItems = maxItems;
		this.numPage = numPage;
		this.totalItems = totalItems;
	}
	
	public void setMaxItems(int maxItems) {
		this.maxItems = maxItems;
	}
	
	public void setNumPage(int numPage) {
		this.numPage = numPage;
	}
	
	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;
	}
	
	public void setOrderBy(OrderBy order) {
		this.orderBy = order;
	}
	
	public void setOrder(Order desc) {
		this.order = desc;
	}
	
	public int getMaxItems() {
		return maxItems;
	}
	
	public int getNumPage() {
		return numPage;
	}
	
	public int getTotalItems() {
		return totalItems;
	}
	
	public OrderBy getOrderBy() {
		return orderBy;
	}
	
	public String getOrder() {
		return order.toString();
	}
	
	public int getMaxPage() {
		return totalItems/maxItems + (totalItems%maxItems==0 ? 0 : 1);
	}
	
	public String toString() {
		return "Max items: "+this.maxItems+" Page "+this.numPage+"/"+this.getMaxPage()+"Ordered by "+this.getOrderBy()+" "+this.order;
		
	}
}
