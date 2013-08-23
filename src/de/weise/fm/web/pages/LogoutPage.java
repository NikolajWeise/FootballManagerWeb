package de.weise.fm.web.pages;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class LogoutPage extends AbstractPage {

    @Override
    public void enter() {
        VerticalLayout layout = new VerticalLayout(new Label(bundle.getString("logged out")));
        setCompositionRoot(layout);

        VaadinSession.getCurrent().close();
    }
}
