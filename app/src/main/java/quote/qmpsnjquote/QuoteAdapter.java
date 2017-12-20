package quote.qmpsnjquote;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

/**
 * @author Max Page-Slowik, Naasir Jusab
 * This is the adapted for the custom listview
 */
public class QuoteAdapter extends ArrayAdapter<String> {


    private ArrayList<String> categories = new ArrayList<>();

    private Context context;

    private DataSnapshot snap;

    private static LayoutInflater inflater=null;

    /**
     *  The QuoteAdapter constructor
     * @param context the context the adapter is in
     * @param categories the list of categories to be converted to list view
     * @param snap the datasnapshot to get access to the database
     */
    public QuoteAdapter(Context context, ArrayList<String> categories, DataSnapshot snap){
        super(context,0,categories);
        this.categories =  categories;
        this.context = context;
        this.snap = snap;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    /**
     *  Gets the custom view for the list view
     * @param position the position that is clicked
     * @param convertView the view to be converted
     * @param parent the parent view group
     * @return the view to be displayed
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.category_list,null);
        }
        TextView tv=(TextView) v.findViewById(R.id.category);
        tv.setText(categories.get(position));
        //on lcick listener for item in list view
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent quoteFromCategory = new Intent(context, QuoteActivity.class);
                Quote q = getQuoteFromCategory(position);
                quoteFromCategory.putExtra("attributed", q.getAuthorName());
                quoteFromCategory.putExtra("blurb", q.getBlurb());
                quoteFromCategory.putExtra("reference", q.getReference());
                quoteFromCategory.putExtra("quote", q.getQuote());
                quoteFromCategory.putExtra("date", q.getDate());

                context.startActivity(quoteFromCategory);
            }
        });
       return v;
    }

    /**
     * For getting a random quote from the category
     * @param index the index of the category
     * @return the Quote
     */
    private Quote getQuoteFromCategory(int index){

        DataSnapshot category = snap.child(categories.get(index));
        ArrayList<Quote> quotesFromCategory = new ArrayList<>();


        for(DataSnapshot quote : category.getChildren()){
            quotesFromCategory.add(quote.getValue(Quote.class));

        }


        int value = (int)(Math.random()* category.getChildrenCount());
        return quotesFromCategory.get(value);
    }

}


