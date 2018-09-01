package fr.uha.ledigabel.poketrainer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by natle on 02/02/2018.
 */

public class Regions {
    private List<Region> regions;

    public List<Region> getRegions(){
        if (regions == null) regions = new ArrayList<>();
        return regions;
    }

    public void addRegion(Region region){
        getRegions().add(region);
    }

    public void populate(){
        addRegion(new Region("Kanto", new Pokemons()));
    }
}
