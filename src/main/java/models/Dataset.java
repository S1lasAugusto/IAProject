package models;

import java.util.List;

public class Dataset {

    private List<Pokemon> pokemons;
    private List<HeldItem> held_items;

    public Dataset() {
    }

    public Dataset(List<Pokemon> pokemonList) {
        this.pokemons = pokemonList;
    }

    public List<Pokemon> getPokemons() {
        return pokemons;
    }

    public List<HeldItem> getHeldItems() {
        return held_items;
    }

    @Override
    public String toString() {
        return "Dataset{" +
                "pokemons=" + pokemons +
                ", held_items=" + held_items +
                '}';
    }
}
