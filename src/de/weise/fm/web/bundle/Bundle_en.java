package de.weise.fm.web.bundle;

import java.util.ListResourceBundle;

public class Bundle_en extends ListResourceBundle {

    @Override
    protected Object[][] getContents() {
        return new Object[][] {

            {"save", "Save"},
            {"new", "New"},
            {"delete", "Delete"},
            {"edit", "Edit"},

            // welcome
            {"welcome", "This web app's goal is to show some Vaadin framework's advantages in web development. " +
            		"Some graphics may be more beautiful with help of a web designer. :)" +
            		"<br/><br/>You are automatically logged in with account \"{1}\"." +
            		"<br/>Logout and reload this page to get another account."},
    		{"language", "Language"},

            // logout
            {"logged out", "logged out"},

            // formation
            {"playerColHeaders", new String[] {"name", "pos", "no", "age", "overall", "cond",
                    "strg", "sped", "agil", "awar", "ctch", "roru", "thra", "thrp", "blkr", "blkp", "tckl",
                    "covm", "covz", "reco", "kika", "kikp"}},
            {"formationSaved", "Formation saved"},
            {"formationNotSavedCpt", "Formation not saved"},
            {"formationNotSaved", "\nThe current formation is not valid.\n" +
            		"Maybe there aren't exactly seven players at line of scrimmage."},
    		{"formationNotSavedName", "\nPlease enter a name for your formation."},
    		{"formationNotDeletedCpt", "Formation not deleted"},
    		{"formationNotDeleted", "\nYou cannot delete the only formation in your playbook."},
    		{"formationDeleted", "Formation deleted"},
    		{"formations", "Formations"},
    		{"editName", "Edit name"},

    		// player details
            {"name", "Name"},
            {"age", "Age"},
            {"position", "Position"},
            {"attributes", "Attributes"},
            {"CONDITION", "Condition"},
            {"STRENGTH", "Strength"},
            {"SPEED", "Speed"},
            {"AGILITY", "Agility"},
            {"AWARENESS", "Awareness"},
            {"CATCHING", "Catching"},
            {"ROUTE_RUN", "Route running"},
            {"THROW_ACC", "Throw accuracy"},
            {"THROW_POW", "Throw power"},
            {"BLOCK_RUN", "Run blocking"},
            {"BLOCK_PAS", "Pass blocking"},
            {"TACKLE", "Tackle"},
            {"COVER_MAN", "Man coverage"},
            {"COVER_ZON", "Zone coverage"},
            {"PLAY_RECO", "Play recognition"},
            {"KICK_ACC", "Kick accuracy"},
            {"KICK_POW", "Kick power"},
            {"stats", "Stats"},

    		// plays
    		{"plays", "Plays"},
    		{"hideRoutes", "hide/show routes"},
    		{"primaryAction", "Primary receiver"},
    		{"playSaved", "Play saved"},
            {"playNotSavedCpt", "Play not saved"},
            {"playNotSaved", "\nThe current play is not valid.\n" +
            		"Don't forget to set a primary receiver in pass plays."},
            {"playNotSavedName", "\nPlease enter a name for your play."},
            {"playNotDeletedCpt", "Play not deleted"},
            {"playNotDeleted", "\nYou cannot delete the only play in your playbook."},
            {"playDeleted", "Play deleted"},
            {"actions", "Actions"},
            {"RUN_BLOCK", "Run block"},
            {"PASS_BLOCK", "Pass block"},
            {"LEAD_BLOCK", "Lead block"},
            {"RCV_HOOK", "Hook"},
            {"RCV_COMEBACK", "Comeback"},
            {"RCV_IN", "In"},
            {"RCV_OUT", "Out"},
            {"RCV_SLANT", "Slant"},
            {"RCV_FLAG", "Flag"},
            {"RCV_RUN", "Receive"},
            {"CRY_COUNTER", "Counter"},
            {"CRY_RUN", "Run"},
            {"MAN", "Man-to-man"},
            {"BLITZ", "Blitz"},
            {"ZONE", "Zone"},

            {"playType", "Play type"},
            {"DRAW", "Draw"},
            {"PA", "Play action"},
            {"PASS", "Pass"},
            {"RUN", "Run"},
            {"DEFENSE", "Defense"},

            // training
            {"indTraining", "Individual training settings"},
            {"formationTraining", "Formation training settings"},
            {"offFormations", "Offense formations"},
            {"defFormations", "Defense formations"},
            {"offPlays", "Offense plays"},
            {"defPlays", "Defense plays"},

       };
    }
}
