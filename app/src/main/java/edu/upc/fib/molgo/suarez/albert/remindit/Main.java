package edu.upc.fib.molgo.suarez.albert.remindit;

import edu.upc.fib.molgo.suarez.albert.remindit.domain.*;
import edu.upc.fib.molgo.suarez.albert.remindit.utils.Utils;

/**
 * Created by Albert on 08/11/2015.
 */
public class Main {

    public static void main(String[] arg0) {
        Reminder reminder = new Reminder();
        Meeting m = new Meeting(10, 5, 2015, 10, 0, 11, 0, "Startup android");
        Task t1 = new Task(10, 5, 2015, 20, 5, 2015);
        Task t2 = new Task(10, 5, 2015, 15, 5, 2015);
        m.addTask(t1);
        m.addTask(t2);
        reminder.addEvent(m);
        reminder.addEvent(t1);
        reminder.addEvent(t2);

        //System.out.println(reminder.toString());

        System.out.print(reminder.printUndoneTasks());
        t2.setDone(true);
        System.out.println("I HAVE DONE THE SECOND TASK!\n");
        System.out.print(reminder.printUndoneTasks());

        String[] days = Utils.getDaysOfWeek();
        for (int i = 0; i  < days.length; i++) System.out.println(days[i]);

    }

}
