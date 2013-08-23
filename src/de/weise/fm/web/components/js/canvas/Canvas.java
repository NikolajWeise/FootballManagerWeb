package de.weise.fm.web.components.js.canvas;

import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.AbstractJavaScriptComponent;

import de.weise.fm.web.model.play.Play;

/**
 * Canvas
 */
@JavaScript({ "canvas.js", "canvas-connector.js" })
public class Canvas extends AbstractJavaScriptComponent {
    // XXX (low) The class name Canvas is too "small"
    // It is an html5-canvas but it's funtionality is more complex and its use is specific for Plays.
    // It's name could be sth like Play***Canvas...
    // Refactoring causes ugly js-refactoring, so this is a low-low-low-priority todo :P

    public Canvas(int height, int width) {
        super.setHeight(height, Unit.PIXELS);
        super.setWidth(width, Unit.PIXELS);
    }

    public void setPlay(Play play) {
        getState().setPlay(play);
    }

    public Play getPlay() {
        return getState().getPlay();
    }

    @Override
    protected CanvasState getState() {
        return (CanvasState)super.getState();
    }
}
