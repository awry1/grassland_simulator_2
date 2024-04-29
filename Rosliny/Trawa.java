package ProjektJava.Rosliny;

import ProjektJava.Punkt;
import ProjektJava.Swiat;
import ProjektJava.Roslina;

import java.awt.*;

public class Trawa extends Roslina
{
    public Trawa(Swiat swiat, int wiek, Punkt pozycja)
    {
        super(swiat, 0, wiek, pozycja, TypOrganizmu.TRAWA);
        setKolorPola(new Color(51,204,51));
    }
}