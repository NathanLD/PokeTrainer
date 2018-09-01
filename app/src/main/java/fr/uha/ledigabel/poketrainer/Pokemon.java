package fr.uha.ledigabel.poketrainer;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by natle on 30/01/2018.
 */

public class Pokemon  implements Parcelable {
    private String name;
    private PkmType type1;
    private PkmType type2;
    // image ?

    public Pokemon(String name, PkmType type1, PkmType type2) {

        this.name = name;
        this.type1 = type1;
        this.type2 = type2;
    }

    protected Pokemon(Parcel in) {
        name = in.readString();
        String t1 = in.readString();
        type1 = PkmType.getByName(t1);
        String t2 = in.readString();
        type2 = PkmType.getByName(t2);
    }

    public static final Parcelable.Creator<Pokemon> CREATOR = new Parcelable.Creator<Pokemon>() {
        @Override
        public Pokemon createFromParcel(Parcel in) {
            return new Pokemon(in);
        }

        @Override
        public Pokemon[] newArray(int size) {
            return new Pokemon[size];
        }
    };

    public String getName() {
        return name;
    }

    public PkmType getType1() {
        return type1;
    }

    public PkmType getType2() {
        return type2;
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "name='" + name + '\'' +
                ", type1=" + type1 +
                ", type2=" + type2 +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(type1.toString());
        dest.writeString(type2.toString());
    }
}
