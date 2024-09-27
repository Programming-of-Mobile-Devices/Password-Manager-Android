package org.gmele.android.safe;


import android.content.Context;
import android.graphics.Path;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.ArrayAdapter;

import org.gmele.general.crypto.Crypto;
import org.gmele.general.exceptions.GmException;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;


public class DataHandler
{
    final String[] ColNames = {"ΙΔΙΟΚΤΗΤΗΣ", "ΓΕΝΙΚΗ ΚΑΤΗΓΟΡΙΑ", "ΣΥΣΤΗΜΑ", "ΑΝΤΙΚΕΙΜΕΝΟ", "ΛΟΓΑΡΙΑΣΜΟΣ", "ΟΝΟΜΑ ΧΡΗΣΤΗ", "ΣΥΝΘΗΜΑΤΙΚΟ", "ΣΗΜΕΙΩΣΕΙΣ"};
    ArrayList <KeyRec> KeyList = new ArrayList ();
    boolean Changed;

    DataHandler ()
    {
        KeyList.add (new KeyRec ());
        KeyList.add (new KeyRec ());
        KeyList.add (new KeyRec ());
        Changed = false;

    }

    boolean IsChanged ()
    {
        return Changed;
    }

    boolean LoadData (String Fn, String Pass1, String Pass2)
    {
        try
        {
            String Ln;
            ArrayList <KeyRec> TmpList = new ArrayList ();
            byte[] Data = Crypto.IOUtil.readFile (new File (Fn));
            byte[] AsFile = Crypto.DecryptBuf (Data, Pass1, Pass2);
            BufferedReader Br = new BufferedReader (new InputStreamReader (new ByteArrayInputStream (AsFile)));
            while ((Ln = Br.readLine ()) != null)
            {
                Ln = Ln + " ";
                String[] Str = Ln.split ("\t");
                if (Str.length != 8)
                    return false;
                Str[7] = Str[7].trim ();
                TmpList.add (new KeyRec (Str));

            }
            Br.close ();
            KeyList.clear ();
            for (KeyRec tmp : TmpList)
                KeyList.add (tmp);
            Changed = false;
            return true;
        }
        catch (IOException | GmException | OutOfMemoryError E)
        {
            E.printStackTrace ();
            return false;
        }
    }

    boolean SaveData (String Fn, String Pass1, String Pass2)
    {

        try
        {
            ByteArrayOutputStream Bos = new ByteArrayOutputStream ();
            PrintWriter Pw = new PrintWriter (Bos);
            for (KeyRec tmp : KeyList)
            {
                tmp.Fields[7] = tmp.Fields[7].trim ();
                tmp.NL2CR ();
                String Ln = TextUtils.join ("\t", tmp.Fields);
                Pw.println (Ln);
                tmp.CR2NL ();
            }
            Pw.flush ();
            byte[] AsFile = Bos.toByteArray ();
            Pw.close ();
            byte[] Data = Crypto.EncryptBuf (AsFile, Pass1, Pass2);
            Crypto.IOUtil.writeFile (new File (Fn), Data);
            Changed = false;
            return true;
        }
        catch (IOException | GmException e)
        {
            System.out.println ("*** " + e.getMessage ());
            return false;
        }

    }


    void InsertRow (int Row)
    {
        KeyRec NR = new KeyRec ();
        KeyList.add (Row, NR);
        Changed = true;
    }

    void DeleteRow (int Row)
    {
        KeyList.remove (Row);
        Changed = true;
    }

    void MoveUp (int Row)
    {
        Collections.swap (KeyList, Row, Row - 1);
        Changed = true;
    }

    void MoveDown (int Row)
    {
        Collections.swap (KeyList, Row, Row + 1);
        Changed = true;
    }


    public int getRowCount ()
    {
        return KeyList.size ();
    }

    public int getColumnCount ()
    {
        return 8;
    }


    public String getColumnName (int columnIndex)
    {
        return ColNames[columnIndex];
    }


    public Class <?> getColumnClass (int columnIndex)
    {
        return String.class;
    }


    public boolean isCellEditable (int rowIndex, int columnIndex)
    {
        return true;
    }


    public Object getValueAt (int rowIndex, int columnIndex)
    {
        return KeyList.get (rowIndex).Fields[columnIndex];
    }


    public void setValueAt (Object aValue, int rowIndex, int columnIndex)
    {
        String NV = (String) aValue;
        if (!NV.equals (KeyList.get (rowIndex).GetField (columnIndex)))
        {
            KeyList.get (rowIndex).SetField (columnIndex, NV);
            Changed = true;
        }
    }

}

class KeyRec
{
    String[] Fields;

    KeyRec ()
    {
        Fields = new String[8];
        for (int i = 0; i < 8; i++)
            Fields[i] = "";
    }

    KeyRec (String Line)
    {
        Fields = Line.split ("\t");
        Fields[7] = Fields[7].trim ();
        CR2NL ();
    }

    KeyRec (String[] F)
    {
        Fields = F;
        CR2NL ();
    }

    void SetField (int NoF, String Val)
    {
        Fields[NoF] = Val.replace ("\\r", "\n");
    }

    String GetField (int NoF)
    {
        return Fields[NoF];
    }

    void CR2NL ()
    {
        for (int i = 0; i < 8; i++)
        {
            Fields[i] = Fields[i].replace ("\\r", "\n");
        }
    }

    void NL2CR ()
    {
        for (int i = 0; i < 8; i++)
            Fields[i] = Fields[i].replace ("\n", "\\r");
    }

    String[] getFields()
    {
        return Fields;
    }

}
