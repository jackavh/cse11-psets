import tester.*;
import java.util.List;
import java.util.Arrays;
import java.util.Comparator;
import java.util.ArrayList;
import java.lang.IllegalArgumentException;

class Point {
  int x, y;
  Point(int x, int y) {
    this.x = x;
    this.y = y;
  }
  double distance(Point other) {
    return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
  }
}

class PointCompare implements Comparator<Point> {
    public int compare(Point p1, Point p2) {
        if (p1.x - p2.x == 0) {
            return p1.y - p2.y;
        }
        return p1.x - p2.x;
    }
}

class PointDistanceCompare implements Comparator<Point> {
    public int compare(Point p1, Point p2) {
        Point origin = new Point(0, 0);
        return (int)(p1.distance(origin) - p2.distance(origin));
    }
}

class StringCompare implements Comparator<String> {
    public int compare(String s1, String s2) {
        return s1.compareTo(s2);
    }
}

class StringLengthCompare implements Comparator<String> {
    public int compare(String s1, String s2) {
        return s1.length() - s2.length();
    }
}

class BooleanCompare implements Comparator<Boolean> {
    public int compare(Boolean a, Boolean b) {
        if (a && !b) {return 1;}
        if (!a && b) {return -1;}
        return 0;
    }
}

class CompareLists {
    <E> E maximum(List<E> arr, Comparator<E> comp) {
        if (arr.isEmpty()) { return null; }
        E max = arr.get(0);
        for (E x : arr) {
            if (comp.compare(x, max) > 0) {
                max = x;
            }
        }
        return max;
    }

    <E> E maximum(E[] arr, Comparator<E> comp) {
        if (arr.length == 0) { return null; }
        E max = arr[0];
        for (E x : arr) {
            if (comp.compare(x, max) > 0) {
                max = x;
            }
        }
        return max;
    }

    <E> List<E> lesserThan(List<E> arr, Comparator<E> comp, E ele) {
        List<E> ret = new ArrayList<E>();
        for (E x : arr) {
            if (comp.compare(x, ele) < 0) {
            System.out.println(ele.getClass());
            System.out.println(x.getClass());
            System.out.println(ret.getClass());
                ret.add(x);
            }
        }
        return ret;
    }

    <E> boolean inOrder(List<E> arr, Comparator<E> comp) {
        if (arr.isEmpty()) { return false; }
        if (arr.get(0) == null) {
            throw new IllegalArgumentException("null value in list");
        }
        for (int i = 1; i < arr.size(); i++) {
            if (arr.get(i) == null) {
                throw new IllegalArgumentException("null value in list");
            }
            if (comp.compare(arr.get(i), arr.get(i-1)) < 0) {
                return false;
            }
        }
        return true;
    }

