package de.weise.fm.web.components.layout;

import java.util.ResourceBundle;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Reindeer;

import de.weise.fm.web.model.Attributes;
import de.weise.fm.web.model.Player;
import de.weise.fm.web.model.play.Position;

/**
 * Modal popup window which shows {@link Player} details explicitly.
 */
public class PlayerDetailWindow extends Window {

    private static final float WIDTH = 300;

    private ResourceBundle bundle;

    public PlayerDetailWindow(final Player player, final ResourceBundle bundle) {
        super(player.toString());
        this.bundle = bundle;

        VerticalLayout layout = new VerticalLayout();
        setContent(layout);
        setModal(true);
        setClosable(true);
        setDraggable(false);
        setResizable(false);
        setWidth(WIDTH, Unit.PIXELS);
        addStyleName(Reindeer.WINDOW_BLACK);

        PlayerDetails attributes = new PlayerDetails(player);
        layout.addComponent(attributes);
        layout.setComponentAlignment(attributes, Alignment.MIDDLE_CENTER);
    }

    private class PlayerDetails extends GridLayout {

        private static final int ROWS = 5;

        public PlayerDetails(Player player) {
            super(2, ROWS);
            setWidth("90%");
            setSpacing(true);

            // row 0
            addComponent(createBundleLabel("name"));
            addComponent(new Label(player.getName()));
            // row 1
            addComponent(createBundleLabel("age"));
            addComponent(new Label(String.valueOf(player.getAge())));
            // row 2
            addComponent(createBundleLabel("position"));
            addComponent(new Label(player.getPosition().name()));
            // 2nd last row (use all columns)
            addComponent(new PlayerAttributes(player), 0, ROWS-2, 1, ROWS-2);
            // last row (use all columns)
//            addComponent(new PlayerStats(player), 0, ROWS-1, 1, ROWS-1);
        }
    }

    private class PlayerAttributes extends DefaultGrid {

        public PlayerAttributes(Player player) {
            super(bundle.getString("attributes"));

            for(Attributes.Type type : Attributes.Type.values()) {
                addComponent(createBundleLabel(type, player.getPosition()));
                int attValue = player.getAttributes().getValue(type);
                addComponent(new Label(String.valueOf(attValue)));
            }
        }
    }

    private class PlayerStats extends DefaultGrid {

        public PlayerStats(Player player) {
            super(bundle.getString("stats"));

            // TODO player stats
            addComponent(new Label("hallo"));
            addComponent(new Label("welt"));
            addComponent(new Label("yrd"));
            addComponent(new Label("53"));
        }
    }

    /*
    STATS

    receptions
    interceptions
    attempts
    yards
    avg
    TD
    fumble
    forced fumble
    fumble recoveries
    sacks
    tackles
    broken tackles

    */

    private class DefaultGrid extends GridLayout {

        public DefaultGrid(String caption) {
            super(2, 2); // rows increase automatically with attribute count
            setWidth("100%");
            addStyleName("border");

            Label lblCaption = new Label(caption);
            lblCaption.addStyleName("h4");
            addComponent(lblCaption, 0, 0, 1, 0);
        }
    }

    private Label createBundleLabel(String bundleString) {
        return new Label(bundle.getString(bundleString));
    }

    private Label createBundleLabel(Attributes.Type type, Position position) {
        Label label = createBundleLabel(type.name());
        if(type.isPrimeAttribute(position)) {
            label.addStyleName("yellow");
        }
        return label;
    }
}
