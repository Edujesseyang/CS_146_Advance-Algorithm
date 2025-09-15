package Heap.FindOneGoodPerson;

import java.security.InvalidParameterException;
import java.util.ArrayList;

class Person {
    private final boolean isGood;

    Person(boolean isGood) {
        this.isGood = isGood;
    }

    boolean checkOther(Person other) {
        if (this == other) {
            throw new InvalidParameterException("Can not vote themself");
        }

        return other.isGood;
    }

    boolean isGood() {
        return isGood;
    }
}

class Group {
    private ArrayList<Person> list;

    Group() {
        list = new ArrayList<>();
    }

    Group(ArrayList<Person> input) {
        list = new ArrayList<>();
        list.addAll(input);
    }

    void addPerson(Person p) {
        list.add(p);
    }

    boolean isEmpty() {
        return list.isEmpty();
    }

    Person popPerson() {
        Person temp = list.get(list.size() - 1);
        list.remove(list.size() - 1);
        return temp;
    }

    int size() {
        return list.size();
    }

    ArrayList<Person> getList() {
        return list;
    }

    Group deepCopy() {
        ArrayList<Person> res = new ArrayList<>();
        for (Person p : list) {
            Person copy = new Person(p.isGood());
            res.add(copy);
        }
        return new Group(res);
    }

}
