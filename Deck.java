package esercizi;

public class Deck {

    // ── Costanti ─────────────────────────────────────────────────
    private static final int DIMENSIONE_MAZZO = 8;

    // ── Riferimenti al pool completo di carte e sinergie ─────────
    // I dati risiedono in CartePool.java per facilitare l'aggiunta di
    // nuove carte senza modificare la logica del Deck.
    public static final Carta[]     POOL     = CartePool.TUTTE_LE_CARTE;
    private static final String[][] SINERGIE = CartePool.SINERGIE;

    // ── Campi d'istanza ──────────────────────────────────────────
    private Carta[] carte; // array delle carte nel mazzo
    private int numeroCarte; // quante carte sono state aggiunte finora

    // ── Costruttori ──────────────────────────────────────────────

    /** Crea un mazzo vuoto. */
    public Deck() {
        this.carte = new Carta[DIMENSIONE_MAZZO];
        this.numeroCarte = 0;
    }

    /** Crea un mazzo copiando un array di carte (al massimo DIMENSIONE_MAZZO). */
    public Deck(Carta[] carte) {
        this.carte = new Carta[DIMENSIONE_MAZZO];
        this.numeroCarte = Math.min(carte.length, DIMENSIONE_MAZZO);
        // Copia gli elementi uno a uno: O(n) dove n = numeroCarte
        for (int i = 0; i < this.numeroCarte; i++) {
            this.carte[i] = carte[i];
        }
    }

    // ── Metodi di gestione ───────────────────────────────────────

    /**
     * Aggiunge una carta al mazzo se:
     * a) il mazzo non è pieno
     * b) la carta appartiene al POOL
     * c) la carta non è già presente nel mazzo
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
        for (Carta c : POOL) { // for-each: vedi spiegazione sotto
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

    /**
     * Restituisce la carta all'indice specificato, o null se l'indice non è valido.
     */
    public Carta getCarta(int indice) {
        if (indice < 0 || indice >= numeroCarte) {
            System.out.println("Indice non valido.");
            return null;
        }
        return carte[indice];
    }

    public int getNumeroCarte() {
        return numeroCarte;
    }

    public Carta[] getCarte() {
        return carte;
    }

    // ── Metodi di calcolo (privati) ──────────────────────────────

    /**
     * Calcola l'elisir medio del mazzo.
     * Complessità: O(n) — un solo passaggio sulle n carte del mazzo.
     */
    private double calcolaElisirMedio() {
        if (numeroCarte == 0)
            return 0;
        int tot = 0;
        for (int i = 0; i < numeroCarte; i++)
            tot += carte[i].getElisir();
        return (double) tot / numeroCarte;
    }

    /** Conta quante carte del mazzo hanno il tipo indicato. O(n). */
    private int contaTipo(String tipo) {
        int n = 0;
        for (int i = 0; i < numeroCarte; i++)
            if (carte[i].getTipo().equalsIgnoreCase(tipo))
                n++;
        return n;
    }

    /** Conta le carte con Evoluzione. O(n). */
    private int contaEvo() {
        int n = 0;
        for (int i = 0; i < numeroCarte; i++)
            if (carte[i].isEvo())
                n++;
        return n;
    }

    /** Conta le carte Hero. O(n). */
    private int contaHero() {
        int n = 0;
        for (int i = 0; i < numeroCarte; i++)
            if (carte[i].isHero())
                n++;
        return n;
    }

    /** Verifica se il mazzo contiene la carta con il nome indicato. O(n). */
    private boolean hasCarta(String nome) {
        for (int i = 0; i < numeroCarte; i++)
            if (carte[i].getNome().equalsIgnoreCase(nome))
                return true;
        return false;
    }

    /**
     * Conta le sinergie attive nel mazzo.
     *
     * ──────────────────────────────────────────────────────────
     * FOR-EACH (for con i due punti):
     * La sintassi "for (String[] coppia : SINERGIE)" è equivalente a:
     *
     * for (int i = 0; i < SINERGIE.length; i++) {
     * String[] coppia = SINERGIE[i];
     * ...
     * }
     *
     * Vantaggi del for-each:
     * - Più leggibile: non serve gestire l'indice 'i'.
     * - Impossibile andare fuori dai limiti dell'array per errore.
     * Limite: non si conosce l'indice dell'elemento corrente (se serve
     * l'indice, si usa il for classico).
     * ──────────────────────────────────────────────────────────
     *
     * Complessità: O(m * n) dove m = numero di sinergie (16),
     * n = carte nel mazzo (max 8) → praticamente costante.
     */
    private int contaSinergie() {
        int n = 0;
        // for-each: per ogni coppia sinergica nell'array SINERGIE
        for (String[] coppia : SINERGIE)
            if (hasCarta(coppia[0]) && hasCarta(coppia[1]))
                n++;
        return n;
    }

