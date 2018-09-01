package fr.uha.ledigabel.poketrainer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by natle on 02/02/2018.
 */

public class Model {
    public static Regions regions;
    public static Pokemons pokemons;
    public static Trainers trainers;

    public Model(){
        regions = new Regions();
        regions.populate();

        pokemons = new Pokemons();
        pokemons.populate();

        trainers = new Trainers();
        trainers.populate();
    }
}
