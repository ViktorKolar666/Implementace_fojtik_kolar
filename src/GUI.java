import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    private JButton predchozi;
    private JButton dalsi;
    private JLabel stavLabel;
    private DemonstraceAlgoritmu algoritmus;

    public GUI() {
        initKomponenty();

        // Inicializace algoritmu
        algoritmus = new DemonstraceAlgoritmu();
        algoritmus.setText("karkulka jela domu");
        algoritmus.setVzorek("ela");
        algoritmus.naZacatek();

        aktualizovatStav();
    }

    private void initKomponenty() {
        setTitle("Demonstrace algoritmu - GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLayout(new BorderLayout());

        // Label pro zobrazení aktuálního stavu
        stavLabel = new JLabel("Aktuální krok: ");
        stavLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(stavLabel, BorderLayout.CENTER);

        // Panel pro tlačítka
        JPanel tlacitkaPanel = new JPanel();
        tlacitkaPanel.setLayout(new FlowLayout());

        // Tlačítko Předchozí
        predchozi = new JButton("Předchozí");
        predchozi.addActionListener(e ->
                JOptionPane.showMessageDialog(null, "Nelze se vracet zpět. Algoritmus pokračuje jen vpřed."));
        tlacitkaPanel.add(predchozi);

        // Tlačítko Další
        dalsi = new JButton("Další");
        dalsi.addActionListener(e -> {
            if (!algoritmus.isHotovo()) {
                algoritmus.provedKrok();
                aktualizovatStav();
            } else {
                JOptionPane.showMessageDialog(null, "Algoritmus je hotov.");
            }
        });
        tlacitkaPanel.add(dalsi);

        add(tlacitkaPanel, BorderLayout.SOUTH);

        // Menu pro resetování
        JMenuBar menuBar = new JMenuBar();

        // Tlačítko pro resetování procesu
        JMenuItem resetItem = new JMenuItem("Reset");
        resetItem.addActionListener(e -> resetujAlgoritmus());
        menuBar.add(resetItem);
        setJMenuBar(menuBar);
    }

    private void aktualizovatStav() {
        String stav = "Krok " + algoritmus.getPocetKroku() + ": " + algoritmus.getAktualniKrok();
        stavLabel.setText(stav);
    }

    private void resetujAlgoritmus() {
        // Znovu inicializujeme algoritmus
        algoritmus.naZacatek();
        aktualizovatStav();
        JOptionPane.showMessageDialog(this, "Algoritmus byl resetován a začíná znovu.");
    }
}