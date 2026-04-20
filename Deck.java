package esercizi;

/**
 * Rappresenta un mazzo di Clash Royale (massimo 8 carte).
 *
 * Contiene:
 *   - POOL   : le 16 carte disponibili tra cui scegliere (array statico)
 *   - SINERGIE: coppie di carte che si potenziano a vicenda
 *   - confrontaDeck() : calcola la percentuale di vittoria stimata
 *
 * ──────────────────────────────────────────────────────────────
 * COSA SIGNIFICA final?
 *   La parola chiave "final" applicata a una variabile indica che il suo
 *   valore non può essere riassegnato dopo l'inizializzazione.
 *
 *   - Su variabile primitiva: il valore numerico non cambia mai.
 *       final int MAX = 8;  →  MAX = 10 darebbe errore di compilazione.
 *
 *   - Su variabile di riferimento (array/oggetto): non si può puntare a un
 *     altro array, ma il contenuto dell'array può ancora essere modificato.
 *       final Carta[] POOL = {...};  →  POOL = new Carta[]{...} è vietato,
 *       ma POOL[0] = nuovaCarta; è ancora permesso.
 *
 *   Combinata con "static", final crea una costante di classe:
 *       private static final int DIMENSIONE_MAZZO = 8;
 * ──────────────────────────────────────────────────────────────
 */
public class Deck {

    // ── Costanti ─────────────────────────────────────────────────
    // 'static final' = costante di classe: un'unica copia condivisa,
    // immutabile per tutta la vita del programma.
    private static final int DIMENSIONE_MAZZO = 8;

    // ── Pool delle 16 carte disponibili ──────────────────────────
    // 'public' → accessibile da Main per mostrare il menù di scelta.
    // 'static' → unica copia per tutta la classe (non dipende dall'istanza).
    // 'final'  → il riferimento all'array non può essere sostituito.
    public static final Carta[] POOL = {
        new Carta("PEKKA",          7, "truppa",       true,  false),  //  1
        new Carta("Golden Knight",  4, "truppa",       false, true),   //  2
        new Carta("Battle Ram",     4, "truppa",       true,  false),  //  3
        new Carta("Furnace",        4, "edificio",     false, false),  //  4
        new Carta("Vines",          3, "incantesimo",  false, false),  //  5
        new Carta("Zap",            2, "incantesimo",  false, false),  //  6
        new Carta("Bandit",         3, "truppa",       false, false),  //  7
        new Carta("Electro Wizard", 4, "truppa",       false, false),  //  8
        new Carta("Cannon",         3, "edificio",     true,  false),  //  9
        new Carta("Musketeer",      4, "truppa",       false, true),   // 10
        new Carta("Skeletons",      1, "truppa",       true,  false),  // 11
        new Carta("Hog Rider",      4, "truppa",       false, false),  // 12
        new Carta("Fireball",       4, "incantesimo",  false, false),  // 13
        new Carta("Ice Golem",      2, "truppa",       false, false),  // 14
        new Carta("Ice Spirit",     1, "truppa",       false, false),  // 15
        new Carta("Log",            2, "incantesimo",  false, false)   // 16
    };

    // ── Sinergie: coppie di carte che si potenziano a vicenda ────
    // Array bidimensionale: ogni riga è una coppia {nomeCarta1, nomeCarta2}.
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

    // ── Campi d'istanza ──────────────────────────────────────────
    private Carta[] carte;      // array delle carte nel mazzo
    private int     numeroCarte; // quante carte sono state aggiunte finora

    // ── Costruttori ──────────────────────────────────────────────

    /** Crea un mazzo vuoto. */
    public Deck() {
        this.carte       = new Carta[DIMENSIONE_MAZZO];
        this.numeroCarte = 0;
    }

    /** Crea un mazzo copiando un array di carte (al massimo DIMENSIONE_MAZZO). */
    public Deck(Carta[] carte) {
        this.carte       = new Carta[DIMENSIONE_MAZZO];
        this.numeroCarte = Math.min(carte.length, DIMENSIONE_MAZZO);
        // Copia gli elementi uno a uno: O(n) dove n = numeroCarte
        for (int i = 0; i < this.numeroCarte; i++) {
            this.carte[i] = carte[i];
        }
    }

    // ── Metodi di gestione ───────────────────────────────────────

