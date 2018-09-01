package fr.uha.ledigabel.poketrainer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by natle on 30/01/2018.
 */

public class Trainers {
    private List<Trainer> trainers;

    public List<Trainer> getTrainers(){
        if (trainers == null) trainers = new ArrayList<>();
        return trainers;
    }

    public void addTrainer(Trainer trainer){
        getTrainers().add(trainer);
    }

    public void populate(){
        boolean noBadges[] = {false, false, false, false, false, false, false, false};
        addTrainer(new Trainer("Nathan01", true, 21, noBadges, new Pokemons()));
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(Trainer t : trainers){
            sb.append("\t"+t);
        }
        return sb.toString();
    }
}
