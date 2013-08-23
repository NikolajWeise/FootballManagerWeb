package de.weise.fm.web.pages;

import java.util.Collection;

import de.weise.fm.web.components.layout.FormationField;
import de.weise.fm.web.model.play.DefenseFormation;
import de.weise.fm.web.model.play.Formation;
import de.weise.fm.web.model.play.Position;

public class FormationDefPage extends FormationOffPage {

    @Override
    protected FormationField createFormationField() {
        FormationField formationField = new FormationField(HEIGHT, WIDTH) {
            @Override
            protected int convertLayoutPositionToHeight(float layoutHeight) {
                int h = Math.round((layoutHeight - offsetHeight) / 10f);
                return Math.max(1, h);
            }
        };
        formationField.setHeightOffset(0);

        formationField.setSelectablePositions(Position.getDefPositions());
        return formationField;
    }

    @Override
    protected Collection<Formation> getFormations() {
        return playbook.getDefenseFormations();
    }

    @Override
    protected Formation createNewFormation() {
        return new DefenseFormation(bundle.getString("new"));
    }
}
