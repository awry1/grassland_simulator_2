package ProjektJava;

import ProjektJava.Rosliny.*;
import ProjektJava.Zwierzeta.*;
import java.util.*;

public class Swiat
{
    private int rozmX;
    private int rozmY;
    private Organizm[][] plansza;
    private ArrayList<Organizm> kolejka;
    private Vector<String> dziennik;
    private Czlowiek czlowiek;
    private GUI interfejs;

    public Swiat(int rozmX, int rozmY, GUI interfejs)
    {
        this.rozmX = rozmX;
        this.rozmY = rozmY;
        dziennik = new Vector<String>();
        czlowiek = null;
        plansza = new Organizm[rozmX][rozmY];
        for(int i = 0; i < rozmX; i++)
        {
            for (int j = 0; j < rozmX; j++)
                plansza[i][j] = null;
        }
        kolejka = new ArrayList<>();
        this.interfejs = interfejs;
    }

    public void setRozmX(int rozmX) {this.rozmX = rozmX;}
    public void setRozmY(int rozmY) {this.rozmY = rozmY;}
    public void setCzlowiek(Czlowiek czlowiek) {this.czlowiek = czlowiek;}
    public void setPole(Punkt polozenie, Organizm organizm) {plansza[polozenie.getX()][polozenie.getY()] = organizm;}

    public int getRozmX() {return rozmX;}
    public int getRozmY() {return rozmY;}
    public Czlowiek getCzlowiek() {return czlowiek;}
    public ArrayList<Organizm> getKolejka() {return kolejka;}
    public Vector<String> getDziennik() {return dziennik;}
    public Organizm getPole(Punkt polozenie) {return plansza[polozenie.getX()][polozenie.getY()];}
    public boolean getStanGry()
    {
        if (czlowiek != null)
            return czlowiek.getStatusZycia();
        else
            return false;
    }
    public void aktualizujDziennik(String message) {dziennik.add(message);}
    public void wyczyscDziennik() {dziennik.clear();}
    public void usunOrganizm(Organizm ofiara, Organizm przeciwnik)
    {
        if(przeciwnik != null)
            aktualizujDziennik(ofiara.getTypOrganizmu().zwrocNazwe() + " został zjedzony przez " + przeciwnik.getTypOrganizmu().zwrocNazwe());
        else
            aktualizujDziennik(ofiara.getTypOrganizmu().zwrocNazwe() + " został zjedzony");
        ofiara.setStatusZycia(false);
    }
    public void dodajOrganizm(Organizm.TypOrganizmu typ, Punkt pozycja, int wiek)
    {
        Organizm tmp = null;
        switch (typ)
        {
            case CZLOWIEK:
                tmp = new Czlowiek(this, wiek, pozycja);
                czlowiek = (Czlowiek)tmp;
                break;
            case WILK:
                tmp = new Wilk(this, wiek, pozycja);
                break;
            case OWCA:
                tmp = new Owca(this, wiek, pozycja);
                break;
            case LIS:
                tmp = new Lis(this, wiek, pozycja);
                break;
            case ZOLW:
                tmp = new Zolw(this, wiek, pozycja);
                break;
            case ANTYLOPA:
                tmp = new Antylopa(this, wiek, pozycja);
                break;
            case TRAWA:
                tmp = new Trawa(this, wiek, pozycja);
                break;
            case MLECZ:
                tmp = new Mlecz(this, wiek, pozycja);
                break;
            case GUARANA:
                tmp = new Guarana(this, wiek, pozycja);
                break;
            case WILCZE_JAGODY:
                tmp = new Wilcze_Jagody(this, wiek, pozycja);
                break;
            case BARSZCZ_SOSNOWSKIEGO:
                tmp = new Barszcz_Sosnowskiego(this, wiek, pozycja);
                break;
        }
        if(tmp != null)
        {
            dodajDoKolejki(tmp);
            plansza[pozycja.getX()][pozycja.getY()] = tmp;
            aktualizujDziennik(tmp.getTypOrganizmu().zwrocNazwe() + " rozmnożył się");
        }
    }
    public void dodajOrganizm(Organizm.TypOrganizmu typ, Punkt pozycja, int wiek, int sila, boolean statusZycia)
    {
        Organizm tmp = null;
        switch (typ)
        {
            case CZLOWIEK:
                tmp = new Czlowiek(this, wiek, pozycja);
                czlowiek = (Czlowiek)tmp;
                break;
            case WILK:
                tmp = new Wilk(this, wiek, pozycja);
                break;
            case OWCA:
                tmp = new Owca(this, wiek, pozycja);
                break;
            case LIS:
                tmp = new Lis(this, wiek, pozycja);
                break;
            case ZOLW:
                tmp = new Zolw(this, wiek, pozycja);
                break;
            case ANTYLOPA:
                tmp = new Antylopa(this, wiek, pozycja);
                break;
            case TRAWA:
                tmp = new Trawa(this, wiek, pozycja);
                break;
            case MLECZ:
                tmp = new Mlecz(this, wiek, pozycja);
                break;
            case GUARANA:
                tmp = new Guarana(this, wiek, pozycja);
                break;
            case WILCZE_JAGODY:
                tmp = new Wilcze_Jagody(this, wiek, pozycja);
                break;
            case BARSZCZ_SOSNOWSKIEGO:
                tmp = new Barszcz_Sosnowskiego(this, wiek, pozycja);
                break;
        }
        if(tmp != null)
        {
            tmp.setSila(sila);
            tmp.setStatusZycia(statusZycia);
            dodajDoKolejki(tmp);
            plansza[pozycja.getX()][pozycja.getY()] = tmp;
        }
    }
    public void dodajDoKolejki(Organizm organizm)
    {
        boolean done = false;
        for(int i = 0; i < kolejka.size() && done == false; i++)
        {
            if(organizm.getInicjatywa() > kolejka.get(i).getInicjatywa())
            {
                kolejka.add(i, organizm);
                done = true;
            }
        }
        if(done == false)
            kolejka.add(organizm);
    }
    public void wykonajTure()
    {
        wyczyscDziennik();
        wykonajAkcje();
        usunOrganizmy();
        if(getStanGry() == true)
        {
            int zdolnosc = getCzlowiek().getZdolnosc();
            if(zdolnosc > 0)
                getCzlowiek().setZdolnosc(zdolnosc - 1);
        }
    }
    public void wykonajAkcje()
    {
        for(int i = 0; i < kolejka.size(); i++)
            kolejka.get(i).akcja();
    }
    public void usunOrganizmy()
    {
        for (int i = 0; i < kolejka.size(); i++)
        {
            if (kolejka.get(i).getStatusZycia() == false)
            {
                kolejka.remove(i);
                i--;
            }
        }
    }
}