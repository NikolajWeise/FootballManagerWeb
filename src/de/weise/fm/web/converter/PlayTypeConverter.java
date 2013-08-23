package de.weise.fm.web.converter;

import java.util.Locale;
import java.util.ResourceBundle;

import com.vaadin.data.util.converter.Converter;

import de.weise.fm.web.bundle.Bundle;
import de.weise.fm.web.model.play.Play.PlayType;

/**
 * Converts a {@link PlayType} to a String.
 */
public class PlayTypeConverter
        implements Converter<String, PlayType> {

    @Override
    public PlayType convertToModel(String value, Class<? extends PlayType> targetType, Locale locale)
            throws ConversionException {
        return PlayType.get(value);
    }

    @Override
    public String convertToPresentation(PlayType value, Class<? extends String> targetType, Locale locale)
            throws ConversionException {
        ResourceBundle bundle = ResourceBundle.getBundle(Bundle.class.getName(), locale);
        return bundle.getString(value.name());
    }

    @Override
    public Class<PlayType> getModelType() {
        return PlayType.class;
    }

    @Override
    public Class<String> getPresentationType() {
        return String.class;
    }
}
