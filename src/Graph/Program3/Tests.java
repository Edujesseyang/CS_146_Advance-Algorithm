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
        s1.get(4).requires(s1.get(0));

        int finishTime = s1.finish();
        assertEquals(14, finishTime);

        int s3Needs = s1.get(3).start();
        assertEquals(7, s3Needs);

        int s4Needs = s1.get(4).start();
        assertEquals(12, s4Needs);
    }

    @Test
    public void test2() {
        Schedule schedule = new Schedule();
        schedule.insert(8); //adds job 0 with time 8
        Schedule.Job j1 = schedule.insert(3); //adds job 1 with time 3
        schedule.insert(5); //adds job 2 with time 5
        assertEquals(8, schedule.finish()); //should return 8, since job 0 takes time 8 to complete.

        /* Note it is not the earliest completion time of any job, but the earliest the entire set can complete. */
        schedule.get(0).requires(schedule.get(2)); //job 2 must precede job 0
        assertEquals(13, schedule.finish()); //should return 13 (job 0 cannot start until time 5)
        schedule.get(0).requires(j1); //job 1 must precede job 0

        assertEquals(13, schedule.finish()); //should return 13
        assertEquals(5, schedule.get(0).start()); //should return 5
        assertEquals(0, j1.start()); //should return 0
        assertEquals(0, schedule.get(2).start()); //should return 0
        j1.requires(schedule.get(2)); //job 2 must precede job 1

        assertEquals(16, schedule.finish()); //should return 16
        assertEquals(8, schedule.get(0).start()); //should return 8
        assertEquals(5, schedule.get(1).start()); //should return 5
        assertEquals(0, schedule.get(2).start()); //should return 0
        schedule.get(1).requires(schedule.get(0)); //job 0 must precede job 1 (creates loop)

        assertEquals(-1, schedule.finish()); //should return -1
        assertEquals(-1, schedule.get(0).start()); //should return -1
        assertEquals(-1, schedule.get(1).start()); //should return -1
        assertEquals(0, schedule.get(2).start()); //should return 0 (no loops in prerequisites)
    }

    @Test
    public void test3() {
        // 1)  0→1→2, duration = [3,2,5]
        Schedule s1 = new Schedule();
        Schedule.Job a0 = s1.insert(3);
        Schedule.Job a1 = s1.insert(2);
        Schedule.Job a2 = s1.insert(5);
        a1.requires(a0);
        a2.requires(a1);
        assertEquals(10, s1.finish());
        assertEquals(0, a0.start());
        assertEquals(3, a1.start());
        assertEquals(5, a2.start());

        // 2)  A→C, B→C, duration = [2,5,3]
        Schedule s2 = new Schedule();
        Schedule.Job bA = s2.insert(2);
        Schedule.Job bB = s2.insert(5);
        Schedule.Job bC = s2.insert(3);
        bC.requires(bA);
        bC.requires(bB);
        assertEquals(8, s2.finish());
        assertEquals(5, bC.start());

        // 3)  linear 0->1->2  and D<->E
        Schedule s3 = new Schedule();
        Schedule.Job c0 = s3.insert(3);
        Schedule.Job c1 = s3.insert(2);
        Schedule.Job c2 = s3.insert(5);
        c1.requires(c0);
        c2.requires(c1);
        Schedule.Job d = s3.insert(4);
        Schedule.Job e = s3.insert(7);
        d.requires(e); // D←E
        e.requires(d); // E←D cycle
        assertEquals(-1, s3.finish());
        assertEquals(5, c2.start());  // c2 on linear,
        assertEquals(-1, d.start());
        assertEquals(-1, e.start());
    }

}
