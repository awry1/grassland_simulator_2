package ProjektJava.Rosliny;

import ProjektJava.Punkt;
import ProjektJava.Swiat;
import ProjektJava.Roslina;

import java.awt.*;

public class Wilcze_Jagody extends Roslina
{
    public Wilcze_Jagody(Swiat swiat, int wiek, Punkt pozycja)
    {
        super(swiat, 0, wiek, pozycja, TypOrganizmu.WILCZE_JAGODY);
        this.setKolorPola(new Color(85,85,85));
    }
}