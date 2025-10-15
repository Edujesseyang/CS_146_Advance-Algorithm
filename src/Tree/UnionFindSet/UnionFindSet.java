package Tree.UnionFindSet;

import java.util.ArrayList;
import java.util.List;


public class UnionFindSet {
    List<Integer> set;    // use a negative number store in the root for indicate the size,   true size = |set[i]|


    UnionFindSet() {
        this.set = new ArrayList<>();
        initSet();
    }

    void initSet() {
        for (int i = 0; i < set.size(); i++) set.add(i);
    }

    int findRep(int i) {  // return a -size
        if (set.get(i) < 0) return i;
        set.set(set.get(i), findRep(set.get(i)));
        return set.get(i);
    }

    void joint(int x, int y) {
        int rootX = findRep(x);
        int rootY = findRep(y);

        int sizeOfX = -set.get(rootX); // get true size of both roots
        int sizeOfY = -set.get(rootY);

        if (set.get(rootX) < set.get(rootY)) { // means rootX has bigger size
            set.set(rootY, rootX);  // parent of Y = X
            set.set(rootX, -sizeOfX - sizeOfY); // update the size of new root

        } else {
            set.set(rootX, rootY);
            set.set(rootY, -sizeOfX - sizeOfY); // update the size of new root
        }
    }




}
