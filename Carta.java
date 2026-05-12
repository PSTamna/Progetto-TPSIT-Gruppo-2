package esercizi;

import java.util.Scanner;

/**
 * Rappresenta una carta di Clash Royale.
 *
 * Campi principali:
 * - nome : nome univoco della carta
 * - elisir : costo in mana (1-9)
 * - tipo : "truppa", "edificio" o "incantesimo"
 * - evo : true se la carta ha una versione Evoluzione (bordo viola)
 * - hero : true se la carta ha una versione Eroe (bordo dorato)
 * - forza : forza fisica della carta (1 = molto debole, 5 = molto forte)
 * - splashDmg : danni splash medi inflitti dalla carta
 * - dps : danni per secondo della carta
 * - velocita : velocità della truppa (1 = molto lenta, 5 = molto veloce, 0 =
 * edifici/incantesimi)
 * - colpisceAereo : true se la carta può colpire unità aeree
 *
 * numCarte è un contatore STATICO: appartiene alla classe, non all'istanza.
 * Viene incrementato ogni volta che si crea una nuova Carta (in qualsiasi
 * costruttore), quindi conta tutte le istanze create durante l'esecuzione.
 */
public class Carta {

    // ── Campi d'istanza ─────────────────────────────────────────
    private String tipo;
    private boolean evo;
    private boolean hero;
    private int forza; // forza fisica (1-5)
    private double splashDmg; // danni splash medi
    private double dps; // danni per secondo
    private int velocita; // velocità truppa (1-5, 0 per edifici/incantesimi)
    private boolean colpisceAereo; // true se può colpire unità aeree

    // ── Campo statico ────────────────────────────────────────────
    // 'static' → condiviso tra tutte le istanze della classe
    public static int numCarte = 0;

    // ── Costruttori ──────────────────────────────────────────────

    /**
     * Costruttore vuoto: crea una carta senza dati (da riempire con inserisciDati).
     */
    public Carta() {
        numCarte++;
    }

    /** Costruttore completo: inizializza tutti i campi in una sola riga. */
    public Carta(String nome, int elisir, String tipo, boolean evo, boolean hero) {
        this.nome = nome;
        this.elisir = elisir;
        this.tipo = tipo;
        this.evo = evo;
        this.hero = hero;
        numCarte++;
    }

    /**
     * Costruttore completo: inizializza tutti i campi, compresi quelli avanzati.
     *
     * @param nome          nome univoco della carta
     * @param elisir        costo in elisir (1-9)
     * @param tipo          "truppa", "edificio" o "incantesimo"
     * @param evo           true se ha evoluzione
     * @param hero          true se ha versione eroica
     * @param forza         forza fisica (1-5)
     * @param splashDmg     danni splash medi
     * @param dps           danni per secondo
     * @param velocita      velocità truppa (1-5, 0 per edifici/incantesimi)
     * @param colpisceAereo true se può colpire unità aeree
     */
    public Carta(String nome, int elisir, String tipo, boolean evo, boolean hero,
            int forza, double splashDmg, double dps, int velocita, boolean colpisceAereo) {
        this.nome = nome;
        this.elisir = elisir;
        this.tipo = tipo;
        this.evo = evo;
        this.hero = hero;
        this.forza = forza;
        this.splashDmg = splashDmg;
        this.dps = dps;
        this.velocita = velocita;
        this.colpisceAereo = colpisceAereo;
        numCarte++;
    }

    // ── Getter e Setter ──────────────────────────────────────────
    // I campi sono private (incapsulamento): si accede solo tramite questi metodi.

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getElisir() {
        return elisir;
    }

    public void setElisir(int elisir) {
        this.elisir = elisir;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isEvo() {
        return evo;
    }

    public void setEvo(boolean evo) {
        this.evo = evo;
    }

    public boolean isHero() {
        return hero;
    }

    public void setHero(boolean hero) {
        this.hero = hero;
    }

    public int getForza() {
        return forza;
    }

    public void setForza(int forza) {
        this.forza = forza;
    }

    public double getSplashDmg() {
        return splashDmg;
    }

    public void setSplashDmg(double splashDmg) {
        this.splashDmg = splashDmg;
    }

    public double getDps() {
        return dps;
    }

    public void setDps(double dps) {
        this.dps = dps;
    }

    public int getVelocita() {
        return velocita;
    }

    public void setVelocita(int velocita) {
        this.velocita = velocita;
    }

    public boolean isColpisceAereo() {
        return colpisceAereo;
    }

    public void setColpisceAereo(boolean colpisceAereo) {
        this.colpisceAereo = colpisceAereo;
    }

    // ── Metodi ───────────────────────────────────────────────────

    /**
     * Legge i dati della carta dall'input dell'utente.
     *
     * PROBLEMA DEL BUFFER con nextInt() / nextBoolean():
     * Questi metodi leggono solo il token numerico/booleano e lasciano il
     * carattere '\n' (invio) nel buffer. Se subito dopo si chiamasse
     * nextLine(), essa leggerebbe la riga vuota rimasta.
     * Soluzione: chiamare t.nextLine() subito dopo nextInt()/nextBoolean()
     * per "pulire" il buffer.
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

        System.out.print("Forza fisica della carta (1-5): ");
        this.forza = t.nextInt();
        t.nextLine(); // libera il buffer

        System.out.print("Danni splash medi (0 se assenti): ");
        this.splashDmg = t.nextDouble();
        t.nextLine(); // libera il buffer

        System.out.print("Danni per secondo (DPS): ");
        this.dps = t.nextDouble();
        t.nextLine(); // libera il buffer

        System.out.print("Velocità della truppa (1-5, 0 per edifici/incantesimi): ");
        this.velocita = t.nextInt();
        t.nextLine(); // libera il buffer

        System.out.print("La carta colpisce unità aeree? (true/false): ");
        this.colpisceAereo = t.nextBoolean();
        t.nextLine(); // libera il buffer
    }

    /**
     * Restituisce una rappresentazione testuale della carta.
     * Override del metodo toString() di Object: viene chiamato automaticamente
     * quando si usa la carta in una concatenazione di stringhe (es. "" + carta).
     */
    @Override
    public String toString() {
        return "Carta{" +
                "nome='" + nome + '\'' +
                ", elisir=" + elisir +
                ", tipo='" + tipo + '\'' +
                ", evo=" + evo +
                ", hero=" + hero +
                ", forza=" + forza +
                ", splash=" + splashDmg +
                ", dps=" + dps +
                ", vel=" + velocita +
                ", aereo=" + colpisceAereo +
                '}';
    }
}
