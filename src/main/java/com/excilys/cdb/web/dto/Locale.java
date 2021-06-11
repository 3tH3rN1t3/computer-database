package com.excilys.cdb.web.dto;

public enum Locale {
	FR("Français"), EN("English");
	
	private String lang;
	
	private Locale(String lang) {
		this.lang = lang;
	}
	
	@Override
	public String toString() {
		return super.toString().toLowerCase();
	}
	
	public String getLang() {
		return lang;
	}
}
