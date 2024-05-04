package org.gmele.android.safe;

/**
 * Created by gmele on 23/5/2017.
 */

public class Globals
{
    String Pass1;
    String Pass2;
    String KeyFileName;
    DataHandler DH;
    long LastBackTime;

    static Globals Instance = null;

    static Globals GetInstance ()
    {
        if (Instance == null)
            Instance = new Globals ();
        return Instance;
    }

    private Globals ()
    {
        Pass1 = null;
        Pass2 = null;
        KeyFileName = null;
        DH = null;
        LastBackTime = 0;
    }

}
