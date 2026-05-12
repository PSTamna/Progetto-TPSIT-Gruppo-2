package esercizi;

/**
 * Pool completo di tutte le carte di Clash Royale.
 *
 * Ogni carta è definita con il costruttore completo:
 *   nome, elisir, tipo, evo, hero, forza, splashDmg, dps, velocita, colpisceAereo
 *
 * Valori di riferimento (livello torneo):
 *   forza     : 1-5  (1 = molto debole, 5 = molto forte)
 *   splashDmg : danni splash medi (0 se single-target)
 *   dps       : danni per secondo
 *   velocita  : 1-5  (1 = molto lenta, 5 = molto veloce, 0 = edifici/incantesimi)
 *   colpisceAereo : true se la carta può colpire unità aeree
 *
 * Per aggiungere nuove carte in futuro, basta inserire una nuova riga
 * nell'array TUTTE_LE_CARTE senza modificare altre classi.
 */
public class CartePool {

    public static final Carta[] TUTTE_LE_CARTE = {

        // ═══════════════════════════════════════════════════════════
        //  TRUPPE — 1 ELISIR
        // ═══════════════════════════════════════════════════════════
        //                    nome              eli  tipo      evo    hero  forza splash dps  vel  aereo
        new Carta("Skeletons",        1, "truppa", true,  false, 1,   0,  100, 4, false),
        new Carta("Ice Spirit",       1, "truppa", false, false, 1,  48,    0, 5, true),
        new Carta("Fire Spirit",      1, "truppa", false, false, 1,  90,    0, 5, true),
        new Carta("Electro Spirit",   1, "truppa", false, false, 1,  52,    0, 5, true),
        new Carta("Heal Spirit",      1, "truppa", false, false, 1,   0,    0, 5, true),

        // ═══════════════════════════════════════════════════════════
        //  TRUPPE — 2 ELISIR
        // ═══════════════════════════════════════════════════════════
        new Carta("Goblins",          2, "truppa", false, false, 1,   0,  120, 5, false),
        new Carta("Spear Goblins",    2, "truppa", false, false, 1,   0,   80, 5, true),
        new Carta("Bats",             2, "truppa", true,  false, 1,   0,  100, 5, true),
        new Carta("Ice Golem",        2, "truppa", false, false, 1,  40,    0, 2, false),
        new Carta("Wall Breakers",    2, "truppa", true,  false, 2, 300,    0, 5, false),

        // ═══════════════════════════════════════════════════════════
        //  TRUPPE — 3 ELISIR
        // ═══════════════════════════════════════════════════════════
        new Carta("Knight",           3, "truppa", true,  false, 3,   0,  167, 3, false),
        new Carta("Archers",          3, "truppa", true,  false, 2,   0,  126, 3, true),
        new Carta("Bomber",           3, "truppa", true,  false, 2, 188,    0, 3, false),
        new Carta("Miner",            3, "truppa", false, false, 3,   0,  160, 4, false),
        new Carta("Bandit",           3, "truppa", false, false, 3,   0,  160, 4, false),
        new Carta("Skeleton Army",    3, "truppa", true,  false, 2,   0,  200, 4, false),
        new Carta("Minions",          3, "truppa", false, false, 2,   0,  130, 4, true),
        new Carta("Goblin Gang",      3, "truppa", false, false, 2,   0,  150, 5, false),
        new Carta("Dart Goblin",      3, "truppa", true,  false, 1,   0,  120, 5, true),
        new Carta("Firecracker",      3, "truppa", true,  false, 1, 100,   80, 3, true),
        new Carta("Guards",           3, "truppa", false, false, 2,   0,  130, 4, false),
        new Carta("Skeleton Barrel",  3, "truppa", true,  false, 2, 100,    0, 4, false),
        new Carta("Goblin Demolisher",3, "truppa", false, false, 2, 150,    0, 3, false),

        // ═══════════════════════════════════════════════════════════
        //  TRUPPE — 4 ELISIR
        // ═══════════════════════════════════════════════════════════
        new Carta("Musketeer",        4, "truppa", false, true,  3,   0,  160, 3, true),
        new Carta("Valkyrie",         4, "truppa", true,  false, 4, 180,    0, 3, false),
        new Carta("Baby Dragon",      4, "truppa", true,  false, 3, 130,    0, 4, true),
        new Carta("Hog Rider",        4, "truppa", false, false, 4,   0,  240, 5, false),
        new Carta("Battle Ram",       4, "truppa", true,  false, 3,   0,  170, 4, false),
        new Carta("Dark Prince",      4, "truppa", false, false, 3, 160,    0, 4, false),
        new Carta("Electro Wizard",   4, "truppa", false, false, 3,  96,  103, 3, true),
        new Carta("Flying Machine",   4, "truppa", false, false, 2,   0,  130, 3, true),
        new Carta("Mini P.E.K.K.A",   4, "truppa", false, false, 4,   0,  280, 4, false),
        new Carta("Magic Archer",     4, "truppa", false, false, 2,   0,  120, 3, true),
        new Carta("Zappies",          4, "truppa", false, false, 2,  80,   70, 3, true),
        new Carta("Hunter",           4, "truppa", true,  false, 3,   0,  200, 3, true),
        new Carta("Night Witch",      4, "truppa", false, false, 3,   0,  180, 3, true),
        new Carta("Inferno Dragon",   4, "truppa", true,  false, 3,   0,  250, 3, true),
        new Carta("Mother Witch",     4, "truppa", false, false, 2,   0,  110, 3, true),
        new Carta("Phoenix",          4, "truppa", false, false, 3,   0,  150, 4, true),
        new Carta("Battle Healer",    4, "truppa", false, false, 3,   0,  120, 3, true),
        new Carta("Ram Rider",        4, "truppa", false, false, 3,   0,  180, 4, false),
        new Carta("Elixir Golem",     3, "truppa", false, false, 2,   0,  100, 3, false),

        // ═══════════════════════════════════════════════════════════
        //  TRUPPE — 5 ELISIR
        // ═══════════════════════════════════════════════════════════
        new Carta("Prince",           5, "truppa", false, false, 4,   0,  260, 4, false),
        new Carta("Wizard",           5, "truppa", true,  false, 3, 200,    0, 3, true),
        new Carta("Witch",            5, "truppa", true,  false, 3, 100,   90, 3, true),
        new Carta("Bowler",           5, "truppa", false, true,  4, 220,    0, 2, false),
        new Carta("Executioner",      5, "truppa", true,  false, 3, 190,    0, 3, true),
        new Carta("Royal Hogs",       5, "truppa", true,  false, 3,   0,  200, 5, false),
        new Carta("Cannon Cart",      5, "truppa", false, false, 3,   0,  190, 3, false),
        new Carta("Minion Horde",     5, "truppa", true,  false, 3,   0,  260, 4, true),
        new Carta("Elite Barbarians", 6, "truppa", false, false, 4,   0,  280, 5, false),
        new Carta("Royal Recruits",   6, "truppa", true,  false, 3,   0,  180, 2, false),
        new Carta("Barbarians",       5, "truppa", true,  false, 3,   0,  200, 3, false),
        new Carta("Rascals",          5, "truppa", false, false, 3,   0,  170, 3, false),

        // ═══════════════════════════════════════════════════════════
        //  TRUPPE — 6+ ELISIR
        // ═══════════════════════════════════════════════════════════
        new Carta("Giant",            5, "truppa", false, false, 4,   0,  120, 2, false),
        new Carta("Royal Giant",      6, "truppa", true,  false, 4,   0,  180, 2, false),
        new Carta("P.E.K.K.A",       7, "truppa", true,  false, 5,   0,  300, 1, false),
        new Carta("Golem",            8, "truppa", false, false, 5, 200,    0, 1, false),
        new Carta("Lava Hound",       7, "truppa", false, false, 4,   0,   60, 2, true),
        new Carta("Mega Knight",      7, "truppa", true,  false, 5, 280,    0, 2, false),
        new Carta("Sparky",           6, "truppa", false, false, 4, 350,    0, 2, false),
        new Carta("Electro Giant",    7, "truppa", false, false, 5,  90,    0, 1, false),
        new Carta("Three Musketeers", 9, "truppa", false, false, 4,   0,  480, 3, true),
        new Carta("Balloon",          5, "truppa", false, false, 4, 200,    0, 3, false),

        // ═══════════════════════════════════════════════════════════
        //  CAMPIONI (CHAMPIONS)
        // ═══════════════════════════════════════════════════════════
        new Carta("Golden Knight",    4, "truppa", false, true,  3,   0,  185, 3, false),
        new Carta("Skeleton King",    4, "truppa", false, true,  4, 150,    0, 3, false),
        new Carta("Archer Queen",     5, "truppa", false, true,  4,   0,  260, 3, true),
        new Carta("Mighty Miner",     4, "truppa", false, true,  3,   0,  200, 3, false),
        new Carta("Monk",             5, "truppa", false, true,  4,   0,  200, 3, false),
        new Carta("Little Prince",    3, "truppa", false, true,  2,   0,  120, 3, true),
        new Carta("Goblinstein",      4, "truppa", false, true,  3, 120,    0, 3, false),
        new Carta("Boss Bandit",      5, "truppa", false, true,  4,   0,  220, 4, false),

        // ═══════════════════════════════════════════════════════════
        //  EDIFICI — BUILDINGS
        // ═══════════════════════════════════════════════════════════
        new Carta("Cannon",           3, "edificio", true,  false, 2,   0, 127, 0, false),
        new Carta("Tesla",            4, "edificio", true,  false, 3,   0, 150, 0, true),
        new Carta("Bomb Tower",       4, "edificio", false, false, 3, 180,   0, 0, false),
        new Carta("Furnace",          4, "edificio", false, false, 2, 100,  80, 0, true),
        new Carta("Tombstone",        3, "edificio", false, false, 1,   0,  40, 0, false),
        new Carta("Goblin Cage",      4, "edificio", true,  false, 3,   0, 140, 0, false),
        new Carta("Goblin Drill",     4, "edificio", true,  false, 3,   0, 100, 0, false),
        new Carta("Inferno Tower",    5, "edificio", false, false, 4,   0, 200, 0, true),
        new Carta("Mortar",           4, "edificio", true,  false, 3, 150,   0, 0, false),
        new Carta("X-Bow",            6, "edificio", false, false, 4,   0, 100, 0, true),
        new Carta("Elixir Collector", 6, "edificio", false, false, 1,   0,   0, 0, false),
        new Carta("Barbarian Hut",    7, "edificio", false, false, 3,   0, 100, 0, false),

        // ═══════════════════════════════════════════════════════════
        //  INCANTESIMI — SPELLS
        // ═══════════════════════════════════════════════════════════
        new Carta("Zap",              2, "incantesimo", false, false, 1,  75,   0, 0, true),
        new Carta("The Log",          2, "incantesimo", false, false, 2, 240,   0, 0, false),
        new Carta("Giant Snowball",   2, "incantesimo", true,  false, 1,  80,   0, 0, true),
        new Carta("Barbarian Barrel", 2, "incantesimo", false, true,  2, 120,   0, 0, false),
        new Carta("Rage",             2, "incantesimo", false, false, 1,   0,   0, 0, false),
        new Carta("Arrows",           3, "incantesimo", false, false, 2, 180,   0, 0, true),
        new Carta("Tornado",          3, "incantesimo", false, false, 2,  80,   0, 0, true),
        new Carta("Earthquake",       3, "incantesimo", false, false, 2, 120,   0, 0, false),
        new Carta("Royal Delivery",   3, "incantesimo", false, false, 2, 150,   0, 0, true),
        new Carta("Clone",            3, "incantesimo", false, false, 1,   0,   0, 0, false),
        new Carta("Goblin Curse",     2, "incantesimo", false, false, 1,  60,   0, 0, false),
        new Carta("Void",             3, "incantesimo", false, false, 2, 100,   0, 0, true),
        new Carta("Vines",            3, "incantesimo", false, false, 2, 140,   0, 0, false),
        new Carta("Fireball",         4, "incantesimo", false, false, 3, 325,   0, 0, true),
        new Carta("Freeze",           4, "incantesimo", false, false, 2,  70,   0, 0, true),
        new Carta("Poison",           4, "incantesimo", false, false, 3, 220,   0, 0, true),
        new Carta("Graveyard",        5, "incantesimo", false, false, 3,   0, 100, 0, false),
        new Carta("Rocket",           6, "incantesimo", false, false, 4, 500,   0, 0, true),
        new Carta("Lightning",        6, "incantesimo", false, false, 4, 400,   0, 0, true),

        // ═══════════════════════════════════════════════════════════
        //  TRUPPE EXTRA (rimanenti)
        // ═══════════════════════════════════════════════════════════
        new Carta("Goblin Barrel",    3, "incantesimo", false, false, 2,   0, 100, 0, false),
        new Carta("Mirror",           1, "incantesimo", false, false, 1,   0,   0, 0, false),
        new Carta("Lumberjack",       4, "truppa", true,  false, 3,   0, 200, 5, false),
        new Carta("Electro Dragon",   5, "truppa", true,  false, 3,  90,  90, 3, true),
        new Carta("Royal Ghost",      3, "truppa", true,  false, 3,   0, 180, 4, false),
        new Carta("Fisherman",        3, "truppa", false, false, 3,   0, 140, 3, false),
        new Carta("Goblin Giant",     6, "truppa", false, false, 4,   0, 130, 2, true),
    };

