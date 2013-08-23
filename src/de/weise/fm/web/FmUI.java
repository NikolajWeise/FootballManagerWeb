package de.weise.fm.web;

import java.util.Locale;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.weise.fm.web.components.layout.MainMenu;
import de.weise.fm.web.components.layout.Navigator;
import de.weise.fm.web.converter.FmConverterFactory;
import de.weise.fm.web.model.User;
import de.weise.fm.web.model.Users;
import de.weise.fm.web.pages.FormationDefPage;
import de.weise.fm.web.pages.FormationOffPage;
import de.weise.fm.web.pages.HomePage;
import de.weise.fm.web.pages.LineupPage;
import de.weise.fm.web.pages.LogoutPage;
import de.weise.fm.web.pages.PlaybookDefPage;
import de.weise.fm.web.pages.PlaybookOffPage;
import de.weise.fm.web.pages.TrainingPlanPage;
import de.weise.fm.web.pages.TrainingSettingsPage;

/**
 * Defines the layout for the application. During {@link #init(VaadinRequest)} creates a main menu and vertical layout
 * as page content. Every page will add its content to that layout. This is done by navigation from the main menu, which
 * holds the {@link Navigator}.
 *
 * @see Navigator
 */
@Theme("fm")
@Title("Football Manager")
public class FmUI extends UI {

    private User user;

    @Override
    protected void init(VaadinRequest request) {
        user = Users.getUserFromSession(getSession());
        initUI();
        initLayout();
    }

    /**
     * Initializes default locale and converter factories.
     */
    private void initUI() {
        Locale locale = Locale.ENGLISH;
        getSession().setLocale(locale);
        getSession().setConverterFactory(new FmConverterFactory());
    }

    /**
     * Initializes the application's layout.
     */
    private void initLayout() {
        VerticalLayout pageContent = new VerticalLayout();
        pageContent.setSizeFull();

        // navigator
        Navigator navigator = new Navigator(this, pageContent);
        navigator.addView(new HomePage());
        navigator.addView(new LineupPage());
        navigator.addView(new FormationOffPage());
        navigator.addView(new FormationDefPage());
        navigator.addView(new PlaybookOffPage());
        navigator.addView(new PlaybookDefPage());
        navigator.addView(new TrainingPlanPage());
        navigator.addView(new TrainingSettingsPage());
        navigator.addView(new LogoutPage());

        // whole page layout
        VerticalLayout clayout = new VerticalLayout();
        clayout.setSizeFull();
//        clayout.addStyleName(Reindeer.LAYOUT_WHITE);
        setContent(clayout);

        VerticalLayout layout = new VerticalLayout();
        clayout.addComponent(layout);
        clayout.setComponentAlignment(layout, Alignment.TOP_CENTER);

        layout.addComponent(createHeader());

        HelpImage imgHelp = new HelpImage(new ThemeResource("img/help/help.png"), pageContent, navigator);
        imgHelp.setHeight("100%");
        imgHelp.addStyleName("help");
        navigator.addViewChangeListener(imgHelp);

        MainMenu mainMenu = new MainMenu(navigator);
        HorizontalLayout menuLayout = new HorizontalLayout(mainMenu, imgHelp);
        menuLayout.setWidth("100%");
        menuLayout.setExpandRatio(mainMenu, 1f);
        layout.addComponent(menuLayout);

        layout.addComponent(new Label("<br/>", ContentMode.HTML));
        layout.addComponent(pageContent);
//        layout.addComponent(createFooter());

        layout.setWidth("975px");
        layout.setHeight("100%");
        layout.setExpandRatio(pageContent, 1f);
    }

    private Component createHeader() {
        HorizontalLayout header = new HorizontalLayout();
        header.setWidth("100%");
        header.addStyleName("header");

        ThemeResource resTitle = new ThemeResource("img/title.png");
        ThemeResource resLogo;
        if(user.getName().equals("User1")) {
            resLogo = new ThemeResource("img/logos/logo_team1.png");
        } else {
            resLogo = new ThemeResource("img/logos/logo_team2.png");
        }

        Image imgTitle = new Image(null, resTitle);
        Image imgLogo = new Image(null, resLogo);
        header.addComponents(imgTitle, imgLogo);
        header.setExpandRatio(imgTitle, 1f);
        header.setComponentAlignment(imgTitle, Alignment.MIDDLE_CENTER);
        return header;
    }

    private Component createFooter() {
        VerticalLayout footer = new VerticalLayout();
        footer.setWidth("100%");
        Label label = new Label("Nikolaj Weise, mail: nikolaj.weise@gmail.com");
        label.setSizeUndefined();
        footer.addComponent(label);
        footer.setComponentAlignment(label, Alignment.BOTTOM_CENTER);
        return footer;
    }

    /**
     * This image provides a click listener and a view change event. If it is clicked, it adds a style name to the page
     * content according to the current navigation state. E.g. the navigation state is "HomePage", the image will add
     * the style name "help-HomePage" to the page content.<br>
     * The view change event will simply remove the last added style name.
     */
    private class HelpImage extends Image implements ViewChangeListener, ClickListener {

        private String addedStyle = "";
        private final VerticalLayout pageContent;
        private Navigator navigator;

        /**
         * @param resource The image ressource.
         * @param pageContent The page content where to add the help style.
         * @param navigator The navigator to get the current navigation state.
         */
        public HelpImage(Resource resource, VerticalLayout pageContent, Navigator navigator) {
            super(null, resource);
            this.pageContent = pageContent;
            this.navigator = navigator;

            addClickListener(this);
        }

        /**
         * Adds a style name to the page content according to the current navigation state.
         */
        @Override
        public void click(ClickEvent event) {
            removeLastStyle();

            String state = navigator.getState();
            if(!addedStyle.equals(state)) {
                pageContent.addStyleName("help-" + state);
                addedStyle = state;
            } else {
                addedStyle = "";
            }
        }

        @Override
        public void afterViewChange(ViewChangeEvent event) {
            removeLastStyle();
        }

        /**
         * Removes the last added style name.
         */
        public void removeLastStyle() {
            pageContent.removeStyleName("help-" + addedStyle);
        }

        /**
         * @deprecated Must be overwritten but does nothing else than allowing the view change every time.
         */
        @Deprecated
        @Override
        public boolean beforeViewChange(ViewChangeEvent event) {
            return true;
        }
    }
}
