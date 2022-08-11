package application;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.Gson;

import io.jenetics.Genotype;
import io.jenetics.Phenotype;
import io.jenetics.engine.Constraint;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionParams;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.engine.EvolutionStatistics;
import io.jenetics.util.Factory;
import models.Dataset;
import models.genetic_alg.PokemonGene;
import models.genetic_alg.PokemonTeamChromosome;

public class Main {

    private static Dataset dataset;

    public static void main(String[] args) {

        dataset = readDataset();

        if (dataset == null) {
            return;
        }

        // 1.) Define the genotype (factory) suitable
        // for the problem.
        Factory<Genotype<PokemonGene>> gtf = Genotype.of(PokemonTeamChromosome.of(dataset.getPokemons().size(), dataset.getHeldItems().size()));

        Engine<PokemonGene, Double> engine = Engine.builder(Main::Fitness, gtf)
                .populationSize(100)
                .maximizing()
                .constraint(Constraint.of(pt -> 
                    pt.genotype().chromosome().isValid()
                ))
                .build();
                
        final EvolutionStatistics<Double, ?> statistics = EvolutionStatistics.ofNumber();
        // 4.) Start the execution (evolution) and
        // collect the result.
        Genotype<PokemonGene> result = engine.stream()
                // Truncate the evolution stream after 7 "steady"
                // generations.
                .limit(5000)
                // Update the evaluation statistics after
                // each generation
                .peek(statistics)
                // Collect (reduce) the evolution stream to
                // its best phenotype.
                .collect(EvolutionResult.toBestGenotype());

        System.out.println(resultString(result));

        System.out.println(statistics);

        System.out.println(result.chromosome().isValid());
    }

    private static Double Fitness(Genotype<PokemonGene> genotype) {

        double fitness = 0;

        for (int i = 0; i < 5; i++) {
            var pokemon = dataset.getPokemons().get(genotype.get(0).get(i).allele().get(0));
            
            fitness += pokemon.getLevelData().get(pokemon.getLevelData().size() - 1).getHp();
            fitness += pokemon.getLevelData().get(pokemon.getLevelData().size() - 1).getHeal_5s();
            fitness += pokemon.getLevelData().get(pokemon.getLevelData().size() - 1).getDefense();
            fitness += pokemon.getLevelData().get(pokemon.getLevelData().size() - 1).getSp_defense();
        }

        return fitness;
    }

    private static String resultString(Genotype<PokemonGene> result) {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 5; i++) {
            var pokemon = dataset.getPokemons().get(result.get(0).get(i).allele().get(0));
            sb.append("Pokemon " + (i + 1) + ": " + pokemon.getName());
            sb.append(System.getProperty("line.separator"));

            for (int j = 1; j <= 3; j++) {
                var heldItem = dataset.getHeldItems().get(result.get(0).get(i).allele().get(j));
                sb.append("Item " + (j) + ": " + heldItem.getName());
                sb.append(System.getProperty("line.separator"));
            }

            sb.append(System.getProperty("line.separator"));
        }

        return sb.toString();
    }

    private static Dataset readDataset() {
        try {
            // Lê o conteudo de um arquivo e guarda em uma String.
            String json = String.join(" ", Files.readAllLines(
                    Paths.get(".\\input\\NormalizedDataset.json"), StandardCharsets.UTF_8));
            System.out.println("lendo");

            // Desserialização do conteúdo em um novo objeto Dataset, usando o método
            // fromJson do Gson.
            Dataset dataset = new Gson().fromJson(json, Dataset.class);

            return dataset;

        } catch (IOException e) {
            System.out.println("Arquivo não encontrado");
        }

        return null;
    }
}
