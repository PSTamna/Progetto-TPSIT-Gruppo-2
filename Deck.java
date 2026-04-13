package esercizi;

public class Deck {

    // ── Costanti ────────────────────────────────────────────
    private static final int DIMENSIONE_MAZZO = 8;

    // ── Pool delle 16 carte disponibili (precaricare) ───────
    public static final Carta[] POOL = {
        new Carta("PEKKA",          7, "truppa",       true,  false),
        new Carta("Golden Knight",  4, "truppa",       false, true),
        new Carta("Battle Ram",     4, "truppa",       true,  false),
        new Carta("Furnace",        4, "edificio",     false, false),
        new Carta("Vines",          3, "incantesimo",  false, false),
        new Carta("Zap",            2, "incantesimo",  false, false),
        new Carta("Bandit",         3, "truppa",       false, false),
        new Carta("Electro Wizard", 4, "truppa",       false, false),
        new Carta("Cannon",         3, "edificio",     true,  false),
        new Carta("Musketeer",      4, "truppa",       false, true),
        new Carta("Skeletons",      1, "truppa",       true,  false),
        new Carta("Hog Rider",      4, "truppa",       false, false),
        new Carta("Fireball",       4, "incantesimo",  false, false),
        new Carta("Ice Golem",      2, "truppa",       false, false),
        new Carta("Ice Spirit",     1, "truppa",       false, false),
        new Carta("Log",            2, "incantesimo",  false, false)
    };

    // ── Sinergie note: coppie di nomi che si potenziano ─────
    private static final String[][] SINERGIE = {
        {"Hog Rider",      "Zap"},
        {"Hog Rider",      "Log"},
        {"Hog Rider",      "Ice Golem"},
        {"Hog Rider",      "Ice Spirit"},
        {"PEKKA",          "Bandit"},
        {"PEKKA",          "Battle Ram"},
        {"Furnace",        "Skeletons"},
        {"Furnace",        "Ice Spirit"},
        {"Battle Ram",     "Ice Golem"},
        {"Bandit",         "Log"},
        {"Electro Wizard", "Zap"},
        {"Electro Wizard", "PEKKA"},
        {"Musketeer",      "Ice Golem"},
        {"Golden Knight",  "Fireball"},
        {"Cannon",         "Skeletons"},
        {"Vines",          "Bandit"},
    };

    // ── Campi ───────────────────────────────────────────────
    private Carta[] carte;
    private int numeroCarte;

    // ── Costruttori ─────────────────────────────────────────
    public Deck() {
        this.carte = new Carta[DIMENSIONE_MAZZO];
        this.numeroCarte = 0;
    }

    public Deck(Carta[] carte) {
        this.carte = new Carta[DIMENSIONE_MAZZO];
        this.numeroCarte = Math.min(carte.length, DIMENSIONE_MAZZO);
        for (int i = 0; i < this.numeroCarte; i++) {
            this.carte[i] = carte[i];
        }
    }

    // ── Metodi di gestione ──────────────────────────────────
    public boolean aggiuntaCarta(Carta carta) {
        if (numeroCarte >= DIMENSIONE_MAZZO) {
            System.out.println("Mazzo pieno (max " + DIMENSIONE_MAZZO + " carte).");
            return false;
        }
        boolean trovata = false;
        for (Carta c : POOL) {
            if (c.getNome().equalsIgnoreCase(carta.getNome())) {
                trovata = true;
                break;
            }
        }
        if (!trovata) {
            System.out.println("La carta '" + carta.getNome() + "' non e' nel pool disponibile.");
            return false;
        }
        for (int i = 0; i < numeroCarte; i++) {
            if (carte[i].getNome().equalsIgnoreCase(carta.getNome())) {
                System.out.println("La carta '" + carta.getNome() + "' e' gia' nel mazzo.");
                return false;
            }
        }
        carte[numeroCarte] = carta;
        numeroCarte++;
        return true;
    }

    public Carta getCarta(int indice) {
        if (indice < 0 || indice >= numeroCarte) {
            System.out.println("Indice non valido.");
            return null;
        }
        return carte[indice];
    }

    public int getNumeroCarte()  { return numeroCarte; }
    public Carta[] getCarte()    { return carte; }

    // ── Calcoli interni ─────────────────────────────────────

