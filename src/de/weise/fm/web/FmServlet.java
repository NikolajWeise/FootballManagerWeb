package de.weise.fm.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import org.apache.log4j.Logger;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.ServiceException;
import com.vaadin.server.SessionDestroyEvent;
import com.vaadin.server.SessionDestroyListener;
import com.vaadin.server.SessionInitEvent;
import com.vaadin.server.SessionInitListener;
import com.vaadin.server.VaadinServlet;

import de.weise.fm.web.model.User;
import de.weise.fm.web.model.Users;

/**
 * This servlet provides methods for initializing and destroying user sessions.
 */
@WebServlet(value = "/*", asyncSupported = true)
@VaadinServletConfiguration(productionMode = false, ui = FmUI.class, closeIdleSessions = true)
public class FmServlet extends VaadinServlet
        implements SessionInitListener, SessionDestroyListener {

    private static final Logger log = Logger.getLogger(FmServlet.class);

    /**
     * Initializes game data.
     */
    @Override
    protected void servletInitialized()
            throws ServletException {
        getService().addSessionInitListener(this);
        getService().addSessionDestroyListener(this);

        Users.initDefault();
        log.info("game initialized");
    }

    /**
     * Gets a user and sets it to the user session.<br>
     * If the parameter <tt>?user=<i>username</i></tt> is set, this method tries to set this specified user to the
     * session - if this user exists.
     */
    @Override
    public void sessionInit(SessionInitEvent event)
            throws ServiceException {
        String userParam = event.getRequest().getParameter("user");
        User user = Users.getUser(userParam, event.getSession());

        log.info("session initialized with user " + user.getName());
    }

    /**
     * Gets the user from the session which is destroyed and mark it as free.
     */
    @Override
    public void sessionDestroy(SessionDestroyEvent event) {
        User user = Users.getUserFromSession(event.getSession());

        Users.freeUserFromSession(event.getSession());

        log.info("session destroyed with user " + user.getName());
    }
}
