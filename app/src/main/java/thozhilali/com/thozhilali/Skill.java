package thozhilali.com.thozhilali;

/**
 * Created by SEMI on 4/30/2017.
 */

public class Skill {
    int id;
    String skill;

    public Skill(int id, String skill) {
        this.id = id;
        this.skill = skill;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }
}
