package de.weise.fm.web.components.table;

import java.util.Collection;

import de.weise.fm.web.model.play.Play;

/**
 * Table which shows the plays set in {@link SelectionTable#setContent(Collection)}.
 */
public class PlayTable extends SelectionTable<Play> {

    public PlayTable(String caption) {
        super(Play.class, caption);

        setNullSelectionAllowed(true);
        setWidth("200px");
        setHeight("233px");

        setVisibleColumns("name");
    }
}
