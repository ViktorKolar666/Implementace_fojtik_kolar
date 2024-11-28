import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    private JTextArea textArea;
    private JTextArea vzorekArea;
    private JLabel krokyLabel;  // Label pro zobrazení kroků
    private JLabel algoritmusLabel;  // Label pro zobrazení algoritmu s barevnými písmeny
    private JButton dalsiBtn;
    private JButton predchoziBtn;
    private JButton zapnoutAlgoritmusBtn;
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem resetMenuItem;

    private DemonstraceAlgoritmu algoritmus;

    public GUI() {
        algoritmus = new DemonstraceAlgoritmu();

        setTitle("Demonstrace Algoritmu");
        setLayout(new BorderLayout());
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Textová pole pro text a vzorek
        textArea = new JTextArea(5, 20);
        vzorekArea = new JTextArea(1, 20);
        krokyLabel = new JLabel("Kroky:");
        algoritmusLabel = new JLabel("Algoritmus:");  // Label pro algoritmus

        // Tlačítka pro ovládání
        dalsiBtn = new JButton("Další Krok");
        predchoziBtn = new JButton("Předchozí Krok");
        zapnoutAlgoritmusBtn = new JButton("Spustit Algoritmus");

        // Menu pro reset
        menuBar = new JMenuBar();
        menu = new JMenu("Menu");
        resetMenuItem = new JMenuItem("Reset");

        // Přidání menu
        menu.add(resetMenuItem);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        // Nastavení UI komponent
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));
        panel.add(new JLabel("Text:"));
        panel.add(new JScrollPane(textArea));
        panel.add(new JLabel("Vzorek:"));
        panel.add(new JScrollPane(vzorekArea));
        panel.add(krokyLabel);
        panel.add(algoritmusLabel);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(zapnoutAlgoritmusBtn);
        buttonsPanel.add(dalsiBtn);
        buttonsPanel.add(predchoziBtn);
        panel.add(buttonsPanel);

        add(panel, BorderLayout.CENTER);

        // Akce pro tlačítko "Spustit Algoritmus"
        zapnoutAlgoritmusBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = textArea.getText();
                String vzorek = vzorekArea.getText();

                if (text.isEmpty() || vzorek.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Prosím, zadejte text a vzorek.");
                    return;
                }

                algoritmus.setText(text);
                algoritmus.setVzorek(vzorek);
                algoritmus.naZacatek();

                // Zakázání polí při spuštění algoritmu
                textArea.setEditable(false);
                vzorekArea.setEditable(false);

                // Vymazání Labelu pro algoritmus a nastavení kroků
                algoritmusLabel.setText("Algoritmus:");
                krokyLabel.setText("Krok: Inicializace");

                updateUI();
            }
        });

        // Akce pro tlačítko "Další Krok"
        dalsiBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!algoritmus.jeHotovo()) {
                    algoritmus.provedKrok();
                    krokyLabel.setText("Krok: " + algoritmus.getAktualniKrok());  // Změna textu pro aktuální krok
                    updateUI();
                } else {
                    JOptionPane.showMessageDialog(null, "Algoritmus je již dokončen.");
                }
            }
        });

        // Akce pro tlačítko "Předchozí Krok"
        predchoziBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                algoritmus.vratKrok();
                krokyLabel.setText("Krok: " + algoritmus.getAktualniKrok());  // Změna textu pro předchozí krok
                updateUI();
            }
        });

        // Akce pro Reset z menu
        resetMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Vyprázdní textová pole a nastaví je opět editovatelná
                textArea.setText("");
                vzorekArea.setText("");
                textArea.setEditable(true);
                vzorekArea.setEditable(true);

                // Vymaže labely
                krokyLabel.setText("Kroky:");
                algoritmusLabel.setText("Algoritmus:");
            }
        });
    }

    // Metoda pro aktualizaci UI podle aktuálního stavu
    // Metoda pro aktualizaci UI podle aktuálního stavu
    private void updateUI() {
        // Získání textu a vzorku
        String text = algoritmus.getText();
        String vzorek = algoritmus.getVzorek();

        // Získání aktuálních indexů
        int textIndex = algoritmus.getTextIndex();
        int vzorekIndex = algoritmus.getVzorekIndex();

        // Generování HTML pro zobrazení textu
        String htmlText = generateColoredText(text, vzorek, textIndex, vzorekIndex);

        // Nastavení barvy v UI
        algoritmusLabel.setText(htmlText);
    }

    // Pomocná metoda pro generování HTML textu se zvýrazněním písmen
    private String generateColoredText(String text, String vzorek, int textIndex, int vzorekIndex) {
        if (textIndex >= text.length() || vzorekIndex >= vzorek.length()) {
            return text; // Pokud jsou indexy mimo rozsah, nic nezobrazujeme
        }

        char textChar = text.charAt(textIndex);
        char vzorekChar = vzorek.charAt(vzorekIndex);

        // Pokud se porovnávají písmena, upravit barvu na základě shody
        StringBuilder htmlText = new StringBuilder();
        htmlText.append(text.substring(0, textIndex)); // Předchozí text

        if (textChar == vzorekChar) {
            htmlText.append("<font color='green'>").append(textChar).append("</font>");
        } else {
            htmlText.append("<font color='red'>").append(textChar).append("</font>");
        }

        htmlText.append(text.substring(textIndex + 1)); // Zbytek textu

        return "<html>" + htmlText.toString() + "</html>";
    }

}