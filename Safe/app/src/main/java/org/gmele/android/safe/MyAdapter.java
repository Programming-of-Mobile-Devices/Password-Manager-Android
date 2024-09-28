package org.gmele.android.safe;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import java.util.Collections;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/*
* Γενικά , Adapter στο android είναι ουσιαστικά με "γέφυρα" ανάμεσα στα αποθηκευμένα δεδομένα σε οποιοδήποτε
* δομή δεδομένων και το πως αυτά εμφανίζονται στο χρήστη στο UI. Η κλάση ArrayAdapter που κληρονόμεί η MyAdapter
* μπορεί να χρησιμοποιηθεί για να επιστρέφει ένα view για κάθε εγγραφή KeyRec (οπου κάθε object KeyRec αποτελείται
* απο τα 8 πεδία πχ "ιδιοκτησία" κλπ.) .Κάθε φορά που γίνεται μία αλλαγή η κλάση MyAdapter ενημερώνεται απο την
* mainActivity μέσω της κλήσης της συνάρτησης "notifyDataSetChanged()" . Ετσι η κλάση ξέρει πότε θα πρέπει να
* αναδιατάξει-επεξεργαστεί τα δεδομένα(views) και αν θα τα εμφανίσει με κάποιο διαφοτερικό τρόπο π.χ. ταξινομημενα.
* Έχουμε τέλος εισάγει και μια συνάρτηση sort η οποία παρεμβαίνει στα views και τα αναδιατάσει με κάποιο κριτήριο ταξινόμησης
* */
public class MyAdapter extends ArrayAdapter<KeyRec> implements  AdapterView.OnItemClickListener
{

    Context mContext;
    MainActivity Act;
    private ArrayList<KeyRec> dataSet;

