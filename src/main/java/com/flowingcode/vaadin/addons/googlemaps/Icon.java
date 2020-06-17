package com.flowingcode.vaadin.addons.googlemaps;

public class Icon {

	private String path;
	private String strokeColor;
	private String fillColor;
	private int fillOpacity;
	private int repeat;

	public Icon(String path, String strokeColor, String fillColor, int fillOpacity, int repeat) {
		super();
		this.path = path;
		this.strokeColor = strokeColor;
		this.fillColor = fillColor;
		this.fillOpacity = fillOpacity;
		this.repeat = repeat;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getStrokeColor() {
		return strokeColor;
	}

	public void setStrokeColor(String strokeColor) {
		this.strokeColor = strokeColor;
	}

	public String getFillColor() {
		return fillColor;
	}

	public void setFillColor(String fillColor) {
		this.fillColor = fillColor;
	}

	public int getFillOpacity() {
		return fillOpacity;
	}

	public void setFillOpacity(int fillOpacity) {
		this.fillOpacity = fillOpacity;
	}

	public int getRepeat() {
		return repeat;
	}

	public void setRepeat(int repeat) {
		this.repeat = repeat;
	}

	protected String getJson() {
		return "{icon:{" + "path: '" + getPath() + "'," + "strokeColor: '" + getStrokeColor() + "'," + "fillColor: '"
				+ getFillColor() + "'," + "fillOpacity: " + getFillOpacity() + "} , repeat: '" + getRepeat() + "px'}";
	}

}
