package de.weise.fm.web.components.table;

import java.util.List;
import java.util.ResourceBundle;

import com.vaadin.data.Property;
import com.vaadin.ui.Table;

import de.weise.fm.web.components.select.AttributesSelector;
import de.weise.fm.web.model.Attributes;
import de.weise.fm.web.model.Player;
import de.weise.fm.web.model.Team;
import de.weise.fm.web.model.play.Position;

/**
 * In addition to the {@link PlayerTable} shows a column for selecting individual training for each {@link Player}.
 *
 * @see PlayerTable
 */
public class PlayerTrainingTable extends PlayerTable {

    public PlayerTrainingTable(Team team) {
        super(team);

        dataSource.removeAllItems();
        dataSource.addAll(team.getPlayers());
        setPageLength(10);
        setSortEnabled(true);
        setDragMode(TableDragMode.NONE);
        setDropHandler(null);
        setColumnCollapsed("position", false); // initially collapsed by PlayerTable

        addGeneratedColumn("training", new TrainingColumnGenerator());
    }

    @Override
    protected List<String> createColumnHeaders(ResourceBundle bundle) {
        List<String> colHeaders = super.createColumnHeaders(bundle);
        colHeaders.add("training");
        return colHeaders;
    }

    /**
     * @deprecated we show all players, so we do not need this method
     */
    @Override
    @Deprecated
    public void setContent(Position position) {
        super.setContent(position);
    }

    /**
     * Because we do not want to make the entire table editable, we add a generated column with a selector and a
     * listener.
     */
    private class TrainingColumnGenerator implements ColumnGenerator {

        @Override
        public Object generateCell(Table source, Object itemId, Object columnId) {
            Player player = (Player)itemId;

            AttributesSelector attributesSelector = new AttributesSelector();
            attributesSelector.select(player.getIndividualTraining());
            attributesSelector.addValueChangeListener(new TrainingChangeListener(player));

            return attributesSelector;
        }
    }

    /**
     * Listener for changing value at attribute selector. Sets the individual training at the player.
     *
     * @see TrainingColumnGenerator
     */
    private class TrainingChangeListener implements Property.ValueChangeListener {

        private final Player player;

        public TrainingChangeListener(Player player) {
            this.player = player;
        }

        @Override
        public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
            if(event.getProperty().getValue() != null) {
                player.setIndividualTraining((Attributes.Type)event.getProperty().getValue());
            } else {
                player.setIndividualTraining(null);
            }
        }
    }
}
