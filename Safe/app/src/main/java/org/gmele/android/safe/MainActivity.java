package org.gmele.android.safe;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import org.gmele.general.crypto.Crypto;
import org.gmele.general.exceptions.GmException;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemLongClickListener
{
    final int ReqDestroy = 1;
    final int ReqEncrypt = 2;
    final int ReqDecrypt = 3;
    final int ReqOpen = 4;
    Globals GL;
    String Source;
    String Dest;
    Toolbar toolbar;
    FloatingActionButton fab;
    ListView Grid;
    SearchView searchView;
    MyAdapter Adapter;
    HorizontalScrollView ScTitle;
    HorizontalScrollView ScMain;
    View Touched;
    Menu MenuPos;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);
        GL = Globals.GetInstance ();
        CheckSecurityDir ();
        if (savedInstanceState == null)
        {
            GL.DH = new DataHandler ();
        }
        setContentView (R.layout.activity_main);
        Adapter = new MyAdapter (GL.DH.KeyList, this);
        toolbar = (Toolbar) findViewById (R.id.toolbar);
        setSupportActionBar (toolbar);
        //fab = (FloatingActionButton) findViewById (R.id.fab);
        //fab.setOnClickListener (this);
        //getSupportActionBar ().setDisplayShowHomeEnabled (true);
        //getSupportActionBar ().setIcon (R.mipmap.safe3);
        Grid = (ListView) findViewById (R.id.Grid);
        Grid.setAdapter (Adapter);
        Grid.setOnItemClickListener (Adapter);
        Grid.setOnItemLongClickListener (this);
        this.searchView = (SearchView) findViewById(R.id.search_bar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Handle the submission of the query if needed
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    Adapter.resetAdapter();
                } else {
                    Adapter.getFilter().filter(newText); // Apply the filter for non-empty queries
                }
                return true;
            }

        });

        LinearLayout ListTitle = (LinearLayout) findViewById (R.id.listtitle);
        ListTitle.setMinimumWidth (Grid.getWidth ());
        MakeScroll ();
        ViewTreeObserver observer = Grid.getViewTreeObserver ();
        observer.addOnGlobalLayoutListener (new ViewTreeObserver.OnGlobalLayoutListener ()
        {
            @Override
            public void onGlobalLayout ()
            {
                Grid.getViewTreeObserver ().removeOnGlobalLayoutListener (this);
                MakeTitleSize ();

            }
        });
    }

    @Override
    protected void onDestroy ()
    {
        super.onDestroy ();
        GL.Pass1 = null;
        GL.Pass2 = null;
        GL = null;
    }


    @Override
    public void onBackPressed ()
    {
        long CurTime;
        CurTime = System.currentTimeMillis ();
        if (CurTime - GL.LastBackTime > 4000)
        {
            Toast toast = Toast.makeText (this, "Πιέστε ξανά το πλήκτρο \"Back\" για έξοδο από το πρόγραμμα.",
                Toast.LENGTH_LONG);
            toast.setGravity (Gravity.CENTER, 0, 0);
            toast.show ();
            GL.LastBackTime = CurTime;
            return;
        }
        if (!GL.DH.IsChanged ())
        {
            finish ();
            return;
        }
        new AlertDialog.Builder (this)
            .setTitle ("Τερματισμός Προγράμματος")
            .setMessage ("Να τερματιστεί το πρόγραμμα;\nΠιθανές αλλαγές θα χαθούν.")
            .setIcon (android.R.drawable.ic_dialog_alert)
            .setPositiveButton ("ΝΑΙ", new DialogInterface.OnClickListener ()
            {
                public void onClick (DialogInterface dialog, int whichButton)
                {
                    finish ();
                }
            })
            .setNegativeButton ("ΟΧΙ", null)
            .show ();
    }


    @Override
    public void onClick (View v)
    {
        if (v == fab)
        {
            Snackbar.make (v, "Replace with your own action", Snackbar.LENGTH_LONG).setAction ("Action", null).show ();
        }

    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu)
    {
        MenuPos = menu;
        getMenuInflater ().inflate (R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item)
    {
        int id = item.getItemId ();

        if (id == R.id.MIPasswords)
        {
            PasswordDialog PD = new PasswordDialog (this);
            PD.Show ();
            return true;
        }
        if (id == R.id.MIOpen)
        {
            if (GL.Pass1 == null || GL.Pass2 == null || GL.Pass1.equals ("") || GL.Pass2.equals (""))
            {
                Toast toast = Toast.makeText (this, "Δεν έχουν οριστεί συνθηματικά", Toast.LENGTH_LONG);
                toast.setGravity (Gravity.CENTER, 0, 0);
                toast.show ();
                return true;
            }
            if (!GL.DH.IsChanged ())
            {
                //Intent intent = new Intent (Intent.ACTION_PICK);
                //intent.putExtra (Intent.EXTRA_TITLE, "Άνοιγμα Αρχείου Κλειδιών");
                //intent.addFlags (Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                //startActivityForResult (intent, ReqOpen);
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(intent, ReqOpen);
            }
            else
            {
                new AlertDialog.Builder (this)
                    .setTitle ("Άνοιγμα Αρχείου Κλειδιών")
                    .setMessage ("Υπάρχουν αλλαγές που δεν έχουν αποθηκευτεί.\nΝα ανοίξει νέο αρχείο;")
                    .setIcon (android.R.drawable.ic_dialog_info)
                    .setPositiveButton ("ΝΑΙ", new DialogInterface.OnClickListener ()
                    {
                        public void onClick (DialogInterface dialog, int whichButton)
                        {
                            //Intent intent = new Intent (Intent.ACTION_PICK);
                            //intent.putExtra (Intent.EXTRA_TITLE, "Άνοιγμα Αρχείου Κλειδιών");
                            //intent.addFlags (Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                            //startActivityForResult (intent, ReqOpen);
                            Intent intent = new Intent (Intent.ACTION_GET_CONTENT);
                            intent.setType("*/*");
                            intent.addCategory(Intent.CATEGORY_OPENABLE);
                            try
                            {
                                startActivityForResult (Intent.createChooser(intent, "Select a File to Open"), 0);
                            }
                            catch (android.content.ActivityNotFoundException ex)
                            {
                                // Potentially direct the user to the Market with a Dialog
                                Toast.makeText (MainActivity.this, "Please install a File Manager.", Toast.LENGTH_SHORT).show();
                            }


                        }
                    })
                    .setNegativeButton ("ΟΧΙ", null)
                    .setCancelable (false)
                    .show ();
            }
            return true;
        }

        if (id == R.id.MISearch)
        {
            SearchDialog PD = new SearchDialog (this);
            PD.Show ();
            return true;
        }


        if (id == R.id.MISave)
        {
            if (GL.Pass1 == null || GL.Pass2 == null || GL.Pass1.equals ("") || GL.Pass2.equals (""))
            {
                Toast toast = Toast.makeText (this, "Δεν έχουν οριστεί συνθηματικά", Toast.LENGTH_LONG);
                toast.setGravity (Gravity.CENTER, 0, 0);
                toast.show ();
                return true;
            }
            if (GL.KeyFileName != null)
            {
                DoSaveAs (GL.KeyFileName);
            }
            else
            {
                ShowMessage ("Αποθήκευση Αρχείου Κλειδιών", "Δεν έχει οριστεί όνομα.\nΧρησιμοποιείστε " +
                    "την επιλογή \"Αποθήκευση Τρελός\"");
            }
        }
        if (id == R.id.MISaveAs)
        {
            if (GL.Pass1 == null || GL.Pass2 == null || GL.Pass1.equals ("") || GL.Pass2.equals (""))
            {
                Toast toast = Toast.makeText (this, "Δεν έχουν οριστεί συνθηματικά", Toast.LENGTH_LONG);
                toast.setGravity (Gravity.CENTER, 0, 0);
                toast.show ();
                return true;
            }
            final EditText EtTmp = new EditText (this);
            if (GL.KeyFileName != null)
            {
                EtTmp.setText (GL.KeyFileName.substring (GL.KeyFileName.lastIndexOf ("/") + 1));
            }
            new AlertDialog.Builder (this)
                .setTitle ("Επιλογή Αρχείου Κλειδιών")
                .setIcon (android.R.drawable.ic_input_get)
                .setView (EtTmp)
                .setPositiveButton ("ΟΚ", new DialogInterface.OnClickListener ()
                {
                    public void onClick (DialogInterface dialog, int whichButton)
                    {
                        Dest = EtTmp.getText ().toString ();
                        if (!Dest.startsWith ("/"))
                        {
                            Dest = Environment.getExternalStorageDirectory ().getAbsolutePath () + "/SAFE/" + Dest;
                        }
                        File Check = new File (Dest);
                        if (Check.exists ())
                        {
                            ShowMessage ("Αποθήκευση Αρχείου", "Το αρχείο (" + Dest + ") υπάρχει ήδη.\n" +
                                "Για λόγους ασφαλείας δεν θα γίνει αντικατάσταση");
                            return;
                        }
                        DoSaveAs (Dest);
                    }
                })
                .setNegativeButton ("ΑΚΥΡΩΣΗ", null)
                .setCancelable (false)
                .show ();
        }

        if (id == R.id.MIEncrypt)
        {
            if (GL.Pass1 == null || GL.Pass2 == null || GL.Pass1.equals ("") || GL.Pass2.equals (""))
            {
                Toast toast = Toast.makeText (this, "Δεν έχουν οριστεί συνθηματικά", Toast.LENGTH_LONG);
                toast.setGravity (Gravity.CENTER, 0, 0);
                toast.show ();
                return true;
            }

            Intent intent = new Intent (Intent.ACTION_PICK);
            intent.putExtra (Intent.EXTRA_TITLE, "Αρχείο προς Κρυπτογράφηση");
            intent.addFlags (Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            startActivityForResult (intent, ReqEncrypt);
            return true;
        }
        if (id == R.id.MIDecrypt)
        {
            if (GL.Pass1 == null || GL.Pass2 == null || GL.Pass1.equals ("") || GL.Pass2.equals (""))
            {
                Toast toast = Toast.makeText (this, "Δεν έχουν οριστεί συνθηματικά", Toast.LENGTH_LONG);
                toast.setGravity (Gravity.CENTER, 0, 0);
                toast.show ();
                return true;
            }
            Intent intent = new Intent (Intent.ACTION_PICK);
            intent.putExtra (Intent.EXTRA_TITLE, "Αρχείο προς ΑποΚρυπτογράφηση");
            intent.addFlags (Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            startActivityForResult (intent, ReqDecrypt);
            return true;
        }
        if (id == R.id.MIDestroy)
        {
            Intent intent = new Intent (Intent.ACTION_PICK);
            intent.putExtra (Intent.EXTRA_TITLE, "Καταστροφή Αρχείου");
            intent.addFlags (Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            startActivityForResult (intent, ReqDestroy);
            return true;
        }
        if (id == R.id.MILEdit)
        {
            KeyRecDialog KRD = new KeyRecDialog (this);
            return true;
        }
        if (id == R.id.MILInsert)
        {
            GL.DH.InsertRow (Adapter.SelRow);
            Adapter.notifyDataSetChanged ();
            return true;
        }
        if (id == R.id.MILDelete)
        {
            if (GL.DH.getRowCount () > 3)
            {
                GL.DH.DeleteRow (Adapter.SelRow);
                Adapter.notifyDataSetChanged ();
            }
            return true;
        }
        if (id == R.id.MILMoveUp)
        {
            int R = Adapter.SelRow;
            if (R > 0)
            {
                GL.DH.MoveUp (R);
                Adapter.SelRow--;
                Adapter.notifyDataSetChanged ();
            }
        }
        if (id == R.id.MILMoveDown)
        {
            int R = Adapter.SelRow;
            if (R < GL.DH.getRowCount () - 1)
            {
                GL.DH.MoveDown (R);
                Adapter.SelRow++;
                Adapter.notifyDataSetChanged ();
            }
        }

        if( id== R.id.MISort)
        {
            Adapter.Sort();
        }

        return super.onOptionsItemSelected (item);
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult (requestCode, resultCode, data);
        if (requestCode == ReqOpen && resultCode == RESULT_OK)
        {
            DoOpen (data.getData ());
        }
        if (requestCode == ReqEncrypt && resultCode == RESULT_OK)
        {
            DoEncrypt (data.getData ());
        }
        if (requestCode == ReqDecrypt && resultCode == RESULT_OK)
        {
            DoDecrypt (data.getData ());
        }
        if (requestCode == ReqDestroy && resultCode == RESULT_OK)
        {
            DoDestroy (data.getData ());
        }

    }

    void MakeScroll ()
    {
        ScTitle = (HorizontalScrollView) findViewById (R.id.ScTitle);
        ScMain = (HorizontalScrollView) findViewById (R.id.ScMain);
        ScTitle.setOnTouchListener (new View.OnTouchListener ()
        {
            @Override
            public boolean onTouch (View v, MotionEvent event)
            {
                Touched = ScTitle;
                return false;
            }
        });
        ScMain.setOnTouchListener (new View.OnTouchListener ()
        {
            @Override
            public boolean onTouch (View v, MotionEvent event)
            {
                Touched = ScMain;
                return false;
            }
        });
        ScTitle.getViewTreeObserver ().addOnScrollChangedListener (new ViewTreeObserver.OnScrollChangedListener ()
        {
            @Override
            public void onScrollChanged ()
            {
                if (Touched == ScTitle)
                {
                    int ScrollX = ScTitle.getScrollX ();
                    ScMain.setScrollX (ScrollX);
                }
                else
                {
                    if (Touched == ScMain)
                    {
                        int ScrollX = ScMain.getScrollX ();
                        ScTitle.setScrollX (ScrollX);
                    }
                }
            }
        });
    }

    void MakeTitleSize ()
    {
        int S;
        TextView V;
        V = (TextView) findViewById (R.id.TvTC1);
        V.setWidth (Adapter.LastH.TvC1.getWidth ());
        V = (TextView) findViewById (R.id.TvTC2);
        V.setWidth (Adapter.LastH.TvC2.getWidth ());
        V = (TextView) findViewById (R.id.TvTC3);
        V.setWidth (Adapter.LastH.TvC3.getWidth ());
        V = (TextView) findViewById (R.id.TvTC4);
        V.setWidth (Adapter.LastH.TvC4.getWidth ());
        V = (TextView) findViewById (R.id.TvTC5);
        V.setWidth (Adapter.LastH.TvC5.getWidth ());
        V = (TextView) findViewById (R.id.TvTC6);
        V.setWidth (Adapter.LastH.TvC6.getWidth ());
        V = (TextView) findViewById (R.id.TvTC7);
        V.setWidth (Adapter.LastH.TvC7.getWidth ());
        V = (TextView) findViewById (R.id.TvTC8);
        V.setWidth (Adapter.LastH.TvC8.getWidth ());

    }

    void DoOpen (final Uri Fl)
    {
        String Fn = Fl.getPath ();
        if (!Fn.endsWith (".ekf"))
        {
            ShowMessage ("Άνοιγμα Αρχείου Κλειδιών", "Το αρχείο κλειδιών πρέπει να έχει επέκταση \".ekf\".");
            return;
        }
        Fn = FixFilename (Fn);
        if (GL.DH.LoadData (Fn, GL.Pass1, GL.Pass2))
        {
            GL.KeyFileName = Fn;
            Adapter.notifyDataSetChanged ();
        }
        else
        {
            ShowMessage ("Άνοιγμα Αρχείου Κλειδιών", "Το άνοιγμα του αρχείου κλειδιών απέτυχε.\nΕλέγξτε τα " +
                "συνθηματικά και την δυνατότητα ανάγνωσης του αρχείου");
        }

    }

    void DoSaveAs (String Fn)
    {
        System.out.println (Fn);
        if (!Fn.endsWith (".ekf"))
        {
            ShowMessage ("Αποθήκευση Αρχείου Κλειδιών", "Το αρχείο κλειδιών πρέπει να έχει επέκταση \".ekf\".");
            return;
        }

        if (GL.DH.SaveData (Fn, GL.Pass1, GL.Pass2))
        {
            GL.KeyFileName = Fn;
        }
        else
        {
            ShowMessage ("Άποθήκευση Αρχείου Κλειδιών", "Η αποθήκευση του αρχείου κλειδιών απέτυχε.\nΕλέγξτε τα " +
                "συνθηματικά και την δυνατότητα εγγραφής του αρχείου");
        }

    }

    void DoEncrypt (final Uri Fl)
    {
        Source = Fl.getPath ();
        Dest = Source + ".ef";
        File Check = new File (Dest);
        if (Check.exists ())
        {
            ShowMessage ("Κρυπτογράφηση Αρχείου", "Το κρυπτογραφημένο αρχείο (" + Dest + ") υπάρχει ήδη.\n " +
                "Για λόγους ασφαλείας δεν θα γίνει αντικατάσταση.");
            return;
        }
        try
        {
            Crypto.EncryptFile (Source, Dest, GL.Pass1, GL.Pass2);
            Toast.makeText (MainActivity.this, "Η κρυπτογράφηση ολοκληρώθηκε.", Toast.LENGTH_LONG)
                .show ();

        }
        catch (GmException E)
        {
            ShowMessage ("Κρυπτογράφηση Αρχείου", "Η Κρυπτογράφηση του αρχείου: " + Source + " απέτυχε");
        }
    }

    void DoDecrypt (final Uri Fl)
    {
        Source = Fl.getPath ();
        if (!Source.endsWith (".ef"))
        {
            ShowMessage ("Αποκρυπτογράφηση Αρχείου", "Το κρυπτογραφημένο αρχείο πρέπει να έχει επέκταση \".ef\"");
            return;
        }
        Dest = Source.substring (0, Source.lastIndexOf ('.'));
        File Check = new File (Dest);
        if (Check.exists ())
        {
            ShowMessage ("Αποκρυπτογράφηση Αρχείου", "Το αποκρυπτογραφημένο αρχείο (" + Dest + ") υπάρχει ήδη.\n" +
                "Για λόγους ασφαλείας δεν θα γίνει αντικατάσταση");
            return;
        }
        try
        {
            Crypto.DecryptFile (Source, Dest, GL.Pass1, GL.Pass2);
            Toast.makeText (MainActivity.this, "Η αποκρυπτογράφηση ολοκληρώθηκε.", Toast.LENGTH_LONG)
                .show ();

        }
        catch (GmException E)
        {
            ShowMessage ("Αποκρυπτογράφηση Αρχείου", "Η αποκρυπτογράφηση απέτυχε. Ελέγξτε τα συνθηματικά και " +
                " τη δυνατότητα δημιουργίας του αρχείου");
            System.out.println ("^^^ " + E.getMessage ());
        }
    }

    void DoDestroy (final Uri Fl)
    {
        new AlertDialog.Builder (this)
            .setTitle ("Καταστροφή Αρχείου")
            .setMessage ("Να καταστραφεί το αρχείο: " + Fl.getPath ())
            .setIcon (android.R.drawable.ic_dialog_alert)
            .setPositiveButton ("ΝΑΙ", new DialogInterface.OnClickListener ()
            {
                public void onClick (DialogInterface dialog, int whichButton)
                {
                    try
                    {
                        Crypto.DestroyFile (Fl.getPath ());
                        Toast.makeText (MainActivity.this, "Το Αρχείο Καταστράφηκε", Toast.LENGTH_LONG).show ();
                    }
                    catch (GmException e)
                    {
                        ShowMessage ("Καταστροφή Αρχείου", "Λάθος κατά την καταστροφή αρχείου");
                    }
                }
            })
            .setNegativeButton ("ΟΧΙ", null)
            .show ();
    }

    void ShowMessage (String Title, String Message)
    {
        new AlertDialog.Builder (this)
            .setTitle (Title)
            .setMessage (Message)
            .setIcon (android.R.drawable.ic_dialog_alert)
            .setCancelable (false)
            .setNegativeButton ("ΚΛΕΙΣΙΜΟ", null)
            .show ();
        return;
    }

    void CheckSecurityDir ()
    {
        String Fn = Environment.getExternalStorageDirectory ().getAbsolutePath () + "/SAFE";
        File Fl = new File (Fn);
        if (!Fl.exists ())
        {
            if (Fl.mkdir ())
            {
                Toast toast = Toast.makeText (this, "Δημιουργήθηκε ο κατάλογος: " + Fl.getAbsolutePath (),
                    Toast.LENGTH_LONG);
                toast.setGravity (Gravity.CENTER, 0, 0);
                toast.show ();
            }
            else
            {
                Toast toast = Toast.makeText (this, "Δεν ήταν δυνατο να δημιουργηθεί ο κατάλογος των κλειδιών : (" +
                    Fl.getAbsolutePath () + "). Ελέγξτε τα δικαιώματα της εφαρμογης.",
                    Toast.LENGTH_LONG);
                toast.setGravity (Gravity.CENTER, 0, 0);
                toast.show ();
            }

        }
    }

    @Override
    public boolean onItemLongClick (AdapterView<?> parent, View view, int position, long id)
    {
        KeyRecDialog KRD;
        if (Adapter.SelRow != -1)
            new KeyRecDialog (this);
        return true;
    }

    public String FixFilename (String GFn)
    {
        //System.out.println ("***" + GFn);
        //String[] P = GFn.split (":");
        //String NFn = Environment.getExternalStorageDirectory ().toString (); // + "/" + P[1];
        String NFn = GFn.replace ("/root", "");
        //System.out.println ("****" + NFn);
        return NFn;
    }
    public void DoSearch (String Term)
    {

    }
}
class SearchDialog implements Dialog.OnShowListener, Button.OnClickListener{
    MainActivity MA;
    AlertDialog.Builder SearchDBuilder;
    AlertDialog SearchD;
    EditText SearchText;
    View DialogView;
    Button BtOK;
    Button BtnSearch;
    SearchDialog(MainActivity m)
    {
        MA = m;
        SearchDBuilder = new AlertDialog.Builder (MA);
        SearchDBuilder.setTitle ("Αναζήτηση στοιχείων");
        LayoutInflater inflater = MA.getLayoutInflater ();
        DialogView = inflater.inflate (R.layout.search_lay, null);
        SearchDBuilder.setView (DialogView);
        SearchText = (EditText) DialogView.findViewById(R.id.search_view);
        BtnSearch = (Button) DialogView.findViewById(R.id.search_button);
        SearchDBuilder.setPositiveButton ("OK", null);
        SearchD = SearchDBuilder.create ();
        SearchD.setOnShowListener (this);
        SearchD.show ();

    }
    void Show()
    {
        SearchD.show();
    }


    @Override
    public void onShow(DialogInterface dialogInterface) {
        BtOK = SearchD.getButton (AlertDialog.BUTTON_POSITIVE);
        BtOK.setOnClickListener (this);
    }

    @Override
    public void onClick(View view) {
        if(view == this.BtnSearch)
        {
            //MA.DoSearch();
        }
    }
}
class PasswordDialog implements Dialog.OnShowListener, Button.OnClickListener
{
    Globals GL;
    MainActivity MA;
    AlertDialog.Builder PassDBuilder;
    AlertDialog PassD;
    View DialogView;
    EditText Pass1a;
    EditText Pass1b;
    EditText Pass2a;
    EditText Pass2b;
    Button BtOK;
    Button BtCancel;
    Button BtCopy;
    String P1;
    String P2;
    String P3;
    String P4;

    PasswordDialog (MainActivity m)
    {
        GL = Globals.GetInstance ();
        MA = m;
        PassDBuilder = new AlertDialog.Builder (MA);
        PassDBuilder.setTitle ("Συνθηματικά");
        LayoutInflater inflater = MA.getLayoutInflater ();
        DialogView = inflater.inflate (R.layout.passwords_lay, null);
        PassDBuilder.setView (DialogView);
        Pass1a = (EditText) DialogView.findViewById (R.id.EtPass1a);
        Pass1b = (EditText) DialogView.findViewById (R.id.EtPass1b);
        Pass2a = (EditText) DialogView.findViewById (R.id.EtPass2a);
        Pass2b = (EditText) DialogView.findViewById (R.id.EtPass2b);
        PassDBuilder.setPositiveButton ("OK", null);
        PassDBuilder.setNegativeButton (("Ακύρωση"), null);
        PassDBuilder.setNeutralButton ("CC", null);
        PassD = PassDBuilder.create ();
        PassD.setOnShowListener (this);
        PassD.show ();

    }

    void Show ()
    {
        if (GL.Pass1 != null)
        {
            Pass1a.setText (GL.Pass1);
            Pass1b.setText (GL.Pass1);
        }
        if (GL.Pass2 != null)
        {
            Pass2a.setText (GL.Pass2);
            Pass2b.setText (GL.Pass2);
        }
        PassD.show ();
    }

    @Override
    public void onShow (DialogInterface dialog)
    {
        BtOK = PassD.getButton (AlertDialog.BUTTON_POSITIVE);
        BtOK.setOnClickListener (this);
        BtCancel = PassD.getButton (AlertDialog.BUTTON_NEGATIVE);
        BtCancel.setOnClickListener (this);
        BtCopy = PassD.getButton (AlertDialog.BUTTON_NEUTRAL);
        BtCopy.setOnClickListener (this);
    }

    @Override
    public void onClick (View v)
    {
        if (v == BtOK)
        {
            boolean endit = true;
            P1 = Pass1a.getText ().toString ().trim ();
            P2 = Pass1b.getText ().toString ().trim ();
            P3 = Pass2a.getText ().toString ().trim ();
            P4 = Pass2b.getText ().toString ().trim ();
            if (!P1.equals (P2) || P1.equals (""))
            {
                endit = false;
                Pass1a.setText ("");
                Pass1b.setText ("");
            }
            if (!P3.equals (P4) || P3.equals (""))
            {
                endit = false;
                Pass2a.setText ("");
                Pass2b.setText ("");
            }
            if (endit)
            {
                GL.Pass1 = P1;
                GL.Pass2 = P3;
                PassD.dismiss ();
            }
        }
        if (v == BtCancel)
        {
            PassD.dismiss ();
        }
        if (v == BtCopy);
        {
            Pass1b.setText (Pass1a.getText ().toString ());
            Pass2b.setText (Pass2a.getText ().toString ());
        }
    }
}


class KeyRecDialog implements Dialog.OnShowListener, Button.OnClickListener
{
    Globals GL;
    int SelRow;
    MainActivity MA;
    AlertDialog.Builder KeyDBuilder;
    AlertDialog KeyD;
    View DialogView;
    EditText EtLin1;
    EditText EtLin2;
    EditText EtLin3;
    EditText EtLin4;
    EditText EtLin5;
    EditText EtLin6;
    EditText EtLin7;
    EditText EtLin8;
    Button BtOK;
    Button BtCancel;


    KeyRecDialog (MainActivity m)
    {
        GL = Globals.GetInstance ();
        MA = m;
        SelRow = MA.Adapter.SelRow;
        KeyDBuilder = new AlertDialog.Builder (MA);
        KeyDBuilder.setTitle ("Συνταξη");
        LayoutInflater inflater = MA.getLayoutInflater ();
        DialogView = inflater.inflate (R.layout.keyrec_lay, null);
        KeyDBuilder.setView (DialogView);
        EtLin1 = (EditText) DialogView.findViewById (R.id.EtLin1);
        EtLin2 = (EditText) DialogView.findViewById (R.id.EtLin2);
        EtLin3 = (EditText) DialogView.findViewById (R.id.EtLin3);
        EtLin4 = (EditText) DialogView.findViewById (R.id.EtLin4);
        EtLin5 = (EditText) DialogView.findViewById (R.id.EtLin5);
        EtLin6 = (EditText) DialogView.findViewById (R.id.EtLin6);
        EtLin7 = (EditText) DialogView.findViewById (R.id.EtLin7);
        EtLin8 = (EditText) DialogView.findViewById (R.id.EtLin8);
        KeyDBuilder.setPositiveButton ("OK", null);
        KeyDBuilder.setNegativeButton (("Ακύρωση"), null);
        KeyD = KeyDBuilder.create ();
        KeyD.setOnShowListener (this);
        KeyD.show ();
    }


    @Override
    public void onShow (DialogInterface dialog)
    {
        EtLin1.setText ((String) GL.DH.getValueAt (SelRow, 0));
        EtLin2.setText ((String) GL.DH.getValueAt (SelRow, 1));
        EtLin3.setText ((String) GL.DH.getValueAt (SelRow, 2));
        EtLin4.setText ((String) GL.DH.getValueAt (SelRow, 3));
        EtLin5.setText ((String) GL.DH.getValueAt (SelRow, 4));
        EtLin6.setText ((String) GL.DH.getValueAt (SelRow, 5));
        EtLin7.setText ((String) GL.DH.getValueAt (SelRow, 6));
        EtLin8.setText ((String) GL.DH.getValueAt (SelRow, 7));
        BtOK = KeyD.getButton (AlertDialog.BUTTON_POSITIVE);
        BtOK.setOnClickListener (this);
        BtCancel = KeyD.getButton (AlertDialog.BUTTON_NEGATIVE);
        BtCancel.setOnClickListener (this);
    }

    @Override
    public void onClick (View v)
    {
        if (v == BtOK)
        {
            GL.DH.setValueAt (EtLin1.getText ().toString (), SelRow, 0);
            GL.DH.setValueAt (EtLin2.getText ().toString (), SelRow, 1);
            GL.DH.setValueAt (EtLin3.getText ().toString (), SelRow, 2);
            GL.DH.setValueAt (EtLin4.getText ().toString (), SelRow, 3);
            GL.DH.setValueAt (EtLin5.getText ().toString (), SelRow, 4);
            GL.DH.setValueAt (EtLin6.getText ().toString (), SelRow, 5);
            GL.DH.setValueAt (EtLin7.getText ().toString (), SelRow, 6);
            GL.DH.setValueAt (EtLin8.getText ().toString (), SelRow, 7);
            KeyD.dismiss ();
            MA.Adapter.notifyDataSetChanged ();
        }
        if (v == BtCancel)
        {
            KeyD.dismiss ();

        }

    }


}
