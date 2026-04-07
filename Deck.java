package esercizi;

public class Deck {

    private Carta[] carte;
    private int numeroCarte;
    private static final int DIMENSIONE_MAZZO = 8;

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

    public boolean aggiuntaCarta(Carta carta) {
        if (numeroCarte >= DIMENSIONE_MAZZO) {
            System.out.println("Il mazzo è già pieno (max " + DIMENSIONE_MAZZO + " carte).");
            return false;
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

    public int getNumeroCarte() {
        return numeroCarte;
    }

    public Carta[] getCarte() {
        return carte;
    }

    // Calcola la media del costo in elisir delle carte nel mazzo
    private double calcolaElisirMedio() {
        if (numeroCarte == 0) return 0;
        int totale = 0;
        for (int i = 0; i < numeroCarte; i++) {
            totale += carte[i].getElisir();
        }
        return (double) totale / numeroCarte;
    }

    /**
     * Confronta questo mazzo con un secondo mazzo passato come parametro.
     * Il confronto si basa sull'elisir medio: un mazzo con elisir medio
     * più basso è più veloce e aggressivo, ed è considerato vincente.
     * In caso di parità vince il mazzo con più carte evo o hero.
     *
     * Restituisce la percentuale di vittoria stimata di questo mazzo
     * rispetto al mazzo avversario (tra 0.0 e 100.0).
     */
    public double confrontaDeck(Deck avversario) {
        double elisirProprio    = this.calcolaElisirMedio();
        double elisirAvversario = avversario.calcolaElisirMedio();

        // Conta carte evo + hero come bonus
        int bonusProprio = 0;
        for (int i = 0; i < this.numeroCarte; i++) {
            if (carte[i].isEvo())  bonusProprio++;
            if (carte[i].isHero()) bonusProprio++;
        }

        int bonusAvversario = 0;
        Carta[] carteAvv = avversario.getCarte();
        for (int i = 0; i < avversario.getNumeroCarte(); i++) {
            if (carteAvv[i].isEvo())  bonusAvversario++;
            if (carteAvv[i].isHero()) bonusAvversario++;
        }

        // Differenza di elisir: ogni 0.5 di vantaggio vale ~5% in più
        double diffElisir = elisirAvversario - elisirProprio;
        double percentuale = 50.0 + (diffElisir * 10.0);

        // Bonus evo/hero: ogni carta speciale in più vale +2%
        int diffBonus = bonusProprio - bonusAvversario;
        percentuale += diffBonus * 2.0;

        // Clamp tra 5% e 95% per evitare valori estremi
        percentuale = Math.max(5.0, Math.min(95.0, percentuale));

        return Math.round(percentuale * 10.0) / 10.0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Deck{\n");
        sb.append("  elisir medio: ").append(String.format("%.2f", calcolaElisirMedio())).append("\n");
        for (int i = 0; i < numeroCarte; i++) {
            sb.append("  ").append(i + 1).append(". ").append(carte[i]).append("\n");
        }
        sb.append("}");
        return sb.toString();
    }
}