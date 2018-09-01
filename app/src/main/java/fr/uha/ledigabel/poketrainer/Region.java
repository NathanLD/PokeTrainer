package fr.uha.ledigabel.poketrainer;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by natle on 30/01/2018.
 */

public class Region implements Parcelable {
    private String name;
    private Pokemons pokemons = new Pokemons();  // Pokémons sauvages présents dans la zone
    // image ?

    public Region(String name, Pokemons pokemons) {

        this.name = name;
        this.pokemons = pokemons;
    }

    protected Region(Parcel in) {
        name = in.readString();
        pokemons = in.readParcelable(getClass().getClassLoader());
    }

    public static final Creator<Region> CREATOR = new Creator<Region>() {
        @Override
        public Region createFromParcel(Parcel in) {
            return new Region(in);
        }

        @Override
        public Region[] newArray(int size) {
            return new Region[size];
        }
    };

    public String getName() {
        return name;
    }

    public Pokemons getPokemons() {
        return pokemons;
    }

    public int countPokemons() {
        return pokemons.getPokemons().size();
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "name='" + name + '\'' +
                ", pokemons=" + pokemons +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeParcelable(pokemons, flags);
    }
}
