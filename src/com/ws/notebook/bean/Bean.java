package com.ws.notebook.bean;

public class Bean {
	private String day, time, content; 
	

	public Bean(String day, String time, String content) {
		super();
		this.day = day;
		this.time = time;
		this.content = content;
	}

	

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
