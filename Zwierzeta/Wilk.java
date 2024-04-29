package ProjektJava.Zwierzeta;

import ProjektJava.Punkt;
import ProjektJava.Swiat;
import ProjektJava.Zwierze;

import java.awt.*;

public class Wilk extends Zwierze
{
    public Wilk(Swiat swiat, int wiek, Punkt pozycja)
    {
        super(swiat, 9, 5, false, false, wiek, pozycja, TypOrganizmu.WILK);
        this.setKolorPola(new Color(51,51,51));
    }
}