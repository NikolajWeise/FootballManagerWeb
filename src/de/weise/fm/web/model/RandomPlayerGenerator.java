package de.weise.fm.web.model;

import java.util.Random;

import de.weise.fm.web.model.play.Position;

/**
 * Provides helper methods for creating random players.
 */
public class RandomPlayerGenerator {

    private static final String[] forenames = new String[] {
        "Anton", "Boris", "Chris", "David", "Eric", "Frederic", "Gabriel", "Hubert", "Ingo", "Joe", "Kevin",
        "Louis", "Nick", "Otto", "Perry", "Quaya", "Richard", "Steven", "Tobi", "Alex", "Victor", "James",
        "Walter", "Antol", "Mike", "Oscar", "Austin", "Andrew", "Ahmad", "Reggie", "Darrius", "Harris", "Laron",
        "Matt", "Donald", "Tim", "Tom", "Rob", "Danny", "Alfonzo", "Julian", "Aaron", "Ryan", "Leonard", "Kyle",
        "Stan", "Kenny", "Colin", "Michael", "Patrick", "Frank", "Anquan", "Vernon", "Lawrence", "Barney", "Ted",
        "Ralph", "Donald", "Adrian", "Christian", "Greg", "Jared", "Desmond", "Thomas", "Nick", "Mick", "Danny",
        "Eli", "Peter", "Will",
    };

    private static final String[] surnames = new String[] {
        "Antonovic", "Baumann", "Chestman", "Dark", "Egnus", "Friedrich", "Gayl", "Hank", "Jones", "Kaufmann",
        "Lampard", "Mijankovic", "Neumann", "Olson", "Paffy", "Quo", "Rich", "Slater", "Thomas", "Ukwood",
        "Vice", "Wiggom", "Zed", "Rice", "Wise", "Montana", "Luck", "Bradshaw", "Wayne", "Bey", "Werner", "Landry",
        "Hasselbeck", "Lefeged", "Brown", "White", "Black", "Schneider", "Hunt", "Tebow", "Brady", "Gronkowski",
        "Grabowski", "Amendola", "Dennard", "Edelman", "Dobson", "Mallett", "Blount", "Lawrence", "Kaepernick",
        "Crabtree", "Willis", "Gore", "Jackson", "Boldin", "Davis", "Okoye", "Reid", "Oldman", "Altman", "Ericson",
        "Mosbey", "Frazier", "Humphrey", "Trump", "Peterson", "Ponder", "Jennings", "Allen", "Patterson", "Webb",
        "Bishop", "Cassel", "Kalil", "Beckham", "Gray", "Grey", "Simpson", "Schuster", "Johnson", "Wood", "Turner",
        "Whyte", "Manning", "Parker", "Smith",
    };

    private static Random rand = new Random();

    public static String getForename() {
        String forename = forenames[rand.nextInt(forenames.length)];

        int randVal = rand.nextInt(500);
        if(randVal == 0) { // two forenames
            forename += " " + forenames[rand.nextInt(forenames.length)];
        }

        return forename;
    }

    public static String getSurname() {
        String surname = surnames[rand.nextInt(surnames.length)];

        int randVal = rand.nextInt(500);
        if(randVal == 0) { // two surnames
            surname += "-" + surnames[rand.nextInt(surnames.length)];
        } else if(randVal == 1) { // suffix Jr.
            surname += " Jr.";
        }
        return surname;
    }

    /**
     * Gets a random age between 18 and 34.
     */
    public static int getAge() {
        return getRandom(18, 34);
    }

    /**
     * Gets the player's number according to the specified position.
     */
    public static int getNumber(Position position) {
        if(Position.QB.equals(position) || Position.P.equals(position) || Position.K.equals(position)) {
            return getRandom(1, 19);
        }
        if(Position.WR.equals(position)) {
            return rand.nextBoolean() ? getRandom(10, 19) : getRandom(80, 89);
        }
        if(Position.FB.equals(position) || Position.HB.equals(position)
                || Position.SS.equals(position) || Position.FS.equals(position) || Position.CB.equals(position)) {
            return getRandom(20, 49);
        }
        if(Position.MLB.equals(position)
                || Position.RLB.equals(position) || Position.LLB.equals(position)) {
            return rand.nextBoolean() ? getRandom(50, 59) : getRandom(90, 99);
        }
        if(Position.C.equals(position)) {
            return getRandom(50, 79);
        }
        if(Position.RT.equals(position) || Position.LT.equals(position)
                || Position.RG.equals(position) || Position.LG.equals(position)) {
            return getRandom(60, 79);
        }
        if(Position.DT.equals(position) || Position.DE.equals(position)) {
            return rand.nextBoolean() ? getRandom(60, 79) : getRandom(90, 99);
        }
        if(Position.TE.equals(position)) {
            return getRandom(80, 89);
        }
        return getRandom(1, 99); // never reached
    }

    /**
     * @param min inclusive
     * @param max inclusive
     */
    private static int getRandom(int min, int max) {
        return rand.nextInt(max+1 - min) + min;
    }

    private static int getDefaultSkillValue() {
        return getRandom(1, 70);
    }

    public static Attributes getAttributes(Position position) {
        Attributes attributes = new Attributes();
        for(Attributes.Type type : Attributes.Type.values()) {
            if(type.isPrimeAttribute(position)) {
                attributes.setValue(type, getRandom(70, 99));
            } else {
                attributes.setValue(type, getDefaultSkillValue());
            }
        }

        return attributes;
    }
}
