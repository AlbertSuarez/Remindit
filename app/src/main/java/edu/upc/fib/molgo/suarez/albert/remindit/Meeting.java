package edu.upc.fib.molgo.suarez.albert.remindit;

import java.util.ArrayList;
import java.util.Date;

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
         * A second is in the interval [0, 59].
         */
        private int second;

        /**
         * Create an hour with their params
         * @param h The hour param
         * @param m The minute param
         * @param s The second param
         */
        public Hour(int h, int m, int s) {
            hour = h; minute = m; second = s;
        }

        /**
         * Get the hour param of implicit Hour.
         * @return The hour.
         */
        public int getHour() {
            return hour;
        }

        /**
         * Get the minute param of implicit Hour.
         * @return The minute.
         */
        public int getMinute() {
            return minute;
        }

        /**
         * Get the second param of implicit Hour.
         * @return The second.
         */
        public int getSecond() {
            return second;
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
    private ArrayList<Task> tasks;


    /**
     * Create a Meeting.
     * @param dt The date to set.
     * @param desc The description to set.
     * @param hs The start hour to set.
     * @param he The end hour to set.
     */
    public Meeting(Date dt, String desc, Hour hs, Hour he) {
        super.eventType = EventType.EVENT_MEETING;
        this.date = dt;
        this.description = desc;
        this.hourStart = hs;
        this.hourEnd = he;
        this.tasks = new ArrayList<Task>();
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

    public Hour getHourStart() {
        return hourStart;
    }

    public void setHourStart(Hour hourStart) {
        this.hourStart = hourStart;
    }

    public Hour getHourEnd() {
        return hourEnd;
    }

    public void setHourEnd(Hour hourEnd) {
        this.hourEnd = hourEnd;
    }

    public void addTask(Task t) {
        this.tasks.add(t);
        t.setMeetingAssociated(this);
    }

    public void eraseTask(Task t) {
        tasks.remove(t);
        t.setMeetingAssociated(null);
    }
}
