package de.weise.fm.web.components.table;

import java.util.Collection;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Table;

public class SelectionTable<TYPE> extends Table {

    protected BeanItemContainer<TYPE> dataSource;

    public SelectionTable(Class<TYPE> clazz, String caption) {
        super(caption);
        setSelectable(true);
        setImmediate(true);

        dataSource = new BeanItemContainer<>(clazz);
        setContainerDataSource(dataSource);
    }

    /**
     * Removes all items from the table and adds the given ones to it.
     */
    public void setContent(Collection<TYPE> formations) {
        dataSource.removeAllItems();
        dataSource.addAll(formations);
    }
}
