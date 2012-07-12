package com.lordx.finance;

import java.io.Serializable;

import android.widget.DatePicker;

public class Date implements Serializable, Comparable<Date>{
	
	private static final long serialVersionUID = 1L;
	private byte day, month;
	private short year;
	public Date(int day, int month, int year){
		this.day = (byte)day;
		this.month = (byte)month;
		this.year = (short)year;
	}
	
	public Date(DatePicker d){
		this(d.getDayOfMonth(), d.getMonth()+1, d.getYear());
	}
	
	public int getDay(){
		return day;
	}
	
	public int getMonth(){
		return month;
	}
	
	public int getYear(){
		return year;
	}
	
	@Override
	public String toString(){
		return day + "/" + month + "/" + year;
	}

	@Override
	public int compareTo(Date other) {
		if(year != other.year)
			return compareValues(year, other.year);
		if(month != other.month)
			return compareValues(month, other.month);
		if(day != other.day)
			return compareValues(day, other.day);
		return 0;
	}
	
	private int compareValues(int a, int b){
		return a < b ? -1 : 1;
	}
	
	@Override
	public boolean equals(Object outra){
		return compareTo((Date)outra) == 0;
	}
}