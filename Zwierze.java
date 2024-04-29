package ProjektJava;

import java.util.Random;

public abstract class Zwierze extends Organizm
{
    public Zwierze(Swiat swiat, int sila, int inicjatywa, boolean odbijanie, boolean uciekanie, int wiek, Punkt pozycja, TypOrganizmu typOrganizmu)
    {
        super(swiat, sila, inicjatywa, odbijanie, uciekanie, true, wiek, pozycja, typOrganizmu);
    }

    @Override
    public void akcja()
    {
        int wiek = getWiek();
        boolean statusZycia = getStatusZycia();
        if(statusZycia != false)
        {
            if(wiek != 0)
                ruch(1);
            setWiek(wiek + 1);
        }
    }
    public void ruch(int odleglosc)
    {
        Punkt nowaPozycja = null;
        int maxX = getSwiat().getRozmX() - 1;
        int maxY = getSwiat().getRozmY() - 1;
        int x = getX();
        int y = getY();
        boolean done = false;
        while(done != true)
        {
            Random random = new Random();
            int kierunek = random.nextInt(100) * odleglosc;
            if(kierunek < 25)
            {
                if(y - odleglosc >= 0)
                {
                    nowaPozycja = new Punkt(x, y - odleglosc);
                    done = true;
                }
            }
            else if(kierunek < 50)
            {
                if(x - odleglosc >= 0)
                {
                    nowaPozycja = new Punkt(x - odleglosc, y);
                    done = true;
                }
            }
            else if(kierunek < 75)
            {
                if(y + odleglosc <= maxY)
                {
                    nowaPozycja = new Punkt(x, y + odleglosc);
                    done = true;
                }
            }
            else if(kierunek < 100)
            {
                if(x + odleglosc <= maxX)
                {
                    nowaPozycja = new Punkt(x + odleglosc, y);
                    done = true;
                }
            }
            else if(kierunek < 125)
            {
                if(y - 1 >= 0 && x - 1 >= 0)
                {
                    nowaPozycja = new Punkt(x - 1, y - 1);
                    done = true;
                }
            }
            else if(kierunek < 150)
            {
                if(y - 1 >= 0 && x + 1 <= maxX)
                {
                    nowaPozycja = new Punkt(x + 1, y - 1);
                    done = true;
                }
            }
            else if(kierunek < 175)
            {
                if(y + 1 <= maxY && x - 1 >= 0)
                {
                    nowaPozycja = new Punkt(x - 1, y + 1);
                    done = true;
                }
            }
            else if(kierunek < 200)
            {
                if(y + 1 <= maxY && x + 1 <= maxX)
                {
                    nowaPozycja = new Punkt(x + 1, y + 1);
                    done = true;
                }
            }
        }
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
    @Override
    public void kolizja(Organizm przeciwnik)
    {
        if(getTypOrganizmu() == przeciwnik.getTypOrganizmu())
        {
            if(getWiek() > 0 && przeciwnik.getWiek() > 0)
                rozmnazaj();
        }
        else if (czyOdbija(przeciwnik) == false)
        {
            boolean statusUniku = false;
            Punkt staraPozycja = new Punkt(getX(), getY());
            getSwiat().setPole(staraPozycja, null);
            Punkt pozycjaPrzeciwnik = new Punkt(przeciwnik.getX(), przeciwnik.getY());
            setPozycja(pozycjaPrzeciwnik);
            if(przeciwnik.czyUcieka() == true)
            {
                Random random = new Random();
                int szansaNaUcieczke = random.nextInt(100);
                if(szansaNaUcieczke < 50)
                {
                    Punkt poleUcieczki = new Punkt(przeciwnik.getX(), przeciwnik.getY());
                    getSasiedniePole(poleUcieczki);
                    if(poleUcieczki.getX() != -1)
                    {
                        przeciwnik.setPozycja(poleUcieczki);
                        getSwiat().setPole(pozycjaPrzeciwnik, this);
                        getSwiat().setPole(poleUcieczki, przeciwnik);
                        getSwiat().aktualizujDziennik(przeciwnik.getTypOrganizmu().toString() + " uciekł przed " + getTypOrganizmu().toString());
                        statusUniku = true;
                    }
                }
            }
            if(statusUniku == false)
            {
                if(getSila() >= przeciwnik.getSila())
                {
                    if(przeciwnik.getTypOrganizmu() == TypOrganizmu.GUARANA)
                    {
                        int sila = getSila();
                        setSila(sila + 3);
                    }
                    getSwiat().setPole(pozycjaPrzeciwnik, this);
                    getSwiat().usunOrganizm(przeciwnik, this);
                }
                else if(getSila() < przeciwnik.getSila())
                    getSwiat().usunOrganizm(this, przeciwnik);
            }
        }
    }
    @Override
    public boolean czyOdbija(Organizm przeciwnik)
    {
        if (przeciwnik.getOdbijanie() == true && getSila() < 5)
        {
            getSwiat().aktualizujDziennik(przeciwnik.getTypOrganizmu().toString() + " odbił atak " + this.getTypOrganizmu().toString());
            return true;
        }
        else
            return false;
    }
    @Override
    public boolean czyUcieka() {return false;}
    @Override
    public boolean czyJestZwierzeciem() {return true;}
}