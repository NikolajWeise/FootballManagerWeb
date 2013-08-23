package de.weise.fm.web.pages;

import java.util.Collection;

import org.apache.log4j.Logger;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import de.weise.fm.web.components.EditableReadonlyTextField;
import de.weise.fm.web.components.layout.FormationField;
import de.weise.fm.web.components.table.FormationTable;
import de.weise.fm.web.model.Users;
import de.weise.fm.web.model.play.Formation;
import de.weise.fm.web.model.play.OffenseFormation;
import de.weise.fm.web.model.play.Playbook;
import de.weise.fm.web.model.play.Position;

public class FormationOffPage extends AbstractPage {

    private static final Logger log = Logger.getLogger(FormationOffPage.class);

    protected final int HEIGHT = 300;
    protected final int WIDTH = 530;

    private FormationField formationField;
    private FormationTable tblFormations;
    private TextField txtFormationName;

    protected Playbook playbook;
    private Formation currentFormation;

    @Override
    public void enter() {
        playbook = Users.getUserFromSession(getSession()).getTeam().getPlaybook();

        HorizontalLayout mainLayout = new HorizontalLayout();
        mainLayout.setSpacing(true);
        setCompositionRoot(mainLayout);

        VerticalLayout layout = new VerticalLayout();
        mainLayout.addComponent(layout);

        txtFormationName = new EditableReadonlyTextField();
        Button btnEditName = new Button(bundle.getString("editName"), new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                txtFormationName.setReadOnly(false);
            }
        });
        layout.addComponent(new HorizontalLayout(txtFormationName, btnEditName));

        formationField = createFormationField();
        layout.addComponent(formationField);

        // formation overview
        mainLayout.addComponent(createFormationOverview());
    }

    protected FormationField createFormationField() {
        FormationField formationField = new FormationField(HEIGHT, WIDTH);
        formationField.setHeightOffset(10);
        formationField.setSelectablePositions(Position.getOffSbstPositions());
        return formationField;
    }

    /**
     * Gets the formations which are shown in the formation table.
     */
    protected Collection<Formation> getFormations() {
        return playbook.getOffenseFormations();
    }

    private VerticalLayout createFormationOverview() {
        VerticalLayout layout = new VerticalLayout();

        tblFormations = new FormationTable(bundle.getString("formations"));
        tblFormations.addValueChangeListener(new FormationChangeListener());
        tblFormations.setContent(getFormations());
        tblFormations.select(tblFormations.firstItemId());
        layout.addComponent(tblFormations);

        Button btnNew = new Button(bundle.getString("new"), new NewFormationListener());
        layout.addComponent(btnNew);
        Button btnSave = new Button(bundle.getString("save"), new SaveFormationListener());
        layout.addComponent(btnSave);
        Button btnDelete = new Button(bundle.getString("delete"), new DeleteFormationListener());
        layout.addComponent(btnDelete);

        btnNew.setWidth("100px");
        btnSave.setWidth("100px");
        btnDelete.setWidth("100px");

        return layout;
    }

    private void showFormation(Formation formation) {
        this.currentFormation = formation.clone();
        txtFormationName.setValue(currentFormation.getName());

        formationField.showFormation(currentFormation);
    }

    private class FormationChangeListener implements ValueChangeListener {
        @Override
        public void valueChange(ValueChangeEvent event) {
            if(event.getProperty().getValue() != null) { // temporarily null when removing an item
                Formation formation = (Formation)event.getProperty().getValue();
                showFormation(formation);
            }
        }
    }

    private class NewFormationListener implements Button.ClickListener {
        @Override
        public void buttonClick(Button.ClickEvent event) {
            Formation newFormation = createNewFormation();
            playbook.addFormation(newFormation);
            tblFormations.addItem(newFormation);
            tblFormations.select(newFormation);
            txtFormationName.setReadOnly(false);
            log.debug("add new formation " + newFormation.getName());
        }
    }

    protected Formation createNewFormation() {
        return new OffenseFormation(bundle.getString("new"));
    }

    private class SaveFormationListener implements Button.ClickListener {
        @Override
        public void buttonClick(Button.ClickEvent event) {
            String formationName = txtFormationName.getValue();
            if(formationName == null || formationName.isEmpty() || formationName.equals(bundle.getString("new"))) {
                Notification.show(bundle.getString("formationNotSavedCpt"),
                        bundle.getString("formationNotSavedName"), Type.WARNING_MESSAGE);
                return;
            }

            if(currentFormation != null) {
                if(currentFormation.validate()) {
                    playbook.saveFormation(currentFormation);
                    playbook.changeFormationName(currentFormation, formationName);

                    Notification.show("", bundle.getString("formationSaved"), Type.TRAY_NOTIFICATION);
                    log.debug("saved formation " + currentFormation.getName());
                    tblFormations.setContent(getFormations());
                    tblFormations.select(currentFormation);
                } else {
                    Notification.show(bundle.getString("formationNotSavedCpt"),
                            bundle.getString("formationNotSaved"), Type.WARNING_MESSAGE);
                }
            }
        }
    }

    private class DeleteFormationListener implements Button.ClickListener {
        @Override
        public void buttonClick(Button.ClickEvent event) {
            if(tblFormations.getContainerDataSource().size() > 1) {
                playbook.removeFormation(currentFormation);
                log.debug("removed formation " + currentFormation.getName());
                tblFormations.removeItem(currentFormation);
                tblFormations.select(tblFormations.firstItemId());
                Notification.show("", bundle.getString("formationDeleted"), Type.TRAY_NOTIFICATION);
            } else {
                Notification.show(bundle.getString("formationNotDeletedCpt"),
                        bundle.getString("formationNotDeleted"), Type.WARNING_MESSAGE);
            }
        }
    }
}
