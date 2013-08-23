package de.weise.fm.web.pages;

import java.util.Collection;

import org.apache.log4j.Logger;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.VerticalLayout;

import de.weise.fm.web.components.EditableTextField;
import de.weise.fm.web.components.layout.PlayFormationField;
import de.weise.fm.web.components.table.FormationTable;
import de.weise.fm.web.components.table.PlayTable;
import de.weise.fm.web.model.Users;
import de.weise.fm.web.model.play.Formation;
import de.weise.fm.web.model.play.Play;
import de.weise.fm.web.model.play.Play.PlayType;
import de.weise.fm.web.model.play.Playbook;

public class PlaybookOffPage extends AbstractPage {

    private static final Logger log = Logger.getLogger(PlaybookOffPage.class);

    protected final int HEIGHT = 500;
    protected final int WIDTH = 530;
    private final int HEIGHT_OFFSET = 300;

    protected Playbook playbook;
    protected Formation currentFormation;
    protected Play currentPlay;

    private EditableTextField txtPlayName;
    private PlayFormationField playFormationField;
    private PlayTable tblPlays;

    private Button btnSave;
    private Button btnDelete;

    @Override
    public void enter() {
        playbook = Users.getUserFromSession(getSession()).getTeam().getPlaybook();

        HorizontalLayout layout = new HorizontalLayout();
        layout.setSpacing(true);
        setCompositionRoot(layout);

        // field
        layout.addComponent(createField());

        // formation overview
        layout.addComponent(createFormationOverview());

        enableButtons(false);
    }

    private Layout createField() {
        VerticalLayout layout = new VerticalLayout();

        txtPlayName = new EditableTextField(bundle.getString("editName"));
        Button btnHidePlay = new Button(bundle.getString("hideRoutes"), new HidePlayClick());
        layout.addComponent(new HorizontalLayout(txtPlayName, btnHidePlay));

        playFormationField = createPlayFormationField();
        layout.addComponent(playFormationField);

        return layout;
    }

    protected PlayFormationField createPlayFormationField() {
        PlayFormationField playFormationField = new PlayFormationField(HEIGHT, WIDTH);
        playFormationField.setHeightOffset(HEIGHT_OFFSET);
        return playFormationField;
    }

    private Layout createFormationOverview() {
        VerticalLayout overviewLayout = new VerticalLayout();
        HorizontalLayout tblLayout = new HorizontalLayout();
        overviewLayout.addComponent(tblLayout);

        // tables
        tblPlays = new PlayTable(bundle.getString("plays"));
        tblPlays.addValueChangeListener(new PlayChangeListener());

        FormationTable tblFormations = new FormationTable(bundle.getString("formations"));
        tblFormations.addValueChangeListener(new FormationChangeListener());
        tblFormations.setContent(getFormations());

        tblLayout.addComponent(tblFormations);
        tblLayout.addComponent(tblPlays);

        // buttons
        Button btnNew = new Button(bundle.getString("new"), new NewPlayListener());
        btnSave = new Button(bundle.getString("save"), new SavePlayListener());
        btnDelete = new Button(bundle.getString("delete"), new DeletePlayListener());

        btnNew.setWidth("100px");
        btnSave.setWidth("100px");
        btnDelete.setWidth("100px");

        overviewLayout.addComponents(btnNew, btnSave, btnDelete);

        return overviewLayout;
    }

    /**
     * Gets the formations which are shown in the formation table.
     */
    protected Collection<Formation> getFormations() {
        return playbook.getOffenseFormations();
    }

    /**
     * Enables/disables some components dependent on the given play (<tt>null</tt> or not <tt>null</tt>) and shows the
     * play at the <tt>PlayFormationField</tt>.
     *
     * @param play The play to show or <tt>null</tt> if no play shall be shown.
     * @see PlayFormationField#showPlay(Play)
     */
    private void showPlay(Play play) {
        playFormationField.setPlayVisible(play != null);
        enableButtons(play != null);

        if(play == null) { // none play selected
            currentPlay = null;
            txtPlayName.setValue("");
        } else { // play selected
            currentPlay = play.clone();
            txtPlayName.setValue(currentPlay.getName());

            playFormationField.showPlay(currentPlay);
        }
    }

