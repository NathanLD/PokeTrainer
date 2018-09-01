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
 * Created by natle on 01/02/2018.
 */

public class TrainersActivity extends AppCompatActivity {

    private static final int TRAINER_ADD = 1;
    private static final int TRAINER_EDIT = 2;

    private Trainers trainers;
    private TrainerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainers);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        trainers = Model.trainers;

        Button addTrainer = (Button) findViewById(R.id.addTrainer);
        addTrainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAddTrainer();
            }
        });

        ListView list = (ListView) findViewById(R.id.listTrainers);
        adapter = new TrainerAdapter(this, trainers.getTrainers());
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TrainersActivity.this, EditTrainerActivity.class);
                intent.putExtra("trainer", trainers.getTrainers().get((int) id));
                intent.putExtra("id",id);
                startActivityForResult(intent, TRAINER_EDIT);
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, int position, final long id) {
                view.animate().setDuration(2000).alpha(0).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        trainers.getTrainers().remove((int) id);
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
        if (requestCode == TRAINER_ADD && resultCode == RESULT_OK){
            String nickname = data.getStringExtra("nickname");
            int age = data.getIntExtra("age",-1);
            //SimpleDateFormat sdf =new SimpleDateFormat("MM-dd-yyyy");
            //String birthday = sdf.format(age);
            //System.out.println(birthday);
            boolean male = data.getBooleanExtra("male",true);
            boolean[] badges = data.getBooleanArrayExtra("badges");
            Pokemons pokemons = data.getParcelableExtra("pokemons");
            Trainer t = new Trainer(nickname,male,age, badges, pokemons);
            trainers.addTrainer(t);
            adapter.notifyDataSetChanged();
            Log.d("TrainersActivity","Trainer créé : "+t.toString());
        }

        if (requestCode == TRAINER_EDIT && resultCode == RESULT_OK){
            long id = data.getLongExtra("id", -1);
            trainers.getTrainers().remove((int) id);
            String nickname = data.getStringExtra("nickname");
            int age = data.getIntExtra("age",-1);
            //SimpleDateFormat sdf =new SimpleDateFormat("MM-dd-yyyy");
            //String birthday = sdf.format(age);
            boolean male = data.getBooleanExtra("male",true);
            boolean[] badges = data.getBooleanArrayExtra("badges");
            Pokemons pokemons = data.getParcelableExtra("pokemons");
            Trainer t = new Trainer(nickname,male,age, badges, pokemons);
            trainers.addTrainer(t);
            adapter.notifyDataSetChanged();
            Log.d("MainActivity","Trainer modifié : "+t.toString());
        }
    }

    private class TrainerAdapter extends ArrayAdapter<Trainer> {

        public TrainerAdapter(@NonNull Context context, @NonNull List<Trainer> objects) {
            super(context, android.R.layout.simple_list_item_1, objects);
        }

        private class TrainerHolder{
            TextView nickname;
            TextView age;
            TextView badges_count;
            TextView pokemons_count;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            TrainerHolder holder;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.trainer_item, null);
                holder = new TrainerHolder();
                holder.nickname = (TextView) convertView.findViewById(R.id.nickname);
                holder.age = (TextView) convertView.findViewById(R.id.age);
                holder.badges_count = (TextView) convertView.findViewById(R.id.badges_count);
                holder.pokemons_count = (TextView) convertView.findViewById(R.id.pokemons_count);
                convertView.setTag(holder);
            } else {
                holder = (TrainerHolder) convertView.getTag();
            }
            Trainer trainer = getItem(position);
            holder.nickname.setText(trainer.getNickname());
            //SimpleDateFormat sdf =new SimpleDateFormat("MM-dd-yyyy");
            //holder.age.setText(String.valueOf(Utils.calculateAge(trainer.getAge()))+" ans");
            holder.age.setText(String.valueOf(trainer.getAge())+" ans");
            /*
            if (trainer.isMale())convertView.setBackgroundColor(Color.BLUE);
            else convertView.setBackgroundColor(Color.RED);
             */
            if(trainer.isMale()) holder.nickname.setTextColor(Color.BLUE);
            else holder.nickname.setTextColor(Color.RED);
            holder.badges_count.setText(String.valueOf(trainer.countBadges()));
            holder.pokemons_count.setText(String.valueOf(trainer.countPokemons()));
            return convertView;
        }
    }

    private void onClickAddTrainer() {
        Intent intent = new Intent(this, EditTrainerActivity.class);
        startActivityForResult(intent,TRAINER_ADD);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_trainer, menu);
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
            case R.id.sort_badges : onClickSortBadges(); return true;
            case R.id.sort_pokemons : onClickSortPokemons(); return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void onClickSortPokemons() {
        //Log.d("Myapp","poke");
        Collections.sort(trainers.getTrainers(), new Comparator<Trainer>(){
            @Override
            public int compare(Trainer t1, Trainer t2)
            {
                int compare = (t1.countPokemons() < t2.countPokemons()) ? 1 : 0;
                if(compare == 0){
                    compare = (t1.countPokemons() == t2.countPokemons()) ? 0 : -1;
                }
                return compare;
            }
        });
        //Log.d("Myapp",trainers.toString());
        adapter.notifyDataSetChanged();
    }

    private void onClickSortBadges() {
        Collections.sort(trainers.getTrainers(), new Comparator<Trainer>(){
            @Override
            public int compare(Trainer t1, Trainer t2)
            {
                int compare = (t1.countBadges() < t2.countBadges()) ? 1 : 0;
                if(compare == 0){
                    compare = (t1.countBadges() == t2.countBadges()) ? 0 : -1;
                }
                return compare;
            }
        });
        //Log.d("Myapp",trainers.toString());
        adapter.notifyDataSetChanged();
    }
}
