package edu.upc.fib.molgo.suarez.albert.remindit;

import java.util.Date;

/**
 * Created by Albert on 08/11/2015.
 */
public class Main {

    public static void main(String[] arg0) {
        Calendar c = new Calendar();
        Meeting m = new Meeting(new Date(10,05,1995), "Reunion ER", 10, 00, 12, 00);
        System.out.println(m.toString());
    }

}
