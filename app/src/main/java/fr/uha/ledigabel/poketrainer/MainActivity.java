package fr.uha.ledigabel.poketrainer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private static final int TRAINERS = 1;
    private static final int POKEMONS = 2;
    private static final int REGIONS = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button trainersButton = (Button) findViewById(R.id.trainersButton);
        trainersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickTrainers();
            }
        });

        Button pokemonsButton = (Button) findViewById(R.id.pokemonsButton);
        pokemonsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickPokemons();
            }
        });

        Button regionsButton = (Button) findViewById(R.id.regionsButton);
        regionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRegions();
            }
        });

        ImageView imageTrainer = (ImageView)  findViewById(R.id.trainer);
        imageTrainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickTrainers();
            }
        });

        ImageView imagePokemon = (ImageView)  findViewById(R.id.pokemon);
        imagePokemon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickPokemons();
            }
        });

        ImageView imageRegion = (ImageView)  findViewById(R.id.region);
        imageRegion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRegions();
            }
        });

        Model model = new Model();
    }

    private void onClickRegions() {
        Intent intent = new Intent(this, RegionsActivity.class);
        startActivityForResult(intent,REGIONS);
    }

    private void onClickPokemons() {
        Intent intent = new Intent(this, PokemonsActivity.class);
        startActivityForResult(intent,POKEMONS);
    }

    private void onClickTrainers() {
        Intent intent = new Intent(this, TrainersActivity.class);
        startActivityForResult(intent,TRAINERS);
    }
}
