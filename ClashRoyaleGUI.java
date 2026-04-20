import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ClashRoyaleGUI extends JFrame {

    // Colori sfondo
    private static final Color BG        = new Color(75, 75, 75);
    private static final Color PANEL_BG  = new Color(105, 105, 105);

    // Colori carte
    private static final Color PINK      = new Color(255, 90, 195);
    private static final Color ORANGE    = new Color(255, 160, 50);
    private static final Color GREEN_C   = new Color(70, 200, 90);
    private static final Color BLUE_C    = new Color(100, 165, 255);
    private static final Color BLUE_SK   = new Color(50, 115, 220);
    private static final Color RED_SK    = new Color(210, 45, 45);
    private static final Color GREEN_BTN = new Color(45, 175, 75);

    public ClashRoyaleGUI() {
        setTitle("Clash Royale - Deck Analyzer");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(980, 530);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout(12, 0));
        root.setBackground(BG);
        root.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        root.add(buildLeft(), BorderLayout.CENTER);
        root.add(buildRight(), BorderLayout.EAST);

        setContentPane(root);
    }

    // ==================== PANNELLO SINISTRO ====================

    private JPanel buildLeft() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(BG);

        p.add(buildDeck(true));
        p.add(Box.createVerticalStrut(8));
        p.add(buildBarPanel());
        p.add(Box.createVerticalStrut(8));
        p.add(buildDeck(false));

        return p;
    }

    private JPanel buildDeck(boolean isTop) {
        JPanel grid = new JPanel(new GridLayout(2, 5, 6, 6));
        grid.setBackground(BG);

        if (isTop) {
            grid.add(card("P.E.K.K.A",        PINK,    "purple"));
            grid.add(card("Battle Ram",        ORANGE,  "orange"));
            grid.add(card("Royal Ghost",       GREEN_C, "purple"));
            grid.add(card("Bandit",            BLUE_C,  null));
            grid.add(card("Princess Tower",    BLUE_C,  null));
            grid.add(card("Magic Archer",      GREEN_C, null));
            grid.add(card("Electro Wizard",    GREEN_C, null));
            grid.add(card("Zap",               BLUE_C,  null));
            grid.add(card("Vines",             GREEN_C, null));
            grid.add(skillBtn(BLUE_SK));
        } else {
            grid.add(card("Skeletons",         BLUE_C,  "purple"));
            grid.add(card("Musketeer",         ORANGE,  "orange"));
            grid.add(card("Ice Golem",         ORANGE,  "orange"));
            grid.add(card("Hog Rider",         BLUE_C,  null));
            grid.add(skillBtn(RED_SK));
            grid.add(card("Cannon",            BLUE_C,  null));
            grid.add(card("Fireball",          ORANGE,  null));
            grid.add(card("Log",               GREEN_C, null));
            grid.add(card("Ice Spirit",        BLUE_C,  null));
            grid.add(card("Princess Tower",    BLUE_C,  null));
        }

        return grid;
    }

    private JButton card(String name, Color bg, String diamondColor) {
        String diamond = "";
        if ("purple".equals(diamondColor)) diamond = "<font color='#DD44FF'>&#9670;</font><br>";
        else if ("orange".equals(diamondColor)) diamond = "<font color='#FFAA00'>&#9670;</font><br>";

        String label = "<html><center>" + diamond + name.replace(" ", "<br>") + "</center></html>";

        RoundBtn btn = new RoundBtn(label, bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 11));

        // Menu a tendina al click
        String[] opzioni = {"Opzione 1", "Opzione 2", "Opzione 3", "Opzione 4", "Opzione 5"};
        btn.addActionListener(e -> {
            JPopupMenu menu = new JPopupMenu();
            for (String op : opzioni) {
                JMenuItem item = new JMenuItem(op);
                menu.add(item);
            }
            menu.show(btn, 0, btn.getHeight());
        });

        return btn;
    }

    private JButton skillBtn(Color bg) {
        RoundBtn btn = new RoundBtn("Skill", bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        return btn;
    }

    private JPanel buildBarPanel() {
        JPanel p = new JPanel(new BorderLayout(8, 0));
        p.setBackground(BG);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));

        JProgressBar bar = new JProgressBar(0, 100);
        bar.setValue(47);
        bar.setStringPainted(true);
        bar.setString("47%");
        bar.setForeground(GREEN_BTN);
        bar.setBackground(Color.WHITE);
        bar.setFont(new Font("Arial", Font.BOLD, 15));
        bar.setBorderPainted(false);
        bar.setPreferredSize(new Dimension(0, 42));

        RoundBtn calcola = new RoundBtn("Calcola", GREEN_BTN);
        calcola.setForeground(Color.WHITE);
        calcola.setFont(new Font("Arial", Font.BOLD, 14));
        calcola.setPreferredSize(new Dimension(110, 42));

        p.add(bar, BorderLayout.CENTER);
        p.add(calcola, BorderLayout.EAST);

        return p;
    }

    // ==================== PANNELLO DESTRO ====================

    private JPanel buildRight() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(PANEL_BG);
        p.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));
        p.setPreferredSize(new Dimension(290, 0));

        JTextArea ta = new JTextArea(
            "-Cambia \"carta1\" con \"carta2\"\n\n" +
            "-Cambia \"carta3\" con \"carta4\"\n\n" +
            "//ALTRI CONSIGLI SE NECESSARI//"
        );
        ta.setBackground(PANEL_BG);
        ta.setForeground(Color.WHITE);
        ta.setFont(new Font("Arial", Font.PLAIN, 13));
        ta.setEditable(false);
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        ta.setBorder(null);
        ta.setOpaque(false);

        p.add(ta, BorderLayout.CENTER);
        return p;
    }

    // ==================== BOTTONE ARROTONDATO ====================

    static class RoundBtn extends JButton {
        private final Color bg;

        RoundBtn(String text, Color bg) {
            super(text);
            this.bg = bg;
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Color c = getModel().isPressed() ? bg.darker()
                    : getModel().isRollover() ? bg.brighter()
                    : bg;
            g2.setColor(c);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    // ==================== MAIN ====================

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ClashRoyaleGUI().setVisible(true));
    }
}