    /**
     * Aggiunge una carta al mazzo se:
     *   a) il mazzo non è pieno
     *   b) la carta appartiene al POOL
     *   c) la carta non è già presente nel mazzo
     *
     * Complessità: O(n) — scorre il POOL e il mazzo una volta ciascuno.
     *
     * @return true se la carta è stata aggiunta, false altrimenti
     */
    public boolean aggiuntaCarta(Carta carta) {
        if (numeroCarte >= DIMENSIONE_MAZZO) {
            System.out.println("Mazzo pieno (max " + DIMENSIONE_MAZZO + " carte).");
            return false;
        }

        // Verifica che la carta esista nel pool — O(n) sul pool (16 elementi)
        boolean trovata = false;
        for (Carta c : POOL) {   // for-each: vedi spiegazione sotto
            if (c.getNome().equalsIgnoreCase(carta.getNome())) {
                trovata = true;
                break;
            }
        }
        if (!trovata) {
            System.out.println("La carta '" + carta.getNome() + "' non e' nel pool disponibile.");
            return false;
        }

        // Verifica che non sia già nel mazzo (no duplicati) — O(n) sul mazzo
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

    /** Restituisce la carta all'indice specificato, o null se l'indice non è valido. */
    public Carta getCarta(int indice) {
        if (indice < 0 || indice >= numeroCarte) {
            System.out.println("Indice non valido.");
            return null;
        }
        return carte[indice];
    }

    public int     getNumeroCarte() { return numeroCarte; }
    public Carta[] getCarte()       { return carte; }

    // ── Metodi di calcolo (privati) ──────────────────────────────

    /**
     * Calcola l'elisir medio del mazzo.
     * Complessità: O(n) — un solo passaggio sulle n carte del mazzo.
     */
    private double calcolaElisirMedio() {
        if (numeroCarte == 0) return 0;
        int tot = 0;
        for (int i = 0; i < numeroCarte; i++) tot += carte[i].getElisir();
        return (double) tot / numeroCarte;
    }

    /** Conta quante carte del mazzo hanno il tipo indicato. O(n). */
    private int contaTipo(String tipo) {
        int n = 0;
        for (int i = 0; i < numeroCarte; i++)
            if (carte[i].getTipo().equalsIgnoreCase(tipo)) n++;
        return n;
    }

    /** Conta le carte con Evoluzione. O(n). */
    private int contaEvo() {
        int n = 0;
        for (int i = 0; i < numeroCarte; i++)
            if (carte[i].isEvo()) n++;
        return n;
    }

    /** Conta le carte Hero. O(n). */
    private int contaHero() {
        int n = 0;
        for (int i = 0; i < numeroCarte; i++)
            if (carte[i].isHero()) n++;
        return n;
    }

    /** Verifica se il mazzo contiene la carta con il nome indicato. O(n). */
    private boolean hasCarta(String nome) {
        for (int i = 0; i < numeroCarte; i++)
            if (carte[i].getNome().equalsIgnoreCase(nome)) return true;
        return false;
    }

    /**
     * Conta le sinergie attive nel mazzo.
     *
     * ──────────────────────────────────────────────────────────
     * FOR-EACH (for con i due punti):
     *   La sintassi  "for (String[] coppia : SINERGIE)"  è equivalente a:
     *
     *       for (int i = 0; i < SINERGIE.length; i++) {
     *           String[] coppia = SINERGIE[i];
     *           ...
     *       }
     *
     *   Vantaggi del for-each:
     *     - Più leggibile: non serve gestire l'indice 'i'.
     *     - Impossibile andare fuori dai limiti dell'array per errore.
     *   Limite: non si conosce l'indice dell'elemento corrente (se serve
     *   l'indice, si usa il for classico).
     * ──────────────────────────────────────────────────────────
     *
     * Complessità: O(m * n) dove m = numero di sinergie (16),
     *              n = carte nel mazzo (max 8) → praticamente costante.
     */
    private int contaSinergie() {
        int n = 0;
        // for-each: per ogni coppia sinergica nell'array SINERGIE
        for (String[] coppia : SINERGIE)
            if (hasCarta(coppia[0]) && hasCarta(coppia[1])) n++;
        return n;
    }

    /**
     * Calcola un punteggio di composizione basato sull'equilibrio
     * tra truppe, edifici e incantesimi nel mazzo.
     *
     * Punteggi:
     *   truppe      4-6 → +2.0 punti (ideale)
     *   edifici     1-2 → +1.5 punti
     *   incantesimi 1-3 → +1.5 punti
     */
    private double punteggioComposizione() {
        int truppe      = contaTipo("truppa");
        int edifici     = contaTipo("edificio");
        int incantesimi = contaTipo("incantesimo");

        double score = 0;

        // Truppe: l'ideale è 4-6 su 8 carte
        if (truppe >= 4 && truppe <= 6) score += 2.0;
        else score += Math.max(0, 2.0 - Math.abs(truppe - 5) * 0.5);

        // Edifici: 1-2 è ottimale; 0 penalizza
        if (edifici >= 1 && edifici <= 2) score += 1.5;
        else if (edifici == 0)            score -= 1.0;
        else                              score += 0.5;

        // Incantesimi: 1-3 è ottimale; 0 penalizza
        if (incantesimi >= 1 && incantesimi <= 3) score += 1.5;
        else if (incantesimi == 0)                 score -= 1.0;
        else                                       score += 0.5;

        return score;
    }

    // ── confrontaDeck ────────────────────────────────────────────

    /**
     * Confronta questo mazzo con il mazzo avversario.
     * Restituisce la percentuale di vittoria stimata di QUESTO mazzo (5-95%).
     *
     * Criteri di valutazione:
     *   1. Elisir medio    — mazzo più veloce → più giocate per minuto
     *   2. Composizione    — equilibrio truppe / edifici / incantesimi
     *   3. Evo e Hero      — bonus qualità carta
     *   4. Sinergie        — coppie di carte che si potenziano a vicenda
     *
     * Il risultato è normalizzato: (punteggioA / (punteggioA + punteggioB)) * 100,
     * poi bloccato nell'intervallo [5%, 95%] per evitare estremi irrealistici.
     *
     * @param avversario il mazzo da confrontare
     * @return percentuale di vittoria stimata (es. 62.5 = 62.5%)
     */
    public double confrontaDeck(Deck avversario) {
        if (avversario == null) {
            throw new IllegalArgumentException("Il mazzo avversario non puo' essere null.");
        }
        if (numeroCarte == 0 || avversario.numeroCarte == 0) {
            return 50.0; // mazzo vuoto → pareggio per convenzione
        }

        double puntoProprio    = calcolaPunteggio();
        double puntoAvversario = avversario.calcolaPunteggio();

        double totale      = puntoProprio + puntoAvversario;
        double percentuale = (puntoProprio / totale) * 100.0;

        // Clamping: limita il valore tra 5 e 95 (nessun mazzo è invincibile)
        percentuale = Math.max(5.0, Math.min(95.0, percentuale));

        // Arrotonda a una cifra decimale
        return Math.round(percentuale * 10.0) / 10.0;
    }

    /**
     * Calcola il punteggio assoluto del mazzo (usato solo internamente).
     *
     * Pipeline:
     *   1. Velocità : elisir basso → più giocate → +punteggio
     *   2. Composizione bilanciata
     *   3. Evo (+2.5 per carta) e Hero (+3.0 per carta)
     *   4. Sinergie (+2.0 per coppia attiva)
     */
    private double calcolaPunteggio() {
        double score = 0;

        // 1. Velocità: elisir medio basso = vantaggio offensivo
        double elisir = calcolaElisirMedio();
        score += (6.0 - elisir) * 3.0;

        // 2. Composizione equilibrata
        score += punteggioComposizione() * 2.0;

        // 3. Bonus carte speciali
        score += contaEvo()  * 2.5;
        score += contaHero() * 3.0;

        // 4. Bonus sinergie
        score += contaSinergie() * 2.0;

        // Il punteggio minimo è 0.1 per evitare divisione per zero in confrontaDeck
        return Math.max(score, 0.1);
    }

    // ── toString ─────────────────────────────────────────────────

    /**
     * Restituisce un riepilogo testuale del mazzo con statistiche chiave.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Deck{\n");
        sb.append("  elisir medio : ").append(String.format("%.2f", calcolaElisirMedio())).append("\n");
        sb.append("  sinergie     : ").append(contaSinergie()).append("\n");
        sb.append("  evo          : ").append(contaEvo())
          .append("  |  hero: ").append(contaHero()).append("\n");
        sb.append("  carte        :\n");

        // for classico con indice: serve 'i' per stampare il numero progressivo
        for (int i = 0; i < numeroCarte; i++) {
            sb.append("    ").append(i + 1).append(". ").append(carte[i]).append("\n");
        }
        sb.append("}");
        return sb.toString();
    }
}
