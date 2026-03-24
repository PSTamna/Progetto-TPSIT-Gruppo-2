package esercizi;
import java.util.Scanner;

public class Main7 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Benvenuto nel sistema di registrazione! ===");
        System.out.println("Inserisci le tue informazioni per completare la registrazione.\n");

        Utente utente = new Utente();
        utente.inserisciDati(scanner);

        System.out.println("\n=== Registrazione completata! Ecco i tuoi dati ===");
        System.out.println(utente);

        scanner.close();
    }
}
