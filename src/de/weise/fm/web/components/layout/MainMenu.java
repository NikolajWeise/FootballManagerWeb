package de.weise.fm.web.components.layout;

import java.util.HashMap;
import java.util.Map;

import com.vaadin.navigator.View;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;

import de.weise.fm.web.pages.FormationDefPage;
import de.weise.fm.web.pages.FormationOffPage;
import de.weise.fm.web.pages.HomePage;
import de.weise.fm.web.pages.LineupPage;
import de.weise.fm.web.pages.LogoutPage;
import de.weise.fm.web.pages.PlaybookDefPage;
import de.weise.fm.web.pages.PlaybookOffPage;
import de.weise.fm.web.pages.TrainingPlanPage;
import de.weise.fm.web.pages.TrainingSettingsPage;

public class MainMenu extends CustomComponent {

    private final Navigator navigator;
    private final MenuBar menu;
    private final NavigationCommand cmd = new NavigationCommand();

    private final Map<MenuItem, Class<? extends View>> tabs = new HashMap<>();

    public MainMenu(Navigator navigator) {
        this.navigator = navigator;

        menu = new MenuBar();
        menu.setWidth("100%");
        menu.setAutoOpen(true);

        createMenuItem("Home", HomePage.class);
        createMenuItem("Lineup", LineupPage.class);
        MenuItem itemFormation = createMenuItem("Formation");
        createMenuItem(itemFormation, "Offense", FormationOffPage.class);
        createMenuItem(itemFormation, "Defense", FormationDefPage.class);
        MenuItem itemPlaybook = createMenuItem("Playbook");
        createMenuItem(itemPlaybook, "Offense", PlaybookOffPage.class);
        createMenuItem(itemPlaybook, "Defense", PlaybookDefPage.class);
        MenuItem itemTraining = createMenuItem("Training");
        createMenuItem(itemTraining, "Plan", TrainingPlanPage.class);
        createMenuItem(itemTraining, "Settings", TrainingSettingsPage.class);
        createMenuItem("Logout", LogoutPage.class);

        setCompositionRoot(menu);
    }

    private MenuItem createMenuItem(String caption) {
        return menu.addItem(caption, null);
    }

    private MenuItem createMenuItem(String caption, Class<? extends View> view) {
        MenuItem menuItem = menu.addItem(caption, cmd);
        tabs.put(menuItem, view);
        return menuItem;
    }

    private MenuItem createMenuItem(MenuItem menuItem, String caption, Class<? extends View> view) {
        MenuItem subMenuItem = menuItem.addItem(caption, cmd);
        tabs.put(subMenuItem, view);
        return subMenuItem;
    }

    private class NavigationCommand implements Command {
        @Override
        public void menuSelected(MenuItem selectedItem) {
            navigator.navigateTo(tabs.get(selectedItem));
        }
    }
}
