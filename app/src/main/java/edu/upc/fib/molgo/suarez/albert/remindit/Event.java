package edu.upc.fib.molgo.suarez.albert.remindit;

/**
 * Event class
 * @author albert.suarez.molgo
 */
public abstract class Event {

    /**
     * Enumeration that describes the type of Event.
     * @author albert.suarez.molgo
     */
    public enum EventType {
        EVENT_MEETING,
        EVENT_TASK
    }

    /**
     * An event has two possible types.
     */
    protected EventType eventType;

    /**
     * Every event is associated to a calendar.
     */
    protected Calendar calendar;




    /**
     * Get the calendar of the implicit event.
     * @return The calendar.
     */
    public Calendar getCalendar() {
        return calendar;
    }

    /**
     * Set implicit calendar for the calendar's param.
     * @param calendar The calendar to set.
     */
    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    /**
     * Indicates if the implicit event is a Meeting or not.
     * @return If it's true, the implicit event is a Meeting. Otherwise it's a Task.
     */
    public boolean isMeeting() {
        if (eventType == EventType.EVENT_MEETING) return true;
        return false;
    }
}
