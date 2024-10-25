package UTS;

public class Employee {
    private String name, skill;

    public Employee(String name, String skill) {
        this.name = name;
        this.skill = skill;
    }

    public String getName() {
        return name;
    }

    public String getSkill() {
        return skill;
    }

    public boolean hasSkill(String requiredSkill) {
        return skill.equalsIgnoreCase(requiredSkill);
    }
}