    <E> boolean inOrder(E[] arr, Comparator<E> comp) {
        if (arr.length == 0) { return false; }
        if (arr[0] == null) {
            throw new IllegalArgumentException("null value in array");
        }
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] == null) {
                throw new IllegalArgumentException("null value in array");
            }
            if (comp.compare(arr[i], arr[i-1]) < 0) {
                return false;
            }
        }
        return true;
    }

    <E> List<E> merge(List<E> listA, List<E> listB, Comparator<E> comp) {
        int i = 0;
        int j = 0;
        List<E> ret = new ArrayList<E>();
        while (i < listA.size() && j < listB.size()) {
            System.out.println(String.format("comparing: %s > %s", listA.get(i), listB.get(j)));
            System.out.println(ret);
            if ( comp.compare(listA.get(i), listB.get(j)) > 0 ) {
                ret.add(listB.get(j));
                j++;
            } else {
                ret.add(listA.get(i));
                i++;
            }
        }
            System.out.println("Done with comparison");
            if (i < listA.size()) {
                System.out.println("add all from listA");
                ret.addAll(inOrder(listA.subList(i, listA.size()), comp));
            }
            if (j < listB.size()) {
            System.out.println("add all from listB");
                ret.addAll(listB.subList(j, listB.size()));
            }
        return ret;
    }

    // -----------------  TESTS  ----------------- \\
    boolean testPointCompare(Tester t) {
        // Possible test cases:
        // X: x1 > x2, x1 = x2, x1 < x2
        // Y: y1 > y2, y1 = y2, y1 < y2
        // x1 > x2 && y1 > y2 -> +
        // x1 > x2 && y1 = y2 -> +
        // x1 > x2 && y1 < y2 -> +
        // x1 = x2 && y1 > y2 -> +
        // x1 = x2 && y1 = y2 -> 0
        // x1 = x2 && y1 < y2 -> -
        // x1 < x2 && y1 > y2 -> -
        // x1 < x2 && y1 = y2 -> -
        // x1 < x2 && y1 < y2 -> -
        PointCompare pc = new PointCompare();
        return t.checkExpect(pc.compare(new Point(0, 0), new Point(-1, -1)), 1) && // x1 > x2 && y1 > y2
               t.checkExpect(pc.compare(new Point(0, 0), new Point(-1,  0)), 1) && // x1 > x2 && y1 = y2
               t.checkExpect(pc.compare(new Point(0, 0), new Point(-1,  1)), 1) && // x1 > x2 && y1 < y2
               t.checkExpect(pc.compare(new Point(0, 0), new Point(0, -1)),1) && // x1 = x2 && y1 > y2
               t.checkExpect(pc.compare(new Point(0, 0), new Point(0,  0)), 0) && // x1 = x2 && y1 = y2
               t.checkExpect(pc.compare(new Point(0, 0), new Point(0,  1)),-1) && // x1 = x2 && y1 < y2
               t.checkExpect(pc.compare(new Point(0, 0), new Point(1, -1)),-1) && // x1 < x2 && y1 > y2
               t.checkExpect(pc.compare(new Point(0, 0), new Point(1,  0)),-1) && // x1 < x2 && y1 = y2
               t.checkExpect(pc.compare(new Point(0, 0), new Point(1,  1)),-1); // x1 < x2 && y1 < y2
    }

    boolean testPointDistanceCompare(Tester t) {
        // Possible test cases:
        // Distance: d1 > d2, d1 = d2, d1 < d2
        // d1 > d2 -> +
        // d1 = d2 -> 0
        // d1 < d2 -> -
        // Graphically,
        // d1 > d2 -> +
        // . . 1 . .  | 1 . . . .
        // . . . . .  | . . . . .
        // . . o 2 .  | . . o . .
        // . . . . .  | . . . 2 .
        // . . . . .  | . . . . .
        // 1: p1=( 0, 2), p2=( 1, 0) -> 1
        // 2: p1=(-2, 2), p2=( 1,-1) -> 1
        // d1 = d2 -> 0
        // . . . . .  | 1 . . . 2
        // . . 1 . .  | . . . . .
        // . 2 o . .  | . . o . .
        // . . . . .  | . . . . .
        // . . . . .  | . . . . .
        // 1: p1=( 0, 1), p2=(-1, 0) -> 0
        // 2: p1=(-2, 2), p2=( 2, 2) -> 0
        // d1 < d2 -> -
        // . . . . .  | . . . . .
        // . . . 1 .  | . . . . .
        // . . o . .  | . 1 o . .
        // . . . . .  | . . . . .
        // 2 . . . .  | . . . . 2
        // 1: p1=( 1, 1), p2=(-2,-2) -> -1
        // 2: p1=(-1, 0), p2=(-2,2) -> -1
        PointDistanceCompare pdc = new PointDistanceCompare();
        return t.checkExpect(pdc.compare(new Point(0,2), new Point(1,0)), 1) &&
               t.checkExpect(pdc.compare(new Point(-2,2), new Point(1,-1)), 1) &&
               t.checkExpect(pdc.compare(new Point(0,1), new Point(-1,0)), 0) &&
               t.checkExpect(pdc.compare(new Point(-2,2), new Point(2,2)), 0) &&
               t.checkExpect(pdc.compare(new Point(1,1), new Point(-2,-2)), -1) &&
               t.checkExpect(pdc.compare(new Point(-1,0), new Point(-2,-2)), -1);
    }

    boolean testStringCompare(Tester t) {
        // Possible test cases:
        // s1 > s2 -> +
        // s1 = s2 -> 0
        // s1 < s2 -> -
        StringCompare sc = new StringCompare();
        return t.checkExpect(sc.compare("a", "b"), -1) &&
               t.checkExpect(sc.compare("a", "a"), 0) &&
               t.checkExpect(sc.compare("b", "a"), 1) &&
               t.checkExpect(sc.compare("a", "aa"), -1);
    }

    boolean testStringLengthCompare(Tester t) {
        StringLengthCompare slc = new StringLengthCompare();
        return t.checkExpect(slc.compare("ab", "abcd"), -2) &&
               t.checkExpect(slc.compare("a", "ab"), -1) &&
               t.checkExpect(slc.compare("abcd", "abcd"), 0) &&
               t.checkExpect(slc.compare("abcde", "abc"), 2);
    }

    boolean testBooleanCompare(Tester t) {
        BooleanCompare bc = new BooleanCompare();
        return t.checkExpect(bc.compare(true, true), 0) &&
               t.checkExpect(bc.compare(true, false), 1) &&
               t.checkExpect(bc.compare(false, true), -1) &&
               t.checkExpect(bc.compare(false, false), 0);
    }

    boolean testCompareList(Tester t) {
        PointCompare pc = new PointCompare();
        Point p1 = new Point(0, 0);
        Point p2 = new Point(1, 0);
        Point p3 = new Point(2, 2);
        StringCompare sc = new StringCompare();
        StringLengthCompare slc = new StringLengthCompare();
        return t.checkExpect(maximum(Arrays.asList("a", "aa", "aaa"), slc), "aaa") &&
               t.checkExpect(maximum(Arrays.asList(p1, p2, p3), pc), p3) &&
               t.checkExpect(maximum(Arrays.asList(), sc), null);
    }

    boolean testCompareArr(Tester t) {
        PointCompare pc = new PointCompare();
        Point p1 = new Point(0, 0);
        Point p2 = new Point(1, 0);
        Point p3 = new Point(2, 2);
        Point[] plist = {p1, p2, p3};
        StringCompare sc = new StringCompare();
        String[] slist = {"a", "aa", "aaa"};
        StringLengthCompare slc = new StringLengthCompare();
        String[] empty = {};
        return t.checkExpect(maximum(slist, slc), "aaa") &&
               t.checkExpect(maximum(plist, pc), p3) &&
               t.checkExpect(maximum(empty, sc), null);
    }

    boolean testLesserThan(Tester t) {
        // Test 1
        Comparator<String> slc = new StringLengthCompare();
        List<String> list1 = Arrays.asList("a", "aa", "aaa", "aaaa");
        String less1 = "aaa"; // Expecting to get back {"a", "aa"}
        // Test 2
        Comparator<Point> pc = new PointCompare();
        List<Point> list2 = Arrays.asList(new Point(0, 0), new Point(1, 0), new Point(2, 2));
        Point less2 = new Point(2, 2); // Expecting to get back {(0, 0), (1, 0)}
        // Test 3
        Comparator<String> sc = new StringCompare();
        List<String> list3 = Arrays.asList("a", "b", "c", "d", "e", "f");
        String less3 = "d"; // Expecting to get back {"a", "b", "c"}
        return t.checkExpect(lesserThan(list1, slc, less1), list1.subList(0,2)) &&
               t.checkExpect(lesserThan(list2, pc, less2), list2.subList(0,2)) &&
               t.checkExpect(lesserThan(list3, sc, less3), list3.subList(0, 3));
    }