    /**
     * Calcola un punteggio di composizione basato sull'equilibrio
     * tra truppe, edifici e incantesimi nel mazzo.
     *
     * Punteggi:
     * truppe 4-6 → +2.0 punti (ideale)
     * edifici 1-2 → +1.5 punti
     * incantesimi 1-3 → +1.5 punti
     */
    private double punteggioComposizione() {
        int truppe = contaTipo("truppa");
        int edifici = contaTipo("edificio");
        int incantesimi = contaTipo("incantesimo");

        double score = 0;

        // Truppe: l'ideale è 4-6 su 8 carte
        if (truppe >= 4 && truppe <= 6)
            score += 2.0;
        else
            score += Math.max(0, 2.0 - Math.abs(truppe - 5) * 0.5);

        // Edifici: 1-2 è ottimale; 0 penalizza
        if (edifici >= 1 && edifici <= 2)
            score += 1.5;
        else if (edifici == 0)
            score -= 1.0;
        else
            score += 0.5;

        // Incantesimi: 1-3 è ottimale; 0 penalizza
        if (incantesimi >= 1 && incantesimi <= 3)
            score += 1.5;
        else if (incantesimi == 0)
            score -= 1.0;
        else
            score += 0.5;

        return score;
    }

    // ── Nuovi metodi di calcolo avanzati ─────────────────────────

    /**
     * Calcola la forza media del mazzo.
     * Complessità: O(n) — un solo passaggio sulle n carte.
     */
    private double calcolaForzaMedia() {
        if (numeroCarte == 0)
            return 0;
        int tot = 0;
        for (int i = 0; i < numeroCarte; i++)
            tot += carte[i].getForza();
        return (double) tot / numeroCarte;
    }

    /**
     * Calcola il DPS (danni per secondo) totale del mazzo.
     * Somma i DPS di tutte le carte per indicare la potenza offensiva complessiva.
     * Complessità: O(n).
     */
    private double calcolaDpsTotale() {
        double tot = 0;
        for (int i = 0; i < numeroCarte; i++)
            tot += carte[i].getDps();
        return tot;
    }

    /**
     * Calcola lo splash damage medio considerando solo le carte che hanno splash >
     * 0.
     * Utile per valutare la capacità del mazzo di gestire orde di truppe.
     * Complessità: O(n).
     *
     * @return media splash tra le carte con splash, oppure 0 se nessuna carta ha
     *         splash
     */
    private double calcolaSplashMedio() {
        double tot = 0;
        int count = 0;
        for (int i = 0; i < numeroCarte; i++) {
            if (carte[i].getSplashDmg() > 0) {
                tot += carte[i].getSplashDmg();
                count++;
            }
        }
        return count > 0 ? tot / count : 0;
    }

    /**
     * Calcola la velocità media delle truppe nel mazzo.
     * Considera solo le carte con velocità > 0 (esclude edifici e incantesimi).
     * Complessità: O(n).
     */
    private double calcolaVelocitaMedia() {
        double tot = 0;
        int count = 0;
        for (int i = 0; i < numeroCarte; i++) {
            if (carte[i].getVelocita() > 0) {
                tot += carte[i].getVelocita();
                count++;
            }
        }
        return count > 0 ? tot / count : 0;
    }

    /**
     * Calcola la capacità antiaerea del mazzo.
     *
     * La capacità è calcolata DINAMICAMENTE in base alle carte presenti:
     * conta quante carte nel mazzo possono colpire unità aeree.
     * Un valore alto significa che il mazzo gestisce bene minacce aeree
     * (es. Lava Hound, Balloon, Minions).
     *
     * Complessità: O(n) — un passaggio sulle n carte del mazzo.
     *
     * @return numero di carte nel mazzo in grado di colpire aereo (0-8)
     */
    public int calcolaCapacitaAntiaerea() {
        int n = 0;
        for (int i = 0; i < numeroCarte; i++)
            if (carte[i].isColpisceAereo())
                n++;
        return n;
    }

