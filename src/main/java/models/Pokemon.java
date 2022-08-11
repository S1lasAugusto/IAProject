package models;

import java.util.ArrayList;
import java.util.List;

public class Pokemon {

    private String name;
    private List<LevelData> level_data;

    public Pokemon(String name) {

        this.name = name;
        this.level_data = new ArrayList<LevelData>();
    }

    public Pokemon() {
        this.level_data = new ArrayList<LevelData>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<LevelData> getLevelData() {
        return level_data;
    }

    @Override

    public String toString() {
        return "Pokemon{" +
                "name='" + name + '\'' +
                ", levelData=" + level_data +
                '}' + '\n';
    }
}
