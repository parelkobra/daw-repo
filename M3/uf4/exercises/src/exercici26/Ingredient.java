package exercici26;

import java.text.DecimalFormat;

import org.apache.commons.lang3.StringUtils;

public class Ingredient {
	private static final String EOL = "\n";
	private static final String EURO = " €";
	private static final String YES = "Yes";
	private static final String NO = "No";
	private static final String DECIMAL_FORMAT = "#.00";

	int id;
	String name;
	String price;
	boolean fruit;

	public Ingredient(int id, String name, double price, boolean isFruit) {
		this.id = id;
		this.name = name;
		this.price = getFormattedPrice(price);
		this.fruit = isFruit;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPrice() {
		return price;
	}

	public boolean isFruit() {
		return fruit;
	}

	public String getFormattedPrice(double price) {
		DecimalFormat format = new DecimalFormat(DECIMAL_FORMAT);
		String formattedPrice = format.format(price);
		return formattedPrice;
	}

	@Override
	public String toString() {
		String output = "";
		output += StringUtils.rightPad(getId() + "-", 2);
		output += StringUtils.rightPad(getName(), 20);
		output += StringUtils.rightPad(getPrice() + EURO, 15);
		output += (isFruit()) ? YES : NO;
		output += EOL;
		return output;
	}
}
