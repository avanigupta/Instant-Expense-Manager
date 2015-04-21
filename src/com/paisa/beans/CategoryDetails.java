package com.paisa.beans;

public class CategoryDetails {

	@Override
	public String toString() {
		return "CategoryDetails [name=" + name + ", position=" + position
				+ ", isChecked=" + isChecked + ", iconSet=" + iconSet
				+ ", categoryAmount=" + categoryAmount + ", date=" + date + "]";
	}
	String name;
	int position;
	boolean isChecked;
	String iconSet;
	double categoryAmount;
	String date;


	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public double getCategoryAmount() {
		return categoryAmount;
	}
	public void setCategoryAmount(double categoryAmount) {
		this.categoryAmount = categoryAmount;
	}
	public String getIconSet() {			
		return iconSet;
	}
	public void setIconSet(String iconSet) {
		this.iconSet = iconSet;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public boolean isChecked() {
		return isChecked;
	}
	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
	
}
