package de.weise.fm.web.components.table;

import java.util.Collection;

import de.weise.fm.web.model.play.Formation;

/**
 * Table which shows the formations set in {@link SelectionTable#setContent(Collection)}.
 */
public class FormationTable extends SelectionTable<Formation> {

    public FormationTable(String caption) {
        super(Formation.class, caption);

        setNullSelectionAllowed(false);
        setWidth("200px");
        setHeight("233px");

        setVisibleColumns("name");
    }
}
