package de.weise.fm.web.bundle;

import java.util.ListResourceBundle;

public class Bundle_de extends ListResourceBundle {

    @Override
    protected Object[][] getContents() {
        return new Object[][] {

            {"save", "Speichern"},
            {"new", "Neu"},
            {"delete", "L�schen"},
            {"edit", "Editieren"},

            // welcome
            {"welcome", "Das Ziel dieser WebApp ist, einige Vorz�ge des Vaadin-Frameworks bzgl. Webentwicklung " +
            		"aufzuzeigen. Einige Grafiken k�nnten mit Hilfe eines Web-Designers ansehnlicher gestaltet " +
            		"werden. :)" +
                    "<br/><br/>Du wurdest automatisch mit dem Account \"{1}\" eingeloggt." +
                    "<br/>Log dich aus und lade die Seite neu um einen anderen Account zu bekommen."},
            {"language", "Sprache"},

            // logout
            {"logged out", "ausgeloggt"},

            // formation
            {"playerColHeaders", new String[] {"name", "pos", "no", "alter", "gesamt", "cond",
                    "strg", "sped", "agil", "awar", "ctch", "roru", "thra", "thrp", "blkr", "blkp", "tckl",
                    "covm", "covz", "reco", "kika", "kikp"}},
            {"formationSaved", "Formation gespeichert"},
            {"formationNotSavedCpt", "Formation nicht gespeichert"},
            {"formationNotSaved", "\nDie Formation ist nicht g�ltig. " +
                    "Eventuell stehen nicht exakt sieben Spieler an der line of scrimmage."},
            {"formationNotSavedName", "\nBitte gib einen Namen f�r die Formation ein."},
            {"formationNotDeletedCpt", "Formation nicht gel�scht"},
            {"formationNotDeleted", "\nDu kannst nicht die einzige Formation in deinem Playbook l�schen."},
            {"formationDeleted", "Formation gel�scht"},
            {"formations", "Formationen"},
            {"editName", "Editiere Namen"},

            // player details
            {"name", "Name"},
            {"age", "Alter"},
            {"position", "Position"},
            {"attributes", "Attribute"},
            {"CONDITION", "Kondition"},
            {"STRENGTH", "St�rke"},
            {"SPEED", "Schnelligkeit"},
            {"AGILITY", "Geschicklichkeit"},
            {"AWARENESS", "Bewusstsein"},
            {"CATCHING", "Fangen"},
            {"ROUTE_RUN", "Route Running"},
            {"THROW_ACC", "Wurf-Genauigkeit"},
            {"THROW_POW", "Wurf-Kraft"},
            {"BLOCK_RUN", "Run Blocken"},
            {"BLOCK_PAS", "Pass Blocken"},
            {"TACKLE", "Tacklen"},
            {"COVER_MAN", "Manndeckung"},
            {"COVER_ZON", "Zonendeckung"},
            {"PLAY_RECO", "Spielzug-Erkennung"},
            {"KICK_ACC", "Schuss-Genauigkeit"},
            {"KICK_POW", "Schuss-Kraft"},
            {"stats", "Stats"},

            // plays
            {"plays", "Spielz�ge"},
            {"hideRoutes", "verstecke/zeige Routen"},
            {"primaryAction", "Prim�rer Passempf�nger"},
            {"playSaved", "Spielzug gespeichert"},
            {"playNotSavedCpt", "Spielzug nicht gespeichert"},
            {"playNotSaved", "\nDer Spielzug ist nicht g�ltig.\n" +
                    "Vergiss nicht einen prim�ren Passempf�nger in Passspielz�gen zu setzen."},
            {"playNotSavedName", "\nBitte gib einen Namen f�r den Spielzug ein."},
            {"playNotDeletedCpt", "Spielzug nicht gel�scht"},
            {"playNotDeleted", "\nDu kannst nicht den einzigen Spielzug in deinem Playbook l�schen."},
            {"playDeleted", "Spielzug gel�scht"},
            {"actions", "Aktionen"},

            {"playType", "Spielzugart"},

            // training
            {"indTraining", "Individuelles Training"},
            {"formationTraining", "Formations-Training"},
            {"offFormations", "Offensive Formationen"},
            {"defFormations", "Defensive Formationen"},
            {"offPlays", "Offensive Spielz�ge"},
            {"defPlays", "Defensive Spielz�ge"},
       };
    }
}
