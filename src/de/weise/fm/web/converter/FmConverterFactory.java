package de.weise.fm.web.converter;

import com.vaadin.data.util.converter.Converter;
import com.vaadin.data.util.converter.DefaultConverterFactory;

import de.weise.fm.web.model.play.ActionType;
import de.weise.fm.web.model.play.Play.PlayType;
import de.weise.fm.web.model.play.Position;

/**
 * Provides additional converters to the {@link DefaultConverterFactory}.
 *
 * @see PositionConverter
 * @see ActionTypeConverter
 * @see PlayTypeConverter
 */
public class FmConverterFactory extends DefaultConverterFactory {

    @SuppressWarnings("unchecked")
    @Override
    public <PRESENTATION, MODEL> Converter<PRESENTATION, MODEL> createConverter(
            Class<PRESENTATION> presentationType, Class<MODEL> modelType) {

        if(String.class.equals(presentationType) && Position.class.equals(modelType)) {
            return (Converter<PRESENTATION, MODEL>)new PositionConverter();
        }
        if(String.class.equals(presentationType) && ActionType.class.equals(modelType)) {
            return (Converter<PRESENTATION, MODEL>)new ActionTypeConverter();
        }
        if(String.class.equals(presentationType) && PlayType.class.equals(modelType)) {
            return (Converter<PRESENTATION, MODEL>)new PlayTypeConverter();
        }

        return super.createConverter(presentationType, modelType);
    }
}
