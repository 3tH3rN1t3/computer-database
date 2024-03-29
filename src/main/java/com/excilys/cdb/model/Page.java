package com.excilys.cdb.model;

import java.util.ArrayList;

public class Page<T> {
	
	public static final int MAX_ITEMS = 10;
	private int pageNumber;
	private final int totalPages;
	private ArrayList<T> items = new ArrayList<T>();
	
	public Page(int pageNumber, int totalPages) {
		this.pageNumber = pageNumber;
		this.totalPages = totalPages;
	}
	
	public void setItems(ArrayList<T> items) {
		this.items = items;
	}
	
	public String toString() {
		String page = "\n";
		for (T t : items) {
			page += t + "\n";
		}
		page += ("\nPage " + pageNumber + "/" + totalPages + "\n");
		return page;
	}
}
