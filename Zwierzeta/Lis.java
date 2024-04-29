package ProjektJava.Zwierzeta;

import ProjektJava.Organizm;
import ProjektJava.Punkt;
import ProjektJava.Swiat;
import ProjektJava.Zwierze;

import java.awt.*;

public class Lis extends Zwierze
{
    public Lis(Swiat swiat, int wiek, Punkt pozycja)
    {
        super(swiat, 3, 7, false, false, wiek, pozycja, TypOrganizmu.LIS);
        setKolorPola(new Color(255, 102, 0, 255));
    }

    @Override
    public void ruch(int odleglosc)
    {
        int maxX = getSwiat().getRozmX() - 1;
        int maxY = getSwiat().getRozmY() - 1;
        int x = getX();
        int y = getY();
        Punkt nowaPozycja = new Punkt(x, y);
        getSlabePole(nowaPozycja, getSila());
        if(nowaPozycja.getX() != -1)
        {
            Organizm poleDocelowe = getSwiat().getPole(nowaPozycja);
            if(poleDocelowe == null)
            {
                Punkt staraPozycja = new Punkt(x, y);
                getSwiat().setPole(staraPozycja, null);
                setPozycja(nowaPozycja);
                getSwiat().setPole(nowaPozycja, this);
            }
            else
                kolizja(poleDocelowe);
        }
    }
}