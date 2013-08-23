package de.weise.fm.web.components;

import java.util.Date;
import java.util.GregorianCalendar;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.DateClickEvent;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClick;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClickHandler;
import com.vaadin.ui.components.calendar.event.BasicEvent;
import com.vaadin.ui.components.calendar.handler.BasicDateClickHandler;

public class TeamCalendar extends CustomComponent {

    public TeamCalendar() {
        final Calendar calendar = new Calendar();
        setCompositionRoot(calendar);
        calendar.setWidth("100%");
        calendar.setHeight("400px");

        calendar.setFirstVisibleHourOfDay(8);
        calendar.setLastVisibleHourOfDay(22);

        GregorianCalendar startDate = new GregorianCalendar();
        startDate.setTime(new Date());
        startDate.set(GregorianCalendar.HOUR, 0);
        startDate.set(GregorianCalendar.MINUTE, 0);
        startDate.set(GregorianCalendar.SECOND, 0);
        calendar.setStartDate(startDate.getTime());

        GregorianCalendar endDate = new GregorianCalendar();
        endDate.setTime(startDate.getTime());
        endDate.add(GregorianCalendar.DAY_OF_MONTH, 6);
        calendar.setEndDate(endDate.getTime());

        calendar.setHandler(new BasicDateClickHandler() {
            @Override
            public void dateClick(DateClickEvent event) {
                // do nothing
            }
        });
        calendar.setHandler(new EventClickHandler() {
            @Override
            public void eventClick(EventClick event) {
                System.out.println(event.getCalendarEvent());
            }
        });
        calendar.addActionHandler(new Handler() {
            @Override
            public void handleAction(Action action, Object sender, Object target) {
                String b = "b";
                System.out.println(b);
            }
            @Override
            public Action[] getActions(Object target, Object sender) {
                return new Action[] { new Action("new"), new Action("old") };
            }
        });

        BeanItemContainer<BasicEvent> container = new BeanItemContainer<>(BasicEvent.class);
        BasicEvent event = new BasicEvent("The Event", "Single Event",
                new GregorianCalendar(2013, 7, 22, 12, 00).getTime(),
                new GregorianCalendar(2013, 7, 22, 14, 00).getTime());
        event.setStyleName("training");
        container.addBean(event);
        BasicEvent event2 = new BasicEvent("The Event", "Single Event",
                new GregorianCalendar(2013, 7, 22, 13, 00).getTime(),
                new GregorianCalendar(2013, 7, 22, 15, 00).getTime());
        event2.setStyleName("training");
        container.addBean(event2);

        container.sort(new Object[]{"start"}, new boolean[]{true});

        calendar.setContainerDataSource(container, "caption", "description", "start", "end", "styleName");

        addAttachListener(new AttachListener() {
            @Override
            public void attach(AttachEvent event) {
                calendar.setLocale(getLocale());
            }
        });
    }
}
