package exercici26;

public class Main {

	public static void main(String[] args) {
		Ingredient[] ingredients = new Ingredient[3];
		ingredients[0] = new Ingredient(1, "Macarrons", 5.10, false);
		ingredients[1] = new Ingredient(2, "Crema de llet", 4.00, false);
		ingredients[2] = new Ingredient(3, "Bacon", 3.20, false);
		List list1 = new List("Macarrons Carbonara", 12.30, ingredients, false);
		System.out.print(list1.getHeader());
		System.out.print(list1.getSubHeader());
		System.out.print(list1.getContent());
	}

}
