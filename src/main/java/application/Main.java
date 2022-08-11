package application;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.google.gson.Gson;

import io.jenetics.Genotype;
import io.jenetics.Phenotype;
import io.jenetics.engine.Constraint;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.engine.EvolutionStatistics;
import io.jenetics.engine.Limits;
import io.jenetics.util.Factory;
import models.Dataset;
import models.HeldItem;
import models.LevelData;
import models.Pokemon;
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
        Factory<Genotype<PokemonGene>> gtf = Genotype
                .of(PokemonTeamChromosome.of(dataset.getPokemons().size(), dataset.getHeldItems().size()));

        Engine<PokemonGene, Double> engine = Engine.builder(Main::Fitness, gtf)
                .constraint(Constraint.of(pt -> pt.isValid(), (pt, g) -> {
                    PokemonTeamChromosome chromosome = (PokemonTeamChromosome) pt.genotype().chromosome();
                    return Phenotype.of(Genotype.of(chromosome.repair()), g);
                }))
                .populationSize(300)
                .maximizing()
                .build();

        final EvolutionStatistics<Double, ?> statistics = EvolutionStatistics.ofNumber();
        // 4.) Start the execution (evolution) and
        // collect the result.
        Genotype<PokemonGene> result = engine.stream()
                // Truncate the evolution stream after 15 "steady"
                // generations.
                .limit(Limits.bySteadyFitness(15))
                // Maximum limit of generations to 5000
                .limit(5000)
                // Update the evaluation statistics after
                // each generation
                .peek(statistics)
                // Collect (reduce) the evolution stream to
                // its best phenotype.
                .collect(EvolutionResult.toBestGenotype());

        System.out.println(resultString(result));

        System.out.println(statistics);
    }

    private static Double Fitness(Genotype<PokemonGene> genotype) {
        double firstClearFitness = 0, midGameFitsness = 0, lateGameFitness = 0;

        for (var pokemonGene : genotype.chromosome()) {
            var pokemon = getPokemonFromPokemonGene(pokemonGene);
            var heldItems = getHeldItemsFromPokemonGene(pokemonGene);

            double individualFitness = 0;
            for (int level = 0; level < 4; level++) {
                LevelData levelWithItems = applyItems(pokemon.getLevelData().get(level), heldItems);
                // Great damage output to kill neutral pokemons faster and great move speed to
                // get to lane quicker

                // damage output (attack speed * attack * crit_rate) and (sp_attack * cdr)
                double damageOutput = levelWithItems.getAttack()
                        * (1 + levelWithItems.getAttack_speed())
                        + levelWithItems.getCrit_chance();

                damageOutput += levelWithItems.getSp_attack()
                        * (1 + levelWithItems.getCdr());

                double moveSpeed = levelWithItems.getMove_speed();
                individualFitness = (damageOutput + moveSpeed) / 2;
            }
            firstClearFitness += individualFitness / (4 * 5);

            individualFitness = 0;
            for (int level = 4; level < 10; level++) {
                LevelData levelWithItems = applyItems(pokemon.getLevelData().get(level), heldItems);
                
                // Great survivability, damage output and movespeed to win fight, flee fights
                // and secure objectives
                double survivability = pokemon.getLevelData().get(level).getHp();
                survivability += levelWithItems.getHeal_5s();
                survivability += levelWithItems.getDefense();
                survivability += levelWithItems.getSp_defense();
                survivability += levelWithItems.getTenacity();
                survivability += levelWithItems.getMove_speed();
                survivability /= 6;

                // damage output (attack speed * attack * crit_rate) and (sp_attack * cdr)
                double damageOutput = levelWithItems.getAttack()
                        * (1 + levelWithItems.getAttack_speed())
                        + levelWithItems.getCrit_chance();

                damageOutput += levelWithItems.getSp_attack()
                        * (1 + levelWithItems.getCdr());
                damageOutput /= 2;

                individualFitness = (survivability + damageOutput) / 2;
            }
            midGameFitsness += individualFitness / (6 * 5);

            individualFitness = 0;
            for (int level = 10; level < 15; level++) {
                LevelData levelWithItems = applyItems(pokemon.getLevelData().get(level), heldItems);
                // damage output (attack speed * attack * crit_rate) and (sp_attack * cdr)
                double damageOutput = levelWithItems.getAttack()
                        * (1 + levelWithItems.getAttack_speed())
                        + levelWithItems.getCrit_chance();

                damageOutput += levelWithItems.getSp_attack()
                        * (1 + levelWithItems.getCdr());
                damageOutput /= 2;
                
                // Great survivability, damage output and movespeed to win fight, flee fights
                // and secure objectives
                double survivability = levelWithItems.getHp();
                survivability += levelWithItems.getHeal_5s();
                survivability += 3 * levelWithItems.getDefense();
                survivability += 3 * levelWithItems.getSp_defense();
                survivability += damageOutput * levelWithItems.getLife_steal();
                survivability /= 9;


                individualFitness = (4 * survivability + 5 * damageOutput) / 9;
            }

            lateGameFitness += individualFitness / (5 * 5);
        }

        return (2 * firstClearFitness + 3 * midGameFitsness + 5 * lateGameFitness) / 10;
    }

    private static LevelData applyItems(LevelData baseStats, List<HeldItem> items) {
        LevelData levelDataItem = new LevelData();

        double hp = baseStats.getHp();
        double heal_5s = baseStats.getHeal_5s();
        double attack = baseStats.getAttack();
        double defense = baseStats.getDefense();
        double sp_attack = baseStats.getSp_attack();
        double sp_defense = baseStats.getSp_defense();
        double move_speed = baseStats.getMove_speed();
        double cdr = baseStats.getCdr();
        double life_steal = baseStats.getLife_steal();
        double crit_chance = baseStats.getCrit_chance();
        double tenacity = baseStats.getTenacity();
        double attack_speed = baseStats.getAttack_speed();

        for (HeldItem item : items) {
            hp += item.getHp();
            heal_5s += item.getHeal_5s();
            attack += item.getAttack();
            defense += item.getDefense();
            sp_attack += item.getSp_attack();
            sp_defense += item.getSp_defense();
            move_speed += item.getMove_speed();
            cdr *= 1 + item.getCdr();
            life_steal *= 1 + item.getLife_steal();
            crit_chance *= 1 + item.getCrit_chance();
            tenacity *= 1 + item.getTenacity();
            attack_speed *= 1 + item.getAttack_speed();
        }

        levelDataItem.setHp(hp);
        levelDataItem.setHeal_5s(heal_5s);
        levelDataItem.setAttack(attack);
        levelDataItem.setDefense(defense);
        levelDataItem.setSp_attack(sp_attack);
        levelDataItem.setSp_defense(sp_defense);
        levelDataItem.setMove_speed(move_speed);
        levelDataItem.setCdr(cdr);
        levelDataItem.setLife_steal(life_steal);
        levelDataItem.setCrit_chance(crit_chance);
        levelDataItem.setTenacity(tenacity);
        levelDataItem.setAttack_speed(attack_speed);

        return levelDataItem;
    }

    private static Pokemon getPokemonFromPokemonGene(PokemonGene pokemonGene) {
        return dataset.getPokemons().get(pokemonGene.getPokemon());
    }

    private static List<HeldItem> getHeldItemsFromPokemonGene(PokemonGene pokemonGene) {
        return pokemonGene.getHeldItems().stream().map(x -> dataset.getHeldItems().get(x)).toList();
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
