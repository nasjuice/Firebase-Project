package quote.qmpsnjquote;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
/**
 * @author Max Page-Slowik, Naasir Jusab
 * This Activity is used to display the quote view
 */
public class QuoteActivity extends MenuActivity {


    private String attributed;
    private String blurb;
    private String quote;
    private String reference;
    private String date;

    private Context context;
    private TextView textViewAttribute;
    private TextView textViewQuote;
    private TextView textViewDate;
    private TextView textViewReference;

    /**
     * creates the quote view
     * @param savedInstanceState the instance state of the app
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        context = this;

        textViewAttribute = (TextView) findViewById(R.id.textViewAttributed);
        textViewDate = (TextView) findViewById(R.id.textViewDate);
        textViewQuote = (TextView) findViewById(R.id.textViewQuote);
        textViewReference = (TextView) findViewById(R.id.textViewReference);
        //for the alertbox blurb
        textViewAttribute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(blurb);
                builder.setPositiveButton("Exit", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        //gets the extras for random
        Bundle bundle = getIntent().getExtras();
        this.attributed = bundle.getString("attributed", "");
        this.blurb = bundle.getString("blurb", "");
        this.date = bundle.getString("date", "");
        this.quote = bundle.getString("quote", "");
        this.reference = bundle.getString("reference", "");
        //adds string to shared preferences
        SharedPreferences pref = getSharedPreferences("FirebaseQuotes", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("attributed", attributed);
        editor.putString("blurb", blurb);
        editor.putString("date", date);
        editor.putString("quote", quote);
        editor.putString("reference", reference);
        editor.commit();

    }

    /**
     * Overridden in resume to set the text the shared preferences
     */
    @Override
    protected void onResume()
    {
        super.onResume();

        textViewAttribute.setText(attributed);
        textViewDate.setText(date);
        textViewQuote.setText(quote);
        textViewReference.setText(reference);

        Linkify.addLinks(textViewReference, Linkify.WEB_URLS);


    }







}
