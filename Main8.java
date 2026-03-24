package esercizi;
import java.util.Scanner;

public class Main8 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Gestione collezione di carte ===");
        System.out.print("Quante carte vuoi inserire? ");
        int n = scanner.nextInt();
        scanner.nextLine(); // consuma il newline residuo

        Carta[] carte = new Carta[n];

        for (int i = 0; i < n; i++) {
            System.out.println("\n--- Carta " + (i + 1) + " di " + n + " ---");
            carte[i] = new Carta();
            carte[i].inserisciDati(scanner);
        }

        System.out.println("\n=== Collezione inserita (" + n + " carte) ===");
        for (int i = 0; i < n; i++) {
            System.out.println((i + 1) + ". " + carte[i]);
        }

        System.out.println("\nTotale carte create: " + Carta.numCarte);

        scanner.close();
    }
}
