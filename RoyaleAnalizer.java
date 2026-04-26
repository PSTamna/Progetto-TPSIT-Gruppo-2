import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import javax.imageio.ImageIO;
import java.util.Random;
import java.util.Set;

public class RoyaleAnalizer extends JFrame {

    // ── Percentuale barra (aggiornabile dal backend) ──────────────
    private volatile float progressPct = 0.47f;
    private JPanel progressBarPanel; // riferimento per repaint mirato

    /** Chiamare dal backend (thread-safe) per aggiornare la barra */
    public void setProgress(float pct) {
        progressPct = Math.max(0f, Math.min(1f, pct));
        if (progressBarPanel != null)
            SwingUtilities.invokeLater(progressBarPanel::repaint);
    }

    // ── Colori fissi ─────────────────────────────────────────────
    private static final Color DIAMOND_1 = new Color(0x5b0a6e);
    private static final Color DIAMOND_2 = new Color(0xcfb42d);
    private static final Color GREEN_BAR = new Color(0x2DB845);
    private static final Color BLUE_BTN  = new Color(0x1A5DFF);
    private static final Color RED_BTN   = new Color(0xDD1F1F);

    // ── Dimensioni scalate (istanza) ─────────────────────────────
    private int CW, CH, TH, SH, G, BH, ARCV, BW;
    private float scale;

    private int sc(int v) { return Math.round(v * scale); }
    private Font cardFont()  { return new Font("Arial Black", Font.BOLD, Math.max(7,  sc(12))); }
    private Font skillFont() { return new Font("Arial Black", Font.BOLD, Math.max(8,  sc(17))); }
    private Font barFont()   { return new Font("Arial Black", Font.BOLD, Math.max(10, sc(20))); }

    // ── Rank Skill ───────────────────────────────────────────────
    private static final String[] SKILL_RANKS = {
        "Master I", "Master II", "Master III", "Champion",
        "Grand Champion", "Royal Champion", "Ultimate Champion"
    };

    // ── Slot 1: Evos ─────────────────────────────────────────────
    private static final String[] CARD_NAMES = {
        "Barbarians","Royal Giant","Firecracker","Skeletons","Mortar",
        "Knight","Royal Recruits","Bats","Archers","Ice Spirit",
        "Valkyrie","Bomber","Wall Breakers","Tesla","Zap",
        "Battle Ram","Wizard","Goblin Barrel","Goblin Giant","Goblin Drill",
        "Goblin Cage","P.E.K.K.A","Mega Knight","Electro Dragon","Musketeer",
        "Cannon","Giant Snowball","Dart Goblin","Lumberjack","Hunter",
        "Executioner","Witch","Inferno Dragon","Skeleton Barrel","Furnace",
        "Baby Dragon","Skeleton Army","Royal Ghost","Royal Hogs","Minion Horde"
    };
    private static final Color[] CARD_COLORS = {
        new Color(0x4482FF),new Color(0x4482FF),new Color(0x4482FF),new Color(0x4482FF),new Color(0x4482FF),
        new Color(0x4482FF),new Color(0x4482FF),new Color(0x4482FF),new Color(0x4482FF),new Color(0x4482FF),
        new Color(0xFF8B43),new Color(0x4482FF),new Color(0x984EDD),new Color(0x4482FF),new Color(0x4482FF),
        new Color(0xFF8B43),new Color(0xFF8B43),new Color(0x984EDD),new Color(0x984EDD),new Color(0x984EDD),
        new Color(0xFF8B43),new Color(0x984EDD),new Color(0xFFE572),new Color(0x984EDD),new Color(0xFF8B43),
        new Color(0x4482FF),new Color(0x4482FF),new Color(0xFF8B43),new Color(0xFFE572),new Color(0x984EDD),
        new Color(0x984EDD),new Color(0x984EDD),new Color(0xFFE572),new Color(0x4482FF),new Color(0xFF8B43),
        new Color(0x984EDD),new Color(0x984EDD),new Color(0xFFE572),new Color(0xFF8B43),new Color(0x4482FF)
    };

    // ── Slot 2: Eroi ─────────────────────────────────────────────
    private static final String[] CARD_NAMES_2 = {
        "Knight","Giant","Mini P.E.K.K.A","Musketeer","Ice Golem",
        "Wizard","Goblins","Mega Minion","Barbarian Barrel","Magic Archer",
        "Balloon","Archer Queen","Golden Knight","Skeleton King","Mighty Miner",
        "Monk","Little Prince","Goblinstein"
    };
    private static final Color[] CARD_COLORS_2;
    static { CARD_COLORS_2 = new Color[CARD_NAMES_2.length]; Arrays.fill(CARD_COLORS_2, new Color(0xE3D445)); }

    // ── Slot 3: Eroi (0-17) + Evo (18-57) ────────────────────────
    private static final int HERO_END = 18;
    private static final String[] CARD_NAMES_3 = {
        // 0-17 Eroi
        "Knight","Giant","Mini P.E.K.K.A","Musketeer","Ice Golem",
        "Wizard","Goblins","Mega Minion","Barbarian Barrel","Magic Archer",
        "Balloon","Archer Queen","Golden Knight","Skeleton King","Mighty Miner",
        "Monk","Little Prince","Goblinstein",
        // 18-57 Evo  (Knight=comune #4482FF, Musketeer=rara #FF8B43)
        "Barbarians","Royal Giant","Firecracker","Skeletons","Mortar",
        "Knight","Royal Recruits","Bats","Archers","Ice Spirit",
        "Valkyrie","Bomber","Wall Breakers","Tesla","Zap",
        "Battle Ram","Wizard","Goblin Barrel","Goblin Giant","Goblin Drill",
        "Goblin Cage","P.E.K.K.A","Mega Knight","Electro Dragon","Musketeer",
        "Cannon","Giant Snowball","Dart Goblin","Lumberjack","Hunter",
        "Executioner","Witch","Inferno Dragon","Skeleton Barrel","Furnace",
        "Baby Dragon","Skeleton Army","Royal Ghost","Royal Hogs","Minion Horde"
    };
    private static final Color[] CARD_COLORS_3 = {
        // 0-17 Eroi
        new Color(0xE3D445),new Color(0xE3D445),new Color(0xE3D445),new Color(0xE3D445),new Color(0xE3D445),
        new Color(0xE3D445),new Color(0xE3D445),new Color(0xE3D445),new Color(0xE3D445),new Color(0xE3D445),
        new Color(0xE3D445),new Color(0xE3D445),new Color(0xE3D445),new Color(0xE3D445),new Color(0xE3D445),
        new Color(0xE3D445),new Color(0xE3D445),new Color(0xE3D445),
        // 18-57 Evo (Knight comune=blu, Musketeer rara=arancio)
        new Color(0x4482FF),new Color(0x4482FF),new Color(0x4482FF),new Color(0x4482FF),new Color(0x4482FF),
        new Color(0x4482FF),new Color(0x4482FF),new Color(0x4482FF),new Color(0x4482FF),new Color(0x4482FF),
        new Color(0xFF8B43),new Color(0x4482FF),new Color(0x984EDD),new Color(0x4482FF),new Color(0x4482FF),
        new Color(0xFF8B43),new Color(0xFF8B43),new Color(0x984EDD),new Color(0x984EDD),new Color(0x984EDD),
        new Color(0xFF8B43),new Color(0x984EDD),new Color(0xFFE572),new Color(0x984EDD),new Color(0xFF8B43),
        new Color(0x4482FF),new Color(0x4482FF),new Color(0xFF8B43),new Color(0xFFE572),new Color(0x984EDD),
        new Color(0x984EDD),new Color(0x984EDD),new Color(0xFFE572),new Color(0x4482FF),new Color(0xFF8B43),
        new Color(0x984EDD),new Color(0x984EDD),new Color(0xFFE572),new Color(0xFF8B43),new Color(0x4482FF)
    };

