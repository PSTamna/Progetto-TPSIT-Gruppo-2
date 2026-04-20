package esercizi;

import java.util.Scanner;

/**
 * ================================================================
 * Main.java — Unico punto d'ingresso del progetto.
 *
 * Flusso:
 *   1. Mostra il pool delle 16 carte disponibili (Deck.POOL).
 *   2. Giocatore 1 costruisce il proprio mazzo scegliendo 8 carte.
 *   3. Giocatore 2 costruisce il proprio mazzo scegliendo 8 carte.
 *   4. I due mazzi vengono confrontati e si mostra la percentuale
 *      di vittoria stimata per ciascun giocatore.
 * ================================================================
 */
public class Main {

    public static void main(String[] args) {

        // Scanner condiviso: viene aperto una volta sola e chiuso alla fine.
        // Chiudere lo scanner è importante per rilasciare le risorse di I/O.
        Scanner scanner = new Scanner(System.in);

        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║   CLASH ROYALE  —  CONFRONTO MAZZI       ║");
        System.out.println("╚══════════════════════════════════════════╝");

        // Stampa l'elenco numerato delle carte tra cui scegliere
        stampaPOOL();

        // Costruzione interattiva dei due mazzi
        System.out.println("\n=== MAZZO — GIOCATORE 1 ===");
        Deck mazzo1 = costruisciMazzo(scanner, "Giocatore 1");

        System.out.println("\n=== MAZZO — GIOCATORE 2 ===");
        Deck mazzo2 = costruisciMazzo(scanner, "Giocatore 2");

        // Riepilogo dei mazzi scelti
        System.out.println("\n─────────────────────────────────────────");
        System.out.println("MAZZO 1:");
        System.out.println(mazzo1);
        System.out.println("MAZZO 2:");
        System.out.println(mazzo2);

        // Calcolo del win-rate stimato (metodo in Deck.java)
        double vittoria1 = mazzo1.confrontaDeck(mazzo2);
        double vittoria2 = mazzo2.confrontaDeck(mazzo1);

        System.out.println("=== RISULTATO CONFRONTO ===");
        System.out.printf("Giocatore 1 : %.1f%%%n", vittoria1);
        System.out.printf("Giocatore 2 : %.1f%%%n", vittoria2);

        if (vittoria1 > vittoria2) {
            System.out.println(">> Vantaggio stimato per: Giocatore 1");
        } else if (vittoria2 > vittoria1) {
            System.out.println(">> Vantaggio stimato per: Giocatore 2");
        } else {
            System.out.println(">> Matchup equilibrato.");
        }

        scanner.close();
    }

    // ────────────────────────────────────────────────────────────
    // METODI PRIVATI DI SUPPORTO
    // ────────────────────────────────────────────────────────────

    /**
     * Stampa l'elenco numerato (1-based) di tutte le carte in Deck.POOL.
     *
     * COMPLESSITÀ O(n):
     *   La notazione O(n) (Big-O) indica che il tempo di esecuzione cresce
     *   in modo lineare rispetto a n (= numero di carte nel pool).
     *   Se il pool passasse da 16 a 32 carte, il ciclo eseguirebbe il doppio
     *   delle iterazioni. Non c'è ciclo annidato, quindi non è O(n²).
     */
    private static void stampaPOOL() {
        System.out.println("\n=== POOL CARTE DISPONIBILI ===");

        // Ciclo classico con indice: usiamo 'i' perché ci serve il numero
        // progressivo da mostrare all'utente (i + 1).
        for (int i = 0; i < Deck.POOL.length; i++) {
            Carta c = Deck.POOL[i];
            System.out.printf("  %2d. %-16s  [%d elisir, %-13s]%s%s%n",
                i + 1,
                c.getNome(),
                c.getElisir(),
                c.getTipo(),
                c.isEvo()  ? "  (EVO)"  : "",
                c.isHero() ? "  (HERO)" : "");
        }
    }

    /**
     * Guida interattivamente un giocatore nella costruzione del mazzo.
     * Legge 8 scelte (numeri 1-16) e aggiunge le carte corrispondenti al Deck.
     *
     * LIBERARE IL BUFFER — perché è necessario:
     *   scanner.nextInt() legge il numero ma lascia nel buffer il carattere '\n'
     *   (invio). Se subito dopo si chiamasse nextLine(), essa leggerebbe quella
     *   riga vuota invece dell'input reale. La chiamata a scanner.nextLine()
     *   subito dopo nextInt() consuma quel '\n' e pulisce il buffer.
     *
     * @param scanner        Scanner condiviso per la lettura da console
     * @param nomeGiocatore  Nome usato nei messaggi di prompt
     * @return               Deck completato con esattamente 8 carte
     */
    private static Deck costruisciMazzo(Scanner scanner, String nomeGiocatore) {
        Deck mazzo = new Deck();
        System.out.println(nomeGiocatore + ": scegli 8 carte digitando il numero corrispondente.");

        // Il ciclo while continua finché non sono state aggiunte 8 carte valide.
        // Complessità: O(n) dove n = 8 (DIMENSIONE_MAZZO), al netto dei reinserimenti.
        while (mazzo.getNumeroCarte() < 8) {

            System.out.printf("  [%d/8] Numero carta > ", mazzo.getNumeroCarte() + 1);

            int scelta = scanner.nextInt();
            scanner.nextLine(); // *** LIBERA IL BUFFER: consuma '\n' lasciato da nextInt() ***

            // Validazione dell'input: deve essere compreso tra 1 e 16
            if (scelta < 1 || scelta > Deck.POOL.length) {
                System.out.println("  Numero non valido. Scegli tra 1 e " + Deck.POOL.length + ".");
                continue; // salta il resto del ciclo e chiede di nuovo
            }

            // POOL è un array 0-based; la scelta dell'utente è 1-based → sottraiamo 1
            Carta cartaScelta = Deck.POOL[scelta - 1];

            // aggiuntaCarta verifica internamente: pool membership e duplicati
            boolean aggiunta = mazzo.aggiuntaCarta(cartaScelta);
            if (aggiunta) {
                System.out.println("  OK — " + cartaScelta.getNome() + " aggiunta al mazzo.");
            }
            // In caso di errore (duplicato ecc.) il messaggio è già stampato da aggiuntaCarta
        }

        return mazzo;
    }
}
