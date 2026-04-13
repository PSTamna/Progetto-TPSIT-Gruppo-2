package esercizi;
import java.util.Scanner;

public class Carta {
    private String nome;
    private int elisir;
    private String tipo;
    private boolean evo;
    private boolean hero;
    public static int numCarte = 0;

    public Carta() {
    	numCarte++;
    }

    public Carta(String nome, int elisir, String tipo, boolean evo, boolean hero) {
        this.nome = nome;
        this.elisir = elisir;
        this.tipo = tipo;
        this.evo = evo;
        this.hero = hero;
        numCarte++;
    }

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

    public void inserisciDati(Scanner t) {
        System.out.print("Inserisci il nome della carta: ");
        this.nome = t.nextLine();
        System.out.print("Inserisci il costo in elisir: (da 1 a 9)");
        this.elisir = t.nextInt();
        t.nextLine(); // consuma il '\n' residuo dopo nextInt()
        System.out.print("Inserisci il tipo della carta (Tank/Struttura/Spell/Supporto/Win Con): ");
        this.tipo = t.nextLine();
        System.out.print("La carta ha un evoluzione? (true/false): ");
        this.evo = t.nextBoolean();
        System.out.print("La carta ha una versione eroica? (true/false): ");
        this.hero = t.nextBoolean();
    }

    @Override
    public String toString() {
        return "Carta{" +
                "nome='" + nome + '\'' +
                ", elisir=" + elisir +
                ", tipo='" + tipo + '\'' +
                ", evo=" + evo +
                ", hero=" + hero +
                '}';
    }
}