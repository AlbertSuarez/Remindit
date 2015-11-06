package edu.upc.fib.molgo.suarez.albert.remindit;

import java.util.ArrayList;

/**
 * Calendar class.
 * @author albert.suarez.molgo
 */
public class Calendar {

    /**
     * A calendar has a lot of events.
     */
    private ArrayList<Event> events;




    /**
     * Create an empty calendar.
     */
    public Calendar() {
        events = new ArrayList<Event>();
    }

    /**
     * Get the events collection of the implicit calendar.
     * @return The events collection.
     */
    public ArrayList<Event> getEvents() {
        return events;
    }

    /**
     * Set implicit events collection for the event's param.
     * @param events The collection to set.
     */
    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }
}