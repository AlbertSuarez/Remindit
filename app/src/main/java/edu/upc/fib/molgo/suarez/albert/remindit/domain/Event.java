package edu.upc.fib.molgo.suarez.albert.remindit.domain;

/**
 * Event class
 * @author albert.suarez.molgo
 */
public abstract class Event {

    public enum EventType {
        EVENT_MEETING,
        EVENT_TASK
    }

    protected EventType eventType;

    protected Reminder reminder;


    public Reminder getReminder() {
        return reminder;
    }

    public void setReminder(Reminder reminder) {
        this.reminder = reminder;
    }

    public boolean isMeeting() {
        if (eventType == EventType.EVENT_MEETING) return true;
        return false;
    }

    @Override
    public abstract String toString();
}
