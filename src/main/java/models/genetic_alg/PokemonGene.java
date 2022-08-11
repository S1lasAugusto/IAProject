package models.genetic_alg;

import java.util.ArrayList;
import java.util.List;
import java.util.random.RandomGenerator;

import io.jenetics.Gene;
import io.jenetics.util.RandomRegistry;

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
        return this.allele.subList(1, 4).stream().distinct().count() == 3;
    }

    @Override
    public List<Integer> allele() {
        return this.allele;
    }

    @Override
    public PokemonGene newInstance() {
        RandomGenerator random = RandomRegistry.random();
        List<Integer> allele = new ArrayList<>();

        allele.add(random.nextInt(this.pokemonMaxValue));

        for (int i = 0; i < 3; i++) {
            allele.add(random.nextInt(this.heldItemsMaxValue));
        }

        return new PokemonGene(this.pokemonMaxValue, this.heldItemsMaxValue, allele);
    }

    @Override
    public PokemonGene newInstance(List<Integer> value) {
        return new PokemonGene(this.pokemonMaxValue, this.heldItemsMaxValue, value);
    }

    public static PokemonGene of(int pokemonMaxValue, int heldItemsMaxValue) {
        RandomGenerator random = RandomRegistry.random();
        List<Integer> allele = new ArrayList<>();

        allele.add(random.nextInt(pokemonMaxValue));

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
