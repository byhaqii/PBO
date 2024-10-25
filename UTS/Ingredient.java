package UTS;

public class Ingredient {
    private String name;
    private int quantity;

    public Ingredient(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void reduceQuantity(int amount) {
        this.quantity -= amount;
    }

    public void restock(int amount) {
        this.quantity += amount;
    }
}
