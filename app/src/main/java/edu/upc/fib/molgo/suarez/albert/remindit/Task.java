package edu.upc.fib.molgo.suarez.albert.remindit;

import java.util.Date;

/**
 * Task class (extends Event)
 * @author albert.suarez.molgo
 */
public class Task extends Event {

    private Date dateStart;
    private Date dateEnd;
    private boolean done;
    private Meeting meetingAssociated;

    public Task(Date ds, Date de) {
        super.eventType = EventType.EVENT_TASK;
        this.dateStart = ds;
        this.dateEnd = de;
        this.done = false;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Meeting getMeetingAssociated() {
        return meetingAssociated;
    }

    public void setMeetingAssociated(Meeting meetingAssociated) {
        this.meetingAssociated = meetingAssociated;
    }
}