    /**
     * Calcola il cycle del mazzo: somma dell'elisir delle 4 carte meno costose.
     *
     * In Clash Royale il "cycle" rappresenta quanti elisir servono per
     * ciclare la mano e rivedere una carta specifica. Un cycle basso
     * consente giocate più frequenti e un maggiore controllo del ritmo.
     *
     * Complessità: O(n log n) per l'ordinamento dell'array dei costi.
     *
     * @return somma elisir delle 4 carte più economiche
     */
    public int calcolaCycle() {
        if (numeroCarte == 0)
            return 0;

        // Copia i costi elisir in un array temporaneo e lo ordina
        int[] costi = new int[numeroCarte];
        for (int i = 0; i < numeroCarte; i++)
            costi[i] = carte[i].getElisir();
        java.util.Arrays.sort(costi);

        // Somma le 4 carte più economiche (o tutte se meno di 4)
        int somma = 0;
        int n = Math.min(4, numeroCarte);
        for (int i = 0; i < n; i++)
            somma += costi[i];
        return somma;
    }

    // ── confrontaDeck ────────────────────────────────────────────

    /**
     * Confronta questo mazzo con il mazzo avversario.
     * Restituisce la percentuale di vittoria stimata di QUESTO mazzo (5-95%).
     *
     * Il calcolo è COMPARATIVO: i due mazzi vengono valutati l'uno contro
     * l'altro su 10 criteri indipendenti, ciascuno con un peso specifico.
     *
     * Criteri e pesi:
     *   1. Elisir medio      (peso 3.0) — mazzo più leggero = ciclo più rapido
     *   2. Composizione      (peso 2.0) — equilibrio truppe/edifici/incantesimi
     *   3. Evo e Hero        (peso 2.5) — qualità carte speciali
     *   4. Sinergie          (peso 3.0) — combo tra carte
     *   5. Forza media       (peso 2.0) — robustezza delle carte
     *   6. DPS totale        (peso 3.5) — potenza offensiva
     *   7. Splash medio      (peso 2.0) — gestione sciami e orde
     *   8. Velocità media    (peso 1.5) — aggressività delle truppe
     *   9. Antiaerea         (peso 2.5) — difesa contro minacce aeree
     *  10. Cycle             (peso 2.0) — velocità di rotazione mano
     *
     * Ogni criterio produce un vantaggio compreso tra -1.0 e +1.0.
     * La somma pesata determina la percentuale finale.
     *
     * @param avversario il mazzo da confrontare
     * @return percentuale di vittoria stimata (5-95%)
     */
    public double confrontaDeck(Deck avversario) {
        if (avversario == null) {
            throw new IllegalArgumentException("Il mazzo avversario non puo' essere null.");
        }
        if (numeroCarte == 0 || avversario.numeroCarte == 0) {
            return 50.0;
        }

        double vantaggio = 0.0;

        // 1. Elisir medio: mazzo più leggero = vantaggio (peso 3.0)
        //    Differenza normalizzata: -(mio - suo) / 5.0 → meno elisir = positivo
        double diffElisir = -(calcolaElisirMedio() - avversario.calcolaElisirMedio());
        vantaggio += clamp(diffElisir / 3.0) * 3.0;

        // 2. Composizione: confronto diretto dei punteggi (peso 2.0)
        double diffComp = punteggioComposizione() - avversario.punteggioComposizione();
        vantaggio += clamp(diffComp / 3.0) * 2.0;

        // 3. Evo e Hero: bonus proporzionale alla differenza (peso 2.5)
        int mieEvo  = contaEvo()  + contaHero();
        int sueEvo  = avversario.contaEvo() + avversario.contaHero();
        vantaggio += clamp((mieEvo - sueEvo) / 3.0) * 2.5;

        // 4. Sinergie: più sinergie = tattica migliore (peso 3.0)
        int diffSinergie = contaSinergie() - avversario.contaSinergie();
        vantaggio += clamp(diffSinergie / 3.0) * 3.0;

        // 5. Forza media: carte più robuste (peso 2.0)
        double diffForza = calcolaForzaMedia() - avversario.calcolaForzaMedia();
        vantaggio += clamp(diffForza / 2.0) * 2.0;

        // 6. DPS totale: potenza offensiva (peso 3.5)
        //    Normalizzato su 500 DPS di differenza = vantaggio pieno
        double diffDps = calcolaDpsTotale() - avversario.calcolaDpsTotale();
        vantaggio += clamp(diffDps / 500.0) * 3.5;

        // 7. Splash medio: gestione sciami (peso 2.0)
        //    Se l'avversario ha molte truppe leggere, lo splash conta di più
        double diffSplash = calcolaSplashMedio() - avversario.calcolaSplashMedio();
        vantaggio += clamp(diffSplash / 150.0) * 2.0;

        // 8. Velocità media truppe: aggressività (peso 1.5)
        double diffVel = calcolaVelocitaMedia() - avversario.calcolaVelocitaMedia();
        vantaggio += clamp(diffVel / 2.0) * 1.5;

        // 9. Antiaerea: confronto diretto (peso 2.5)
        //    Se l'avversario ha carte aeree e io non ho antiaerea → penalità
        int miaAntiaerea = calcolaCapacitaAntiaerea();
        int suaAntiaerea = avversario.calcolaCapacitaAntiaerea();
        double diffAerea = miaAntiaerea - suaAntiaerea;
        vantaggio += clamp(diffAerea / 3.0) * 2.5;

        // 10. Cycle: ciclo più basso = più giocate (peso 2.0)
        //     Differenza invertita: meno cycle = meglio
        int diffCycle = -(calcolaCycle() - avversario.calcolaCycle());
        vantaggio += clamp(diffCycle / 4.0) * 2.0;

        // Conversione: vantaggio totale → percentuale (50% = equilibrio)
        // La somma dei pesi è 24.0; scaliamo il vantaggio su ±45%
        double pesoTotale = 24.0;
        double percentuale = 50.0 + (vantaggio / pesoTotale) * 45.0;

        // Clamping: limita il valore tra 5 e 95
        percentuale = Math.max(5.0, Math.min(95.0, percentuale));

        return Math.round(percentuale * 10.0) / 10.0;
    }

