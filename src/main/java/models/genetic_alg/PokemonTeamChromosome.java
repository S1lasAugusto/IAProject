package models.genetic_alg;

import io.jenetics.AbstractChromosome;
import io.jenetics.Chromosome;
import io.jenetics.util.ISeq;

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
                && this._genes.stream().map(x -> x.allele().get(0)).distinct().count() == 5;
    }

    public static PokemonTeamChromosome of(int pokemonLength, int heldItemLength) {
        return new PokemonTeamChromosome(seq(pokemonLength, heldItemLength), pokemonLength, heldItemLength);
    }

    private static ISeq<PokemonGene> seq(int pokemonLength, int heldItemLength) {

        ISeq<PokemonGene> seq = ISeq.empty();

        for (int i = 0; i < 5; i++) {
            seq = seq.append(PokemonGene.of(pokemonLength, heldItemLength));
        }

        return seq;
    }
}
