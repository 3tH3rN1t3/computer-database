package com.excilys.cdb.model;

public class Page {
	
	private int maxItems = 10;
	private int numPage = 1;
	private int totalItems = 0;
	private SearchBy searchBy = SearchBy.NAME;
	private String search = "";
	private OrderBy orderBy = OrderBy.ID;
	private Order order = Order.ASC;
	
	public Page() {
		
	}
	
	public Page(int totalItems) {
		this.totalItems = totalItems;
	}
	
	public Page(int maxItems, int numPage, int totalItems) {
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
	
	public void setSearchBy(SearchBy searchBy) {
		this.searchBy = searchBy;
	}
	
	public void setSearch(String search) {
		this.search = search;
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
	
	public SearchBy getSearchBy() {
		return searchBy;
	}
	
	public String getSearch() {
		return search;
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
		return "Searching "+search+" by "+searchBy.toString().toLowerCase()+" Max items: "+this.maxItems+" Page "+this.numPage+"/"+this.getMaxPage()+" Ordered by "+this.getOrderBy()+" "+this.order;
		
	}
}
