package de.test;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.AbstractJavaScriptComponent;
import com.vaadin.ui.JavaScriptFunction;

@JavaScript({ "mylibrary.js", "mycomponent-connector.js" })
public class MyComponent extends AbstractJavaScriptComponent {

    public interface ValueChangeListener
            extends Serializable {

        void valueChange();
    }

    ArrayList<ValueChangeListener> listeners = new ArrayList<ValueChangeListener>();

    public MyComponent() {
        addFunction("onClick", new JavaScriptFunction() {
            @Override
            public void call(JSONArray arguments)
                    throws JSONException {
                getState().setValue(arguments.getString(0));
                for(ValueChangeListener listener : listeners) {
                    listener.valueChange();
                }
            }
        });
    }

    public void addListener(ValueChangeListener listener) {
        listeners.add(listener);
    }

    public void setValue(String value) {
        getState().setValue(value);
    }

    public String getValue() {
        return getState().getValue();
    }

    @Override
    protected MyComponentState getState() {
        return (MyComponentState)super.getState();
    }
}
