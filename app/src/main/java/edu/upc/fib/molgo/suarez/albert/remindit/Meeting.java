package edu.upc.fib.molgo.suarez.albert.remindit;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Meeting class (extends Event)
 * @author albert.suarez.molgo
 */
public class Meeting extends Event {

    /**
     * Hour class (private)
     * @author albert.suarez.molgo
     */
    private class Hour {

        /**
         * An hour is in the interval [0, 23].
         */
        private int hour;

        /**
         * A minute is in the interval [0, 59].
         */
        private int minute;

        /**
         * Create an hour with their params
         * @param h The hour param
         * @param m The minute param
         */
        public Hour(int h, int m) {
            hour = h;
            minute = m;
        }

        /**
         * Get the hour param of implicit Hour.
         * @return The hour.
         */
        public int getHour() {
            return hour;
        }

        /**
         * Set the hour implicit to hour's param.
         * @param hour The hour to set.
         */
        public void setHour(int hour) {
            this.hour = hour;
        }

        /**
         * Get the minute param of implicit Hour.
         * @return The minute.
         */
        public int getMinute() {
            return minute;
        }

        /**
         * Set the minute implicit to minute's param.
         * @param minute The minute to set.
         */
        public void setMinute(int minute) {
            this.minute = minute;
        }

        /**
         * Get the String representation of an Hour.
         * @return The Hour representation.
         */
        public String toString() {
            return (this.hour + ":" + this.minute + "\n");
        }
    }

    /**
     * A meeting is celebrated in a date.
     */
    private Date date;

    /**
     * Every meeting has a description.
     */
    private String description;

    /**
     * Every meeting has a start hour.
     */
    private Hour hourStart;

    /**
     * Every meeting has a end hour.
     */
    private Hour hourEnd;

    /**
     * A meeting can have an assigned tasks.
     */
    private Set<Task> tasks;




    /**
     * Create a Meeting.
     * @param dt The date to set.
     * @param desc The description to set.
     */
    public Meeting(Date dt, String desc, int hourStart, int minuteStart, int hourEnd, int minuteEnd) {
        super.eventType = EventType.EVENT_MEETING;
        this.date = dt;
        this.description = desc;
        this.hourStart.setHour(hourStart); this.hourStart.setHour(minuteStart);
        this.hourEnd.setHour(hourEnd); this.hourEnd.setMinute(minuteEnd);
        this.tasks = new LinkedHashSet<>();
    }

    /**
     * Get the date of the implicit Meeting.
     * @return The date.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Set the date implicit of the date's param.
     * @param date The date to set.
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Get the description of the implicit Meeting.
     * @return The description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description implicit of the description's param.
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the start hour of the implicit Meeting.
     * @return The start hour.
     */
    public Hour getHourStart() {
        return hourStart;
    }

    /**
     * Set the start hour implicit of the start hour's param.
     * @param hour The hour to set.
     * @param minute The minute to set.
     */
    public void setHourStart(int hour, int minute) {
        this.hourStart.setHour(hour);
        this.hourStart.setMinute(minute);
    }

    /**
     * Get the end hour of the implicit Meeting.
     * @return The end hour.
     */
    public Hour getHourEnd() {
        return hourEnd;
    }

    /**
     * Set the end hour implicit of the start hour's param.
     * @param hour The hour to set.
     * @param minute The minute to set.
     */
    public void setHourEnd(int hour, int minute) {
        this.hourEnd.setHour(hour);
        this.hourEnd.setMinute(minute);
    }

    /**
     * Add a task to the collection of tasks implicit.
     * @param t The task to add.
     */
    public void addTask(Task t) {
        tasks.add(t);
        t.setMeetingAssociated(this);
    }

    /**
     * Erase a task to the collection of tasks implicit.
     * @param t The task to erase.
     */
    public void eraseTask(Task t) {
        tasks.remove(t);
        t.setMeetingAssociated(null);
    }

    /**
     * Get the String representation of an Event.
     * @return The String representation.
     */
    public String toString() {
        String result = "";
        result += this.date.toString() + "\n";
        result += this.getDescription() + "\n";
        result += hourStart.toString();
        result += hourEnd.toString();
        return result;
    }
}
