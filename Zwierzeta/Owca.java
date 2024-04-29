package ProjektJava.Zwierzeta;

import ProjektJava.Punkt;
import ProjektJava.Swiat;
import ProjektJava.Zwierze;

import java.awt.*;

public class Owca extends Zwierze
{
    public Owca(Swiat swiat, int wiek, Punkt pozycja)
    {
        super(swiat, 4, 4, false, false, wiek, pozycja, TypOrganizmu.OWCA);
        this.setKolorPola(new Color(204, 204, 204));
    }
}