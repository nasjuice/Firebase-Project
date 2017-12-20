package quote.qmpsnjquote;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

 /**
 *
 * @author Naasir Jusab and Maximilian Page-Slowik
 * This class communicates with firebase in order to display the categories.
 */


public class MainActivity extends MenuActivity {
    private DatabaseReference mDatabase;
    private FirebaseAuth dbAuth;
    private ArrayList<String> categories;


     /**
      * This method is in charge of communicating with firebase in order to populate the categories
      * list which will then be displayed on the screen.
      * @param savedInstanceState
      */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        dbAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        categories = new ArrayList<>();
        final ListView listView = (ListView) findViewById(R.id.quote_list);
        final Context context = this;

        mDatabase = FirebaseDatabase.getInstance().getReference();
        dbAuth.signInWithEmailAndPassword("mpageslowik@mps.com","pass123");

        //get the data for the categories
        mDatabase.child("quotes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapShot : dataSnapshot.getChildren()){
                    categories.add(snapShot.getKey());

                }
                QuoteAdapter adapter = new QuoteAdapter(context, categories, dataSnapshot);

                listView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("ERROR",databaseError.getMessage());
            }
        });

        //sets the click for a listview item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, QuoteActivity.class);
                intent.putExtra("id", (int) id);
                startActivity(intent);
            }
        });
    }









}
