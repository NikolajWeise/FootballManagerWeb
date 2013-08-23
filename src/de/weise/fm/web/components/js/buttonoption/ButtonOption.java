package de.weise.fm.web.components.js.buttonoption;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import com.vaadin.annotations.JavaScript;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.data.util.converter.ConverterUtil;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.AbstractJavaScriptComponent;
import com.vaadin.ui.JavaScriptFunction;

@JavaScript({ "buttonoption.js", "buttonoption-connector.js" })
public class ButtonOption<V> extends AbstractJavaScriptComponent {

    private List<ValueChangeListener<V>> listeners = new ArrayList<>(1);

    /** the selected/active option/item */
    private V value = null;
    private Map<String, V> itemMapping = new HashMap<>();

    public ButtonOption() {
        addStyleName("btn-group");

        addFunction("onClick", new JavaScriptFunction() {

            @Override
            public void call(JSONArray arguments)
                    throws JSONException {
                value = itemMapping.get(arguments.getString(0));
                fireEvent();
            }
        });
        addFunction("markAsClean", new JavaScriptFunction() {

            @Override
            public void call(JSONArray arguments)
                    throws JSONException {
                getState().setDirty(false);
            }
        });
    }

    public void addValueChangeListener(ValueChangeListener<V> listener) {
        listeners.add(listener);
    }

    private void fireEvent() {
        for(ValueChangeListener<V> listener : listeners) {
            listener.valueChange(value);
        }
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if(visible) {
            getState().setDirty(true);
        }
    }

    @SuppressWarnings("unchecked")
    private String convertItem(V item) {
        Converter<String, V> converter = ConverterUtil.getConverter(
                String.class, (Class<V>)item.getClass(), null);

        String itemString;
        if(converter != null) {
            Locale locale = getLocale();
            if(locale == null) {
                locale = VaadinSession.getCurrent().getLocale();
            }
            itemString = converter.convertToPresentation(item, String.class, locale);
        } else {
            itemString = item.toString();
        }
        return itemString;
    }

    public void addItem(V item) {
        addItem(item, convertItem(item));
    }

    private void addItem(V item, String name) {
        itemMapping.put(name, item);
        getState().getItems().add(name);
        getState().setDirty(true);
    }

    public void addItems(V[] items) {
        for(V item : items) {
            addItem(item);
        }
    }

    public void addItems(Collection<V> items) {
        for(V item : items) {
            addItem(item);
        }
    }

    private String findItem(V item) {
        for(String optionString : itemMapping.keySet()) {
            V option = itemMapping.get(optionString);
            if(option.equals(item)) {
                return optionString;
            }
        }
        return null;
    }

    public void removeItem(V item) {
        String itemString = findItem(item);
        if(itemString != null) {
            itemMapping.remove(itemString);
            getState().getItems().remove(itemString);
            getState().setDirty(true);
        }
    }

    public void removeAllItems() {
        itemMapping.clear();
        getState().getItems().clear();
        getState().setDirty(true);
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;

        String itemString = findItem(value);
        if(itemString != null) {
            getState().setActiveItem(itemString);
        }
    }

    @Override
    protected ButtonOptionState getState() {
        return (ButtonOptionState)super.getState();
    }

    public interface ValueChangeListener<V>
            extends Serializable {

        void valueChange(V value);
    }
}
