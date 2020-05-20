package exercici26;

import java.text.DecimalFormat;

import org.apache.commons.lang3.StringUtils;

public class List {

	private static final String EOL = "\n";

	String name;
	String price;
	Ingredient[] ingredient;
	boolean dessert;

	public List(String name, double price, Ingredient[] ingredient, boolean dessert) {
		this.name = name;
		this.price = getFormattedPrice(price);
		this.ingredient = ingredient;
		this.dessert = dessert;
	}

	public String getName() {
		return name;
	}

	public String getPrice() {
		return price;
	}

	public Ingredient[] getIngredient() {
		return ingredient;
	}

	public boolean isDessert() {
		return dessert;
	}

	public String getFormattedPrice(double price) {
		DecimalFormat format = new DecimalFormat("#.00");
		String formattedPrice = format.format(price);
		return formattedPrice;
	}

	public String getHeader() {
		String output = "";
		if (dessert) {
			output += "Postre: ";
		}
		output += getName();
		output += ", preu: ";
		output += getPrice();
		output += "€";
		output += EOL;
		output += "Ingredients:";
		output += EOL;
		return output;
	}

	public String getSubHeader() {
		String output = "";
		output += StringUtils.rightPad("#", 2);
		output += StringUtils.rightPad("Nom", 20);
		output += StringUtils.rightPad("Preu", 15);
		output += "Fruita" + EOL;
		output += StringUtils.repeat("-", 43);
		output += EOL;
		return output;
	}

	public String getContent() {
		String output = "";
		for (Ingredient item : ingredient) {
			output += item.toString();
		}
		return output;
	}

	public void getOutput() {
	}

}
