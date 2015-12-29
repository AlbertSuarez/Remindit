package edu.upc.fib.molgo.suarez.albert.remindit.domain;

import java.util.Date;

import edu.upc.fib.molgo.suarez.albert.remindit.utils.Utils;

/**
 * Task class (extends Event)
 * @author albert.suarez.molgo
 */
public class Task extends Event {

    private Date dateStart;

    private Date dateEnd;

    private boolean done;

    private Meeting meetingAssociated;

    
    public Task(int dayStart, int monthStart, int yearStart, int dayEnd, int monthEnd, int yearEnd) {
        super();
        super.eventType = EventType.EVENT_TASK;
        this.dateStart = Utils.createDate(dayStart, monthStart, yearStart);
        this.dateEnd = Utils.createDate(dayEnd, monthEnd, yearEnd);
        this.done = false;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(int day, int month, int year) {
        this.dateStart = Utils.createDate(day, month, year);
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(int day, int month, int year) {
        this.dateEnd = Utils.createDate(day, month, year);
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

    @Override
    public String toString() {
        String result = "";
        result += "Start date: " + Utils.dateToString(dateStart);
        result += "End date: " + Utils.dateToString(dateEnd);
        if (done) result += "DONE" + "\n";
        else result += "NOT DONE" + "\n";
        if (meetingAssociated != null)
            result += "Associated with a meeting with \'" + meetingAssociated.getDescription() + "\' description \n";
        else
            result += "It is not associated with a meeting \n";
        return result;
    }
}
