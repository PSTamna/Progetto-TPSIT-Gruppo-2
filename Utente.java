package esercizi;

import java.util.Scanner;

/**
 * Rappresenta un utente registrato nel sistema.
 *
 * Campi: nome, cognome, username, email, telefono, skill.
 * utentiCreati è un contatore statico che tiene traccia di quanti
 * oggetti Utente sono stati istanziati durante l'esecuzione.
 */
public class Utente {

    // ── Livelli di skill validi (ordinati dal più basso al più alto) ──
    // L'indice nell'array coincide con il rango numerico (0 = più basso, 7 = più alto).
    public static final String[] LIVELLI_SKILL = {
        "Unranked",
        "Master 1", "Master 2", "Master 3",
        "Champion", "Grand Champion", "Royal Champion", "Ultimate Champion"
    };

    // ── Campi d'istanza ─────────────────────────────────────────
    private String nome;
    private String cognome;
    private String username;
    private String email;
    private int    telefono;
    private String skill;

    // ── Campo statico ────────────────────────────────────────────
    public static int utentiCreati = 0;

    // ── Costruttori ──────────────────────────────────────────────

    /** Costruttore vuoto: crea un utente da riempire con inserisciDati(). */
    public Utente() {
        utentiCreati++;
    }

    /** Costruttore senza skill (compatibilità con codice precedente). */
    public Utente(String nome, String cognome, String username, String email, int telefono) {
        this.nome     = nome;
        this.cognome  = cognome;
        this.username = username;
        this.email    = email;
        this.telefono = telefono;
        utentiCreati++;
    }

    /** Costruttore completo con skill. */
    public Utente(String nome, String cognome, String username, String email, int telefono, String skill) {
        this.nome     = nome;
        this.cognome  = cognome;
        this.username = username;
        this.email    = email;
        this.telefono = telefono;
        this.skill    = validaSkill(skill);
        utentiCreati++;
    }

    /** Costruttore di copia: crea un nuovo Utente con gli stessi dati di u. */
    public Utente(Utente u) {
        this.nome     = u.nome;
        this.cognome  = u.cognome;
        this.username = u.username;
        this.email    = u.email;
        this.telefono = u.telefono;
        this.skill    = u.skill;
        utentiCreati++;
    }

    // ── Getter e Setter ──────────────────────────────────────────

    public String getNome()              { return nome; }
    public void   setNome(String nome)   { this.nome = nome; }

    public String getCognome()               { return cognome; }
    public void   setCognome(String cognome) { this.cognome = cognome; }

    public String getUsername()                { return username; }
    public void   setUsername(String username) { this.username = username; }

    public String getEmail()             { return email; }
    public void   setEmail(String email) { this.email = email; }

    public int  getTelefono()              { return telefono; }
    public void setTelefono(int telefono)  { this.telefono = telefono; }

    public String getSkill()             { return skill; }

    /**
     * Imposta la skill dell'utente.
     * Lancia IllegalArgumentException se il valore non è tra quelli ammessi.
     */
    public void setSkill(String skill) { this.skill = validaSkill(skill); }

    // ── Metodi statici per la gestione della skill ────────────────

    /**
     * Restituisce il rango numerico della skill (0 = Unranked, 7 = Ultimate Champion),
     * oppure -1 se il valore non è riconosciuto (ricerca case-insensitive).
     */
    public static int rangSkill(String skill) {
        for (int i = 0; i < LIVELLI_SKILL.length; i++)
            if (LIVELLI_SKILL[i].equalsIgnoreCase(skill)) return i;
        return -1;
    }

    /**
     * Verifica che il valore di skill sia tra quelli ammessi e restituisce
     * la forma canonica (es. "grand champion" → "Grand Champion").
     * Lancia IllegalArgumentException se il valore non è valido.
     */
    private static String validaSkill(String skill) {
        int rank = rangSkill(skill);
        if (rank < 0)
            throw new IllegalArgumentException("Skill non valida: '" + skill + "'.");
        return LIVELLI_SKILL[rank];
    }

    // ── Metodi ───────────────────────────────────────────────────

    /**
     * Legge i dati dell'utente da console.
     * Per la skill mostra i livelli disponibili e ripete la richiesta finché
     * l'utente non inserisce un valore valido.
     *
     * NOTA sul buffer:
     *   Al termine si chiama t.nextLine() dopo t.nextInt() per consumare il
     *   carattere '\n' rimasto nel buffer.
     */
    public void inserisciDati(Scanner t) {
        System.out.print("Inserisci il tuo nome: ");
        this.nome = t.nextLine();

        System.out.print("Inserisci il tuo cognome: ");
        this.cognome = t.nextLine();

        System.out.print("Inserisci il tuo username: ");
        this.username = t.nextLine();

        System.out.print("Inserisci la tua email: ");
        this.email = t.nextLine();

        System.out.print("Inserisci il tuo numero di telefono: ");
        this.telefono = t.nextInt();
        t.nextLine(); // libera il buffer dopo nextInt()

        System.out.println("Livelli di skill disponibili:");
        for (int i = 0; i < LIVELLI_SKILL.length; i++)
            System.out.printf("  %d. %s%n", i + 1, LIVELLI_SKILL[i]);
        while (true) {
            System.out.print("Inserisci il tuo livello di skill: ");
            String input = t.nextLine().trim();
            int rank = rangSkill(input);
            if (rank >= 0) { this.skill = LIVELLI_SKILL[rank]; break; }
            System.out.println("  Valore non valido. Riprova.");
        }
    }

    /** Rappresentazione testuale dell'utente. */
    @Override
    public String toString() {
        return "Utente{" +
               "nome='"      + nome     + '\'' +
               ", cognome='" + cognome  + '\'' +
               ", username='"+ username + '\'' +
               ", email='"   + email    + '\'' +
               ", telefono=" + telefono +
               ", skill='"   + skill    + '\'' +
               '}';
    }
}