    // ── Stato carta ──────────────────────────────────────────────
    private static class CardState {
        String name = null; Color color = null; boolean selected = false; String tipo = null;
    }

    // ── Slot 4-8: tutte le carte (file cartetot) ─────────────────
    // ── Slot 4-8: 117 carte dal file Skeletons ────────────────────
    private static final String[] CARD_NAMES_TOT = {
        "Skeletons","Fire Spirit","Electro Spirit","Ice Spirit","Heal Spirit",
        "Mirror","Goblin","Spear Goblin","Bomber","Bats",
        "Zap","Snowball","Ice Golem","Berserker","Barbarian Barrel",
        "Log","Wallbreaker","Suspicious Bush","Rage","Goblin Curse",
        "Knight","Archers","Minions","Arrows","Tombstone",
        "Cannon","Mega Minion","Skeleton Army","Guards","Goblin Barrel",
        "Vines","Goblin","Skeleton Barrel","Dart Goblin","Princess",
        "Miner","Firecracker","Earthquake","Ice Wizard","Royal Ghost",
        "Bandit","Royal Delivery","Elixir Golem","Tornado","Clone",
        "Void","Fisherman","Little Prince","Musketeer","Mini Pekka",
        "Goblin Hut","Goblin Cage","Fireball","Valkyrie","Battle Ram",
        "Skeleton Dragons","Bomb Tower","Mortar","Hog Rider","Fly Machine",
        "Dark Prince","Battle Healer","Rune Giant","Poison","Tesla",
        "Zappies","Furnace","Hunter","Inferno Dragon","Electro Wizard",
        "Goblin Demolisher","Phoenix","Magic Archer","Goblin Drill","Lumberjack",
        "Night Witch","Mother Witch","Skeleton King","Golden Knight","Mighty Miner",
        "Giant","Barbarians","Wizard","Inferno Tower","witch",
        "Royal Hogs","Prince","Ram Rider","Electro Dragon","Graveyard",
        "Rascals","Bowler","Executioner","Cannon Cart","Goblin Machine",
        "Archer Queen","Monk","Goblenstein","Rocket","Royal Giant",
        "Giant Skeleton","Lightning","Barbarian Hut","Goblin Giant","Elite Barbarians",
        "X-Bow","Sparky","Elixir Collector","Spirit Empress","Boss Bandit",
        "P.E.K.K.A","Royal Recruits","Mega Knight","Electro Giant","Lavahound",
        "Golem","Three Musketeers"
    };
    private static final Color[] CARD_COLORS_TOT = {
        // 1-12
        new Color(0x4482FF),new Color(0x4482FF),new Color(0x4482FF),new Color(0x4482FF),new Color(0x4482FF),
        new Color(0x984EDD),new Color(0x4482FF),new Color(0x4482FF),new Color(0x4482FF),new Color(0x4482FF),
        new Color(0x4482FF),new Color(0x4482FF),
        // 13-24
        new Color(0xFF8B43),new Color(0x984EDD),new Color(0x984EDD),new Color(0xFFE572),new Color(0x984EDD),
        new Color(0x984EDD),new Color(0x984EDD),new Color(0x984EDD),new Color(0x4482FF),new Color(0x4482FF),
        new Color(0x4482FF),new Color(0x4482FF),
        // 25-36
        new Color(0xFF8B43),new Color(0x4482FF),new Color(0xFF8B43),new Color(0x984EDD),new Color(0xFF8B43),
        new Color(0x984EDD),new Color(0x984EDD),new Color(0x4482FF),new Color(0x984EDD),new Color(0xFF8B43),
        new Color(0xFFE572),new Color(0xFFE572),
        // 37-48
        new Color(0xFF8B43),new Color(0xFF8B43),new Color(0xFFE572),new Color(0xFFE572),new Color(0xFFE572),
        new Color(0xFF8B43),new Color(0xFF8B43),new Color(0x984EDD),new Color(0x984EDD),new Color(0x984EDD),
        new Color(0xFFE572),new Color(0xE3D445),
        // 49-60
        new Color(0x4482FF),new Color(0xFF8B43),new Color(0xFF8B43),new Color(0xFF8B43),new Color(0xFF8B43),
        new Color(0xFF8B43),new Color(0x984EDD),new Color(0xFF8B43),new Color(0xFF8B43),new Color(0xFF8B43),
        new Color(0xFF8B43),new Color(0x984EDD),
        // 61-72
        new Color(0x984EDD),new Color(0xFF8B43),new Color(0x984EDD),new Color(0x984EDD),new Color(0x4482FF),
        new Color(0xFF8B43),new Color(0xFF8B43),new Color(0xFF8B43),new Color(0xFFE572),new Color(0xFFE572),
        new Color(0x984EDD),new Color(0xFFE572),
        // 73-84
        new Color(0xFFE572),new Color(0x984EDD),new Color(0xFFE572),new Color(0xFFE572),new Color(0xFFE572),
        new Color(0xE3D445),new Color(0xE3D445),new Color(0xE3D445),new Color(0x4482FF),new Color(0x4482FF),
        new Color(0xFF8B43),new Color(0xFF8B43),
        // 85-96
        new Color(0xFF8B43),new Color(0xFF8B43),new Color(0x984EDD),new Color(0xFFE572),new Color(0x984EDD),
        new Color(0x984EDD),new Color(0xFF8B43),new Color(0x984EDD),new Color(0x984EDD),new Color(0x984EDD),
        new Color(0xE3D445),new Color(0xE3D445),
        // 97-108
        new Color(0x984EDD),new Color(0xFF8B43),new Color(0x4482FF),new Color(0x984EDD),new Color(0x984EDD),
        new Color(0xFF8B43),new Color(0x984EDD),new Color(0xFF8B43),new Color(0x984EDD),new Color(0x984EDD),
        new Color(0xFF8B43),new Color(0x984EDD),
        // 109-117
        new Color(0xE3D445),new Color(0xE3D445),new Color(0x984EDD),new Color(0x4482FF),new Color(0xFFE572),
        new Color(0x984EDD),new Color(0xFFE572),new Color(0x984EDD),new Color(0xFF8B43)
    };

