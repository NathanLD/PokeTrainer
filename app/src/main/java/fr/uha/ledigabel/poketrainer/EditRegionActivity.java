package fr.uha.ledigabel.poketrainer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by natle on 02/02/2018.
 */

public class EditRegionActivity extends AppCompatActivity {

    private EditText name_ui;
    private Spinner pokemon_ui;
    private TextView status_ui;

    private Pokemons wildpokemons;
    private WildPokemonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_region);

        name_ui = (EditText) findViewById(R.id.region);
        status_ui = (TextView) findViewById(R.id.status_regionEdit);

        pokemon_ui = (Spinner) findViewById(R.id.wild_spinner);
        String[] names = Model.pokemons.getNames();
        pokemon_ui.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, names));

        if (getIntent().hasExtra("region")) {
            Region region = getIntent().getParcelableExtra("region");
            name_ui.setText(region.getName());
            wildpokemons = region.getPokemons();
        } else {
            wildpokemons = new Pokemons();
        }

        Button valid = (Button) findViewById(R.id.validRegionButton);
        valid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onValidRegion(v);
            }
        });

        Button cancel = (Button) findViewById(R.id.cancelRegionButton);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCancelRegion(v);
            }
        });

        // ajouté pour wild
        Button addWild = (Button) findViewById(R.id.add_wild_button);
        addWild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddWild(v);
            }
        });

        // ajouté pour wild
        ListView list = (ListView) findViewById(R.id.list_wild);
        adapter = new EditRegionActivity.WildPokemonAdapter(this, wildpokemons.getPokemons());
        list.setAdapter(adapter);
    }

    private class WildPokemonAdapter extends ArrayAdapter<Pokemon> {

        public WildPokemonAdapter(@NonNull Context context, @NonNull List<Pokemon> objects) {
            super(context, android.R.layout.simple_list_item_1, objects);
        }

        private class WildPokemonHolder {
            TextView species;
            TextView type1;
            TextView type2;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            EditRegionActivity.WildPokemonAdapter.WildPokemonHolder holder;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.pokemon_item, null);
                holder = new EditRegionActivity.WildPokemonAdapter.WildPokemonHolder();
                holder.species = (TextView) convertView.findViewById(R.id.species);
                holder.type1 = (TextView) convertView.findViewById(R.id.type1);
                holder.type2 = (TextView) convertView.findViewById(R.id.type2);
                convertView.setTag(holder);
            } else {
                holder = (EditRegionActivity.WildPokemonAdapter.WildPokemonHolder) convertView.getTag();
            }
            Pokemon pokemon = getItem(position);
            holder.species.setText(pokemon.getName());
            // Mettre les types en couleur ??
            holder.type1.setText(pokemon.getType1().toString());
            holder.type1.setTextColor((pokemon.getType1().getColor()));
            holder.type2.setText(pokemon.getType2().toString());
            holder.type2.setTextColor((pokemon.getType2().getColor()));
            return convertView;
        }
    }

    private void onAddWild(View v) {
        for (Pokemon p : Model.pokemons.getPokemons()) {
            if (p.getName() == pokemon_ui.getSelectedItem().toString() && wildpokemons.contains(p)) {
                status_ui.setTextColor(Color.RED);
                status_ui.setText(getString(R.string.alreadyInList));
            } else if (p.getName() == pokemon_ui.getSelectedItem().toString()){
                wildpokemons.addPokemon(p);
                status_ui.setText("");
                adapter.notifyDataSetChanged();
            }
        }
    }



    private void onCancelRegion(View v) {
        setResult(RESULT_CANCELED);
        finish();
    }

    private void onValidRegion(View v) {
        if (name_ui.getText().length() == 0){
            name_ui.setHintTextColor(Color.RED);
            name_ui.setHint(getString(R.string.noName));
        }
        else{
            String name = name_ui.getText().toString();
            Intent intent = getIntent();
            intent.putExtra("name", name);
            // TRUCS WILD
            intent.putExtra("pokemons", wildpokemons);
            setResult(RESULT_OK, intent);
            finish();
        }


    }

}
