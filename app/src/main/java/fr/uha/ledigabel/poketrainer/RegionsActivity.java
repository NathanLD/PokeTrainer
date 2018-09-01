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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by natle on 02/02/2018.
 */

public class RegionsActivity extends AppCompatActivity {

    private static final int REGION_ADD = 1;
    private static final int REGION_EDIT = 2;

    private Regions regions;
    private RegionsActivity.RegionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regions);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        regions = Model.regions;
        //regions.populate();

        Button addRegion = (Button) findViewById(R.id.addRegion);
        addRegion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAddRegion();
            }
        });

        ListView list = (ListView) findViewById(R.id.listRegions);
        adapter = new RegionsActivity.RegionAdapter(this, regions.getRegions());
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(RegionsActivity.this, EditRegionActivity.class);
                intent.putExtra("region", regions.getRegions().get((int) id));
                intent.putExtra("id",id);
                startActivityForResult(intent, REGION_EDIT);
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, int position, final long id) {
                view.animate().setDuration(2000).alpha(0).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        regions.getRegions().remove((int) id);
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
        if (requestCode == REGION_ADD && resultCode == RESULT_OK){
            String name = data.getStringExtra("name");
            // TRUCS POKEMON WILD
            Pokemons pokemons = data.getParcelableExtra("pokemons");
            Region r = new Region(name, pokemons);
            regions.addRegion(r);
            //Model.regions.addRegion(r);
            adapter.notifyDataSetChanged();
            Log.d("RegionsActivity","Region créée : "+r.toString());
        }

        if (requestCode == REGION_EDIT && resultCode == RESULT_OK){
            long id = data.getLongExtra("id", -1);
            regions.getRegions().remove((int) id);
            String name = data.getStringExtra("name");
            // TRUCS POKEMON WILD
            Pokemons pokemons = data.getParcelableExtra("pokemons");
            Region r = new Region(name, pokemons);
            regions.addRegion(r);
            //Model.regions.addRegion(r);
            adapter.notifyDataSetChanged();
            Log.d("RegionsActivity","Region modifiée : "+r.toString());
        }
    }

    private class RegionAdapter extends ArrayAdapter<Region> {

        public RegionAdapter(@NonNull Context context, @NonNull List<Region> objects) {
            super(context, android.R.layout.simple_list_item_1, objects);
        }

        private class RegionHolder{
            TextView name;
            TextView pokemon_count;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            RegionsActivity.RegionAdapter.RegionHolder holder;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.region_item, null);
                holder = new RegionsActivity.RegionAdapter.RegionHolder();
                holder.name = (TextView) convertView.findViewById(R.id.region);
                holder.pokemon_count = (TextView) convertView.findViewById(R.id.pokemon_count);
                convertView.setTag(holder);
            } else {
                holder = (RegionsActivity.RegionAdapter.RegionHolder) convertView.getTag();
            }
            Region region = getItem(position);
            holder.name.setText(region.getName());
           // TRUC PKMN WILD
            holder.pokemon_count.setText(String.valueOf(region.getPokemons().getPokemons().size()));
            return convertView;
        }
    }

    private void onClickAddRegion() {
        Intent intent = new Intent(this, EditRegionActivity.class);
        startActivityForResult(intent,REGION_ADD);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_region, menu);
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
            case R.id.sort_pokemons : onClickSortPokemons(); return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void onClickSortPokemons() {
        //Log.d("Myapp","poke");
        Collections.sort(regions.getRegions(), new Comparator<Region>(){
            @Override
            public int compare(Region r1, Region r2)
            {
                int compare = (r1.countPokemons() < r2.countPokemons()) ? 1 : 0;
                if(compare == 0){
                    compare = (r1.countPokemons() == r2.countPokemons()) ? 0 : -1;
                }
                return compare;
            }
        });
        //Log.d("Myapp",trainers.toString());
        adapter.notifyDataSetChanged();
    }

}
