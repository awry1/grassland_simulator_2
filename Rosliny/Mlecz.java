package ProjektJava.Rosliny;

import ProjektJava.Punkt;
import ProjektJava.Swiat;
import ProjektJava.Roslina;

import java.awt.*;
import java.util.Random;

public class Mlecz extends Roslina
{
    public Mlecz(Swiat swiat, int wiek, Punkt pozycja)
    {
        super(swiat, 0, wiek, pozycja, TypOrganizmu.MLECZ);
        setKolorPola(new Color(255,204,0));
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
                for(int i = 0; i < 3; i++)
                {
                    int szansa = random.nextInt(100);
                    if (szansa < 30)
                        rozmnazaj();
                }
            }
            setWiek(wiek + 1);
        }
    }
}