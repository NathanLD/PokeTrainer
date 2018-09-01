package fr.uha.ledigabel.poketrainer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by natle on 02/02/2018.
 */

public class EditPokemonActivity extends AppCompatActivity {

    private EditText species_ui;
    private Spinner type1_ui;
    private Spinner type2_ui;
    private TextView status_ui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pokemon);

        species_ui = (EditText) findViewById(R.id.species);
        type1_ui = (Spinner) findViewById(R.id.type1);
        type1_ui.setAdapter(new ArrayAdapter<PkmType>(this, android.R.layout.simple_spinner_item, PkmType.values()));
        type2_ui = (Spinner) findViewById(R.id.type2);
        type2_ui.setAdapter(new ArrayAdapter<PkmType>(this, android.R.layout.simple_spinner_item, PkmType.values()));
        status_ui = (TextView) findViewById(R.id.status_pokemonEdit);

        if (getIntent().hasExtra("pokemon")) {
            Pokemon pokemon = getIntent().getParcelableExtra("pokemon");
            species_ui.setText(pokemon.getName());

            type1_ui.setSelection(pokemon.getType1().getId());
            type2_ui.setSelection(pokemon.getType2().getId());
        }

        Button valid = (Button) findViewById(R.id.validPokemonButton);
        valid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onValidPokemon(v);
            }
        });

        Button cancel = (Button) findViewById(R.id.cancelPokemonButton);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCancelPokemon(v);
            }
        });
    }

    private void onCancelPokemon(View v) {
        setResult(RESULT_CANCELED);
        finish();
    }

    private void onValidPokemon(View v) {
        if (species_ui.getText().length() == 0){
            species_ui.setHintTextColor(Color.RED);
            species_ui.setHint(getString(R.string.noName));
        }
        else if(type1_ui.getSelectedItem().equals(PkmType.NOTYPE)){
            status_ui.setTextColor(Color.RED);
            status_ui.setText(getString(R.string.noType1));
        }
        else{
            String species = species_ui.getText().toString();
            String type1 = type1_ui.getSelectedItem().toString();
            String type2 = type2_ui.getSelectedItem().toString();

            Intent intent = getIntent();
            intent.putExtra("species", species);
            intent.putExtra("type1", type1);
            intent.putExtra("type2", type2);
            setResult(RESULT_OK, intent);
            finish();
        }

    }

}
