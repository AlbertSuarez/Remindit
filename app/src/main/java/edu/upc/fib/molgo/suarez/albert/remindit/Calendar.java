package edu.upc.fib.molgo.suarez.albert.remindit;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Calendar class.
 * @author albert.suarez.molgo
 */
public class Calendar {

    /**
     * A calendar has a lot of events.
     */
    private Set<Event> events;




    /**
     * Create an empty calendar.
     */
    public Calendar() {
        events = new LinkedHashSet<>();
    }

    /**
     * Get the events collection of the implicit calendar.
     * @return The events collection.
     */
    public Set<Event> getEvents() {
        return events;
    }

    /**
     * Set implicit events collection for the event's param.
     * @param events The collection to set.
     */
    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    /**
     * Add an event to implicit collection of events.
     * @param e The event to add.
     */
    public void addEvent(Event e) {
        events.add(e);
        e.setCalendar(this);
    }

    /**
     * Erase an event to implicit collection of events.
     * @param e The event to erase.
     */
    public void eraseEvent(Event e) {
        events.remove(e);
        e.setCalendar(null);
    }

    /**
     * Prints all calendar.
     * @return All calendar in a string.
     */
    public String print() {
        String s = "";
        for (Event e : events) {
            s = s + (e.toString()) + "\n";
        }
        return s;
    }

    /**
     * Get all of undone tasks of the calendar
     * @return The collection of undone tasks
     */
    public Set<Task> getUndoneTasks() {
        Set<Task> result = new LinkedHashSet<>();
        for (Event e : events) {
            if (!e.isMeeting()) {
                Task t = (Task) e;
                if (!t.isDone()) result.add(t);
            }
        }
        return result;
    }
}