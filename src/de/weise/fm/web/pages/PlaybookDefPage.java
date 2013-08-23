package de.weise.fm.web.pages;

import java.util.Collection;

import de.weise.fm.web.components.layout.PlayFormationField;
import de.weise.fm.web.model.play.DefaultPlays;
import de.weise.fm.web.model.play.Formation;
import de.weise.fm.web.model.play.Play;

public class PlaybookDefPage extends PlaybookOffPage {

    private final int HEIGHT_OFFSET = 30;

    @Override
    protected PlayFormationField createPlayFormationField() {
        PlayFormationField playFormationField = new PlayFormationField(HEIGHT, WIDTH) {
            @Override
            protected int convertLayoutPositionToHeight(float layoutHeight) {
                return Math.round((layoutHeight - offsetHeight) / 10f);
            }

            @Override
            public void setEnabled(boolean enabled) {
                super.setEnabled(enabled);
                optionPlayType.setVisible(false); // ALWAYS
            }
        };
        playFormationField.setHeightOffset(HEIGHT_OFFSET);
        return playFormationField;
    }

    @Override
    protected Collection<Formation> getFormations() {
        return playbook.getDefenseFormations();
    }

    @Override
    protected Play createNewPlay() {
        return new DefaultPlays.DefensePlay(bundle.getString("new"), currentFormation);
    }
}
