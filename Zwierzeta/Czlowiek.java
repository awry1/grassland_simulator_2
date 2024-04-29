package ProjektJava.Zwierzeta;

import ProjektJava.Organizm;
import ProjektJava.Punkt;
import ProjektJava.Swiat;
import ProjektJava.Zwierze;

import java.awt.*;

public class Czlowiek extends Zwierze
{
    private int kierunek;
    private int zdolnosc;

    public Czlowiek(Swiat swiat, int wiek, Punkt pozycja)
    {
        super(swiat, 5, 4, false, false, wiek, pozycja, TypOrganizmu.CZLOWIEK);
        setKolorPola(new Color(0,0,0));
        kierunek = 0;
        zdolnosc = 0;
    }

    public void setKierunek(int kierunek) {this.kierunek = kierunek;}
    public void setZdolnosc(int zdolnosc) {this.zdolnosc = zdolnosc;}

    public int getZdolnosc() {return zdolnosc;}

    @Override
    public void akcja()
    {
        int wiek = getWiek();
        boolean statusZycia = getStatusZycia();
        if(statusZycia != false)
        {
            if(wiek != 0)
            {
                calopalenie();
                ruch();
                calopalenie();
            }
            setWiek(wiek + 1);
        }
    }

    public void ruch()
    {
        Punkt nowaPozycja = null;
        int maxX = getSwiat().getRozmX() - 1;
        int maxY = getSwiat().getRozmY() - 1;
        int x = getX();
        int y = getY();
        switch(kierunek)
        {
            case 0:
                return;
            case 1:
                if(y - 1 >= 0)
                    nowaPozycja = new Punkt(x, y - 1);
                break;
            case 2:
                if(x - 1 >= 0)
                    nowaPozycja = new Punkt(x - 1, y);
                break;
            case 3:
                if(y + 1 <= maxY)
                    nowaPozycja = new Punkt(x, y + 1);
                break;
            case 4:
                if(x + 1 <= maxX)
                    nowaPozycja = new Punkt(x + 1, y);
                break;
            default:
                break;
        }
        setKierunek(0);
        if(nowaPozycja != null)
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

    public void calopalenie()
    {
        if(zdolnosc <= 5)
            return;
        int maxX = getSwiat().getRozmX() - 1;
        int maxY = getSwiat().getRozmY() - 1;
        int x = getX();
        int y = getY();
        Organizm ofiara;
        if(y - 1 >= 0)
        {
            Punkt pozycja = new Punkt(x, y - 1);
            ofiara = getSwiat().getPole(pozycja);
            if(ofiara != null)
            {
                getSwiat().usunOrganizm(ofiara, this);
                getSwiat().setPole(pozycja, null);
            }
        }
        if(x - 1 >= 0)
        {
            Punkt pozycja = new Punkt(x - 1, y);
            ofiara = getSwiat().getPole(pozycja);
            if(ofiara != null)
            {
                getSwiat().usunOrganizm(ofiara, this);
                getSwiat().setPole(pozycja, null);
            }
        }
        if(y + 1 <= maxY)
        {
            Punkt pozycja = new Punkt(x, y + 1);
            ofiara = getSwiat().getPole(pozycja);
            if(ofiara != null)
            {
                getSwiat().usunOrganizm(ofiara, this);
                getSwiat().setPole(pozycja, null);
            }
        }
        if(x + 1 <= maxX)
        {
            Punkt pozycja = new Punkt(x + 1, y);
            ofiara = getSwiat().getPole(pozycja);
            if(ofiara != null)
            {
                getSwiat().usunOrganizm(ofiara, this);
                getSwiat().setPole(pozycja, null);
            }
        }
    }
}