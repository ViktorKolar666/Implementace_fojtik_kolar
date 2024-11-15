import javax.swing.*;

public class DemonstraceAlgoritmu {

    // Enum pro kroky
    private enum Kroky {
        INICIALIZACE, KONTROLA_DELKY_VZORKU, ZACATEK_POROVNANI, POROVNANI_ZNAKU,
        POKRACOVANI_V_POROVNANI, VZOREK_NALEZEN, POSUN_INDEXU, VZOREK_NENALEZEN, KONEC
    }

    // Atributy
    private String text;
    private String vzorek;
    private int textLength;
    private int vzorekLength;
    private int textIndex;
    private int vzorekIndex;
    private int pocetKroku;
    private boolean hotovo;

    private Kroky aktualniKrok;

    // Gettery a settery
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getVzorek() {
        return vzorek;
    }

    public void setVzorek(String vzorek) {
        this.vzorek = vzorek;
    }

    public int getPocetKroku() {
        return pocetKroku;
    }

    public Kroky getAktualniKrok() {
        return aktualniKrok;
    }

    public boolean isHotovo() {
        return hotovo;
    }

    public void naZacatek() {
        textLength = text.length();
        vzorekLength = vzorek.length();
        textIndex = 0;
        vzorekIndex = 0;
        pocetKroku = 0;
        hotovo = false;
        aktualniKrok = Kroky.INICIALIZACE;
    }

    public void provedKrok() {
        if (hotovo) {
            return;
        }

        switch (aktualniKrok) {
            case INICIALIZACE:
                inicializace();
                break;
            case KONTROLA_DELKY_VZORKU:
                kontrolaDelkyVzorku();
                break;
            case ZACATEK_POROVNANI:
                zacatekPorovnani();
                break;
            case POROVNANI_ZNAKU:
                porovnaniZnaku();
                break;
            case POKRACOVANI_V_POROVNANI:
                pokracovaniVPorovnani();
                break;
            case VZOREK_NALEZEN:
                vzorekNalezen();
                break;
            case POSUN_INDEXU:
                posunIndexu();
                break;
            case VZOREK_NENALEZEN:
                vzorekNenalezen();
                break;
            case KONEC:
                konec();
                break;
        }
    }

    private void inicializace() {
        naZacatek();
        aktualniKrok = Kroky.KONTROLA_DELKY_VZORKU;
        pocetKroku++;
    }

    private void kontrolaDelkyVzorku() {
        if (vzorekLength > textLength) {
            aktualniKrok = Kroky.VZOREK_NENALEZEN;
        } else {
            aktualniKrok = Kroky.ZACATEK_POROVNANI;
        }
        pocetKroku++;
    }

    private void zacatekPorovnani() {
        if (textIndex > textLength - vzorekLength) {
            aktualniKrok = Kroky.VZOREK_NENALEZEN;
        } else {
            aktualniKrok = Kroky.POROVNANI_ZNAKU;
        }
        pocetKroku++;
    }

    private void porovnaniZnaku() {
        if (text.charAt(textIndex) == vzorek.charAt(vzorekIndex)) {
            aktualniKrok = Kroky.POKRACOVANI_V_POROVNANI;
        } else {
            aktualniKrok = Kroky.POSUN_INDEXU;
        }
        pocetKroku++;
    }

    private void pokracovaniVPorovnani() {
        textIndex++;
        vzorekIndex++;
        if (vzorekIndex == vzorekLength) {
            aktualniKrok = Kroky.VZOREK_NALEZEN;
        } else if (textIndex == textLength) {
            aktualniKrok = Kroky.VZOREK_NENALEZEN;
        } else {
            aktualniKrok = Kroky.POROVNANI_ZNAKU;
        }
        pocetKroku++;
    }

    private void vzorekNalezen() {
        hotovo = true;
        pocetKroku++;
        aktualniKrok = Kroky.KONEC;
    }

    private void posunIndexu() {
        textIndex = textIndex - vzorekIndex + 1;
        vzorekIndex = 0;
        aktualniKrok = Kroky.ZACATEK_POROVNANI;
        pocetKroku++;
    }

    private void vzorekNenalezen() {
        hotovo = true;
        pocetKroku++;
        aktualniKrok = Kroky.KONEC;
    }

    private void konec() {
        hotovo = true;
        pocetKroku++;
    }

    // Metoda main spouštějící GUI
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GUI gui = new GUI();
            gui.setVisible(true);
        });
    }
}
