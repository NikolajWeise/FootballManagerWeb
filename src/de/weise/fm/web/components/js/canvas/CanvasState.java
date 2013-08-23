package de.weise.fm.web.components.js.canvas;

import com.vaadin.shared.ui.JavaScriptComponentState;

import de.weise.fm.web.model.play.Play;

public class CanvasState extends JavaScriptComponentState {

    private Play play;

    public Play getPlay() {
        return play;
    }

    public void setPlay(Play play) {
        this.play = play;
    }
}
