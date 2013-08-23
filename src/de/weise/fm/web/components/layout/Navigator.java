package de.weise.fm.web.components.layout;

import com.vaadin.navigator.View;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.UI;

/**
 * Simple extension to the {@link com.vaadin.navigator.Navigator Vaadin Navigator}. Provides methods to navigate with
 * classes instead of strings representing a navigation state.
 * <p>
 * This Navigator will cause problems when adding more than one view of the same class!
 *
 * @see com.vaadin.navigator.Navigator
 */
public class Navigator extends com.vaadin.navigator.Navigator {

    private Class<? extends View> startView = null;

    /**
     * @see com.vaadin.navigator.Navigator#Navigator(UI, ComponentContainer)
     */
    public Navigator(UI ui, ComponentContainer container) {
        super(ui, container);
    }

    /**
     * Adds the given view to the navigator. The name to navigate to is the simple class name of the given view.
     *
     * @param view The view to add.
     * @see com.vaadin.navigator.Navigator#addView(String, View)
     */
    public void addView(View view) {
        if(startView == null) {
            startView = view.getClass();
            super.addView("", view);
        } else {
            String viewName = view.getClass().getSimpleName();
            super.addView(viewName, view);
        }
    }

    /**
     * Navigates to the instance of the class of the given view. That instance must be set before by calling
     * {@link #addView(View)}.
     *
     * @param view The view to navigate to.
     * @see com.vaadin.navigator.Navigator#navigateTo(String)
     */
    public void navigateTo(Class<? extends View> view) {
        if(view == null) {
            throw new IllegalArgumentException("paramter view must not be null");
        }

        if(view.equals(startView)) {
            super.navigateTo("");
        } else {
            super.navigateTo(view.getSimpleName());
        }
    }
}
