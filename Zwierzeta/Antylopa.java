package ProjektJava.Zwierzeta;

import ProjektJava.Punkt;
import ProjektJava.Swiat;
import ProjektJava.Zwierze;

import java.awt.*;

public class Antylopa extends Zwierze
{
    public Antylopa(Swiat swiat, int wiek, Punkt pozycja)
    {
        super(swiat, 4, 4, false, true, wiek, pozycja, TypOrganizmu.ANTYLOPA);
        setKolorPola(new Color(153, 102, 0));
    }

    @Override
    public void akcja()
    {
        int wiek = getWiek();
        boolean statusZycia = getStatusZycia();
        if(statusZycia != false)
        {
            if(wiek != 0)
                ruch(2);
            setWiek(wiek + 1);
        }
    }
    @Override
    public boolean czyUcieka() {return true;}
}