    private double calcolaElisirMedio() {
        if (numeroCarte == 0) return 0;
        int tot = 0;
        for (int i = 0; i < numeroCarte; i++) tot += carte[i].getElisir();
        return (double) tot / numeroCarte;
    }

    private int contaTipo(String tipo) {
        int n = 0;
        for (int i = 0; i < numeroCarte; i++)
            if (carte[i].getTipo().equalsIgnoreCase(tipo)) n++;
        return n;
    }

    private int contaEvo() {
        int n = 0;
        for (int i = 0; i < numeroCarte; i++)
            if (carte[i].isEvo()) n++;
        return n;
    }

    private int contaHero() {
        int n = 0;
        for (int i = 0; i < numeroCarte; i++)
            if (carte[i].isHero()) n++;
        return n;
    }

    private boolean hasCarta(String nome) {
        for (int i = 0; i < numeroCarte; i++)
            if (carte[i].getNome().equalsIgnoreCase(nome)) return true;
        return false;
    }

    private int contaSinergie() {
        int n = 0;
        for (String[] coppia : SINERGIE)
            if (hasCarta(coppia[0]) && hasCarta(coppia[1])) n++;
        return n;
    }

    private double punteggioComposizione() {
        int truppe      = contaTipo("truppa");
        int edifici     = contaTipo("edificio");
        int incantesimi = contaTipo("incantesimo");

        double score = 0;
        if (truppe >= 4 && truppe <= 6) score += 2.0;
        else score += Math.max(0, 2.0 - Math.abs(truppe - 5) * 0.5);

        if (edifici >= 1 && edifici <= 2) score += 1.5;
        else if (edifici == 0)            score -= 1.0;
        else                              score += 0.5;

        if (incantesimi >= 1 && incantesimi <= 3) score += 1.5;
        else if (incantesimi == 0)                 score -= 1.0;
        else                                       score += 0.5;

        return score;
    }

    // ── confrontaDeck ────────────────────────────────────────
    /**
     * Confronta questo mazzo con il mazzo avversario.
     * Restituisce la percentuale di vittoria stimata di questo mazzo (0.0 – 100.0).
     *
     * Criteri:
     *   1. Elisir medio    — mazzo più veloce ha vantaggio offensivo
     *   2. Composizione    — equilibrio truppe / edifici / incantesimi
     *   3. Evo e hero      — bonus qualità carta
     *   4. Sinergie        — coppie di carte che si potenziano a vicenda
     */
    public double confrontaDeck(Deck avversario) {
        if (avversario == null) {
            throw new IllegalArgumentException("Il mazzo avversario non può essere null.");
        }
        if (numeroCarte == 0 || avversario.numeroCarte == 0) {
            return 50.0;
        }

        double puntoProprio    = calcolaPunteggio();
        double puntoAvversario = avversario.calcolaPunteggio();

        double totale = puntoProprio + puntoAvversario;
        double percentuale = (puntoProprio / totale) * 100.0;
        percentuale = Math.max(5.0, Math.min(95.0, percentuale));
        return Math.round(percentuale * 10.0) / 10.0;
    }

    private double calcolaPunteggio() {
        double score = 0;

        // 1. Velocità: elisir basso = più giocate per minuto
        double elisir = calcolaElisirMedio();
        score += (6.0 - elisir) * 3.0;

        // 2. Composizione bilanciata
        score += punteggioComposizione() * 2.0;

        // 3. Evo (+2.5) e hero (+3.0)
        score += contaEvo()  * 2.5;
        score += contaHero() * 3.0;

        // 4. Sinergie (+2.0 per coppia)
        score += contaSinergie() * 2.0;

        return Math.max(score, 0.1);
    }

    // ── toString ────────────────────────────────────────────
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Deck{\n");
        sb.append("  elisir medio : ").append(String.format("%.2f", calcolaElisirMedio())).append("\n");
        sb.append("  sinergie     : ").append(contaSinergie()).append("\n");
        sb.append("  evo          : ").append(contaEvo()).append("  |  hero: ").append(contaHero()).append("\n");
        sb.append("  carte        :\n");
        for (int i = 0; i < numeroCarte; i++) {
            sb.append("    ").append(i + 1).append(". ").append(carte[i]).append("\n");
        }
        sb.append("}");
        return sb.toString();
    }
}
