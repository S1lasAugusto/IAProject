package models;

public class LevelData {

    private double hp;
    private double heal_5s;
    private double attack;
    private double defense;
    private double sp_attack;
    private double sp_defense;
    private double move_speed;
    private double cdr;
    private double life_steal;
    private double crit_chance;
    private double tenacity;
    private double attack_speed;

    public LevelData() {
    }

    public LevelData(double hp,
                     double heal_5s,
                     double attack,
                     double defense,
                     double sp_attack,
                     double sp_defense,
                     double move_speed,
                     double cdr,
                     double life_steal,
                     double critic_chance,
                     double tenacity,
                     double attack_speed) {

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

    public double getHp() {
        return hp;
    }

    public void setHp(double hp) {
        this.hp = hp;
    }

    public double getHeal_5s() {
        return heal_5s;
    }

    public void setHeal_5s(double heal_5s) {
        this.heal_5s = heal_5s;
    }

    public double getAttack() {
        return attack;
    }

    public void setAttack(double attack) {
        this.attack = attack;
    }

    public double getDefense() {
        return defense;
    }

    public void setDefense(double defense) {
        this.defense = defense;
    }

    public double getSp_attack() {
        return sp_attack;
    }

    public void setSp_attack(double sp_attack) {
        this.sp_attack = sp_attack;
    }

    public double getSp_defense() {
        return sp_defense;
    }

    public void setSp_defense(double sp_defense) {
        this.sp_defense = sp_defense;
    }

    public double getMove_speed() {
        return move_speed;
    }

    public void setMove_speed(double move_speed) {
        this.move_speed = move_speed;
    }

    public double getCdr() {
        return cdr;
    }

    public void setCdr(double cdr) {
        this.cdr = cdr;
    }

    public double getLife_steal() {
        return life_steal;
    }

    public void setLife_steal(double life_steal) {
        this.life_steal = life_steal;
    }

    public double getCrit_chance() {
        return crit_chance;
    }

    public void setCrit_chance(double crit_chance) {
        this.crit_chance = crit_chance;
    }

    public double getTenacity() {
        return tenacity;
    }

    public void setTenacity(double tenacity) {
        this.tenacity = tenacity;
    }

    public double getAttack_speed() {
        return attack_speed;
    }

    public void setAttack_speed(double attack_speed) {
        this.attack_speed = attack_speed;
    }

    @Override
    public String toString() {

        return "LevelData{" +
                "hp=" + hp +
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
                '}';
    }
}
