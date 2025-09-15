package Heap.FindOneGoodPerson;

import java.util.ArrayList;
import java.util.Arrays;

public class FindAGoodPerson {
    /*
    * Problem Statement
    You have a group of n people. Each person is either:

        - a truth-teller (good person), who always tells the truth, or

        - a liar (bad person), who may answer arbitrarily (sometimes truthfully, sometimes not).

    It is guaranteed that at least half of the people are truth-tellers. S[bad] > n/2

    You are allowed to ask only one type of yes/no question:

    “PersonA, is PersonB a truth-teller?”

    Your goal is to design an algorithm that, using such queries, can identify at least one truth-teller.

    You want the algorithm to be as efficient as possible in terms of the number of queries.
*/

    public static void main(String[] args) {
        Person p1 = new Person(true);
        Person p2 = new Person(true);
        Person p3 = new Person(false); //1
        Person p4 = new Person(true);
        Person p5 = new Person(false); //2
        Person p6 = new Person(false); //3
        Person p7 = new Person(true);
        Person p8 = new Person(true);
        Person p9 = new Person(false); //4
        Person p10 = new Person(true);
        Person p11 = new Person(false); //5
        Person p12 = new Person(true);
        Person p13 = new Person(false); //6

        Group test1 = new Group(new ArrayList<>(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13)));
        Person result = findGoodPerson(test1);
        System.out.println("Result : " + result.isGood());
    }

    private static Person findGoodPerson(Group input) {
        Group workSpace = new Group(input.getList()); // copy work space

        Person candidate = findPossibleCandidate(workSpace); // find a possible candidate

        Group recheckGroup = new Group(); // init a group for all people who vote no.
        int voteYes = 0; // count vote for yes
        for (Person p : input.getList()) {
            if (p == candidate) { // no self-voting
                continue;
            }
            if (p.checkOther(candidate)) { // let every body vote for candidate
                voteYes++;
            } else {
                recheckGroup.addPerson(p); // who votes for no, save them for later recheck
            }
        }

        if (voteYes > (input.size() + 1) / 2 - 1) { // ceil - 1
            return candidate; // if more than half yes, candidate is 100% good
        }

        return findGoodPerson(recheckGroup); // if candidate is not good, whoever vote for no is possible group of good

    }


    private static Person findPossibleCandidate(Group input) {
        Group nextGroup = new Group(); // a group for next recursive call

        while (input.size() >= 2) { // if more than 2
            Person p1 = input.popPerson();  // grab 2 people
            Person p2 = input.popPerson();

            if (p1.checkOther(p2) && p2.checkOther(p1)) { // let them check each other
                nextGroup.addPerson(p1); // grab whatever on throw into the nextGroup
            }
        }

        if (input.size() == 1) { // when only one left, throw it into the nextGroup
            nextGroup.addPerson(input.popPerson());
        }

        if (nextGroup.size() == 1) {
            return nextGroup.popPerson(); // base case, only one left in the nextGroup return him
        } else {
            return findPossibleCandidate(nextGroup); // go check next round
        }
    }
}
