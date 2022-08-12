package models.genetic_alg;

import io.jenetics.AbstractChromosome;
import io.jenetics.Chromosome;
import io.jenetics.util.ISeq;

// How a pokemon team is represented
public class PokemonTeamChromosome extends AbstractChromosome<PokemonGene> {

    private int pokemonLength;
    private int heldItemLength;
 
    private PokemonTeamChromosome(ISeq<? extends PokemonGene> genes, int pokemonLength, int heldItemLength) {
        super(genes);

        this.pokemonLength = pokemonLength;
        this.heldItemLength = heldItemLength;
    }

    @Override
    public Chromosome<PokemonGene> newInstance(ISeq<PokemonGene> genes) {
        return new PokemonTeamChromosome(genes, this.pokemonLength, this.heldItemLength);
    }

    @Override
    public Chromosome<PokemonGene> newInstance() {
        return new PokemonTeamChromosome(
                seq(this.pokemonLength, this.heldItemLength),
                this.pokemonLength, this.heldItemLength);
    }

    @Override
    public boolean isValid() {
        return this._genes.forAll(x -> x.isValid())
                && hasDistinctPokemons();
    }

    // Checkes if the 5 chosen pokemon are distinct
    public boolean hasDistinctPokemons() {
        return this._genes.stream().map(x -> x.allele().get(0)).distinct().count() == 5;
    }

    // Creates a valid Team member
    public static PokemonTeamChromosome of(int pokemonLength, int heldItemLength) {
        return new PokemonTeamChromosome(seq(pokemonLength, heldItemLength), pokemonLength, heldItemLength);
    }

    // Creates a valid Team member gene sequence
    private static ISeq<PokemonGene> seq(int pokemonLength, int heldItemLength) {

        ISeq<PokemonGene> seq = ISeq.empty();

        // Creating the 5 distinct pokemons
        int count = 0;
        while (count < 5) {
            var gene = PokemonGene.of(pokemonLength, heldItemLength);

            // Checks if the gene sequence has the chosen pokemon already
            if (!seq.stream().map(x -> x.getPokemon()).anyMatch(x -> x == gene.getPokemon())) {
                seq = seq.append(gene);
                count++;
            }
        }

        return seq;
    }
}