    /**
     * Versione con skill: aggiusta il win-rate di base in base al divario di
     * livello tra i due giocatori.
     *
     * Ogni livello di vantaggio vale +6 punti percentuali per il giocatore
     * più skillato. Il risultato è sempre bloccato nel range [5%, 95%].
     *
     * Esempio:
     *   base = 50%, gap = +3 (es. Champion vs Unranked) → 50 + 18 = 68%
     *   base = 80%, gap = -7 (Unranked vs Ultimate Champ) → 80 - 42 = 38%
     *
     * @param avversario      mazzo dell'avversario
     * @param skillProprio    livello di skill di questo giocatore
     * @param skillAvversario livello di skill dell'avversario
     * @return percentuale di vittoria stimata (5–95%)
     */
    public double confrontaDeck(Deck avversario, String skillProprio, String skillAvversario) {
        double base = confrontaDeck(avversario);
        int gap = Utente.rangSkill(skillProprio) - Utente.rangSkill(skillAvversario);
        double risultato = base + gap * 6.0;
        return Math.max(5.0, Math.min(95.0, Math.round(risultato * 10.0) / 10.0));
    }

    /**
     * Limita un valore nell'intervallo [-1.0, +1.0].
     * Usato per normalizzare le differenze tra metriche dei due mazzi.
     */
    private static double clamp(double v) {
        return Math.max(-1.0, Math.min(1.0, v));
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
        sb.append("  cycle        : ").append(calcolaCycle()).append(" elisir\n");
        sb.append("  forza media  : ").append(String.format("%.1f", calcolaForzaMedia())).append("/5\n");
        sb.append("  DPS totale   : ").append(String.format("%.0f", calcolaDpsTotale())).append("\n");
        sb.append("  splash medio : ").append(String.format("%.0f", calcolaSplashMedio())).append("\n");
        sb.append("  vel. media   : ").append(String.format("%.1f", calcolaVelocitaMedia())).append("/5\n");
        sb.append("  antiaerea    : ").append(calcolaCapacitaAntiaerea()).append("/").append(numeroCarte)
                .append(" carte\n");
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
