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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by natle on 01/02/2018.
 */

public class EditTrainerActivity extends AppCompatActivity {

    private EditText nickname_ui;
    private EditText age_ui;
    private RadioGroup gender_ui;
    private RadioButton male_ui;
    private RadioButton female_ui;
    private TextView genderView_ui;

    private Spinner pokemon_ui;
    private Pokemons ownpokemons;
    private OwnPokemonAdapter adapter;

    /*private CheckBox badgeearth_ui;
    private CheckBox badgefire_ui;
    private CheckBox badge3_ui;
    private CheckBox badge4_ui;
    private CheckBox badgewater_ui;
    private CheckBox badge6_ui;
    private CheckBox badge7_ui;
    private CheckBox badge8_ui;*/
    private List<CheckBox> badges_ui = new ArrayList<CheckBox>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_trainer);

        nickname_ui = (EditText) findViewById(R.id.nickname);
        age_ui = (EditText) findViewById(R.id.age);
        gender_ui = (RadioGroup) findViewById(R.id.gender);
        genderView_ui = (TextView) findViewById(R.id.genderText);
        male_ui = (RadioButton) findViewById(R.id.maleRadioButton);
        male_ui.setChecked(true);
        female_ui = (RadioButton) findViewById(R.id.femaleRadioButton);

        // Voir si on peut pas faire un for, mais faut mettre les id dans une collection
        badges_ui.add((CheckBox) findViewById(R.id.boulder));
        badges_ui.add((CheckBox) findViewById(R.id.cascade));
        badges_ui.add((CheckBox) findViewById(R.id.thunder));
        badges_ui.add((CheckBox) findViewById(R.id.rainbow));
        badges_ui.add((CheckBox) findViewById(R.id.soul));
        badges_ui.add((CheckBox) findViewById(R.id.marsh));
        badges_ui.add((CheckBox) findViewById(R.id.volcano));
        badges_ui.add((CheckBox) findViewById(R.id.earth));

        pokemon_ui = (Spinner) findViewById(R.id.own_spinner);
        String[] names = Model.pokemons.getNames();
        pokemon_ui.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, names));

        if (getIntent().hasExtra("trainer")) {
            Trainer trainer = getIntent().getParcelableExtra("trainer");
            nickname_ui.setText(trainer.getNickname());
            //SimpleDateFormat sdf = new SimpleDateFormat("DDDD dd MMMM YYYY");
            //age_ui.setText(String.valueOf(Utils.calculateAge(trainer.getAge())));
            age_ui.setText(String.valueOf(trainer.getAge()));
            male_ui.setChecked(trainer.isMale());
            female_ui.setChecked(!trainer.isMale());

            /*badges_ui.get(0).setChecked(trainer.getBadges()[0]);
            badges_ui.get(1).setChecked(trainer.getBadges()[1]);
            badges_ui.get(2).setChecked(trainer.getBadges()[2]);
            badges_ui.get(3).setChecked(trainer.getBadges()[3]);
            badges_ui.get(4).setChecked(trainer.getBadges()[4]);
            badges_ui.get(5).setChecked(trainer.getBadges()[5]);
            badges_ui.get(6).setChecked(trainer.getBadges()[6]);
            badges_ui.get(7).setChecked(trainer.getBadges()[7]);*/
            for(int i = 0; i< Utils.NB_BADGES; i++){
               badges_ui.get(i).setChecked(trainer.getBadges()[i]);
            }

            ownpokemons = trainer.getPokemons();
        }
        else{
            ownpokemons = new Pokemons();
        }

        Button valid = (Button) findViewById(R.id.validTrainerButton);
        valid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onValidTrainer(v);
            }
        });

        Button cancel = (Button) findViewById(R.id.cancelTrainerButton);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCancelTrainer(v);
            }
        });

        // ajouté pour own
        Button addOwn = (Button) findViewById(R.id.add_own_button);
        addOwn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddOwn(v);
            }
        });

        // ajouté pour own
        GridView list = (GridView) findViewById(R.id.list_own);
        adapter = new EditTrainerActivity.OwnPokemonAdapter(this, ownpokemons.getPokemons());
        list.setAdapter(adapter);
    }

    private class OwnPokemonAdapter extends ArrayAdapter<Pokemon> {

        public OwnPokemonAdapter(@NonNull Context context, @NonNull List<Pokemon> objects) {
            super(context, android.R.layout.simple_list_item_1, objects);
        }

        private class OwnPokemonHolder{
            TextView species;
            TextView type1;
            TextView type2;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            EditTrainerActivity.OwnPokemonAdapter.OwnPokemonHolder holder;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.pokemon_item, null);
                holder = new EditTrainerActivity.OwnPokemonAdapter.OwnPokemonHolder();
                holder.species = (TextView) convertView.findViewById(R.id.species);
                holder.type1 = (TextView) convertView.findViewById(R.id.type1);
                holder.type2 = (TextView) convertView.findViewById(R.id.type2);
                convertView.setTag(holder);
            } else {
                holder = (EditTrainerActivity.OwnPokemonAdapter.OwnPokemonHolder) convertView.getTag();
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

    private void onAddOwn(View v) {
        for(Pokemon p : Model.pokemons.getPokemons()){
            if(p.getName()==pokemon_ui.getSelectedItem().toString()){
                ownpokemons.addPokemon(p);
                adapter.notifyDataSetChanged();
                return;
            }
        }
    }

    private void onCancelTrainer(View v) {
        setResult(RESULT_CANCELED);
        finish();
    }

    private void onValidTrainer(View v) {
        if (nickname_ui.getText().length() == 0){
            nickname_ui.setHintTextColor(Color.RED);
            nickname_ui.setHint(getString(R.string.noName));
        }
        else if(age_ui.getText().toString().length() < 1){
            genderView_ui.setTextColor(Color.RED);
            genderView_ui.setText(getResources().getString(R.string.noAge));
        }
        else {
            try {
                String nickname = nickname_ui.getText().toString();
                int age = Integer.parseInt(age_ui.getText().toString());


                boolean male = false;
                switch (gender_ui.getCheckedRadioButtonId()) {
                    case R.id.maleRadioButton:
                        male = true;
                        break;
                    case R.id.femaleRadioButton:
                        male = false;
                        break;
                }

                boolean badges[] = new boolean[Utils.NB_BADGES];
                for (int i = 0; i < Utils.NB_BADGES; i++) {
                    badges[i] = badges_ui.get(i).isChecked();
                }

                Intent intent = getIntent();
                intent.putExtra("nickname", nickname);
                intent.putExtra("age", age);
                intent.putExtra("male", male);
                intent.putExtra("badges", badges);
                intent.putExtra("pokemons", ownpokemons);
                setResult(RESULT_OK, intent);
                finish();
            }
            catch(Exception e){
                genderView_ui.setText(getResources().getString(R.string.error));
                genderView_ui.setTextColor(Color.RED);
                e.printStackTrace();
                return;
            }
        }
    }
}