package TwoThreeTree;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.junit.Assert.*;


public class Tests {
    @Test
    public void testSortOrder() { // test random 3000 elements
        List<Integer> input = new ArrayList<>();
        for (int i = 0; i < 3000; i++) {
            input.add((int) Math.floor(Math.random() * 1000));
        }
        Tree t1 = new Tree(input);
        List<Integer> output = t1.toList();
        Integer compare = output.get(0);
        output.remove(0);
        for (Integer i : output) {
            assertTrue(compare < i);
            compare = i;
        }
        System.out.println(output);
    }

    @Test
    public void testConstructor() {
        Tree t1 = new Tree();
        assertNotNull(t1);
        System.out.println(t1);

        t1.insert(1);
        System.out.println(t1 + "\n");
        t1.insert(10);
        System.out.println(t1 + "\n");
        t1.insert(1);
        System.out.println(t1 + "\n");
        t1.insert(10);
        System.out.println(t1 + "\n");
        t1.insert(15);
        System.out.println(t1 + "\n");
        t1.insert(25);
        System.out.println(t1 + "\n");
        t1.insert(34);
        System.out.println(t1 + "\n");
        t1.insert(105);
        System.out.println(t1 + "\n");
        t1.insert(14);
        System.out.println(t1 + "\n");
        t1.insert(51);
        System.out.println(t1 + "\n");
        t1.insert(31);
        System.out.println(t1 + "\n");

        assertEquals(9, t1.size());
        System.out.println("Pay attention: " + t1);
        assertEquals(9, t1.size(25));
        assertEquals(4, t1.size(10));
        System.out.println("10 has " + t1.size(10) + " children");
        System.out.println("25 has " + t1.size(25) + " children");

        int n1 = t1.get(2);
        System.out.println("index 2 = " + n1);

        int n2 = t1.get(5);
        System.out.println("index 5 = " + n2);
    }

    @Test
    public void testListConstruct() {
        List<Integer> input = new ArrayList<>(Arrays.asList(90, 54, 82, 2, 63, 1, 0, 45, 45, 90));
        Tree t1 = new Tree(input);

        assertEquals(8, t1.size);
        assertEquals(0, t1.size(255));

        List<Integer> input2 = new ArrayList<>(Arrays.asList(905, 544, 852, 24, 653, 15, 4, 454, 435, 950));
        List<Integer> input3 = new ArrayList<>(Arrays.asList(5, 4, 2, 2, 3, 1, 0, 4, 5, 9));
        t1.insert(input2);
        t1.insert(input3);
        System.out.println(t1);

        int n1 = t1.get(3);
        assertEquals(3, n1);
        System.out.println("index 3 = " + n1);

        int n2 = t1.get(4);
        assertEquals(4, n2);
        System.out.println("index 4 = " + n2);

        int n3 = t1.get(5);
        assertEquals(5, n3);
        System.out.println("index 5 = " + n3);

        int n4 = t1.get(14);
        assertEquals(435, n4);
        System.out.println("index 14 = " + n4);

        int n5 = t1.get(15);
        assertEquals(454, n5);
        System.out.println("index 15 = " + n5);

        int n6 = t1.get(19);
        assertEquals(905, n6);
        System.out.println("index 20 = " + n6);

        int n7 = t1.get(20);
        assertEquals(950, n7);
        System.out.println("index 950 = " + n7);

        try {
            int n8 = t1.get(220);
            System.out.println(n8);
        } catch (IndexOutOfBoundsException e) {
            assertEquals("Index out of bound", e.getMessage());
        }
        Tree t2 = new Tree();
        assertEquals(0, t2.size());
        assertEquals(0, t2.size(2));

        try {
            int n8 = t1.get(-1);
            System.out.println(n8);
        } catch (IndexOutOfBoundsException e) {
            assertEquals("Index out of bound", e.getMessage());
        }

        try {
            int n8 = t2.get(0);
            System.out.println(n8);
        } catch (IllegalStateException e) {
            assertEquals("empty tree", e.getMessage());
        }

        try {
            Integer i = null;
            t2.insert(i);
        } catch (IllegalArgumentException e) {
            assertEquals("null parameter", e.getMessage());
        }
    }

    @Test
    public void testNewAdding() {
        System.out.println("Test New updates:");
        Tree t1 = new Tree();
        t1.insert(19);
        t1.insert(20);
        t1.insert(21);
        System.out.println(t1);

    }

