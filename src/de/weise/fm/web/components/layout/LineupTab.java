package de.weise.fm.web.components.layout;

import java.util.HashMap;
import java.util.Map;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

import de.weise.fm.web.components.table.PlayerTable;
import de.weise.fm.web.model.Team;
import de.weise.fm.web.model.play.Position;

/**
 * TabSheet which holds the {@link PlayerTable} and sets its content according to the selected tab.
 */
public class LineupTab extends CustomComponent {

    private final Map<VerticalLayout, Position> tabs = new HashMap<>();
    private PlayerTable playerTable;

    public LineupTab(Team team) {
        TabSheet tabSheet = new TabSheet();
        setCompositionRoot(tabSheet);
        tabSheet.addStyleName(Reindeer.TABSHEET_SMALL);

        playerTable = new PlayerTable(team);

        for(Position position : Position.getOffPositions()) {
            addTab(tabSheet, position);
        }
        for(Position position : Position.getDefPositions()) {
            addTab(tabSheet, position);
        }
        for(Position position : Position.getSpcPositions()) {
            addTab(tabSheet, position);
        }

        // the default selected tab is 0, but the listener is added after setting the default selection internally
        // therefore we select tab 1, add a listener and go back to tab 0
        // this will select the first tab AND calling the listener, which paints the content dependent on the selection
        tabSheet.setSelectedTab(1);
        tabSheet.addSelectedTabChangeListener(new Listener());
        tabSheet.setSelectedTab(0);
    }

    private void addTab(TabSheet tabSheet, Position position) {
        VerticalLayout tab = new VerticalLayout();
        tab.setCaption(position.name());
        tabSheet.addTab(tab).setStyleName("lineup");
        tabs.put(tab, position);
    }

    private class Listener implements SelectedTabChangeListener {

        @Override
        public void selectedTabChange(SelectedTabChangeEvent event) {
            Layout selectedTab = (Layout)event.getTabSheet().getSelectedTab();
            selectedTab.addComponent(playerTable);

            playerTable.setContent(tabs.get(selectedTab));
        }
    }
}
