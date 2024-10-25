package UTS;

import java.util.ArrayList;

public class Food {
    private String name;
    private int price, duration;
    private ArrayList<String> ingredients;

    public Food(String name, int price, ArrayList<String> ingredients, int duration) {
        this.name = name;
        this.price = price;
        this.ingredients = ingredients;
        this.duration = duration;
    }

    public int getHarga() {
        return price;
    }

    public int getDuration() {
        return duration;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }
}
