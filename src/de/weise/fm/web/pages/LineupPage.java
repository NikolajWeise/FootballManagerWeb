package de.weise.fm.web.pages;

import com.vaadin.ui.VerticalLayout;

import de.weise.fm.web.components.layout.LineupTab;
import de.weise.fm.web.components.layout.PlayerDetailWindow;
import de.weise.fm.web.components.table.PlayerTable;
import de.weise.fm.web.model.Team;
import de.weise.fm.web.model.Users;

/**
 * @see LineupTab
 * @see PlayerTable
 * @see PlayerDetailWindow
 */
public class LineupPage extends AbstractPage {

    @Override
    public void enter() {
        VerticalLayout layout = new VerticalLayout();
        setCompositionRoot(layout);

        final Team team = Users.getUserFromSession(getSession()).getTeam();

        LineupTab lineupTab = new LineupTab(team);
        layout.addComponent(lineupTab);
    }
}