    // ═══════════════════════════════════════════════════════════
    //  SINERGIE: coppie di carte che si potenziano a vicenda
    // ═══════════════════════════════════════════════════════════
    public static final String[][] SINERGIE = {
        // Hog Rider combos
        {"Hog Rider",      "Zap"},
        {"Hog Rider",      "The Log"},
        {"Hog Rider",      "Ice Golem"},
        {"Hog Rider",      "Ice Spirit"},
        {"Hog Rider",      "Musketeer"},
        {"Hog Rider",      "Earthquake"},
        {"Hog Rider",      "Fireball"},
        // P.E.K.K.A combos
        {"P.E.K.K.A",      "Bandit"},
        {"P.E.K.K.A",      "Battle Ram"},
        {"P.E.K.K.A",      "Electro Wizard"},
        {"P.E.K.K.A",      "Magic Archer"},
        {"P.E.K.K.A",      "Poison"},
        // Golem combos
        {"Golem",           "Night Witch"},
        {"Golem",           "Baby Dragon"},
        {"Golem",           "Lightning"},
        {"Golem",           "Lumberjack"},
        // Lava Hound combos
        {"Lava Hound",      "Balloon"},
        {"Lava Hound",      "Minions"},
        {"Lava Hound",      "Inferno Dragon"},
        {"Lava Hound",      "Skeleton Army"},
        // Giant combos
        {"Giant",           "Witch"},
        {"Giant",           "Musketeer"},
        {"Giant",           "Sparky"},
        // Mega Knight combos
        {"Mega Knight",     "Inferno Dragon"},
        {"Mega Knight",     "Bats"},
        {"Mega Knight",     "Skeleton Barrel"},
        // X-Bow combos
        {"X-Bow",           "Tesla"},
        {"X-Bow",           "Archers"},
        {"X-Bow",           "Knight"},
        // Royal Giant combos
        {"Royal Giant",     "Furnace"},
        {"Royal Giant",     "Fisherman"},
        {"Royal Giant",     "Lightning"},
        // Bridge Spam
        {"Bandit",          "Battle Ram"},
        {"Bandit",          "Dark Prince"},
        {"Bandit",          "The Log"},
        {"Golden Knight",   "Fireball"},
        // Cycle combos
        {"Skeletons",       "Ice Spirit"},
        {"Cannon",          "Skeletons"},
        {"Knight",          "Archers"},
        // Graveyard combos
        {"Graveyard",       "Poison"},
        {"Graveyard",       "Knight"},
        {"Graveyard",       "Baby Dragon"},
        // Electro Giant combos
        {"Electro Giant",   "Tornado"},
        {"Electro Giant",   "Lightning"},
        // Splashyard
        {"Bowler",          "Graveyard"},
        {"Bowler",          "Tornado"},
        // Miner combos
        {"Miner",           "Wall Breakers"},
        {"Miner",           "Poison"},
        {"Miner",           "Bats"},
        // Furnace combos
        {"Furnace",         "Skeletons"},
        {"Furnace",         "Ice Spirit"},
        // Royal Hogs combos
        {"Royal Hogs",      "Fireball"},
        {"Royal Hogs",      "Earthquake"},
        // Misc
        {"Electro Wizard",  "Zap"},
        {"Musketeer",       "Ice Golem"},
        {"Valkyrie",        "Hog Rider"},
        {"Balloon",         "Freeze"},
        {"Balloon",         "Lumberjack"},
        {"Prince",          "Dark Prince"},
        {"Three Musketeers","Elixir Collector"},
        {"Witch",           "Giant"},
        {"Wizard",          "Giant"},
    };
}
