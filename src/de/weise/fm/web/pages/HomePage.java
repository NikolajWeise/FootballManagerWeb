package de.weise.fm.web.pages;

import java.util.Locale;

import org.apache.log4j.Logger;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import de.weise.fm.web.model.Users;

public class HomePage extends AbstractPage {

    private static final Logger log = Logger.getLogger(HomePage.class);

    @Override
    public void enter() {
        String welcomeMsg = bundle.getString("welcome");
        welcomeMsg = welcomeMsg.replace("{1}", Users.getUserFromSession(getSession()).getName());
        final Label lblWelcome = new Label(welcomeMsg, ContentMode.HTML);
        VerticalLayout layout = new VerticalLayout(lblWelcome);
        setCompositionRoot(layout);
        layout.addComponent(new Label("<br/>", ContentMode.HTML));

        final ComboBox comboBox = new ComboBox(bundle.getString("language"));
        layout.addComponent(comboBox);
        comboBox.addItem("english");
        comboBox.addItem("deutsch");
        comboBox.setTextInputAllowed(false);
        comboBox.setImmediate(true);
        comboBox.setNullSelectionAllowed(false);
//        comboBox.setEnabled(false);
//        layout.addComponent(new Label("Language selection is disabled because the german bundle contains bugs."));

        if(getLocale().equals(Locale.ENGLISH)) {
            comboBox.setValue("english");
        } else {
            comboBox.setValue("deutsch");
        }

        comboBox.addValueChangeListener(new ValueChangeListener() {

            @Override
            public void valueChange(ValueChangeEvent event) {
                Locale locale = Locale.ENGLISH;
                if(event.getProperty().getValue().equals("deutsch")) {
                    locale = Locale.GERMAN;
                }
                getUI().setLocale(locale);
                reloadBundle();
                log.debug("changed language to " + locale.getLanguage());

                String welcomeMsg = bundle.getString("welcome");
                welcomeMsg = welcomeMsg.replace("{1}", Users.getUserFromSession(getSession()).getName());
                lblWelcome.setValue(welcomeMsg);
                comboBox.setCaption(bundle.getString("language"));
            }
        });
    }
}
