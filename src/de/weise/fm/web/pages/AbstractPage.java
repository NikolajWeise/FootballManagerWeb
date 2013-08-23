package de.weise.fm.web.pages;

import java.util.ResourceBundle;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;

import de.weise.fm.web.bundle.Bundle;

/**
 * Loads the ResourceBundle dependent on the UI's current locale.
 *
 * <pre>
 * ResourceBundle.getBundle(Bundle.class.getName(), getLocale());
 * </pre>
 *
 * Implements {@link View} to navigate to AbstractPages.
 */
public abstract class AbstractPage extends CustomComponent
        implements View {

    protected ResourceBundle bundle;

    @Override
    public final void enter(ViewChangeEvent event) {
        reloadBundle();
        enter();
    }

    protected final void reloadBundle() {
        bundle = ResourceBundle.getBundle(Bundle.class.getName(), getLocale());
    }

    public abstract void enter();
}
