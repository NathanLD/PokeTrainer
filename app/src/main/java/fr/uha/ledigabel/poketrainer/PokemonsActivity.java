package fr.uha.ledigabel.poketrainer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by natle on 02/02/2018.
 */

public class PokemonsActivity extends AppCompatActivity {

    private static final int POKEMON_ADD = 1;
    private static final int POKEMON_EDIT = 2;

    private Pokemons pokemons;
    private PokemonsActivity.PokemonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemons);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pokemons = Model.pokemons;

        Button addPokemon = (Button) findViewById(R.id.addPokemon);
        addPokemon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAddPokemon();
            }
        });

        ListView list = (ListView) findViewById(R.id.listPokemons);
        adapter = new PokemonsActivity.PokemonAdapter(this, pokemons.getPokemons());
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PokemonsActivity.this, EditPokemonActivity.class);
                intent.putExtra("pokemon", pokemons.getPokemons().get((int) id));
                intent.putExtra("id",id);
                startActivityForResult(intent, POKEMON_EDIT);
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, int position, final long id) {
                view.animate().setDuration(2000).alpha(0).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        pokemons.getPokemons().remove((int) id);
                        adapter.notifyDataSetChanged();
                        view.setAlpha(1);
                    }
                });
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == POKEMON_ADD && resultCode == RESULT_OK){
            String species = data.getStringExtra("species");
            String t1 = data.getStringExtra("type1");
            PkmType type1 = PkmType.getByName(t1);
            String t2 = data.getStringExtra("type2");
            PkmType type2 = PkmType.getByName(t2);

            Pokemon p = new Pokemon(species,type1,type2);
            pokemons.addPokemon(p);
            adapter.notifyDataSetChanged();
            Log.d("PokemonsActivity","Pokemon créé : "+p.toString());
        }

        if (requestCode == POKEMON_EDIT && resultCode == RESULT_OK){
            long id = data.getLongExtra("id", -1);
            pokemons.getPokemons().remove((int) id);
            String species = data.getStringExtra("species");
            String t1 = data.getStringExtra("type1");
            PkmType type1 = PkmType.getByName(t1);
            String t2 = data.getStringExtra("type2");
            PkmType type2 = PkmType.getByName(t2);

            Pokemon p = new Pokemon(species,type1,type2);
            pokemons.addPokemon(p);
            adapter.notifyDataSetChanged();
            Log.d("PokemonsActivity","Pokemon modifié : "+p.toString());
        }
    }

    private class PokemonAdapter extends ArrayAdapter<Pokemon> {

        public PokemonAdapter(@NonNull Context context, @NonNull List<Pokemon> objects) {
            super(context, android.R.layout.simple_list_item_1, objects);
        }

        private class PokemonHolder{
            TextView species;
            TextView type1;
            TextView type2;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            PokemonsActivity.PokemonAdapter.PokemonHolder holder;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.pokemon_item, null);
                holder = new PokemonsActivity.PokemonAdapter.PokemonHolder();
                holder.species = (TextView) convertView.findViewById(R.id.species);
                holder.type1 = (TextView) convertView.findViewById(R.id.type1);
                holder.type2 = (TextView) convertView.findViewById(R.id.type2);
                convertView.setTag(holder);
            } else {
                holder = (PokemonsActivity.PokemonAdapter.PokemonHolder) convertView.getTag();
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

    private void onClickAddPokemon() {
        Intent intent = new Intent(this, EditPokemonActivity.class);
        startActivityForResult(intent,POKEMON_ADD);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pokemon, menu);
        //Log.d("Myapp","options");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //Log.d("Myapp","switch");
        switch (id){
            //case R.id.action_settings : return true;
            case R.id.sort_type : onClickSortTypes(); return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void onClickSortTypes() {
        //Log.d("Myapp","poke");
        Collections.sort(pokemons.getPokemons(), new Comparator<Pokemon>(){
            @Override
            public int compare(Pokemon p1, Pokemon p2)
            {
                int compare = (p1.getType1().getId() > p2.getType1().getId()) ? 1 : 0;
                if(compare == 0){
                    compare = (p1.getType1().getId() == p2.getType1().getId()) ? 0 : -1;
                }
                if(compare == 0) {
                    compare = (p1.getType2().getId() > p2.getType2().getId()) ? 1 : 0;
                    if (compare == 0) {
                        compare = (p1.getType2().getId() == p2.getType2().getId()) ? 0 : -1;
                    }
                }
                return compare;
            }
        });
        //Log.d("Myapp",trainers.toString());
        adapter.notifyDataSetChanged();
    }

}
