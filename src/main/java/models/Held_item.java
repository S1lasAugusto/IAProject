package models;

public class Held_item {

    private String name;
    private int hp;
    private int heal_5s;
    private int attack;
    private int defense;
    private int sp_attack;
    private int sp_defense;
    private double move_speed;
    private double cdr;
    private double life_steal;
    private double crit_chance;
    private double tenacity;
    private double attack_speed;

    public Held_item() {
    }

    public Held_item(String name,
                     int hp,
                     int heal_5s,
                     int attack,
                     int defense,
                     int sp_attack,
                     int sp_defense,
                     double move_speed,
                     double cdr,
                     double life_steal,
                     double critic_chance,
                     double tenacity,
                     double attack_speed) {

        this.name = this.name;
        this.hp = hp;
        this.heal_5s = heal_5s;
        this.attack = attack;
        this.defense = defense;
        this.sp_attack = sp_attack;
        this.sp_defense = sp_defense;
        this.move_speed = move_speed;
        this.cdr = cdr;
        this.life_steal = life_steal;
        this.crit_chance = critic_chance;
        this.tenacity = tenacity;
        this.attack_speed = attack_speed;
    }


    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getHeal_5s() {
        return heal_5s;
    }

    public void setHeal_5s(int heal_5s) {
        this.heal_5s = heal_5s;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getSp_attack() {
        return sp_attack;
    }

    public void setSp_attack(int sp_attack) {
        this.sp_attack = sp_attack;
    }

    public int getSp_defense() {
        return sp_defense;
    }

    public void setSp_defense(int sp_defense) {
        this.sp_defense = sp_defense;
    }

    public double getMove_speed() {
        return move_speed;
    }

    public void setMove_speed(int move_speed) {
        this.move_speed = move_speed;
    }

    public double getCdr() {
        return cdr;
    }

    public void setCdr(int cdr) {
        this.cdr = cdr;
    }

    public double getLife_steal() {
        return life_steal;
    }

    public void setLife_steal(int life_steal) {
        this.life_steal = life_steal;
    }

    public double getCrit_chance() {
        return crit_chance;
    }

    public void setCrit_chance(int crit_chance) {
        this.crit_chance = crit_chance;
    }

    public double getTenacity() {
        return tenacity;
    }

    public void setTenacity(int tenacity) {
        this.tenacity = tenacity;
    }

    public double getAttack_speed() {
        return attack_speed;
    }

    public void setAttack_speed(int attack_speed) {
        this.attack_speed = attack_speed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Held_item{" +
                "name='" + name + '\'' +
                ", hp=" + hp +
                ", heal_5s=" + heal_5s +
                ", attack=" + attack +
                ", defense=" + defense +
                ", sp_attack=" + sp_attack +
                ", sp_defense=" + sp_defense +
                ", move_speed=" + move_speed +
                ", cdr=" + cdr +
                ", life_steal=" + life_steal +
                ", crit_chance=" + crit_chance +
                ", tenacity=" + tenacity +
                ", attack_speed=" + attack_speed +
                '}' + '\n';
    }
}
