package fr.uha.ledigabel.poketrainer;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by natle on 02/02/2018.
 */

public class Pokemons implements Parcelable  {
    private List<Pokemon> pokemons;

    public List<Pokemon> getPokemons(){
        if (pokemons == null) pokemons = new ArrayList<Pokemon>();
        return pokemons;
    }

    public Pokemons(){
        pokemons = new ArrayList<Pokemon>();
    }

    public void addPokemon(Pokemon pokemon){
        getPokemons().add(pokemon);
    }

    public void populate(){
        addPokemon(new Pokemon("Pikachu",PkmType.ELEC,PkmType.NOTYPE));
        addPokemon(new Pokemon("Electhor",PkmType.ELEC,PkmType.FLYING));
        addPokemon(new Pokemon("Salameche",PkmType.FIRE,PkmType.NOTYPE));
    }

    protected Pokemons(Parcel in) {
        if (pokemons == null) pokemons = new ArrayList<Pokemon>();
        in.readTypedList(pokemons, Pokemon.CREATOR);
    }

    public static final Parcelable.Creator<Pokemons> CREATOR = new Parcelable.Creator<Pokemons>() {
        @Override
        public Pokemons createFromParcel(Parcel in) {
            return new Pokemons(in);
        }

        @Override
        public Pokemons[] newArray(int size) {
            return new Pokemons[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(pokemons);
    }

    public String[] getNames(){
        String[] names = new String[pokemons.size()];
        for(int i=0;i<pokemons.size();i++){
            names[i]=pokemons.get(i).getName();
        }
        return names;
    }

    public boolean contains(Pokemon p){
        return pokemons.contains(p);
    }

    /*public int getIndex(String name){
        for(int i=0;i<pokemons.size();i++){
            if(pokemons.get(i).getName()==name){
                return i;
            }
        }
        return -1;
    }*/
}
