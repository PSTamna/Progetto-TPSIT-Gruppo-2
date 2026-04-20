package esercizi;

import java.util.Scanner;

/**
 * Rappresenta un utente registrato nel sistema.
 *
 * Campi: nome, cognome, username, email, telefono.
 * utentiCreati è un contatore statico che tiene traccia di quanti
 * oggetti Utente sono stati istanziati durante l'esecuzione.
 */
public class Utente {

    // ── Campi d'istanza ─────────────────────────────────────────
    private String nome;
    private String cognome;
    private String username;
    private String email;
    private int    telefono;

    // ── Campo statico ────────────────────────────────────────────
    // 'static' → condiviso tra tutte le istanze; conta gli utenti creati
    public static int utentiCreati = 0;

    // ── Costruttori ──────────────────────────────────────────────

    /** Costruttore vuoto: crea un utente da riempire con inserisciDati(). */
    public Utente() {
        utentiCreati++;
    }

    /** Costruttore completo: inizializza tutti i campi. */
    public Utente(String nome, String cognome, String username, String email, int telefono) {
        this.nome      = nome;
        this.cognome   = cognome;
        this.username  = username;
        this.email     = email;
        this.telefono  = telefono;
        utentiCreati++;
    }

    /** Costruttore di copia: crea un nuovo Utente con gli stessi dati di u. */
    public Utente(Utente u) {
        this.nome      = u.nome;
        this.cognome   = u.cognome;
        this.username  = u.username;
        this.email     = u.email;
        this.telefono  = u.telefono;
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

    // ── Metodi ───────────────────────────────────────────────────

    /**
     * Legge i dati dell'utente da console.
     *
     * NOTA sul buffer:
     *   Al termine si chiama t.nextLine() dopo t.nextInt() per consumare il
     *   carattere '\n' rimasto nel buffer; senza questa pulizia, la prima
     *   nextLine() chiamata successivamente leggerebbe una stringa vuota.
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
    }

    /** Rappresentazione testuale dell'utente. */
    @Override
    public String toString() {
        return "Utente{" +
               "nome='"     + nome     + '\'' +
               ", cognome='" + cognome  + '\'' +
               ", username='"+ username + '\'' +
               ", email='"  + email    + '\'' +
               ", telefono=" + telefono +
               '}';
    }
}
