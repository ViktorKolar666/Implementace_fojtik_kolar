import java.util.Stack;

public class DemonstraceAlgoritmu {

    // Definice kroků algoritmu
    public enum Kroky {
        INICIALIZACE, KONTROLA_DELKY_VZORKU, ZACATEK_POROVNANI, POROVNANI_ZNAKU,
        POKRACOVANI_V_POROVNANI, VZOREK_NALEZEN, POSUN_INDEXU, VZOREK_NENALEZEN, KONEC
    }

    // Atributy třídy
    private String text;
    private String vzorek;
    private int textLength;
    private int vzorekLength;
    private int textIndex;
    private int vzorekIndex;
    private boolean hotovo;
    private Kroky aktualniKrok;

    // Zásobník pro historii stavů
    private Stack<State> historie;

    // Pomocná třída pro uchovávání stavu
    private class State {
        int textIndex;
        int vzorekIndex;
        Kroky krok;

        State(int textIndex, int vzorekIndex, Kroky krok) {
            this.textIndex = textIndex;
            this.vzorekIndex = vzorekIndex;
            this.krok = krok;
        }
    }

    // Konstruktor
    public DemonstraceAlgoritmu() {
        historie = new Stack<>();
    }

    // Settery
    public void setText(String text) {
        this.text = text;
    }

    public void setVzorek(String vzorek) {
        this.vzorek = vzorek;
    }

    // Gettery
    public String getText() {
        return text;
    }

    public String getVzorek() {
        return vzorek;
    }

    public int getTextIndex() {
        return textIndex;
    }

    public int getVzorekIndex() {
        return vzorekIndex;
    }

    public String getAktualniKrok() {
        return aktualniKrok.toString();
    }

    public boolean jeHotovo() {
        return hotovo;
    }

    // Inicializace algoritmu
    public void naZacatek() {
        textLength = text.length();
        vzorekLength = vzorek.length();
        textIndex = 0;
        vzorekIndex = 0;
        hotovo = false;
        aktualniKrok = Kroky.INICIALIZACE;
        historie.clear();
    }

    // Provede jeden krok algoritmu
    public void provedKrok() {
        if (hotovo) {
            return;
        }

        // Uložení aktuálního stavu do historie
        historie.push(new State(textIndex, vzorekIndex, aktualniKrok));

        // Přechod mezi kroky
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

    // Vrací se o jeden krok zpět
    public void vratKrok() {
        if (historie.isEmpty()) {
            return;
        }

        // Obnovení předchozího stavu
        State posledniStav = historie.pop();
        textIndex = posledniStav.textIndex;
        vzorekIndex = posledniStav.vzorekIndex;
        aktualniKrok = posledniStav.krok;

        hotovo = false; // Pokud vracíme krok, algoritmus není dokončen
    }

    // Metody pro jednotlivé kroky
    private void inicializace() {
        aktualniKrok = Kroky.KONTROLA_DELKY_VZORKU;
    }

    private void kontrolaDelkyVzorku() {
        if (vzorekLength > textLength) {
            aktualniKrok = Kroky.VZOREK_NENALEZEN;
        } else {
            aktualniKrok = Kroky.ZACATEK_POROVNANI;
        }
    }

    private void zacatekPorovnani() {
        if (textIndex > textLength - vzorekLength) {
            aktualniKrok = Kroky.VZOREK_NENALEZEN;
        } else {
            aktualniKrok = Kroky.POROVNANI_ZNAKU;
        }
    }

    private void porovnaniZnaku() {
        if (text.charAt(textIndex) == vzorek.charAt(vzorekIndex)) {
            aktualniKrok = Kroky.POKRACOVANI_V_POROVNANI;
        } else {
            aktualniKrok = Kroky.POSUN_INDEXU;
        }
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
    }

    private void vzorekNalezen() {
        hotovo = true;
        aktualniKrok = Kroky.KONEC;
    }

    private void posunIndexu() {
        textIndex = textIndex - vzorekIndex + 1;
        vzorekIndex = 0;
        aktualniKrok = Kroky.ZACATEK_POROVNANI;
    }

    private void vzorekNenalezen() {
        hotovo = true;
        aktualniKrok = Kroky.KONEC;
    }

    private void konec() {
        hotovo = true;
    }

    // Metoda main pro spuštění GUI
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> new GUI().setVisible(true));
    }
}