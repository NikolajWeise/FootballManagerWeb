package de.weise.fm.web.components.select;

import java.util.ResourceBundle;

import com.vaadin.ui.NativeSelect;

import de.weise.fm.web.bundle.Bundle;
import de.weise.fm.web.model.Attributes;

public class AttributesSelector extends NativeSelect {

    public AttributesSelector() {
        super();

        setImmediate(true);
        setNullSelectionAllowed(true);
        setNullSelectionItemId("-");

        for(Attributes.Type type : Attributes.Type.values()) {
            if(!type.equals(Attributes.Type.CONDITION)) {
                addItem(type);
            }
        }

        addAttachListener(new AttachListener() {
            @Override
            public void attach(AttachEvent event) {
                ResourceBundle bundle = ResourceBundle.getBundle(Bundle.class.getName(), getLocale());
                for(Attributes.Type type : Attributes.Type.values()) {
                    if(!type.equals(Attributes.Type.CONDITION)) {
                        setItemCaption(type, bundle.getString(type.name()));
                    }
                }
            }
        });
    }
}