    private void enableButtons(boolean enable) {
        playFormationField.setEnabled(enable);
        txtPlayName.setEnabled(enable);
        btnSave.setEnabled(enable);
        btnDelete.setEnabled(enable);
    }

    /**
     * Refreshes the table of plays depending on the selected formation and shows the formation in the field.
     */
    private class FormationChangeListener implements ValueChangeListener {
        @Override
        public void valueChange(ValueChangeEvent event) {
            currentFormation = (Formation)event.getProperty().getValue();
            tblPlays.setContent(playbook.getPlays(currentFormation));
            tblPlays.select(null);

            playFormationField.showFormation(currentFormation);
            playFormationField.setPlayVisible(false);
        }
    }

    /**
     * Shows the selected play.
     */
    private class PlayChangeListener implements ValueChangeListener {
        @Override
        public void valueChange(ValueChangeEvent event) {
            Play play = (Play)event.getProperty().getValue();
            showPlay(play);
        }
    }

    /**
     * Creates a new play, adds it to the playbook and selects it in the table.
     */
    private class NewPlayListener implements Button.ClickListener {
        @Override
        public void buttonClick(Button.ClickEvent event) {
            Play newPlay = createNewPlay();
            playbook.addPlay(newPlay);
            tblPlays.addItem(newPlay);
            tblPlays.select(newPlay);
            txtPlayName.setReadOnly(false);
            log.debug("add new play " + newPlay.getName());
        }
    }

    protected Play createNewPlay() {
        return new Play(bundle.getString("new"), currentFormation, PlayType.PASS);
    }

    /**
     * Saves the selected play and refreshes the table.
     */
    private class SavePlayListener implements Button.ClickListener {
        @Override
        public void buttonClick(Button.ClickEvent event) {
            if(currentPlay != null) {
                // validate name
                String playName = txtPlayName.getValue();
                if(playName == null || playName.isEmpty() || playName.equals(bundle.getString("new"))) {
                    Notification.show(bundle.getString("playNotSavedCpt"),
                            bundle.getString("formationNotSavedName"), Type.WARNING_MESSAGE);
                    return;
                }

                if(currentPlay.validate()) { // validate play
                    // changing the name will save the play too
                    if(!playbook.changePlayName(currentPlay, playName)) {
                        playbook.savePlay(currentPlay);
                    }
                    Notification.show("", bundle.getString("playSaved"), Type.TRAY_NOTIFICATION);
                    log.debug("saved play " + currentPlay.getName());

                    // savePlay() removes and adds a play, so we need to refresh the list
                    tblPlays.setContent(playbook.getPlays(currentPlay.getFormation()));
                    tblPlays.select(currentPlay);
                } else {
                    Notification.show(bundle.getString("playNotSavedCpt"),
                            bundle.getString("playNotSaved"), Type.WARNING_MESSAGE);
                }
            }
        }
    }

    /**
     * Removes the selected play from the playbook, from the table and deselects the items in the table.
     */
    private class DeletePlayListener implements Button.ClickListener {
        @Override
        public void buttonClick(Button.ClickEvent event) {
            // TODO need to check that on removing formations
            if(playbook.getPlays().values().size() > 1
                    || playbook.getPlays().values().iterator().next().size() > 1) {
                log.debug("removed play " + currentPlay.getName());
                playbook.removePlay(currentPlay);
                tblPlays.removeItem(currentPlay);
                Notification.show("", bundle.getString("playDeleted"), Type.TRAY_NOTIFICATION);
                tblPlays.select(null);
            } else {
                Notification.show(bundle.getString("playNotDeletedCpt"),
                        bundle.getString("playNotDeleted"), Type.WARNING_MESSAGE);
            }
        }
    }

    /**
     * Sets the visibility of the play (routes). The field's visibility is not influenced by this.
     */
    private class HidePlayClick implements Button.ClickListener {
        @Override
        public void buttonClick(ClickEvent event) {
            playFormationField.setPlayVisible(!playFormationField.isPlayVisible());
        }
    }
}