<<<<<<< HEAD

    boolean testInOrder(Tester t) {
        // Test 1
        Comparator<String> slc = new StringLengthCompare();
        List<String> list1 = Arrays.asList("a", "aaaaaa", "aaa", "aaaa");
        // Test 2
        Comparator<Point> pc = new PointCompare();
        List<Point> list2 = Arrays.asList(new Point(0, 0), new Point(1, 0), new Point(2, 2));
        // Test 3
        Comparator<String> sc = new StringCompare();
        List<String> list3 = Arrays.asList("a", "b", "c", "d", "e", "f");
        return t.checkExpect(inOrder(list1, slc), false) &&
               t.checkExpect(inOrder(list2, pc), true) &&
               t.checkExpect(inOrder(list3, sc), true);
    }

    boolean testInOrderArr(Tester t) {
        // Test 1
        Comparator<String> slc = new StringLengthCompare();
        String[] list1 = {"a", "aaaaaa", "aaa", "aaaa"};
        // Test 2
        Comparator<Point> pc = new PointCompare();
        Point[] list2 = {new Point(0, 0), new Point(1, 0), new Point(2, 2)};
        // Test 3
        Comparator<String> sc = new StringCompare();
        String[] list3 = {"a", "b", "c", "d", "e", "f"};
        return t.checkExpect(inOrder(list1, slc), false) &&
               t.checkExpect(inOrder(list2, pc), true) &&
               t.checkExpect(inOrder(list3, sc), true);
    }

    boolean testMerge(Tester t) {
        // Test 1
        Comparator<String> slc = new StringLengthCompare();
        List<String> list1 = Arrays.asList("a", "aaaaaa", "aaa", "aaaa");
        List<String> list2 = Arrays.asList("a", "aa", "aaa", "aaaa");
        // a, a, aa, aaa, aaa, aaaa, aaaa, aaaaaa
        List<String> merged1 = Arrays.asList("a", "a", "aa", "aaa", "aaa", "aaaa", "aaaa", "aaaaaa");
        // Test 2
        Comparator<Point> pc = new PointCompare();
        List<Point> list3 = Arrays.asList(new Point(0, 0), new Point(1, 0), new Point(2, 2));
        List<Point> list4 = Arrays.asList(new Point(0, 0), new Point(1, 0), new Point(2, 2));
        List<Point> merged2 = Arrays.asList(new Point(0, 0), new Point(0, 0), new Point(1, 0), new Point(1, 0), new Point(2, 2), new Point(2, 2));
        // Test 3
        Comparator<String> sc = new StringCompare();
        List<String> list5 = Arrays.asList("a", "b", "c", "d", "e", "f");
        List<String> list6 = Arrays.asList("a", "b", "c", "d", "e", "f");
        List<String> merged3 = Arrays.asList("a", "a", "b", "b", "c", "c", "d", "d", "e", "e", "f", "f");
        return t.checkExpect(merge(slc, list1, list2), merged1) &&
               t.checkExpect(merge(pc, list3, list4), merged2) &&
               t.checkExpect(merge(sc, list5, list6), merged3);
    }
=======
>>>>>>> parent of 40a5c53 (fix param order on merge())
}
