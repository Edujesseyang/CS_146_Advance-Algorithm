package Graph.Program3;

import org.junit.Test;
import org.junit.Assert.*;

import static org.junit.Assert.assertEquals;

public class Tests {

    @Test
    public void test1() {
        Schedule s1 = new Schedule();
        s1.insert(2);  // j0
        s1.insert(4);  // j1
        s1.insert(1);  // j2
        s1.insert(5);  // j3
        s1.insert(2);  // j4

        s1.get(4).requires(s1.get(3));  // j4 needs j3 done
        s1.get(3).requires(s1.get(2));  // j3 needs j2 done
        s1.get(2).requires(s1.get(1));  // j2 needs j1 done
        s1.get(1).requires(s1.get(0));  // j1 needs j0 done

        int finishTime = s1.finish();

        assertEquals(14, finishTime);

        int s3Needs = s1.get(3).start();
        assertEquals(7,s3Needs);

        Schedule.Job j1 = new Schedule().insert(3);

    }

    @Test
    public void test2(){
        Schedule schedule = new Schedule();
        schedule.insert(8); //adds job 0 with time 8
        Schedule.Job j1 = schedule.insert(3); //adds job 1 with time 3
        schedule.insert(5); //adds job 2 with time 5
        schedule.finish(); //should return 8, since job 0 takes time 8 to complete.
        /* Note it is not the earliest completion time of any job, but the earliest the entire set can complete. */
        schedule.get(0).requires(schedule.get(2)); //job 2 must precede job 0
        schedule.finish(); //should return 13 (job 0 cannot start until time 5)
        schedule.get(0).requires(j1); //job 1 must precede job 0
        schedule.finish(); //should return 13
        schedule.get(0).start(); //should return 5
        j1.start(); //should return 0
        schedule.get(2).start(); //should return 0
        j1.requires(schedule.get(2)); //job 2 must precede job 1
        schedule.finish(); //should return 16
        schedule.get(0).start(); //should return 8
        schedule.get(1).start(); //should return 5
        schedule.get(2).start(); //should return 0
        schedule.get(1).requires(schedule.get(0)); //job 0 must precede job 1 (creates loop)
        schedule.finish(); //should return -1
        schedule.get(0).start(); //should return -1
        schedule.get(1).start(); //should return -1
        schedule.get(2).start(); //should return 0 (no loops in prerequisites)



    }

}
