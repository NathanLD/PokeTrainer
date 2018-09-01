package fr.uha.ledigabel.poketrainer;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by natle on 30/01/2018.
 */

public class Trainer implements Parcelable {
    private String nickname;
    private boolean isMale;
    private int age;
    private boolean badges[] = new boolean[Utils.NB_BADGES];
    private Pokemons pokemons = new Pokemons();

    public Trainer(String nickname, boolean isMale, int age, boolean[] badges, Pokemons pokemons) {
        this.nickname = nickname;
        this.isMale = isMale;
        this.age = age;
        this.badges = badges;
        this.pokemons = pokemons;
    }

    protected Trainer(Parcel in) {
        nickname = in.readString();
        isMale = in.readByte() != 0;
        age = in.readInt();

        boolean[] myBooleanArr = new boolean[Utils.NB_BADGES];
        in.readBooleanArray(myBooleanArr);
        badges = myBooleanArr;

        pokemons = in.readParcelable(getClass().getClassLoader());
    }

    public static final Creator<Trainer> CREATOR = new Creator<Trainer>() {
        @Override
        public Trainer createFromParcel(Parcel in) {
            return new Trainer(in);
        }

        @Override
        public Trainer[] newArray(int size) {
            return new Trainer[size];
        }
    };

    public String getNickname() {
        return nickname;
    }

    public boolean isMale() {
        return isMale;
    }

    public int getAge() {
        return age;
    }

    public boolean[] getBadges() {
        return badges;
    }

    public Pokemons getPokemons() {
        return pokemons;
    }

    public int countBadges(){
        int count = 0;
        for(int i = 0; i< Utils.NB_BADGES; i++){
            if(badges[i]){
                count++;
            }
        }
        return count;
    }

    @Override
    public String toString() {
        return "Trainer{" +
                "nickname='" + nickname + '\'' +
                ", isMale=" + isMale +
                ", age=" + age +
                ", badges=" + Arrays.toString(badges) +
                ", pokemons=" + pokemons +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nickname);
        dest.writeByte((byte) (isMale ? 1 : 0));
        dest.writeInt(age);
        dest.writeBooleanArray(badges);
        dest.writeParcelable(pokemons, flags);
    }

    public int countPokemons() {
        return pokemons.getPokemons().size();
    }

    public int compareTo(Trainer t)
    {
        return(countBadges() - t.countBadges());
    }


}
