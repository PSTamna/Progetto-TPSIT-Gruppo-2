package esercizi;

public class MainCarta {

	public static void main(String[] args) {
		Carta pekka = new Carta("PEKKA", 7, "truppa", true, false);
        Carta goldenKnight = new Carta("Golden Knight", 4, "truppa", false, true);
        Carta battleRam = new Carta("Battle Ram", 4, "truppa", true, false);
        Carta furnace = new Carta("Furnace", 4, "edificio", false, false);
        Carta vines = new Carta("Vines", 3, "incantesimo", false, false);
        Carta zap = new Carta("Zap", 2, "incantesimo", false, false);
        Carta bandit = new Carta("Bandit", 3, "truppa", false, false);
        Carta electroWizard = new Carta("Electro Wizard", 4, "truppa", false, false);
        Carta cannon = new Carta("Cannon", 3, "edificio", true, false);
        Carta musketeer = new Carta("Musketeer", 4, "truppa", false, true);
        Carta skeletons = new Carta("Skeletons", 1, "truppa", true, false);
        Carta hogRider = new Carta("Hog Rider", 4, "truppa", false, false);
        Carta fireball = new Carta("Fireball", 4, "incantesimo", false, false);
        Carta iceGolem = new Carta("Ice Golem", 2, "truppa", false, false);
        Carta iceSpirit = new Carta("Ice Spirit", 1, "truppa", false, false);
        Carta log = new Carta("Log", 2, "incantesimo", false, false);
        
        System.out.println(pekka.toString());
        System.out.println(goldenKnight.toString());
        System.out.println(battleRam.toString());
        System.out.println(furnace.toString());
        System.out.println(vines.toString());
        System.out.println(zap.toString());
        System.out.println(bandit.toString());
        System.out.println(electroWizard.toString());
        System.out.println(cannon.toString());
        System.out.println(musketeer.toString());
        System.out.println(skeletons.toString());
        System.out.println(hogRider.toString());
        System.out.println(fireball.toString());
        System.out.println(iceGolem.toString());
        System.out.println(iceSpirit.toString());
        System.out.println(log.toString());
	}
}
