package ProjektJava.Rosliny;

import ProjektJava.Punkt;
import ProjektJava.Swiat;
import ProjektJava.Roslina;

import java.awt.*;

public class Guarana extends Roslina
{
    public Guarana(Swiat swiat, int wiek, Punkt pozycja)
    {
        super(swiat, 0, wiek, pozycja, TypOrganizmu.GUARANA);
        setKolorPola(new Color(51, 153, 153));
    }
}