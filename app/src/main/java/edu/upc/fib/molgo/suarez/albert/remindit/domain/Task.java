package edu.upc.fib.molgo.suarez.albert.remindit.domain;

import java.util.Date;

import edu.upc.fib.molgo.suarez.albert.remindit.utils.Utils;

/**
 * Task class (extends Event)
 * @author albert.suarez.molgo
 */
public class Task extends Event {

    private String title;

    private Date dateStart;

    private Date dateEnd;

    private boolean done;

    private String meetingAssociated;


    public Task() {}

    public Task(long id, String title, int dayStart, int monthStart, int yearStart, int dayEnd, int monthEnd, int yearEnd) {
        super();
        super.eventType = EventType.EVENT_TASK;
        this.id = id;
        this.title = title;
        this.dateStart = Utils.createDate(dayStart, monthStart, yearStart);
        this.dateEnd = Utils.createDate(dayEnd, monthEnd, yearEnd);
        this.done = false;
    }

    public Task(String title, int dayStart, int monthStart, int yearStart, int dayEnd, int monthEnd, int yearEnd) {
        super();
        super.eventType = EventType.EVENT_TASK;
        this.title = title;
        this.dateStart = Utils.createDate(dayStart, monthStart, yearStart);
        this.dateEnd = Utils.createDate(dayEnd, monthEnd, yearEnd);
        this.done = false;
    }

    public Task(String title, Date dateStart, Date dateEnd) {
        super();
        super.eventType = EventType.EVENT_TASK;
        this.title = title;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.done = false;
    }

    public Task(long id, String title, Date dateStart, Date dateEnd) {
        super();
        super.eventType = EventType.EVENT_TASK;
        this.id = id;
        this.title = title;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.done = false;
    }

    public Task(long id, String title, Date dateStart, Date dateEnd, boolean done, String meetingAssociated) {
        super();
        super.eventType = EventType.EVENT_TASK;
        this.id = id;
        this.title = title;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.done = done;
        this.meetingAssociated = meetingAssociated;
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

    public String getTitle() {
        return title;
    }

    public int getStartDay() {
        return Utils.getDay(dateStart);
    }

    public int getStartMonth() {
        return Utils.getMonth(dateStart);
    }

    public int getStartYear() {
        return Utils.getYear(dateStart);
    }

    public int getEndDay() {
        return Utils.getDay(dateEnd);
    }

    public int getEndMonth() {
        return Utils.getMonth(dateEnd);
    }

    public int getEndYear() {
        return Utils.getYear(dateEnd);
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getMeetingAssociated() {
        return meetingAssociated;
    }

    public void setMeetingAssociated(String meetingAssociated) {
        this.meetingAssociated = meetingAssociated;
    }

    @Override
    public String toString() {
        String result = "";
        result += "Title: " + this.title + "\n";
        result += "Start date: " + Utils.dateToString(dateStart);
        result += "End date: " + Utils.dateToString(dateEnd);
        if (done) result += "DONE" + "\n";
        else result += "NOT DONE" + "\n";
        if (meetingAssociated != null)
            result += "Associated with a meeting with \'" + meetingAssociated + "\' description \n";
        else
            result += "It is not associated with a meeting \n";
        return result;
    }
}
