package ProjektJava;

import java.util.Random;

public abstract class Roslina extends Organizm
{
    public Roslina(Swiat swiat, int sila, int wiek, Punkt pozycja, TypOrganizmu typOrganizmu)
    {
        super(swiat, sila, 0, false, false, false, wiek, pozycja, typOrganizmu);
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
                if (szansa < 30)
                    rozmnazaj();
            }
            setWiek(wiek + 1);
        }
    }
    @Override
    public void kolizja(Organizm przeciwnik) {}
    @Override
    public boolean czyOdbija(Organizm przeciwnik) {return false;}
    @Override
    public boolean czyUcieka() {return false;}
    @Override
    public boolean czyJestZwierzeciem() {return false;}
}