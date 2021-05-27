package com.excilys.cdb.model;

public class Pagination {
	
	private int maxItems = 10;
	private int numPage = 1;
	private int totalItems = 0;
	
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
	
	public int getMaxItems() {
		return maxItems;
	}
	
	public void setNumPage(int numPage) {
		this.numPage = numPage;
	}
	
	public int getNumPage() {
		return numPage;
	}
	
	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;
	}
	
	public int getTotalItems() {
		return totalItems;
	}
	
	public int getMaxPage() {
		return totalItems/maxItems + (totalItems%maxItems==0 ? 0 : 1);
	}
}
