package de.weise.fm.web.converter;

import java.util.Locale;
import java.util.ResourceBundle;

import com.vaadin.data.util.converter.Converter;

import de.weise.fm.web.bundle.Bundle;
import de.weise.fm.web.model.play.ActionType;

/**
 * Converts an {@link ActionType} to a String.
 */
public class ActionTypeConverter
        implements Converter<String, ActionType> {

    @Override
    public ActionType convertToModel(String value, Class<? extends ActionType> targetType, Locale locale)
            throws ConversionException {
        return ActionType.get(value);
    }

    @Override
    public String convertToPresentation(ActionType value, Class<? extends String> targetType, Locale locale)
            throws ConversionException {
        ResourceBundle bundle = ResourceBundle.getBundle(Bundle.class.getName(), locale);
        return bundle.getString(value.name());
    }

    @Override
    public Class<ActionType> getModelType() {
        return ActionType.class;
    }

    @Override
    public Class<String> getPresentationType() {
        return String.class;
    }
}
