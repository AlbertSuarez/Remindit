package edu.upc.fib.molgo.suarez.albert.remindit;

import java.util.Date;

/**
 * Task class (extends Event)
 * @author albert.suarez.molgo
 */
public class Task extends Event {

    /**
     * A Task has a start date.
     */
    private Date dateStart;

    /**
     * A Task has a end date.
     */
    private Date dateEnd;

    /**
     * A Task can be done or not.
     */
    private boolean done;

    /**
     * A Task can be associated to a meeting.
     */
    private Meeting meetingAssociated;




    /**
     * Create a Task.
     * @param ds The start date.
     * @param de The end date.
     */
    public Task(Date ds, Date de) {
        super.eventType = EventType.EVENT_TASK;
        this.dateStart = ds;
        this.dateEnd = de;
        this.done = false;
    }

    /**
     * Get the start date of implicit Task.
     * @return The start date.
     */
    public Date getDateStart() {
        return dateStart;
    }

    /**
     * Set the start date implicit to start date's param.
     * @param dateStart The start date to set.
     */
    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    /**
     * Get the end date of implicit Task.
     * @return The end date.
     */
    public Date getDateEnd() {
        return dateEnd;
    }

    /**
     * Set the end date implicit to end date's param.
     * @param dateEnd The end date to set.
     */
    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    /**
     * Indicates if the Task is done or not.
     * @return If it's true, the task is done. Otherwise, it isn't done.
     */
    public boolean isDone() {
        return done;
    }

    /**
     * Set the done attribute implicit to param.
     * @param done The done to set.
     */
    public void setDone(boolean done) {
        this.done = done;
    }

    /**
     * Get the meeting associated of implicit Task.
     * @return The meeting associated.
     */
    public Meeting getMeetingAssociated() {
        return meetingAssociated;
    }

    /**
     * Set the meeting associated implicit to param.
     * @param meetingAssociated The meeting associated to set.
     */
    public void setMeetingAssociated(Meeting meetingAssociated) {
        this.meetingAssociated = meetingAssociated;
    }

    @Override
    public String toString() {
        String result = "";
        result += this.dateStart.toString() + "\n";
        result += this.dateEnd.toString() + "\n";
        if (done) result += "DONE" + "\n";
        else result += "NOT DONE" + "\n";
        return result;
    }
}
