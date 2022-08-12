package models.genetic_alg;

import java.util.ArrayList;
import java.util.List;
import java.util.random.RandomGenerator;

import io.jenetics.Gene;
import io.jenetics.util.RandomRegistry;

// How a pokemon and its held items are represented in the problem
public class PokemonGene implements Gene<List<Integer>, PokemonGene> {

    private int pokemonMaxValue;
    private int heldItemsMaxValue;
    private List<Integer> allele;

    private PokemonGene(int pokemonMaxValue, int heldItemsMaxValue, List<Integer> allele) {
        this.pokemonMaxValue = pokemonMaxValue;
        this.heldItemsMaxValue = heldItemsMaxValue;
        this.allele = allele;
    }

    @Override
    public boolean isValid() {
        // Checks if all three chosen held items are distinct
        return this.allele.subList(1, 4).stream().distinct().count() == 3;
    }

    // getters
    @Override
    public List<Integer> allele() {
        return this.allele;
    }

    public int getPokemon() {
        return this.allele.get(0);
    }

    public List<Integer> getHeldItems() {
        return this.allele.subList(1, 4);
    }

    // Creates a new valid random PokemonGene
    @Override
    public PokemonGene newInstance() {
        RandomGenerator random = RandomRegistry.random();
        List<Integer> allele = new ArrayList<>();

        // Random pokemon
        allele.add(random.nextInt(this.pokemonMaxValue));

        // Three different held items
        int count = 0;
        while (count < 3) {
            int gene = random.nextInt(heldItemsMaxValue);
            if (!allele.subList(1, allele.size()).contains(gene)) {
                allele.add(random.nextInt(heldItemsMaxValue));
                count++;
            }
        }

        return new PokemonGene(this.pokemonMaxValue, this.heldItemsMaxValue, allele);
    }

    // Creates a new PokemonGene based on the given values
    @Override
    public PokemonGene newInstance(List<Integer> value) {
        return new PokemonGene(this.pokemonMaxValue, this.heldItemsMaxValue, value);
    }

    // Creates a new valid random PokemonGene
    public static PokemonGene of(int pokemonMaxValue, int heldItemsMaxValue) {
        RandomGenerator random = RandomRegistry.random();
        List<Integer> allele = new ArrayList<>();

        // Random pokemon
        allele.add(random.nextInt(pokemonMaxValue));

        // Three different held items
        int count = 0;
        while (count < 3) {
            int gene = random.nextInt(heldItemsMaxValue);
            if (!allele.subList(1, allele.size()).contains(gene)) {
                allele.add(random.nextInt(heldItemsMaxValue));
                count++;
            }
        }

        return new PokemonGene(pokemonMaxValue, heldItemsMaxValue, allele);
    }
}
