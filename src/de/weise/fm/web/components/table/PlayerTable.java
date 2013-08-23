package de.weise.fm.web.components.table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.event.dd.acceptcriteria.And;
import com.vaadin.event.dd.acceptcriteria.Not;
import com.vaadin.event.dd.acceptcriteria.SourceIsTarget;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.dd.VerticalDropLocation;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;

import de.weise.fm.web.bundle.Bundle;
import de.weise.fm.web.components.layout.PlayerDetailWindow;
import de.weise.fm.web.model.Attributes;
import de.weise.fm.web.model.Player;
import de.weise.fm.web.model.Team;
import de.weise.fm.web.model.play.Position;

/**
 * Table for showing {@link Player Players}.<br>
 * This table provides a drag&drop handler for reordering players and a <tt>ValueChangeListener</tt> for selecting
 * players to show the {@link PlayerDetailWindow}.
 * <p>
 * When extending this class, note to deactivate drag&drop if not needed:
 * <pre>
 * setDragMode(TableDragMode.NONE);
 * setDropHandler(null);
 * </pre>
 */
public class PlayerTable extends Table {

    protected BeanItemContainer<Player> dataSource;
    protected Team team;
    protected ResourceBundle bundle;
    private Position position;

    public PlayerTable(Team team) {
        super();
        this.team = team;

        setColumnCollapsingAllowed(true);
        setColumnReorderingAllowed(false);
        setSortEnabled(false);
        setSelectable(true);
        setImmediate(true);
        setWidth("100%");
        setPageLength(5);
        setDragMode(TableDragMode.ROW);
        setDropHandler(new PlayerDropHandler());
        addValueChangeListener(new PlayerSelectionListener());

        dataSource = new BeanItemContainer<>(Player.class);
        dataSource.addNestedContainerBean("attributes");

        List<Object> columns = new ArrayList<>();
        columns.addAll(Arrays.asList("name", "position", "number", "age", "overall"));

        setContainerDataSource(dataSource, columns);

        // add all attributes to the table
        for(Attributes.Type type : Attributes.Type.values()) {
            if(type.equals(Attributes.Type.CONDITION)) { // condition is a special column
                addGeneratedColumn(type, new ConditionColumnGenerator());
            } else {
                addGeneratedColumn(type, new AttributeColumnGenerator());
            }
            columns.add(type);
        }

        // set column order
        setVisibleColumns(columns.toArray());
        setColumnCollapsed("position", true);

        // set column headers
        addAttachListener(new AttachListener() {
            @Override
            public void attach(AttachEvent event) {
                bundle = ResourceBundle.getBundle(Bundle.class.getName(), getLocale());
                List<String> colHeaders = createColumnHeaders(bundle);
                setColumnHeaders(colHeaders.toArray(new String[colHeaders.size()]));
            }
        });
    }

    protected List<String> createColumnHeaders(ResourceBundle bundle) {
        List<String> colHeaders = new ArrayList<>();
        for(String s : bundle.getStringArray("playerColHeaders")) {
            colHeaders.add(s);
        }
        return colHeaders;
    }

    /**
     * Sets the table's content according to the specified position. In other words shows all players which have the
     * same position as the given one.
     */
    public void setContent(Position position) {
        this.position = position;
        dataSource.removeAllItems();
        dataSource.addAll(team.getPlayers(position));
    }

    private class AttributeColumnGenerator implements ColumnGenerator {

        @Override
        public Object generateCell(Table source, Object itemId, Object columnId) {
            Player player = (Player)itemId;
            return player.getAttributes().getValue((Attributes.Type)columnId);
        }
    }

    private class ConditionColumnGenerator implements ColumnGenerator {

        // TODO use nice percentage bar from bootstrap
        @Override
        public Object generateCell(Table source, Object itemId, Object columnId) {
            Player player = (Player)itemId;

            HorizontalLayout layout = new HorizontalLayout();
            layout.setSizeUndefined();

            Image imgGreen = new Image(null, new ThemeResource("img/barGreen.png"));
            Image imgRed = new Image(null, new ThemeResource("img/barRed.png"));
            int con = player.getAttributes().getValue(Attributes.Type.CONDITION);
            int width = Math.round(con / 100f * 50f); // 50px = max image width
            imgGreen.setWidth(width, Unit.PIXELS);
            imgRed.setWidth(50 - width, Unit.PIXELS);
            imgGreen.setHeight("15px");
            imgRed.setHeight("15px");

            layout.addComponents(imgGreen, imgRed);
            return layout;
        }
    }

    private class PlayerDropHandler implements DropHandler {

        @Override
        public AcceptCriterion getAcceptCriterion() {
            // only top and bottom allowed (and only items from the same table)
            return new And(SourceIsTarget.get(), new Not(VerticalLocationIs.MIDDLE));
        }

        @Override
        public void drop(DragAndDropEvent event) {
            AbstractSelectTargetDetails targetDetails = (AbstractSelectTargetDetails)event.getTargetDetails();
            Player targetPlayer = (Player)targetDetails.getItemIdOver();
            Player movedPlayer = (Player)((TableTransferable)event.getTransferable()).getItemId();

            if(VerticalDropLocation.TOP.equals(targetDetails.getDropLocation())) {
                team.lineupPlayer(movedPlayer, targetPlayer, false);
            } else if(VerticalDropLocation.BOTTOM.equals(targetDetails.getDropLocation())) {
                team.lineupPlayer(movedPlayer, targetPlayer, true);
            }

            setContent(position);
        }
    }

    private class PlayerSelectionListener implements ValueChangeListener {
        @Override
        public void valueChange(Property.ValueChangeEvent event) {
            if(event.getProperty().getValue() == null) {
                return;
            }

            Player player = (Player)event.getProperty().getValue();

            Window win = new PlayerDetailWindow(player, bundle);
            getUI().addWindow(win);
            win.addCloseListener(new CloseListener() {
                @Override
                public void windowClose(CloseEvent e) {
                    setValue(null); // deselect player at table
                }
            });
        }
    }
}
