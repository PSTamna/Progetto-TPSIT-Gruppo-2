package esercizi;

import java.util.Scanner;

/**
 * ================================================================
 * Main.java — Unico punto d'ingresso del progetto.
 *
 * Flusso:
 *   1. Mostra il pool completo delle carte disponibili (Deck.POOL).
 *   2. Giocatore 1 costruisce il proprio mazzo scegliendo 8 carte per nome.
 *   3. Giocatore 2 costruisce il proprio mazzo scegliendo 8 carte per nome.
 *   4. I due mazzi vengono confrontati e si mostra la percentuale
 *      di vittoria stimata per ciascun giocatore.
 * ================================================================
 */
public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║   CLASH ROYALE  —  CONFRONTO MAZZI       ║");
        System.out.println("╚══════════════════════════════════════════╝");

        // Stampa l'elenco completo delle carte
        stampaPOOL();

        // Raccolta skill e costruzione interattiva dei due mazzi
        System.out.println("\n=== GIOCATORE 1 ===");
        String skill1 = chiediSkill(scanner, "Giocatore 1");
        System.out.println("\n=== MAZZO — GIOCATORE 1 ===");
        Deck mazzo1 = costruisciMazzo(scanner, "Giocatore 1");

        System.out.println("\n=== GIOCATORE 2 ===");
        String skill2 = chiediSkill(scanner, "Giocatore 2");
        System.out.println("\n=== MAZZO — GIOCATORE 2 ===");
        Deck mazzo2 = costruisciMazzo(scanner, "Giocatore 2");

        // Riepilogo dei mazzi scelti
        System.out.println("\n─────────────────────────────────────────");
        System.out.println("MAZZO 1:");
        System.out.println(mazzo1);
        System.out.println("MAZZO 2:");
        System.out.println(mazzo2);

        // Calcolo del win-rate stimato considerando deck e divario di skill
        double vittoria1 = mazzo1.confrontaDeck(mazzo2, skill1, skill2);
        double vittoria2 = mazzo2.confrontaDeck(mazzo1, skill2, skill1);

        System.out.println("=== RISULTATO CONFRONTO ===");
        System.out.printf("Giocatore 1 [%-16s] : %.1f%%%n", skill1, vittoria1);
        System.out.printf("Giocatore 2 [%-16s] : %.1f%%%n", skill2, vittoria2);

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
     * Stampa l'elenco numerato di tutte le carte nel pool,
     * raggruppate per tipo (truppe, edifici, incantesimi).
     *
     * Complessità: O(n * t) dove n = carte nel pool, t = numero di tipi (3).
     */
    private static void stampaPOOL() {
        System.out.println("\n=== POOL CARTE DISPONIBILI (" + Deck.POOL.length + " carte) ===");

        String[] tipi = {"truppa", "edificio", "incantesimo"};
        String[] nomiTipi = {"TRUPPE", "EDIFICI", "INCANTESIMI"};

        for (int t = 0; t < tipi.length; t++) {
            System.out.println("\n── " + nomiTipi[t] + " ──");
            System.out.println("  Nome                Elisir  Forza  DPS   Splash  Vel  Aereo  Extra");
            System.out.println("  ────                ──────  ─────  ───   ──────  ───  ─────  ─────");

            for (int i = 0; i < Deck.POOL.length; i++) {
                Carta c = Deck.POOL[i];
                if (!c.getTipo().equalsIgnoreCase(tipi[t])) continue;

                String extra = (c.isEvo() ? "EVO " : "") + (c.isHero() ? "HERO" : "");
                System.out.printf("  %-20s %d       %d/5   %-5.0f %-6.0f  %d/5  %-5s  %s%n",
                    c.getNome(),
                    c.getElisir(),
                    c.getForza(),
                    c.getDps(),
                    c.getSplashDmg(),
                    c.getVelocita(),
                    c.isColpisceAereo() ? "si" : "no",
                    extra);
            }
        }
    }

    /**
     * Chiede al giocatore di scegliere il proprio livello di skill.
     */
    private static String chiediSkill(Scanner scanner, String nomeGiocatore) {
        System.out.println(nomeGiocatore + ": scegli il tuo livello di skill.");
        for (int i = 0; i < Utente.LIVELLI_SKILL.length; i++)
            System.out.printf("  %d. %s%n", i + 1, Utente.LIVELLI_SKILL[i]);

        while (true) {
            System.out.printf("  Numero livello (1-%d) > ", Utente.LIVELLI_SKILL.length);
            int scelta = scanner.nextInt();
            scanner.nextLine(); // libera il buffer
            if (scelta >= 1 && scelta <= Utente.LIVELLI_SKILL.length)
                return Utente.LIVELLI_SKILL[scelta - 1];
            System.out.println("  Numero non valido. Riprova.");
        }
    }

    /**
     * Cerca una carta nel pool per nome (case-insensitive, anche parziale).
     * Restituisce la prima carta il cui nome contiene la stringa cercata,
     * oppure null se nessuna carta corrisponde.
     *
     * Complessità: O(n) dove n = numero di carte nel pool.
     *
     * @param nome la stringa di ricerca
     * @return la Carta trovata, oppure null
     */
    private static Carta cercaCarta(String nome) {
        String lower = nome.toLowerCase();
        // Prima cerca corrispondenza esatta
        for (Carta c : Deck.POOL) {
            if (c.getNome().equalsIgnoreCase(nome)) return c;
        }
        // Se non trova, cerca corrispondenza parziale
        for (Carta c : Deck.POOL) {
            if (c.getNome().toLowerCase().contains(lower)) return c;
        }
        return null;
    }

    /**
     * Guida interattivamente un giocatore nella costruzione del mazzo.
     * Il giocatore sceglie le carte digitando il nome (anche parziale).
     *
     * Con 120+ carte nel pool, la selezione avviene per nome anziché
     * per numero, rendendo l'interfaccia più pratica e intuitiva.
     *
     * @param scanner        Scanner condiviso per la lettura da console
     * @param nomeGiocatore  Nome usato nei messaggi di prompt
     * @return               Deck completato con esattamente 8 carte
     */
    private static Deck costruisciMazzo(Scanner scanner, String nomeGiocatore) {
        Deck mazzo = new Deck();
        System.out.println(nomeGiocatore + ": scegli 8 carte digitando il nome (anche parziale).");

        while (mazzo.getNumeroCarte() < 8) {
            System.out.printf("  [%d/8] Nome carta > ", mazzo.getNumeroCarte() + 1);
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("  Inserisci un nome valido.");
                continue;
            }

            // Cerca la carta nel pool
            Carta cartaScelta = cercaCarta(input);
            if (cartaScelta == null) {
                System.out.println("  Carta '" + input + "' non trovata nel pool. Riprova.");
                continue;
            }

            // aggiuntaCarta verifica internamente: pool membership e duplicati
            boolean aggiunta = mazzo.aggiuntaCarta(cartaScelta);
            if (aggiunta) {
                System.out.println("  OK — " + cartaScelta.getNome() + " aggiunta al mazzo.");
            }
        }

        return mazzo;
    }
}
