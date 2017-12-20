package quote.qmpsnjquote;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Naasir Jusab and Maximilian Page-Slowik
 *
 * This class takes care of handling and creating different menu options.
 */

public class MenuActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private FirebaseAuth dbAuth;
    private ArrayList<Quote> arrayOfQuotes;
    private ArrayList<String> categories;

    /**
     * creates the menu and sets up the firebase authorization and database
     * @param savedInstanceState the instance state of the app
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        arrayOfQuotes = new ArrayList<>();
        categories = new ArrayList<>();

        dbAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        dbAuth.signInWithEmailAndPassword("mpageslowik@mps.com","pass123");


        getCategories();

    }

    /**
     *  Create options menu
     * @param menu menu object
     * @return boolean if inflated
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     *
     * @param item menu item selected
     * @return true if selected false if nothing happens
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //id of selected item for intent launch of the about screen
        if (id == R.id.about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);



        }
        //launch of the last ran quote in the file
        if(id == R.id.last_run)
        {

            Intent lastRunQuote = new Intent(this, QuoteActivity.class);
            SharedPreferences pref = getSharedPreferences("FirebaseQuotes", MODE_PRIVATE);
            lastRunQuote.putExtra("attributed", pref.getString("attributed", ""));
            lastRunQuote.putExtra("quote", pref.getString("quote", ""));
            lastRunQuote.putExtra("date", pref.getString("date", ""));
            lastRunQuote.putExtra("blurb", pref.getString("blurb", ""));
            lastRunQuote.putExtra("reference", pref.getString("reference", ""));
            startActivity(lastRunQuote);

        }
        //a random quote launched from menu
        if(id == R.id.random)
        {
            Random rand = new Random();
            int random = rand.nextInt(arrayOfQuotes.size());
            Quote qt = arrayOfQuotes.get(random);
            Intent randQuote = new Intent(this, QuoteActivity.class);
            randQuote.putExtra("attributed", qt.getAuthorName());
            randQuote.putExtra("quote", qt.getQuote());
            randQuote.putExtra("date", qt.getDate());
            randQuote.putExtra("blurb", qt.getBlurb());
            randQuote.putExtra("reference",qt.getReference());
            startActivity(randQuote);

        }

        return false;
    }

    /**
     * Adds the category from database to the list
     */
    private void getCategories(){
        mDatabase.child("quotes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapShot : dataSnapshot.getChildren()){
                    categories.add(snapShot.getKey());

                    Log.i("CATEGORY: ", snapShot.getKey());
                    getQuoteArray(snapShot.getKey());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("ERROR",databaseError.getMessage());
            }
        });

    }

    /**
     * Adds to the quote array from each category
     * @param category specific category
     */
    private void getQuoteArray(String category){

        mDatabase.child("quotes").child(category).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    arrayOfQuotes.add(ds.getValue(Quote.class));
                    Log.i("QUOTE: ",ds.getValue(Quote.class).getAuthorName());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("ERROR",databaseError.getMessage());
            }
        });

    }


}
