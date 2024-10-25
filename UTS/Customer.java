package UTS;

public class Customer {
    private String name; 
    private Food preference;
    private int patienceLevel, satisfaction;

    public Customer(String name, Food preference, int patienceLevel) {
        this.name = name;
        this.preference = preference;
        this.patienceLevel = patienceLevel;
        this.satisfaction = 100;
    }

    public String getName() {
        return name;
    }

    public int getPatienceLevel() {
        return patienceLevel;
    }

    public Food getPreference() {
        return preference;
    }

    public int getSatisfaction() {
        return satisfaction;
    }

    public void updateSatisfaction(int time) {
        if (time > patienceLevel) {
            int excessTime = time - patienceLevel;
            satisfaction -= excessTime * 5; 
        }
        satisfaction = Math.max(0, Math.min(100, satisfaction)); 
    }

    public String getOrderDetails() {
        return name + " prefers " + preference.getName() + " (Patience: " + patienceLevel + ")";
    }
}
