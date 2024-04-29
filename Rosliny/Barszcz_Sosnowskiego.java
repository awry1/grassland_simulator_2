package ProjektJava.Rosliny;

import ProjektJava.Organizm;
import ProjektJava.Punkt;
import ProjektJava.Swiat;
import ProjektJava.Roslina;

import java.awt.*;
import java.util.Random;

public class Barszcz_Sosnowskiego extends Roslina
{
    public Barszcz_Sosnowskiego(Swiat swiat, int wiek, Punkt pozycja)
    {
        super(swiat, 0, wiek, pozycja, TypOrganizmu.BARSZCZ_SOSNOWSKIEGO);
        setKolorPola(new Color(204, 51, 0, 255));
    }

    @Override
    public void akcja()
    {
        int wiek = getWiek();
        boolean statusZycia = getStatusZycia();
        if(statusZycia != false)
        {
            //calopalenie();    funkcja w tym miejscu gwarantuje niemożliwość zjedzenia barszczu
            if(wiek != 0)
            {
                calopalenie();
                Random random = new Random();
                int szansa = random.nextInt(100);
                if (szansa < 30)
                    rozmnazaj();
            }
            setWiek(wiek + 1);
        }
    }
    public void calopalenie()
    {
        int maxX = getSwiat().getRozmX() - 1;
        int maxY = getSwiat().getRozmY() - 1;
        int x = getX();
        int y = getY();
        Organizm ofiara;
        if(y - 1 >= 0)
        {
            Punkt pozycja = new Punkt(x, y - 1);
            ofiara = getSwiat().getPole(pozycja);
            if(ofiara != null && ofiara.czyJestZwierzeciem() == true)
            {
                getSwiat().usunOrganizm(ofiara, this);
                getSwiat().setPole(pozycja, null);
            }
        }
        if(x - 1 >= 0)
        {
            Punkt pozycja = new Punkt(x - 1, y);
            ofiara = getSwiat().getPole(pozycja);
            if(ofiara != null && ofiara.czyJestZwierzeciem() == true)
            {
                getSwiat().usunOrganizm(ofiara, this);
                getSwiat().setPole(pozycja, null);
            }
        }
        if(y + 1 <= maxY)
        {
            Punkt pozycja = new Punkt(x, y + 1);
            ofiara = getSwiat().getPole(pozycja);
            if(ofiara != null && ofiara.czyJestZwierzeciem() == true)
            {
                getSwiat().usunOrganizm(ofiara, this);
                getSwiat().setPole(pozycja, null);
            }
        }
        if(x + 1 <= maxX)
        {
            Punkt pozycja = new Punkt(x + 1, y);
            ofiara = getSwiat().getPole(pozycja);
            if(ofiara != null && ofiara.czyJestZwierzeciem() == true)
            {
                getSwiat().usunOrganizm(ofiara, this);
                getSwiat().setPole(pozycja, null);
            }
        }
    }
}