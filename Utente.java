package esercizi;
import java.util.*;
public class Utente {
	private String nome;
	private String cognome;
	private String username;
	private String email;
	private int telefono;
	public static int utentiCreati = 0;
	
	public Utente() {
		utentiCreati++;
	}
	
	public Utente(String n,String c,String u,String e,int t) {
		this.nome = n;
		this.cognome = c;
		this.username = u;
		this.email = e;
		this.telefono = t;
		utentiCreati++;
	}
	
	public Utente(Utente u) {
		u.nome = this.nome;
		u.cognome = this.cognome;
		u.username = this.username;
		u.email = this.email;
		u.telefono = this.telefono;
		
		utentiCreati++;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getTelefono() {
		return telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}
	
	public void inserisciDati(Scanner t) {
		System.out.print("inserisci il tuo nome");
		this.nome = t.nextLine();
		System.out.print("inserisci il tuo cognome");
		this.cognome = t.nextLine();
		System.out.print("inserisci il tuo nome");
		this.nome = t.nextLine();
		System.out.print("inserisci il tuo username");
		this.username = t.nextLine();
		System.out.print("inserisci la tua email");
		this.email = t.nextLine();
		System.out.print("inserisci il tuo numero di telefono");
		this.telefono = t.nextInt();
		
	}
	
	public String toString() {
	    return "Utente{" +
	           "nome='" + nome + '\'' +
	           ", cognome='" + cognome + '\'' +
	           ", username='" + username + '\'' +
	           ", email='" + email + '\'' +
	           ", telefono=" + telefono +
	           '}';
	}
}
