package fr.uha.ledigabel.poketrainer;


import android.graphics.Color;

/**
 * Created by natle on 30/01/2018.
 */

enum PkmType {

    NOTYPE("",0, Color.BLACK),
    FIRE ("Fire",1, Utils.getIntFromColor(250,110,0)),
    WATER("Water",2, Utils.getIntFromColor(64,79,255)),
    ELEC("Electricity",3, Utils.getIntFromColor(210,140,63)),
    GRASS("Grass",4, Utils.getIntFromColor(0,186,0)),
    PSY("Psy",5, Utils.getIntFromColor(255,89,222)),
    ICE("Ice",6, Utils.getIntFromColor(0,219,255)),
    FLYING("Flying",7, Utils.getIntFromColor(162,162,246)),
    DRAGON("Dragon",8,Utils.getIntFromColor(131,23,246));


    private final String type;
    private int id;
    private int color;

    PkmType(String type, int id, int color) {
        this.type = type;
        this.id = id;
        this.color = color;
    }

    public int getId(){
        return this.id;
    }

    public int getColor(){
        return this.color;
    }

    @Override public String toString(){
        return type;
    }

    public static PkmType getByName(String name){
        switch(name){
            case "Fire":
                return FIRE;
            case "Water":
                return WATER;
            case "Electricity":
                return ELEC;
            case "Grass":
                return GRASS;
            case "Psy":
                return PSY;
            case "Ice":
                return ICE;
            case "Flying":
                return FLYING;
            case "Dragon":
                return DRAGON;
            default:
                return NOTYPE;
        }
    }
}
