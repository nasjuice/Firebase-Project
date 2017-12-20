package quote.qmpsnjquote;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author Max Page-Slowik, Naasir Jusab
 * This is a bean object that holds all the necessary data for an object to be qualified as a Quote.
 */
public class Quote {

    private int id;
    private String category;
    private String attributed;
    private String blurb;
    private String quote;
    private String reference;
    private String date;
    private final static String TAG = "Quote";

    public Quote() {

    }

    /**
     * This method returns the quote field.
     * @return String quote
     */
    public String getQuote() {
        return quote;
    }

    /**
     * This method gives a value to the quote field.
     * @param quote String
     */
    public void setQuote(String quote) {
        this.quote = quote;
    }

    /**
     * This method return a quote ID.
     * @return Integer id
     */
    public int getQuoteId() {
        return id;
    }

    /**
     * This method gives a value to the id field.
     * @param id Integer
     */
    public void setQuoteId(int id) {
        this.id = id;
    }

    /**
     * This method returns the author name.
     * @return String attributed
     */
    public String getAuthorName() {
        return attributed;
    }

    public void setAuthorName(String authorName) {
        this.attributed = authorName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBlurb() {
        return blurb;
    }

    public void setBlurb(String blurb) {
        this.blurb = blurb;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