    // ── Contesto mazzo (anti-duplicati) ──────────────────────────
    private static class DeckContext {
        final Set<String>    selected  = new HashSet<>();
        final List<Runnable> refreshers = new ArrayList<>();
        void selectCard(String rem, String add) {
            if (rem != null) selected.remove(rem);
            if (add != null) selected.add(add);
            for (Runnable r : refreshers) r.run();
        }
        void refresh() { for (Runnable r : refreshers) r.run(); }
    }

    // ── Sfondo animato: onde marine astratte in grigio ───────────
    private static class WaveBg extends JPanel {
        private float t = 0;
        private static final float PI2 = (float)(2 * Math.PI);
        private Timer waveTimer;

        // Parametri: [yPos, amp, bandH, freq, speed, gray, alpha, phase]
        private static final float[][] WAVES = {
            {0.08f, 0.055f, 0.060f, 1.4f,  0.55f, 90,  90,  0.0f},
            {0.20f, 0.065f, 0.075f, 2.2f, -0.70f, 40, 110,  1.1f},
            {0.33f, 0.070f, 0.080f, 1.8f,  0.80f, 98, 100,  2.3f},
            {0.46f, 0.060f, 0.090f, 2.9f, -0.60f, 28, 120,  0.7f},
            {0.58f, 0.075f, 0.085f, 1.4f,  0.72f, 80, 105,  3.5f},
            {0.70f, 0.060f, 0.075f, 2.5f, -0.85f, 88, 100,  1.8f},
            {0.82f, 0.055f, 0.065f, 2.0f,  0.50f, 35,  95,  4.2f},
            {0.92f, 0.045f, 0.060f, 2.3f, -0.45f, 75,  85,  2.9f},
            {0.14f, 0.040f, 0.050f, 3.2f,  0.90f, 60,  70,  5.1f},
            {0.63f, 0.050f, 0.060f, 1.6f, -0.55f, 50,  80,  3.8f},
        };

        WaveBg() {
            setLayout(new BorderLayout());
            waveTimer = new Timer(16, e -> { t += 0.009f; repaint(); });
            waveTimer.start();
        }
        void pauseWaves()  { waveTimer.stop(); }
        void resumeWaves() { waveTimer.start(); }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING,    RenderingHints.VALUE_RENDER_SPEED);
            int w = getWidth(), h = getHeight();

            // Sfondo: gradiente radiale scuro al centro, ancora più scuro ai bordi
            g2.setPaint(new java.awt.RadialGradientPaint(w/2f, h/2f, Math.max(w,h)*0.6f,
                new float[]{0f, 1f}, new Color[]{new Color(0x323232), new Color(0x181818)}));
            g2.fillRect(0, 0, w, h);

            int steps = Math.max(100, w / 2); // campioni più fitti per curve più morbide

