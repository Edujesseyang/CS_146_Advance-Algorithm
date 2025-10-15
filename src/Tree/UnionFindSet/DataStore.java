package Tree.UnionFindSet;

import java.util.ArrayList;
import java.util.List;

public class DataStore {
    List<Integer> myData;
    UnionFindSet ufs;
    DataStore(){
        myData = new ArrayList<>();
        ufs = new UnionFindSet();
    }
}
