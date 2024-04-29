package ProjektJava;

import java.awt.*;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;

public abstract class Organizm
{
    private int sila;
    private int inicjatywa;
    private int wiek;
    private Punkt pozycja;
    private boolean statusZycia;
    private boolean odbijanie;
    private boolean uciekanie;
    private boolean jestZwierzeciem;
    private Swiat swiat;
    public enum TypOrganizmu
    {
        CZLOWIEK,WILK,OWCA,LIS,ZOLW,ANTYLOPA,
        TRAWA,MLECZ,GUARANA,WILCZE_JAGODY,BARSZCZ_SOSNOWSKIEGO;

        @Override
        public String toString()
        {
            String poprzedniaNazwa = super.toString();
            String nowaNazwa = poprzedniaNazwa.toLowerCase();
            return nowaNazwa;
        }
        public String zwrocNazwe()
        {
            String nazwa = toString().replace("_", " ");
            return nazwa;
        }
    }
    private TypOrganizmu typOrganizmu;
    private Color kolorPola;

    public Organizm(Swiat swiat, int sila, int inicjatywa, boolean odbijanie, boolean uciekanie, boolean jestZwierzeciem, int wiek, Punkt pozycja, TypOrganizmu typOrganizmu)
    {
        this.swiat = swiat;
        this.sila = sila;
        this.inicjatywa = inicjatywa;
        this.odbijanie = odbijanie;
        this.uciekanie = uciekanie;
        this.jestZwierzeciem = jestZwierzeciem;
        this.wiek = wiek;
        this.pozycja = pozycja;
        this.typOrganizmu = typOrganizmu;
        this.statusZycia = true;
        swiat.setPole(pozycja,this);
    }

    public void setSila(int sila) {this.sila = sila;}
    public void setInicjatywa(int inicjatywa) {this.inicjatywa = inicjatywa;}
    public void setWiek(int wiek) {this.wiek = wiek;}
    public void setPozycja(Punkt pozycja) {this.pozycja = pozycja;}
    public void setStatusZycia(boolean statusZycia) {this.statusZycia = statusZycia;}
    public void setSwiat(Swiat swiat) {this.swiat = swiat;}
    public void setTypOrganizmu(TypOrganizmu typOrganizmu) {this.typOrganizmu = typOrganizmu;}
    public void setKolorPola(Color kolorPola) {this.kolorPola = kolorPola;}

    public int getSila() {return  this.sila;}
    public int getInicjatywa() {return this.inicjatywa;}
    public int getWiek() {return wiek;}
    public Punkt getPozycja() {return pozycja;}
    public int getX() {return pozycja.getX();}
    public int getY() {return pozycja.getY();}
    public boolean getStatusZycia() {return statusZycia;}
    public boolean getOdbijanie() {return this.odbijanie;}
    public Swiat getSwiat() {return swiat;}
    public TypOrganizmu getTypOrganizmu() {return typOrganizmu;}
    public Color getKolorPola() {return kolorPola;}
    public void getSasiedniePole(Punkt pole)
    {
        int maxX = swiat.getRozmX() - 1;
        int maxY = swiat.getRozmY() - 1;
        int x = pole.getX();
        int y = pole.getY();
        Integer[] kierunek = {0, 1, 2, 3};
        List<Integer> list = Arrays.asList(kierunek);
        Collections.shuffle(list);
        list.toArray(kierunek);
        for(int i = 0; i < 4; i++)
        {
            switch(kierunek[i])
            {
                case 0:
                    if(y - 1 >= 0)
                    {
                        pole.setY(y - 1);
                        pole.setX(x);
                    }
                    break;
                case 1:
                    if(x - 1 >= 0)
                    {
                        pole.setX(x - 1);
                        pole.setY(y);
                    }
                    break;
                case 2:
                    if(y + 1 <= maxY)
                    {
                        pole.setY(y + 1);
                        pole.setX(x);
                    }
                    break;
                case 3:
                    if(x + 1 <= maxX)
                    {
                        pole.setX(x + 1);
                        pole.setY(y);
                    }
                    break;
            }
            if(swiat.getPole(pole) == null)
                break;
        }
        if(swiat.getPole(pole) != null)
        {
            pole.setX(-1);
            pole.setY(-1);
        }
    }
    public void getSlabePole(Punkt pole, int sila)
    {
        int maxX = swiat.getRozmX() - 1;
        int maxY = swiat.getRozmY() - 1;
        int x = pole.getX();
        int y = pole.getY();
        Punkt starePole = new Punkt(x, y);
        Integer[] kierunek = {0, 1, 2, 3};
        List<Integer> list = Arrays.asList(kierunek);
        Collections.shuffle(list);
        list.toArray(kierunek);
        for(int i = 0; i < 4; i++)
        {
            switch(kierunek[i])
            {
                case 0:
                    if(y - 1 >= 0)
                    {
                        pole.setY(y - 1);
                        pole.setX(x);
                    }
                    break;
                case 1:
                    if(x - 1 >= 0)
                    {
                        pole.setX(x - 1);
                        pole.setY(y);
                    }
                    break;
                case 2:
                    if(y + 1 <= maxY)
                    {
                        pole.setY(y + 1);
                        pole.setX(x);
                    }
                    break;
                case 3:
                    if(x + 1 <= maxX)
                    {
                        pole.setX(x + 1);
                        pole.setY(y);
                    }
                    break;
            }
            if((!starePole.equals(pole)) && (swiat.getPole(pole) == null || swiat.getPole(pole).getSila() <= sila))
                break;
        }
        if((starePole.equals(pole)) || (swiat.getPole(pole) != null && swiat.getPole(pole).getSila() > sila))
        {
            pole.setX(-1);
            pole.setY(-1);
        }
    }

    public abstract void akcja();
    public abstract void kolizja(Organizm przeciwnik);
    public void rozmnazaj()
    {
        Punkt nowaPozycja = new Punkt(getX(), getY());
        getSasiedniePole(nowaPozycja);
        if(nowaPozycja.getX() != -1)
            getSwiat().dodajOrganizm(getTypOrganizmu(), nowaPozycja, 0);
    }
    public abstract boolean czyOdbija(Organizm przeciwnik);
    public abstract boolean czyUcieka();
    public abstract boolean czyJestZwierzeciem();
}