            for (float[] wv : WAVES) {
                float baseY = wv[0] * h;
                float amp   = wv[1] * h;
                float band  = wv[2] * h;
                float freq  = wv[3];
                float spd   = wv[4];
                int   gray  = (int) wv[5];
                int   alpha = (int) wv[6];
                float ph    = wv[7];

                // Costruisce la banda d'onda come poligono (bordo superiore + bordo inferiore)
                java.awt.geom.Path2D path = new java.awt.geom.Path2D.Float();
                for (int si = 0; si <= steps; si++) {
                    float nx = (float) si / steps;
                    float px = nx * w;
                    float yOff = amp * (float) Math.sin(freq * nx * PI2 + t * spd + ph)
                               + amp * 0.35f * (float) Math.sin(freq * 2.3f * nx * PI2 + t * spd * 1.6f + ph + 1.0f);
                    float y = baseY - band / 2 + yOff;
                    if (si == 0) path.moveTo(px, y); else path.lineTo(px, y);
                }
                for (int si = steps; si >= 0; si--) {
                    float nx = (float) si / steps;
                    float px = nx * w;
                    float yOff = amp * (float) Math.sin(freq * nx * PI2 + t * spd + ph)
                               + amp * 0.35f * (float) Math.sin(freq * 2.3f * nx * PI2 + t * spd * 1.6f + ph + 1.0f);
                    float y = baseY + band / 2 + yOff;
                    path.lineTo(px, y);
                }
                path.closePath();

                // Riempimento onda
                g2.setColor(new Color(gray, gray, gray, alpha));
                g2.fill(path);

                // Bordo luminoso sulla cresta dell'onda
                java.awt.geom.Path2D crest = new java.awt.geom.Path2D.Float();
                for (int si = 0; si <= steps; si++) {
                    float nx = (float) si / steps;
                    float px = nx * w;
                    float yOff = amp * (float) Math.sin(freq * nx * PI2 + t * spd + ph)
                               + amp * 0.35f * (float) Math.sin(freq * 2.3f * nx * PI2 + t * spd * 1.6f + ph + 1.0f);
                    float y = baseY - band / 2 + yOff;
                    if (si == 0) crest.moveTo(px, y); else crest.lineTo(px, y);
                }
                // Bordo luminoso: due passate (alone + linea brillante)
                g2.setStroke(new BasicStroke(4.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.setColor(new Color(Math.min(gray+20,255), Math.min(gray+20,255), Math.min(gray+20,255), 40));
                g2.draw(crest);
                g2.setStroke(new BasicStroke(1.6f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.setColor(new Color(Math.min(gray+55,255), Math.min(gray+55,255), Math.min(gray+55,255), Math.min(alpha+60,230)));
                g2.draw(crest);
            }

            // Vignette bordo scuro
            for (int i = 0; i < 45; i++) {
                g2.setColor(new Color(0, 0, 0, Math.min(i * 4, 130)));
                g2.drawRect(i, i, w - 2*i - 1, h - 2*i - 1);
            }
            g2.dispose();
        }
    }

    // ── Costruttore ──────────────────────────────────────────────
    public RoyaleAnalizer() {
        setTitle("Game GUI");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Calcola scala in base allo schermo (target 80% altezza)
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        scale = (float)Math.min(
            screen.height * 0.82 / 724.0,
            screen.width  * 0.85 / 590.0);
        scale = Math.max(scale, 1.0f);

        CW = sc(110); CH = sc(155); TH = sc(207); SH = sc(103);
        G  = sc(10);  BH = sc(52);  ARCV = sc(28); BW = Math.max(2, sc(4));

        int sectionW = 5 * CW + 4 * G;
        int sectionH = 2 * CH + G;
        int barMarg  = sc(16);
        int totalH   = sectionH + barMarg + BH + barMarg + sectionH;
        int x5       = 4 * (CW + G);

        JPanel content = new JPanel(null);
        content.setOpaque(false);
        content.setPreferredSize(new Dimension(sectionW, totalH));

        DeckContext topDeck = new DeckContext();
        DeckContext botDeck = new DeckContext();

        // ════ SEZIONE SUPERIORE ════
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 4; col++) {
                int cx = col * (CW + G), cy = row * (CH + G);
                if      (row==0 && col==0) content.add(makeSpecialCard(cx,cy,CW,CH,DIAMOND_1,CARD_NAMES,  CARD_COLORS,  topDeck,"Evo"));
                else if (row==0 && col==1) content.add(makeSpecialCard(cx,cy,CW,CH,DIAMOND_2,CARD_NAMES_2,CARD_COLORS_2,topDeck,"Eroe"));
                else if (row==0 && col==2) content.add(makeSlot3Card  (cx,cy,CW,CH,topDeck));
                else                       content.add(makeSimpleCard(cx,cy,CW,CH,topDeck));
            }
        }
        content.add(makeTowerCard(x5, 0, CW, TH));
        content.add(makeSkillBtn("Skill", BLUE_BTN, x5, TH + G, CW, SH));

        int barY = sectionH + barMarg;
        content.add(makeProgressBar(0.47f, 0, barY, sectionW, BH));

        // ════ SEZIONE INFERIORE ════
        int botY = barY + BH + barMarg;
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 4; col++) {
                int cx = col * (CW + G), cy = botY + row * (CH + G);
                if      (row==0 && col==0) content.add(makeSpecialCard(cx,cy,CW,CH,DIAMOND_1,CARD_NAMES,  CARD_COLORS,  botDeck,"Evo"));
                else if (row==0 && col==1) content.add(makeSpecialCard(cx,cy,CW,CH,DIAMOND_2,CARD_NAMES_2,CARD_COLORS_2,botDeck,"Eroe"));
                else if (row==0 && col==2) content.add(makeSlot3Card  (cx,cy,CW,CH,botDeck));
                else                       content.add(makeSimpleCard(cx,cy,CW,CH,botDeck));
            }
        }
        content.add(makeSkillBtn("Skill", RED_BTN, x5, botY, CW, SH));
        content.add(makeTowerCard(x5, botY + SH + G, CW, TH));

        // Canvas (centra il contenuto)
        JPanel canvas = new JPanel(new GridBagLayout());
        canvas.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1; gbc.weighty = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        canvas.add(content, gbc);

        // Sfondo animato come content pane
        WaveBg bg = new WaveBg();
        bg.add(canvas, BorderLayout.CENTER);
        setContentPane(bg);

        setMinimumSize(new Dimension(sectionW + 80, totalH + 80));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);

        // ── Splash sul glass pane (finestra unica) ──────────────
        try {
            BufferedImage logo = ImageIO.read(new java.io.File(
                System.getProperty("user.home") +
                "\\Desktop\\Tutto\\Senza titolo 573_20260425222013.png"));
            if (logo != null) {
                SplashPane sp = new SplashPane(logo);
                setGlassPane(sp);
                sp.setVisible(true);
                sp.startAnimation(bg); // passa bg per fermare/riprendere onde
            }
        } catch (Exception ignored) {}
    }

    // ── Slot 1 e 2 (con opzione Altro) ──────────────────────────
    private JPanel makeSpecialCard(int x, int y, int w, int h,
                                   Color dColor, String[] names, Color[] colors,
                                   DeckContext deck, String tipoLabel) {
        CardState state   = new CardState();
        boolean[] hover   = {false}, pressed = {false};

        DefaultListModel<String> model = new DefaultListModel<>();
        JList<String> list = new JList<>(model);
        list.setFont(new Font("Arial", Font.PLAIN, Math.max(10, sc(13))));
        list.setFixedCellHeight(Math.max(18, sc(26)));

        // Refresher: mostra carte in base al tipo scelto
        Runnable refresher = () -> {
            boolean altro = "Altro".equals(state.tipo);
            String[] src = altro ? CARD_NAMES_TOT : names;
            model.clear();
            for (String n : src)
                if (!deck.selected.contains(n) || n.equals(state.name)) model.addElement(n);
        };
        deck.refreshers.add(refresher);
        refresher.run();

        JScrollPane scroll = new JScrollPane(list,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setPreferredSize(new Dimension(Math.max(150, sc(185)), Math.max(200, sc(280))));
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        JMenuItem tipoOrig  = new JMenuItem(tipoLabel);
        JMenuItem tipoAltro = new JMenuItem("Altro");
        JMenu tipoMenu = new JMenu("Tipo");
        tipoMenu.add(tipoOrig); tipoMenu.add(tipoAltro);

        JMenuItem rmv = new JMenuItem("Rimuovi");
        JPopupMenu popup = new JPopupMenu();
        popup.add(scroll); popup.addSeparator();
        popup.add(tipoMenu); popup.addSeparator();
        popup.add(rmv);

        JPanel p = new JPanel(null) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int pw = getWidth(), ph = getHeight(), half = BW / 2;
                Color bg   = state.selected ? state.color : Color.WHITE;
                Color fill = pressed[0] ? bg.darker().darker() : hover[0] ? bg.darker() : bg;
                g2.setColor(fill);
                g2.fillRoundRect(half, half, pw-BW, ph-BW, ARCV, ARCV);
                g2.setColor(Color.BLACK); g2.setStroke(new BasicStroke(BW));
                g2.drawRoundRect(half, half, pw-BW, ph-BW, ARCV, ARCV);
                if (state.selected) {
                    if (!"Altro".equals(state.tipo)) {
                        drawDiamond(g2, pw, dColor);
                        drawCardName(g2, state.name, pw, ph);
                    } else {
                        drawWrappedName(g2, state.name, pw, ph);
                    }
                }
                g2.dispose();
            }
        };
        p.setOpaque(false); p.setBounds(x, y, w, h);
        p.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        p.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered (MouseEvent e) { hover[0]=true;  p.repaint(); }
            @Override public void mouseExited  (MouseEvent e) { hover[0]=false; pressed[0]=false; p.repaint(); }
            @Override public void mousePressed (MouseEvent e) { pressed[0]=true; p.repaint(); }
            @Override public void mouseReleased(MouseEvent e) {
                pressed[0]=false; p.repaint();
                if (p.contains(e.getPoint())) popup.show(p, e.getX(), e.getY());
            }
        });
        list.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                int idx = list.locationToIndex(e.getPoint());
                if (idx < 0 || idx >= model.size()) return;
                String chosen = model.getElementAt(idx), prev = state.name;
                boolean altro = "Altro".equals(state.tipo);
                state.name    = chosen;
                state.color   = altro ? colorFor(CARD_NAMES_TOT, CARD_COLORS_TOT, chosen)
                                      : colorFor(names, colors, chosen);
                state.selected = true;
                deck.selectCard(prev, chosen); p.repaint(); popup.setVisible(false);
            }
        });
        tipoOrig.addActionListener(e -> {
            state.tipo = tipoLabel;
            tipoOrig.setEnabled(false); tipoAltro.setEnabled(false);
            deck.refresh(); p.repaint();
        });
        tipoAltro.addActionListener(e -> {
            if (state.selected && !isInList(CARD_NAMES_TOT, state.name)) {
                String prev = state.name; state.name=null; state.color=null; state.selected=false;
                deck.selectCard(prev, null);
            }
            state.tipo = "Altro";
            tipoOrig.setEnabled(false); tipoAltro.setEnabled(false);
            deck.refresh(); p.repaint();
        });
        rmv.addActionListener(e -> {
            String prev = state.name; state.name=null; state.color=null;
            state.selected=false; state.tipo=null;
            tipoOrig.setEnabled(true); tipoAltro.setEnabled(true);
            deck.selectCard(prev, null); p.repaint();
        });
        return p;
    }

    private static boolean isInList(String[] arr, String s) {
        if (s == null) return false;
        for (String a : arr) if (a.equals(s)) return true;
        return false;
    }

    // ── Slot 3 con Tipo ──────────────────────────────────────────
    private JPanel makeSlot3Card(int x, int y, int w, int h, DeckContext deck) {
        CardState state   = new CardState();
        boolean[] hover   = {false}, pressed = {false};

        DefaultListModel<String> model = new DefaultListModel<>();
        JList<String> list = new JList<>(model);
        list.setFont(new Font("Arial", Font.PLAIN, Math.max(10, sc(13))));
        list.setFixedCellHeight(Math.max(18, sc(26)));

        Runnable refresher = () -> {
            model.clear();
            if ("Altro".equals(state.tipo)) {
                for (String n : CARD_NAMES_TOT)
                    if (!deck.selected.contains(n) || n.equals(state.name)) model.addElement(n);
            } else {
                int start = "Evo" .equals(state.tipo) ? HERO_END : 0;
                int end   = "Hero".equals(state.tipo) ? HERO_END : CARD_NAMES_3.length;
                for (int i = start; i < end; i++) {
                    String n = CARD_NAMES_3[i];
                    if (!deck.selected.contains(n) || n.equals(state.name)) model.addElement(n);
                }
            }
        };
        deck.refreshers.add(refresher);
        refresher.run();

        JScrollPane scroll = new JScrollPane(list,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setPreferredSize(new Dimension(Math.max(150, sc(185)), Math.max(200, sc(280))));
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        JMenuItem evoItem   = new JMenuItem("Evo");
        JMenuItem heroItem  = new JMenuItem("Hero");
        JMenuItem altroItem = new JMenuItem("Altro");
        JMenu tipoMenu = new JMenu("Tipo");
        tipoMenu.add(evoItem); tipoMenu.add(heroItem); tipoMenu.add(altroItem);
        JMenuItem rmv = new JMenuItem("Rimuovi");
        JPopupMenu popup = new JPopupMenu();
        popup.add(scroll); popup.addSeparator(); popup.add(tipoMenu); popup.addSeparator(); popup.add(rmv);

        JPanel p = new JPanel(null) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int pw = getWidth(), ph = getHeight(), half = BW / 2;
                Color bg   = state.selected ? state.color : Color.WHITE;
                Color fill = pressed[0] ? bg.darker().darker() : hover[0] ? bg.darker() : bg;
                g2.setColor(fill); g2.fillRoundRect(half, half, pw-BW, ph-BW, ARCV, ARCV);
                g2.setColor(Color.BLACK); g2.setStroke(new BasicStroke(BW));
                g2.drawRoundRect(half, half, pw-BW, ph-BW, ARCV, ARCV);
                if (state.selected) {
                    if ("Altro".equals(state.tipo)) {
                        drawWrappedName(g2, state.name, pw, ph);
                    } else {
                        drawDiamond(g2, pw, "Hero".equals(state.tipo) ? DIAMOND_2 : DIAMOND_1);
                        drawCardName(g2, state.name, pw, ph);
                    }
                }
                g2.dispose();
            }
        };
        p.setOpaque(false); p.setBounds(x, y, w, h);
        p.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        p.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered (MouseEvent e) { hover[0]=true;  p.repaint(); }
            @Override public void mouseExited  (MouseEvent e) { hover[0]=false; pressed[0]=false; p.repaint(); }
            @Override public void mousePressed (MouseEvent e) { pressed[0]=true; p.repaint(); }
            @Override public void mouseReleased(MouseEvent e) {
                pressed[0]=false; p.repaint();
                if (p.contains(e.getPoint())) popup.show(p, e.getX(), e.getY());
            }
        });
        list.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                int idx = list.locationToIndex(e.getPoint());
                if (idx < 0 || idx >= model.size()) return;
                String chosen = model.getElementAt(idx), prev = state.name;
                Color col;
                if ("Altro".equals(state.tipo)) {
                    col = colorFor(CARD_NAMES_TOT, CARD_COLORS_TOT, chosen);
                } else {
                    int start = "Evo".equals(state.tipo) ? HERO_END : 0;
                    int end   = "Hero".equals(state.tipo) ? HERO_END : CARD_NAMES_3.length;
                    col = Color.WHITE;
                    for (int i = start; i < end; i++)
                        if (CARD_NAMES_3[i].equals(chosen)) { col = CARD_COLORS_3[i]; break; }
                }
                state.name = chosen; state.color = col; state.selected = true;
                deck.selectCard(prev, chosen); p.repaint(); popup.setVisible(false);
            }
        });
        evoItem.addActionListener(e -> {
            state.tipo = "Evo"; evoItem.setEnabled(false); heroItem.setEnabled(false); altroItem.setEnabled(false);
            if (state.selected && !isInRange(HERO_END, CARD_NAMES_3.length, state.name)) {
                String pr=state.name; state.name=null; state.color=null; state.selected=false; deck.selectCard(pr,null);
            }
            deck.refresh(); p.repaint();
        });
        heroItem.addActionListener(e -> {
            state.tipo = "Hero"; evoItem.setEnabled(false); heroItem.setEnabled(false); altroItem.setEnabled(false);
            if (state.selected && !isInRange(0, HERO_END, state.name)) {
                String pr=state.name; state.name=null; state.color=null; state.selected=false; deck.selectCard(pr,null);
            }
            deck.refresh(); p.repaint();
        });
        altroItem.addActionListener(e -> {
            state.tipo = "Altro"; evoItem.setEnabled(false); heroItem.setEnabled(false); altroItem.setEnabled(false);
            if (state.selected && !isInList(CARD_NAMES_TOT, state.name)) {
                String pr=state.name; state.name=null; state.color=null; state.selected=false; deck.selectCard(pr,null);
            }
            deck.refresh(); p.repaint();
        });
        rmv.addActionListener(e -> {
            String pr=state.name; state.name=null; state.color=null; state.selected=false; state.tipo=null;
            evoItem.setEnabled(true); heroItem.setEnabled(true); altroItem.setEnabled(true);
            deck.selectCard(pr, null); p.repaint();
        });
        return p;
    }

    private boolean isInRange(int start, int end, String name) {
        if (name == null) return false;
        for (int i = start; i < end; i++) if (CARD_NAMES_3[i].equals(name)) return true;
        return false;
    }

    // ── Slot 4-8: carta semplice (nessun rombo) ──────────────────
    private JPanel makeSimpleCard(int x, int y, int w, int h, DeckContext deck) {
        CardState state   = new CardState();
        boolean[] hover   = {false}, pressed = {false};

        DefaultListModel<String> model = new DefaultListModel<>();
        JList<String> list = new JList<>(model);
        list.setFont(new Font("Arial", Font.PLAIN, Math.max(10, sc(13))));
        list.setFixedCellHeight(Math.max(18, sc(26)));

        Runnable refresher = () -> {
            model.clear();
            for (String n : CARD_NAMES_TOT)
                if (!deck.selected.contains(n) || n.equals(state.name)) model.addElement(n);
        };
        deck.refreshers.add(refresher);
        refresher.run();

        JScrollPane scroll = new JScrollPane(list,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setPreferredSize(new Dimension(Math.max(160, sc(200)), Math.max(220, sc(300))));
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        JMenuItem rmv = new JMenuItem("Rimuovi");
        JPopupMenu popup = new JPopupMenu();
        popup.add(scroll); popup.addSeparator(); popup.add(rmv);

        JPanel p = new JPanel(null) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int pw = getWidth(), ph = getHeight(), half = BW / 2;
                Color bg   = state.selected ? state.color : Color.WHITE;
                Color fill = pressed[0] ? bg.darker().darker() : hover[0] ? bg.darker() : bg;
                g2.setColor(fill); g2.fillRoundRect(half, half, pw-BW, ph-BW, ARCV, ARCV);
                g2.setColor(Color.BLACK); g2.setStroke(new BasicStroke(BW));
                g2.drawRoundRect(half, half, pw-BW, ph-BW, ARCV, ARCV);
                if (state.selected) drawWrappedName(g2, state.name, pw, ph);
                g2.dispose();
            }
        };
        p.setOpaque(false); p.setBounds(x, y, w, h);
        p.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        p.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered (MouseEvent e) { hover[0]=true;  p.repaint(); }
            @Override public void mouseExited  (MouseEvent e) { hover[0]=false; pressed[0]=false; p.repaint(); }
            @Override public void mousePressed (MouseEvent e) { pressed[0]=true; p.repaint(); }
            @Override public void mouseReleased(MouseEvent e) {
                pressed[0]=false; p.repaint();
                if (p.contains(e.getPoint())) popup.show(p, e.getX(), e.getY());
            }
        });
        list.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                int idx = list.locationToIndex(e.getPoint());
                if (idx < 0 || idx >= model.size()) return;
                String chosen = model.getElementAt(idx), prev = state.name;
                state.name = chosen; state.color = colorFor(CARD_NAMES_TOT, CARD_COLORS_TOT, chosen);
                state.selected = true;
                deck.selectCard(prev, chosen); p.repaint(); popup.setVisible(false);
            }
        });
        rmv.addActionListener(e -> {
            String prev = state.name; state.name=null; state.color=null; state.selected=false;
            deck.selectCard(prev, null); p.repaint();
        });
        return p;
    }

    // Nome centrato verticalmente (no rombo) con word-wrap automatico
    private void drawWrappedName(Graphics2D g2, String name, int pw, int ph) {
        Font font = cardFont(); g2.setFont(font); FontMetrics fm = g2.getFontMetrics();
        String[] words = name.split(" ");
        List<String> lines = new ArrayList<>();
        StringBuilder cur = new StringBuilder();
        for (String wd : words) {
            String test = cur.length() == 0 ? wd : cur + " " + wd;
            if (fm.stringWidth(test) <= pw - sc(12)) { cur = new StringBuilder(test); }
            else { if (cur.length() > 0) lines.add(cur.toString()); cur = new StringBuilder(wd); }
        }
        if (cur.length() > 0) lines.add(cur.toString());
        int lh = fm.getHeight(), totH = lines.size() * lh;
        int startY = (ph - totH) / 2 + fm.getAscent();
        g2.setColor(Color.BLACK);
        for (int i = 0; i < lines.size(); i++)
            g2.drawString(lines.get(i), (pw - fm.stringWidth(lines.get(i))) / 2, startY + i * lh);
    }

    // ── Rombo in alto al centro ───────────────────────────────────
    private void drawDiamond(Graphics2D g2, int pw, Color color) {
        int cx = pw/2, cy = sc(26), ds = sc(16);
        int[] xs = {cx, cx+ds, cx, cx-ds}, ys = {cy-ds, cy, cy+ds, cy};
        g2.setColor(color); g2.fillPolygon(xs, ys, 4);
        g2.setColor(new Color(0,0,0,160)); g2.setStroke(new BasicStroke(sc(2)));
        g2.drawPolygon(xs, ys, 4);
    }

    // ── Nome carta centrato ───────────────────────────────────────
    private void drawCardName(Graphics2D g2, String name, int pw, int ph) {
        Font font = cardFont(); g2.setFont(font); FontMetrics fm = g2.getFontMetrics();
        String[] parts = name.contains(" ") ? name.split(" ", 2) : new String[]{name};
        int lh = fm.getHeight(), totH = parts.length * lh;
        int topOff = sc(42), startY = (topOff + ph - sc(10) + totH) / 2 - totH / 2 + fm.getAscent();
        for (int i = 0; i < parts.length; i++) {
            String line = parts[i]; float sz = scale * 12;
            while (fm.stringWidth(line) > pw - sc(12) && sz > sc(7)) {
                sz -= 0.5f; g2.setFont(font.deriveFont(sz)); fm = g2.getFontMetrics();
            }
            g2.setColor(Color.BLACK);
            g2.drawString(line, (pw - fm.stringWidth(line)) / 2, startY + i * lh);
            g2.setFont(font); fm = g2.getFontMetrics();
        }
    }

    // ── Rettangolone col 5: Tower Troop / selezione torre ────────
    private JPanel makeTowerCard(int x, int y, int w, int h) {
        String[] selected = {null}; // null = mostra "Tower Troop"
        boolean[] hover = {false}, pressed = {false};

        String[] options = {"Princess Tower", "Cannoneer", "Dagger Duchess", "Royal Chef"};

        JPanel p = new JPanel(null) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int pw = getWidth(), ph = getHeight(), half = BW / 2;
                Color bg   = Color.WHITE;
                Color fill = pressed[0] ? bg.darker().darker() : hover[0] ? bg.darker() : bg;
                g2.setColor(fill);
                g2.fillRoundRect(half, half, pw-BW, ph-BW, ARCV, ARCV);
                g2.setColor(Color.BLACK); g2.setStroke(new BasicStroke(BW));
                g2.drawRoundRect(half, half, pw-BW, ph-BW, ARCV, ARCV);
                String label = selected[0] != null ? selected[0] : "Tower Troop";
                drawWrappedName(g2, label, pw, ph);
                g2.dispose();
            }
        };
        p.setOpaque(false); p.setBounds(x, y, w, h);
        p.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JPopupMenu popup = new JPopupMenu();
        for (String opt : options) {
            JMenuItem item = new JMenuItem(opt);
            item.addActionListener(e -> { selected[0] = opt; p.repaint(); });
            popup.add(item);
        }
        popup.addSeparator();
        JMenuItem rmv = new JMenuItem("Rimuovi");
        rmv.addActionListener(e -> { selected[0] = null; p.repaint(); });
        popup.add(rmv);

        p.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered (MouseEvent e) { hover[0]=true;  p.repaint(); }
            @Override public void mouseExited  (MouseEvent e) { hover[0]=false; pressed[0]=false; p.repaint(); }
            @Override public void mousePressed (MouseEvent e) { pressed[0]=true; p.repaint(); }
            @Override public void mouseReleased(MouseEvent e) {
                pressed[0]=false; p.repaint();
                if (p.contains(e.getPoint())) popup.show(p, e.getX(), e.getY());
            }
        });
        return p;
    }

    // ── Carta normale ─────────────────────────────────────────────
    private JPanel makeCard(Color bg, int x, int y, int w, int h) {
        boolean[] st = {false, false};
        JPanel p = new JPanel(null) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int pw = getWidth(), ph = getHeight(), half = BW/2;
                Color fill = st[1] ? bg.darker().darker() : st[0] ? bg.darker() : bg;
                g2.setColor(fill); g2.fillRoundRect(half, half, pw-BW, ph-BW, ARCV, ARCV);
                g2.setColor(Color.BLACK); g2.setStroke(new BasicStroke(BW));
                g2.drawRoundRect(half, half, pw-BW, ph-BW, ARCV, ARCV);
                g2.dispose();
            }
        };
        p.setOpaque(false); p.setBounds(x, y, w, h);
        addClick(p, st, buildCardMenu());
        return p;
    }

    // ── Pulsante Skill ────────────────────────────────────────────
    private JPanel makeSkillBtn(String text, Color bg, int x, int y, int w, int h) {
        boolean[] st = {false, false}; String[] label = {text};
        JPanel p = new JPanel(null) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int pw = getWidth(), ph = getHeight(), half = BW/2;
                Color fill = st[1] ? bg.darker().darker() : st[0] ? bg.darker() : bg;
                g2.setColor(fill); g2.fillRoundRect(half, half, pw-BW, ph-BW, ARCV, ARCV);
                g2.setColor(Color.BLACK); g2.setStroke(new BasicStroke(BW));
                g2.drawRoundRect(half, half, pw-BW, ph-BW, ARCV, ARCV);
                String cur = label[0];
                String[] lines = cur.contains(" ") ? cur.split(" ", 2) : new String[]{cur};
                Font font = skillFont(); g2.setFont(font); FontMetrics fm = g2.getFontMetrics();
                float sz = scale * 17;
                for (String ln : lines)
                    while (fm.stringWidth(ln) > pw - sc(10) && sz > sc(8)) {
                        sz -= 1f; g2.setFont(font.deriveFont(sz)); fm = g2.getFontMetrics();
                    }
                int lh = fm.getHeight(), sy = (ph - lines.length * lh) / 2 + fm.getAscent();
                g2.setColor(Color.BLACK);
                for (int i = 0; i < lines.length; i++)
                    g2.drawString(lines[i], (pw - fm.stringWidth(lines[i])) / 2, sy + i * lh);
                g2.dispose();
            }
        };
        p.setOpaque(false); p.setBounds(x, y, w, h);
        p.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        JPopupMenu menu = new JPopupMenu();
        for (String rank : SKILL_RANKS) {
            JMenuItem item = new JMenuItem(rank);
            item.addActionListener(e -> { label[0]=rank; p.repaint(); });
            menu.add(item);
        }
        menu.addSeparator();
        JMenuItem rmvSkill = new JMenuItem("Rimuovi");
        rmvSkill.addActionListener(e -> { label[0]=text; p.repaint(); }); // torna a "Skill"
        menu.add(rmvSkill);
        addClick(p, st, menu);
        return p;
    }

    // ── Barra progresso (percentuale aggiornabile via setProgress()) ─
    private JPanel makeProgressBar(float ignored, int x, int y, int w, int h) {
        JPanel p = new JPanel(null) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int pw = getWidth(), ph = getHeight(), half = BW/2, iw=pw-BW, ih=ph-BW, arc=ph;
                g2.setColor(Color.WHITE); g2.fillRoundRect(half, half, iw, ih, arc, arc);
                g2.setClip(new RoundRectangle2D.Float(half, half, iw, ih, arc, arc));
                g2.setColor(GREEN_BAR); g2.fillRect(half, half, (int)(iw * progressPct), ih);
                g2.setClip(null);
                g2.setColor(Color.BLACK); g2.setStroke(new BasicStroke(BW));
                g2.drawRoundRect(half, half, iw, ih, arc, arc);
                String lbl = Math.round(progressPct * 100) + "%";
                Font font = barFont(); g2.setFont(font); FontMetrics fm = g2.getFontMetrics();
                g2.setColor(Color.BLACK);
                g2.drawString(lbl, pw - fm.stringWidth(lbl) - sc(18), (ph + fm.getAscent() - fm.getDescent()) / 2);
                g2.dispose();
            }
        };
        p.setOpaque(false); p.setBounds(x, y, w, h);
        progressBarPanel = p; // riferimento per aggiornamenti dal backend
        return p;
    }

    // ── Utilities ────────────────────────────────────────────────
    private void addClick(JPanel p, boolean[] st, JPopupMenu menu) {
        p.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        p.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered (MouseEvent e) { st[0]=true;  p.repaint(); }
            @Override public void mouseExited  (MouseEvent e) { st[0]=false; st[1]=false; p.repaint(); }
            @Override public void mousePressed (MouseEvent e) { st[1]=true;  p.repaint(); }
            @Override public void mouseReleased(MouseEvent e) {
                st[1]=false; p.repaint();
                if (p.contains(e.getPoint())) menu.show(p, e.getX(), e.getY());
            }
        });
    }

    private static Color colorFor(String[] names, Color[] colors, String name) {
        for (int i = 0; i < names.length; i++) if (names[i].equals(name)) return colors[i];
        return Color.WHITE;
    }

    private JPopupMenu buildCardMenu() {
        JPopupMenu m = new JPopupMenu();
        m.add(new JMenuItem("Visualizza dettagli"));
        m.add(new JMenuItem("Modifica"));
        m.add(new JMenuItem("Rimuovi"));
        m.addSeparator();
        m.add(new JMenuItem("Annulla"));
        return m;
    }

    // ── Splash glass pane: overlay + logo + animazione ───────────
    private static class SplashPane extends JPanel {
        private final BufferedImage logo;
        private float logoAlpha = 0f;
        private float bgAlpha   = 1f;
        private float logoScale = 0.70f;

        // Pre-renderizzati una volta sola → ogni frame è solo un blit
        private BufferedImage preGlow  = null;
        private BufferedImage preLogo  = null;

        SplashPane(BufferedImage logo) {
            this.logo = logo;
            setOpaque(false);
            addMouseListener(new java.awt.event.MouseAdapter() {});
        }

        /** Costruisce glow e logo scalati ad alta qualità (chiamato una volta) */
        private void prerender(int w, int h) {
            int min = Math.min(w, h);

            // Glow
            int gr = (int)(min * 0.38f);
            int gd = gr * 2;
            preGlow = new BufferedImage(gd, gd, BufferedImage.TYPE_INT_ARGB);
            Graphics2D gg = preGlow.createGraphics();
            gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            gg.setPaint(new java.awt.RadialGradientPaint(gr, gr, gr,
                new float[]{0f, 0.4f, 0.6f, 1f},
                new Color[]{new Color(68,130,255,110), new Color(68,130,255,35),
                            new Color(221,31,31,35),   new Color(0,0,0,0)}));
            gg.fillOval(0, 0, gd, gd);
            // alone interno caldo
            gg.setPaint(new java.awt.RadialGradientPaint(gr, gr, (int)(gr * 0.55f),
                new float[]{0f, 1f},
                new Color[]{new Color(230,210,150,130), new Color(0,0,0,0)}));
            gg.fillOval((int)(gr*0.45f), (int)(gr*0.45f), (int)(gr*1.1f), (int)(gr*1.1f));
            gg.dispose();

            // Logo pre-scalato con qualità massima
            int ls = (int)(min * 0.27f);
            preLogo = new BufferedImage(ls, ls, BufferedImage.TYPE_INT_ARGB);
            Graphics2D lg = preLogo.createGraphics();
            lg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            lg.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            lg.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            lg.drawImage(logo, 0, 0, ls, ls, null);
            lg.dispose();
        }

        @Override
        public boolean contains(int x, int y) { return isVisible(); }

        @Override
        protected void paintComponent(Graphics g) {
            if (bgAlpha <= 0f) return;
            int w = getWidth(), h = getHeight();
            if (preLogo == null) prerender(w, h); // init lazy

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

            // Sfondo scuro
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, bgAlpha));
            g2.setColor(new Color(0x1a1a1a));
            g2.fillRect(0, 0, w, h);

            if (logoAlpha > 0f) {
                int cx = w / 2, cy = h / 2;
                float eff = Math.min(1f, logoAlpha * bgAlpha);

                // Blit glow pre-renderizzato (nessun calcolo)
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, eff * 0.55f));
                g2.drawImage(preGlow, cx - preGlow.getWidth()/2, cy - preGlow.getHeight()/2, null);

                // Blit logo pre-scalato con zoom animation
                int ls = (int)(preLogo.getWidth() * logoScale);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, eff));
                g2.drawImage(preLogo, cx - ls/2, cy - ls/2, ls, ls, null);
            }
            g2.dispose();
        }

        void startAnimation(WaveBg waveBg) {
            waveBg.pauseWaves();

            // 8ms = 120fps → dissolvenza fluidissima
            Timer fadeIn = new Timer(8, null);
            fadeIn.addActionListener(e -> {
                logoAlpha = Math.min(1f, logoAlpha + 0.045f);
                logoScale = Math.min(1.0f, logoScale + 0.016f);
                repaint();
                Toolkit.getDefaultToolkit().sync();
                if (logoAlpha >= 1f) {
                    logoScale = 1.0f;
                    fadeIn.stop();
                    Timer hold = new Timer(900, null);
                    hold.setRepeats(false);
                    hold.addActionListener(hi -> {
                        Timer fadeOut = new Timer(8, null);
                        fadeOut.addActionListener(fo -> {
                            bgAlpha = Math.max(0f, bgAlpha - 0.035f);
                            repaint();
                            Toolkit.getDefaultToolkit().sync();
                            if (bgAlpha <= 0f) {
                                ((Timer) fo.getSource()).stop();
                                setVisible(false);
                                waveBg.resumeWaves();
                            }
                        });
                        fadeOut.start();
                    });
                    hold.start();
                }
            });
            fadeIn.start();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RoyaleAnalizer::new);
    }
}
