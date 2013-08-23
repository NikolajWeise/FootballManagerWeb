package de.weise.fm.web.converter;

import java.util.Locale;

import com.vaadin.data.util.converter.Converter;

import de.weise.fm.web.model.play.Position;

/**
 * Converts a {@link Position} to a String.
 */
public class PositionConverter
        implements Converter<String, Position> {

    @Override
    public Position convertToModel(String value, Class<? extends Position> targetType, Locale locale)
            throws ConversionException {
        return Position.get(value);
    }

    @Override
    public String convertToPresentation(Position value, Class<? extends String> targetType, Locale locale)
            throws ConversionException {
        return value.name();
    }

    @Override
    public Class<Position> getModelType() {
        return Position.class;
    }

    @Override
    public Class<String> getPresentationType() {
        return String.class;
    }
}
