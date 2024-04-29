package ProjektJava;

import ProjektJava.Zwierzeta.Czlowiek;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Vector;

public class GUI implements ActionListener, KeyListener
{
    private JFrame jFrame;
    private Toolkit toolkit;
    private Dimension wymiary;
    private JMenu menu;
    private JMenuItem nowaGra, wczytaj, zapisz, wyjdz;
    private JPanel mainPanel;
    private Swiat swiat;
    private PlanszaGry planszaGry;
    private LogPanel logPanel;
    private boolean inputEnabled;

    public GUI(String naglowek)
    {
        inputEnabled = false;
        toolkit = Toolkit.getDefaultToolkit();
        wymiary = toolkit.getScreenSize();

        jFrame = new JFrame(naglowek);
        jFrame.setBounds((wymiary.width - 600) / 2, (wymiary.height - 800) / 2, 600, 800);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
        jFrame.setResizable(false);
        JMenuBar menuBar = new JMenuBar();
        menu = new JMenu("Menu");
        nowaGra = new JMenuItem("Nowa gra");
        wczytaj = new JMenuItem("Wczytaj grę");
        zapisz = new JMenuItem("Zapisz grę");
        wyjdz = new JMenuItem("Wyjdź");
        nowaGra.addActionListener(this);
        wczytaj.addActionListener(this);
        zapisz.addActionListener(this);
        wyjdz.addActionListener(this);
        menu.add(nowaGra);
        menu.add(wczytaj);
        menu.add(zapisz);
        menu.add(wyjdz);
        menuBar.add(menu);
        jFrame.setJMenuBar(menuBar);
        jFrame.setLayout(new CardLayout());
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.lightGray);
        mainPanel.setLayout(null);
        jFrame.addKeyListener(this);
        jFrame.add(mainPanel);
        jFrame.setVisible(true);
        JOptionPane.showMessageDialog(jFrame, "Strzałki - poruszanie się\n Enter - pominięcie ruchu\nSpacja - aktywowanie zdolności", "Sterowanie", JOptionPane.INFORMATION_MESSAGE);
    }

    private class LogPanel extends JPanel
    {
        private Swiat swiat;
        private JTextArea log;
        private JScrollPane jScrollPane;
        public LogPanel(Swiat swiat)
        {
            super();
            this.swiat = swiat;
            setBounds(0, 584, 584, 154);
            setBackground(Color.white);
            setLayout(new CardLayout());
            log = new JTextArea();
            log.setEditable(false);
            log.setLineWrap(true);
            log.setWrapStyleWord(true);
            jScrollPane = new JScrollPane(log);
            jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            add(jScrollPane);
        }

        public void odswiezLog()
        {
            String wydarzenia = "";
            Vector<String> tmp = swiat.getDziennik();
            if(tmp.size()!= 0)
                for (String wydarzenie : tmp)
                    wydarzenia += wydarzenie + "\n";
            log.setText(wydarzenia);
        }
    }

    private class PlanszaGry extends JPanel
    {
        private final int rozmiarX;
        private final int rozmiarY;
        private PoleGry[][] plansza;

        public PlanszaGry(Swiat swiat)
        {
            super();
            this.rozmiarX = swiat.getRozmX();
            this.rozmiarY = swiat.getRozmX();
            setBounds(0, 0, 584, 584);
            setBackground(Color.red);
            plansza = new PoleGry[rozmiarX][rozmiarY];
            for(int i = 0; i < rozmiarY; i++)
            {
                for (int j = 0; j < rozmiarX; j++)
                {
                    Color kolorPola = Color.white;
                    if (swiat.getPole(new Punkt(j, i)) != null)
                        kolorPola = swiat.getPole(new Punkt(j, i)).getKolorPola();
                    plansza[j][i] = new PoleGry(j, i, kolorPola);
                    plansza[j][i].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (e.getSource() instanceof PoleGry) {
                                PoleGry tmpPole = (PoleGry) e.getSource();
                                if (tmpPole.isZajete() == false) {
                                    OknoWyboru listaOrganizmow = new OknoWyboru(tmpPole.getX() + jFrame.getX(), tmpPole.getY() + jFrame.getY(), new Punkt(tmpPole.getPosX(), tmpPole.getPosY()));
                                }
                            }
                        }
                    });
                    this.add(plansza[j][i]);
                }
            }
            this.setLayout(new GridLayout(rozmiarY, rozmiarX));
        }

        public void odswiezPlansze()
        {
            for(int i = 0; i < rozmiarY; i++)
            {
                for (int j = 0; j < rozmiarX; j++)
                {
                    Color kolorPola = Color.white;
                    if(swiat.getPole(new Punkt(j, i)) != null)
                        kolorPola = swiat.getPole(new Punkt(j, i)).getKolorPola();
                    plansza[j][i].setKolor(kolorPola);
                }
            }
        }

        private class PoleGry extends JButton
        {
            private boolean zajete;
            private final int X,Y;

            private PoleGry(int x, int y, Color kolor)
            {
                if(kolor != Color.white)
                    zajete = true;
                else
                    zajete = false;
                this.X = x;
                this.Y = y;
                setBackground(kolor);
            }

            public void setKolor(Color kolor)
            {
                setBackground(kolor);
                zajete = kolor != Color.white;
            }

            public int getPosX()
            {
                return X;
            }

            public int getPosY()
            {
                return Y;
            }

            public boolean isZajete()
            {
                return zajete;
            }
        }
    }

    private class OknoWyboru extends JFrame
    {
        private String[] listaOrganizmow;
        private Organizm.TypOrganizmu[] typOrganizmuList;
        private JList jList;

        public OknoWyboru(int x, int y, Punkt punkt)
        {
            super("Lista organizmów");
            setBounds(x, y, 180, 225);
            listaOrganizmow = new String[]{ "Wilk", "Owca", "Lis", "Żółw", "Antylopa", "Trawa", "Mlecz", "Guarana", "Wilcze Jagody", "Barszcz Sosnowskiego" };
            typOrganizmuList = new Organizm.TypOrganizmu[] {
                    Organizm.TypOrganizmu.WILK,
                    Organizm.TypOrganizmu.OWCA,
                    Organizm.TypOrganizmu.LIS,
                    Organizm.TypOrganizmu.ZOLW,
                    Organizm.TypOrganizmu.ANTYLOPA,
                    Organizm.TypOrganizmu.TRAWA,
                    Organizm.TypOrganizmu.MLECZ,
                    Organizm.TypOrganizmu.GUARANA,
                    Organizm.TypOrganizmu.WILCZE_JAGODY,
                    Organizm.TypOrganizmu.BARSZCZ_SOSNOWSKIEGO
            };

            jList = new JList(listaOrganizmow);
            jList.setVisibleRowCount(listaOrganizmow.length);
            jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            jList.addListSelectionListener(new ListSelectionListener()
            {
                @Override
                public void valueChanged(ListSelectionEvent e)
                {
                    swiat.dodajOrganizm(typOrganizmuList[jList.getSelectedIndex()], punkt, 1);
                    swiat.wyczyscDziennik();
                    odswiez();
                    dispose();

                }
            });

            JScrollPane sp = new JScrollPane(jList);
            add(sp);
            setVisible(true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == nowaGra)
        {
            if(swiat != null)
            {
                swiat = null;
                mainPanel.remove(planszaGry);
                planszaGry = null;
                mainPanel.remove(logPanel);
            }
            int rozmX = Integer.parseInt(JOptionPane.showInputDialog(jFrame, "Podaj rozmiar (min. 10)", "20").trim());
            if(rozmX < 10)
            {
                JOptionPane.showMessageDialog(null, "rozmiar ustawiono na 10", "Zły input", JOptionPane.WARNING_MESSAGE);
                rozmX = 10;
            }
            int rozmY = rozmX;
            swiat = new Swiat(rozmX, rozmY, this);
            swiat.dodajOrganizm(Organizm.TypOrganizmu.CZLOWIEK, new Punkt(rozmX / 2, rozmY / 2), 1);
            swiat.wyczyscDziennik();
            planszaGry = new PlanszaGry(swiat);
            mainPanel.add(planszaGry);
            logPanel = new LogPanel(swiat);
            mainPanel.add(logPanel);
            odswiez();
            inputEnabled = true;
        }

        if(e.getSource() == zapisz)
        {
            Zapisz("ZAPIS");
        }

        if(e.getSource() == wczytaj)
        {
            Swiat tmpswiat = Wczytaj("ZAPIS");
            if(tmpswiat != null)
            {
                if(swiat != null)
                {
                    swiat = null;
                    mainPanel.remove(planszaGry);
                    planszaGry = null;
                    mainPanel.remove(logPanel);
                }
                swiat = tmpswiat;
                planszaGry = new PlanszaGry(swiat);
                mainPanel.add(planszaGry);
                logPanel = new LogPanel(swiat);
                mainPanel.add(logPanel);
                odswiez();
                inputEnabled = true;
            }
        }

        if (e.getSource() == wyjdz)
            jFrame.dispose();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e)
    {
        if (swiat != null && inputEnabled)
        {
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_ENTER) {}
            else if (swiat.getStanGry())
            {
                if (keyCode == KeyEvent.VK_SPACE)
                {
                    if(swiat.getCzlowiek().getZdolnosc() == 0)
                        swiat.getCzlowiek().setZdolnosc(10);
                    return;
                }
                else if (keyCode == KeyEvent.VK_UP)
                    swiat.getCzlowiek().setKierunek(1);
                else if (keyCode == KeyEvent.VK_LEFT)
                    swiat.getCzlowiek().setKierunek(2);
                else if (keyCode == KeyEvent.VK_DOWN)
                    swiat.getCzlowiek().setKierunek(3);
                else if (keyCode == KeyEvent.VK_RIGHT)
                    swiat.getCzlowiek().setKierunek(4);
            }
            inputEnabled = false;
            swiat.wykonajTure();
            odswiez();
            inputEnabled = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    public void odswiez()
    {
        planszaGry.odswiezPlansze();
        logPanel.odswiezLog();
        SwingUtilities.updateComponentTreeUI(jFrame);
        jFrame.requestFocusInWindow();
    }

    private void Zapisz(String nazwa)
    {
        try
        {
            nazwa += ".txt";
            File file = new File(nazwa);
            file.createNewFile();

            PrintWriter pw = new PrintWriter(file);
            pw.print(swiat.getRozmX() + " ");
            pw.print(swiat.getRozmY() + " \n");
            for(Organizm organizm:swiat.getKolejka())
            {
                pw.print(organizm.getTypOrganizmu() + " ");
                pw.print(organizm.getPozycja().getX() + " ");
                pw.print(organizm.getPozycja().getY() + " ");
                pw.print(organizm.getWiek() + " ");
                pw.print(organizm.getSila() + " ");
                pw.print(organizm.getStatusZycia()+ " ");
                if (organizm.getTypOrganizmu() == Organizm.TypOrganizmu.CZLOWIEK)
                {
                    pw.print(((Czlowiek)organizm).getZdolnosc() + " ");
                }
                pw.println();
            }
            pw.close();
        }
        catch (IOException e)
        {
            System.out.println("Error: " + e);
        }
    }

    private Swiat Wczytaj(String nazwa)
    {
        try
        {
            nazwa += ".txt";
            File file = new File(nazwa);

            Scanner scanner = new Scanner(file);
            String line = scanner.nextLine();
            String[] properties = line.split(" ");
            int rozmX = Integer.parseInt(properties[0]);
            int rozmY = Integer.parseInt(properties[1]);
            Swiat tmpSwiat = new Swiat(rozmX, rozmY, this);

            while (scanner.hasNextLine())
            {
                line = scanner.nextLine();
                properties = line.split(" ");
                Organizm.TypOrganizmu typOrganizmu = Organizm.TypOrganizmu.valueOf(properties[0].toUpperCase());
                int x = Integer.parseInt(properties[1]);
                int y = Integer.parseInt(properties[2]);
                int wiek = Integer.parseInt(properties[3]);
                int sila = Integer.parseInt(properties[4]);
                boolean statusZycia = Boolean.parseBoolean(properties[5]);
                tmpSwiat.dodajOrganizm(typOrganizmu, new Punkt(x, y), wiek, sila, statusZycia);
                if (typOrganizmu == Organizm.TypOrganizmu.CZLOWIEK)
                {
                    int zdolnosc = Integer.parseInt(properties[6]);
                    tmpSwiat.getCzlowiek().setZdolnosc(zdolnosc);
                }
            }
            scanner.close();
            return tmpSwiat;
        }
        catch (IOException e)
        {
            System.out.println("Error: " + e);
        }
        return null;
    }
}