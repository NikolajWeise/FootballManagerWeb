package de.weise.fm.web.pages;

import org.apache.log4j.Logger;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

import de.weise.fm.web.components.table.FormationTable;
import de.weise.fm.web.components.table.PlayTable;
import de.weise.fm.web.components.table.PlayerTrainingTable;
import de.weise.fm.web.model.Team;
import de.weise.fm.web.model.Users;
import de.weise.fm.web.model.play.Formation;
import de.weise.fm.web.model.play.Play;

/**
 * Shows two areas: individual player training and team formation and play training.
 *
 * @see PlayerTrainingTable
 * @see FormationTable
 * @see PlayTable
 */
public class TrainingSettingsPage extends AbstractPage {

    private static final Logger log = Logger.getLogger(TrainingSettingsPage.class);

    private Team team;
    private PlayTable tblPlaysOff;
    private PlayTable tblPlaysDef;

    @Override
    public void enter() {
        VerticalLayout layout = new VerticalLayout();
        setCompositionRoot(layout);

        team = Users.getUserFromSession(getSession()).getTeam();

        // individual training
        Label lblCaption1 = new Label(bundle.getString("indTraining"));
        lblCaption1.addStyleName(Reindeer.LABEL_H2);
        layout.addComponent(lblCaption1);

        PlayerTrainingTable tblPlayer = new PlayerTrainingTable(team);
        layout.addComponent(tblPlayer);

        // formation training
        Label lblCaption2 = new Label(bundle.getString("formationTraining"));
        lblCaption2.addStyleName(Reindeer.LABEL_H2);
        layout.addComponent(lblCaption2);

        tblPlaysOff = new PlayTable(bundle.getString("offPlays"));
        tblPlaysDef = new PlayTable(bundle.getString("defPlays"));

        FormationTable tblFormationOff = new FormationTable(bundle.getString("offFormations"));
        tblFormationOff.setContent(team.getPlaybook().getOffenseFormations());
        tblFormationOff.addValueChangeListener(new FormationChangeListener(true));
        tblFormationOff.select(team.getFormationOffTraining());
        FormationTable tblFormationDef = new FormationTable(bundle.getString("defFormations"));
        tblFormationDef.setContent(team.getPlaybook().getDefenseFormations());
        tblFormationDef.addValueChangeListener(new FormationChangeListener(false));
        tblFormationDef.select(team.getFormationDefTraining());

        tblPlaysOff.select(team.getPlayOffTraining());
        tblPlaysDef.select(team.getPlayDefTraining());
        tblPlaysOff.addValueChangeListener(new PlayChangeListener(true));
        tblPlaysDef.addValueChangeListener(new PlayChangeListener(false));

        layoutTable(tblPlaysOff);
        layoutTable(tblPlaysDef);
        layoutTable(tblFormationOff);
        layoutTable(tblFormationDef);

        layout.addComponent(new HorizontalLayout(tblFormationOff, tblPlaysOff, tblFormationDef, tblPlaysDef));
    }

    private void layoutTable(Table table) {
        table.setVisibleColumns("name", "skill");
        table.setColumnWidth("skill", 30);
    }

    /**
     * Sets the selected formation at the team training and sets the content of the corresponding play table according
     * to the selected formation.
     */
    private class FormationChangeListener implements ValueChangeListener {

        private boolean offense;

        public FormationChangeListener(boolean offense) {
            this.offense = offense;
        }

        @Override
        public void valueChange(ValueChangeEvent event) {
            Formation formation = (Formation)event.getProperty().getValue();
            if(offense) {
                tblPlaysOff.setContent(team.getPlaybook().getPlays(formation));
                team.setFormationOffTraining(formation);
            } else {
                tblPlaysDef.setContent(team.getPlaybook().getPlays(formation));
                team.setFormationDefTraining(formation);
            }
            log.debug("formation training set to " + (formation == null ? "null" : formation.getName()));
        }
    }

    /**
     * Sets the selected play at the team training.
     */
    private class PlayChangeListener implements ValueChangeListener {

        private boolean offense;

        public PlayChangeListener(boolean offense) {
            this.offense = offense;
        }

        @Override
        public void valueChange(ValueChangeEvent event) {
            Play play = (Play)event.getProperty().getValue();
            if(offense) {
                team.setPlayOffTraining(play);
            } else {
                team.setPlayDefTraining(play);
            }
            log.debug("play training set to " + (play == null ? "null" : play.getName()));
        }
    }
}