    private int lastPosition;
    ViewHolder LastH;
    int OrigColor;
    int SelColor;
    int SelRow;
    private Filter filter; // Η κλάση Filter θα μας βοηθήσει με το φιλτράρισμα των views με βαση term
    public MyAdapter (ArrayList<KeyRec> data, Context context)
    {
        super (context, R.layout.list_lay, data);
        this.dataSet = data; // Πάρε της αναφορά των stored data
        this.mContext = context;
        Act = (MainActivity) mContext;
        lastPosition = -1;
        LastH = null;
        SelRow = -1;
        OrigColor = Color.TRANSPARENT;
        SelColor = Color.CYAN;
    }

/*
    @Override
    public void onClick (View v)
    {
        System.out.println ("Clicked....." + v.getClass ().getName ());
        int position = (Integer) v.getTag ();
        Object object = getItem (position);
        KeyRec dataModel = (KeyRec) object;

        switch (v.getId ())
        {
            //case R.id.item_info:
            //    Snackbar.make(v, "Release date " +dataModel.getFeature(), Snackbar.LENGTH_LONG)
            //            .setAction("No action", null).show();
            //    break;
        }
    }
*/
    // Καλείται οποτε πρέπει να κάνει display νέα δεδομένα (η παραμετροποιημένα) και με την κλήση της συνάρτησης "notifyDataSetChanged()"
    @Override
    public View getView (int position, View convertView, ViewGroup parent)
    {
        // Get the data item for this position
        KeyRec dataModel = getItem (position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null)
        {
            viewHolder = new ViewHolder ();
            LayoutInflater inflater = LayoutInflater.from (getContext ());
            convertView = inflater.inflate (R.layout.list_lay, parent, false);
            viewHolder.TvC1 = (TextView) convertView.findViewById (R.id.TvC1);
            viewHolder.TvC2 = (TextView) convertView.findViewById (R.id.TvC2);
            viewHolder.TvC3 = (TextView) convertView.findViewById (R.id.TvC3);
            viewHolder.TvC4 = (TextView) convertView.findViewById (R.id.TvC4);
            viewHolder.TvC5 = (TextView) convertView.findViewById (R.id.TvC5);
            viewHolder.TvC6 = (TextView) convertView.findViewById (R.id.TvC6);
            viewHolder.TvC7 = (TextView) convertView.findViewById (R.id.TvC7);
            viewHolder.TvC8 = (TextView) convertView.findViewById (R.id.TvC8);
            result = convertView;
            convertView.setTag (viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag ();
            result = convertView;
        }

        if (position == SelRow)
            convertView.setBackgroundColor (SelColor);
        else
            convertView.setBackgroundColor (OrigColor);
        Animation animation = AnimationUtils.loadAnimation (mContext, (position > lastPosition) ? R.anim.up_from_bottom
            : R.anim.down_from_top);
        result.startAnimation (animation);
        lastPosition = position;

        viewHolder.TvC1.setText (dataModel.Fields[0]);
        viewHolder.TvC2.setText (dataModel.Fields[1]);
        viewHolder.TvC3.setText (dataModel.Fields[2]);
        viewHolder.TvC4.setText (dataModel.Fields[3]);
        viewHolder.TvC5.setText (dataModel.Fields[4]);
        viewHolder.TvC6.setText (dataModel.Fields[5]);
        viewHolder.TvC7.setText (dataModel.Fields[6]);
        viewHolder.TvC8.setText (dataModel.Fields[7]);
        //viewHolder.TvC1.setOnClickListener (this);
        viewHolder.TvC1.setTag (position);
        viewHolder.TvC2.setTag (position);
        viewHolder.TvC3.setTag (position);
        viewHolder.TvC4.setTag (position);
        viewHolder.TvC5.setTag (position);
        viewHolder.TvC6.setTag (position);
        viewHolder.TvC7.setTag (position);
        viewHolder.TvC8.setTag (position);

        LastH = viewHolder;
        // Return the completed view to render on screen
        return convertView;
    }

    @Override
    public void onItemClick (AdapterView<?> parent, View view, int position, long id)
    {
        System.out.println ("Item clicked " + position + " " + id);
        if (SelRow == position)
        {
            SelRow = -1;
            view.setBackgroundColor (OrigColor);
            Act.MenuPos.clear ();
            Act.getMenuInflater ().inflate (R.menu.menu_main, Act.MenuPos);
        }
        else
        {
            final int firstListItemPosition = parent.getFirstVisiblePosition();
            final int lastListItemPosition = firstListItemPosition + parent.getChildCount() - 1;
            if (SelRow >= firstListItemPosition && SelRow <= lastListItemPosition )
            {

                final int childIndex = SelRow - firstListItemPosition;
                parent.getChildAt (childIndex).setBackgroundColor (OrigColor);
                System.out.println ("***" + firstListItemPosition + " " + lastListItemPosition + " " + position + " " + childIndex) ;
            }
            SelRow = position;
            view.setBackgroundColor (SelColor);
            Act.MenuPos.clear ();
            Act.getMenuInflater ().inflate (R.menu.menu_selected, Act.MenuPos);
        }
    }
    // Κάνει ταξινόμηση των "Αποθηκευμένων δεδομένων"(επειδη το dataset ειναι αναφορά) και ενημερώνει τον adapter για την αλλαγή
    public void Sort()
    {
        Collections.sort(dataSet, (p1, p2) -> p1.getFields()[0].compareTo(p2.getFields()[0])); // Ταξινόμηση κατα το στοιχείο "ιδιοκτήτη"
        notifyDataSetChanged();
    }

    public void Sort(int filterId)
    {
        Collections.sort(dataSet, (p1, p2) -> p1.getFields()[filterId].compareTo(p2.getFields()[filterId])); // Ταξινόμηση κατα το στοιχείο "ιδιοκτήτη"
        notifyDataSetChanged();
    }


    // ΝΕΟ 5. Αναζήτηση με φίλτρα
    public void SearchByFilter(CharSequence constraint)
    {
        getFilter().filter(constraint);
    }

    // Κατασκευάζει ενα αντικείμενο της κλάσης TermFiltering αν δεν υπάρχει
    // και το επιστρέφει με το ονομά της
    @Override
    public Filter getFilter()
    {
        if (filter == null)
            filter = new TermFiltering();


        return filter;

    }

    // Ουσιαστικά φιλτράρει με το κενο string ετσι ώστε να πάρει ολα τα αποτελέσματα (κανει reset)
    public void resetAdapter() {
        getFilter().filter(""); // Reset the filter to show all data
    }

    class ViewHolder
    {
        TextView TvC1;
        TextView TvC2;
        TextView TvC3;
        TextView TvC4;
        TextView TvC5;
        TextView TvC6;
        TextView TvC7;
        TextView TvC8;
    }

    /*
    * Η κλάση Filter γενικά στο Android είναι μια "αφηρημένη" κλάση (abstract class , οποτε πρέπει να κληρονομήθει)
    * η οποία μας βοηθάει να εφαρμόσουμε "φιλτραρίσματα" πάνω σε δεδομένα. Οι λειτουργίες της καλούνται ασύχρονα με την
    * συνάρτηση filter().
    * */
    private class TermFiltering extends Filter {

        // Δημιουργούμε μεταβλητή η οποία τα δεχτεί τα αρχικά δεδομένα , ετσι ώστε οταν δεν υπάρχει όρος για αναζήτηση (κενό string "") να επιστρέψουμε στα αρχικά δεδομένα
        private ArrayList<KeyRec> originalList;

        public TermFiltering() {
            this.originalList = new ArrayList<>(dataSet); // δημιουργία αντιγράφου
        }
        // καλείται μέσω της filtering για το φιλτράρισμα των αποτελεσμάτων
        @Override
        /*CharSequence = readonly string(κατι σαν πίνακα χαρακτήρων πιο συγκεκριμένα) = Input του χρήστη */
        protected FilterResults performFiltering(CharSequence constraint)  {
            FilterResults results = new FilterResults(); // Κλάση της Filter που θα αποθηκεύσει τα φιλτραρισμένα αποτελέσματα
            String prefix = constraint.toString().toLowerCase(); // μετατροπή charsequence -> String

            if (prefix == null || prefix.length() == 0) { // Κενό string "" = επιστροφή αρχικών δεδομένων
                results.values = new ArrayList<>(originalList);
                results.count = originalList.size();
            } else {
                /*Δημιουργούμε την λίστα nList για να αποθηκεύσουμε τα φιλτραρισμένα αποτελέσματα και την θέτουμε ως final για να μην διαγραφτεί
                οταν βγεί εκτος scope και να περάσουμε τα αποτελέσματα στο return
                 */
                final ArrayList<KeyRec> nlist = new ArrayList<>();
                for (KeyRec key : originalList) {
                    for (String field : key.Fields) {
                        // Για κάθε record του keyrec ελέγχουμε αν εμπεριέχεται ο όρος σε κάποιο απο τα πεδία και αν ναι τον περνάμε στην λίστα
                        if (field.toLowerCase().contains(prefix)) {
                            nlist.add(key);
                            break;
                        }
                    }
                }
                results.values = nlist;
                results.count = nlist.size();
            }
            return results;
        }
        // Τρέχει μετά τον filtering για να ενημερώσει τον adapter
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            dataSet.clear();
            dataSet.addAll((ArrayList<KeyRec>) results.values);
            notifyDataSetChanged();
        }
    }


}







