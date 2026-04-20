package esercizi;

import java.util.Scanner;

/**
 * Rappresenta una carta di Clash Royale.
 *
 * Campi principali:
 *   - nome       : nome univoco della carta
 *   - elisir     : costo in mana (1-9)
 *   - tipo       : "truppa", "edificio" o "incantesimo"
 *   - evo        : true se la carta ha una versione Evoluzione (bordo viola)
 *   - hero       : true se la carta ha una versione Eroe (bordo dorato)
 *
 * numCarte è un contatore STATICO: appartiene alla classe, non all'istanza.
 * Viene incrementato ogni volta che si crea una nuova Carta (in qualsiasi
 * costruttore), quindi conta tutte le istanze create durante l'esecuzione.
 */
public class Carta {

    // ── Campi d'istanza ─────────────────────────────────────────
    private String  nome;
    private int     elisir;
    private String  tipo;
    private boolean evo;
    private boolean hero;

    // ── Campo statico ────────────────────────────────────────────
    // 'static' → condiviso tra tutte le istanze della classe
    public static int numCarte = 0;

    // ── Costruttori ──────────────────────────────────────────────

    /** Costruttore vuoto: crea una carta senza dati (da riempire con inserisciDati). */
    public Carta() {
        numCarte++;
    }

    /** Costruttore completo: inizializza tutti i campi in una sola riga. */
    public Carta(String nome, int elisir, String tipo, boolean evo, boolean hero) {
        this.nome   = nome;
        this.elisir = elisir;
        this.tipo   = tipo;
        this.evo    = evo;
        this.hero   = hero;
        numCarte++;
    }

    // ── Getter e Setter ──────────────────────────────────────────
    // I campi sono private (incapsulamento): si accede solo tramite questi metodi.

    public String getNome()            { return nome; }
    public void   setNome(String nome) { this.nome = nome; }

    public int  getElisir()             { return elisir; }
    public void setElisir(int elisir)   { this.elisir = elisir; }

    public String getTipo()             { return tipo; }
    public void   setTipo(String tipo)  { this.tipo = tipo; }

    public boolean isEvo()              { return evo; }
    public void    setEvo(boolean evo)  { this.evo = evo; }

    public boolean isHero()             { return hero; }
    public void    setHero(boolean hero){ this.hero = hero; }

    // ── Metodi ───────────────────────────────────────────────────

    /**
     * Legge i dati della carta dall'input dell'utente.
     *
     * PROBLEMA DEL BUFFER con nextInt() / nextBoolean():
     *   Questi metodi leggono solo il token numerico/booleano e lasciano il
     *   carattere '\n' (invio) nel buffer. Se subito dopo si chiamasse
     *   nextLine(), essa leggerebbe la riga vuota rimasta.
     *   Soluzione: chiamare t.nextLine() subito dopo nextInt()/nextBoolean()
     *   per "pulire" il buffer.
     */
    public void inserisciDati(Scanner t) {
        System.out.print("Inserisci il nome della carta: ");
        this.nome = t.nextLine();

        System.out.print("Inserisci il costo in elisir (da 1 a 9): ");
        this.elisir = t.nextInt();
        t.nextLine(); // libera il buffer dopo nextInt()

        System.out.print("Inserisci il tipo (Tank/Struttura/Spell/Supporto/Win Con): ");
        this.tipo = t.nextLine();

        System.out.print("La carta ha un evoluzione? (true/false): ");
        this.evo = t.nextBoolean();
        t.nextLine(); // libera il buffer dopo nextBoolean()

        System.out.print("La carta ha una versione eroica? (true/false): ");
        this.hero = t.nextBoolean();
        t.nextLine(); // libera il buffer dopo nextBoolean()
    }

    /**
     * Restituisce una rappresentazione testuale della carta.
     * Override del metodo toString() di Object: viene chiamato automaticamente
     * quando si usa la carta in una concatenazione di stringhe (es. "" + carta).
     */
    @Override
    public String toString() {
        return "Carta{" +
               "nome='"  + nome   + '\'' +
               ", elisir=" + elisir +
               ", tipo='"  + tipo   + '\'' +
               ", evo="    + evo    +
               ", hero="   + hero   +
               '}';
    }
}
