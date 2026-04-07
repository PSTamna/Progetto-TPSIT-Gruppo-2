package esercizi;

public class MainConfronto {
    public static void main(String[] args) {

        // ── Mazzo 1: PEKKA Bridge Spam ───────────────────────
        // PEKKA     → evo (bordo viola brillante)
        // Golden Knight → hero (bordo dorato)
        // Battle Ram → evo (bordo viola brillante)
        // Zap       → evo (bordo viola brillante)
        Deck mazzo1 = new Deck(new Carta[]{
            new Carta("PEKKA",          7, "truppa",      true,  false),
            new Carta("Golden Knight",  4, "truppa",      false, true),
            new Carta("Battle Ram",     4, "truppa",      true,  false),
            new Carta("Furnace",        4, "edificio",    false, false),
            new Carta("Vines",          3, "incantesimo", false, false),
            new Carta("Zap",            2, "incantesimo", true,  false),
            new Carta("Bandit",         3, "truppa",      false, false),
            new Carta("Electro Wizard", 4, "truppa",      false, false)
        });

        // ── Mazzo 2: Hog Cycle ───────────────────────────────
        // Musketeer → hero (bordo dorato)
        // Skeletons → evo (bordo viola brillante)
        // Cannon    → normale (bordo arancione standard, non evo)
        Deck mazzo2 = new Deck(new Carta[]{
            new Carta("Cannon",    3, "edificio",    true, false),
            new Carta("Musketeer", 4, "truppa",      false, true),
            new Carta("Skeletons", 1, "truppa",      true,  false),
            new Carta("Hog Rider", 4, "truppa",      false, false),
            new Carta("Fireball",  4, "incantesimo", false, false),
            new Carta("Ice Golem", 2, "truppa",      false, false),
            new Carta("Ice Spirit",1, "truppa",      false, false),
            new Carta("Log",       2, "incantesimo", false, false)
        });

        // ── Stampa mazzi ─────────────────────────────────────
        System.out.println("=== MAZZO 1: PEKKA Bridge Spam ===");
        System.out.println(mazzo1);

        System.out.println("=== MAZZO 2: Hog Cycle ===");
        System.out.println(mazzo2);

        // ── Confronto ────────────────────────────────────────
        double vittoriaMazzo1 = mazzo1.confrontaDeck(mazzo2);
        double vittoriaMazzo2 = mazzo2.confrontaDeck(mazzo1);

        System.out.println("=== RISULTATO CONFRONTO ===");
        System.out.println("Mazzo 1 (PEKKA Bridge Spam) : " + vittoriaMazzo1 + "%");
        System.out.println("Mazzo 2 (Hog Cycle)         : " + vittoriaMazzo2 + "%");

        if (vittoriaMazzo1 > vittoriaMazzo2) {
            System.out.println(">> Vantaggio stimato per: Mazzo 1 (PEKKA Bridge Spam)");
        } else if (vittoriaMazzo2 > vittoriaMazzo1) {
            System.out.println(">> Vantaggio stimato per: Mazzo 2 (Hog Cycle)");
        } else {
            System.out.println(">> Matchup equilibrato.");
        }
    }
}