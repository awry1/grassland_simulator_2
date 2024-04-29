package ProjektJava.Zwierzeta;

import ProjektJava.Punkt;
import ProjektJava.Swiat;
import ProjektJava.Zwierze;

import java.awt.*;
import java.util.Random;

public class Zolw extends Zwierze
{
    public Zolw(Swiat swiat, int wiek, Punkt pozycja)
    {
        super(swiat, 2, 1, true, false, wiek, pozycja, TypOrganizmu.ZOLW);
        setKolorPola(new Color(0,102,0));
    }

    @Override
    public void akcja()
    {
        int wiek = getWiek();
        boolean statusZycia = getStatusZycia();
        if(statusZycia != false)
        {
            if(wiek != 0)
            {
                Random random = new Random();
                int szansa = random.nextInt(100);
                if (szansa < 25)
                    ruch(1);
            }
            setWiek(wiek + 1);
        }
    }
}