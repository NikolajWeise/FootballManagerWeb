package de.weise.fm.web.pages;

import org.apache.log4j.Logger;

import com.vaadin.ui.VerticalLayout;

import de.weise.fm.web.components.TeamCalendar;

public class TrainingPlanPage extends AbstractPage {

    private static final Logger log = Logger.getLogger(TrainingPlanPage.class);

    @Override
    public void enter() {
        VerticalLayout layout = new VerticalLayout();
        setCompositionRoot(layout);

        layout.addComponent(new TeamCalendar());
    }
}