    @Test
    public void testTwoThreeTree_Correctness() {
        // ---- 0) 空树行为 ----
        Tree t = new Tree();
        assertEquals(0, t.size());
        try {
            t.get(0);
            fail("empty tree should throw");
        } catch (IllegalStateException expected) {
        }

        // ---- 1) 基本插入与有序性 / 去重 ----
        assertTrue(t.insert(10));                      // [10]
        assertEquals(1, t.size());
        assertEquals(Arrays.asList(10), t.toList());
        assertEquals(1, t.size(10));                   // 叶子(leaf)单键

        assertTrue(t.insert(20));                      // [10,20] (同一节点两键)
        assertEquals(2, t.size());
        assertEquals(Arrays.asList(10, 20), t.toList());

        assertEquals(1, t.size(10));
        assertEquals(1, t.size(20));

        assertTrue(t.insert(30));                      // 触发分裂(split)：root=[20], L=[10], R=[30]
        assertEquals(3, t.size());
        assertEquals(Arrays.asList(10, 20, 30), t.toList());
        assertEquals(3, t.size(20));                   // 根的子树大小=3
        System.out.println("***Tree*** is : " + t);
        assertEquals(1, t.size(10));                   // 叶子
        assertEquals(1, t.size(30));                   // 叶子

        // ---- 2) 更大序列，验证确定形态 ----
        // 连续插入 40,50,60,70，按 2-3 树应得到：
        //           [40]
        //        /         \
        //     [20]         [60]
        //    /    \       /    \
        //  [10]  [30]   [50]  [70]
        assertTrue(t.insert(40));
        assertTrue(t.insert(50));
        assertTrue(t.insert(60));
        assertTrue(t.insert(70));
        assertEquals(7, t.size());
        assertEquals(Arrays.asList(10, 20, 30, 40, 50, 60, 70), t.toList());

        // 结构性断言（用 size(key) 做探针）
        assertEquals(7, t.size(40));   // 根的子树=全树
        assertEquals(3, t.size(20));   // 左子树 [20] + [10] + [30]
        assertEquals(3, t.size(60));   // 右子树 [60] + [50] + [70]
        assertEquals(1, t.size(10));
        assertEquals(1, t.size(30));
        assertEquals(1, t.size(50));
        assertEquals(1, t.size(70));

        // ---- 3) get(index) 与有序一致性 ----
        List<Integer> sorted = Arrays.asList(10, 20, 30, 40, 50, 60, 70);
        for (int i = 0; i < sorted.size(); i++) {
            assertEquals(sorted.get(i).intValue(), t.get(i));
        }
        // 越界
        try {
            t.get(-1);
            fail("negative index should throw");
        } catch (IndexOutOfBoundsException expected) {
        }
        try {
            t.get(t.size());
            fail("size index should throw");
        } catch (IndexOutOfBoundsException expected) {
        }

        // ---- 4) 重复插入(duplicate) 必须被拒绝且不改变 size ----
        int before = t.size();
        assertFalse("duplicate must be rejected", t.insert(40));
        assertEquals(before, t.size());

        // ---- 5) 插入 null：两种都允许（抛 IAEx 或返回 false），但 size 不变 ----
        before = t.size();
        boolean returnedFalseForNull = false;
        try {
            Integer nullNum = null;
            boolean ok = t.insert(nullNum);
            // 如果实现选择返回 false，也接受
            returnedFalseForNull = !ok;
        } catch (IllegalArgumentException ignored) {
            returnedFalseForNull = true; // 也接受抛异常
        }
        assertTrue("null insertion must not succeed", returnedFalseForNull);
        assertEquals(before, t.size());

        // ---- 6) size(key) 对不存在 key 应为 0 ----
        assertEquals(0, t.size(999));

        // ---- 7) 构造器批量 + 随机回归：有序且去重 ----
        Random rnd = new Random(42);
        List<Integer> bulk = new ArrayList<>();
        for (int i = 0; i < 2000; i++) {
            bulk.add(rnd.nextInt(1000)); // 含重复
        }
        Tree t2 = new Tree(bulk);
        List<Integer> expect = bulk.stream().distinct().sorted().collect(Collectors.toList());
        assertEquals(expect.size(), t2.size());
        assertEquals(expect, t2.toList());

        // spot-check get()
        for (int i = 0; i < Math.min(50, expect.size()); i++) {
            assertEquals(expect.get(i).intValue(), t2.get(i));
        }
    }
}
