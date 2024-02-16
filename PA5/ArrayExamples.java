import tester.*;

/**
 * Write a class called Pair with two int fields, a and b, and include a
 * constructor. (Add Pair at the top level, outside the ArrayExamples class).
 * Then write a method (in ArrayExamples, not in Pair) called addMulti that
 * takes an array of int and returns a Pair where the a field is set to the sum
 * of all integers in the array and the b is set to the multiplication of all
 * integers in the array. Assume the array has at least one element, and integer
 * sum/multiplication wouldn’t go beyond the range of an int.
 */
class Pair {
    int a;
    int b;

    public Pair(int a, int b) {
        this.a = a;
        this.b = b;
    }
}

class ArrayExamples {
    /**
     * Write a method called reverseJoinWith that takes an array of String and a
     * String separator, and returns a new String that contains the strings from the
     * array separated by that separator, but in the reverse order. For example, for
     * an array containing "a", "b", and "c" with separator ":", the result would be
     * "c:b:a" (note that there’s no colon at the end, just in between the
     * elements). If the input array is empty, the method should return the empty
     * string. If the input array contains only one string, the method should return
     * that string.
     */
    String reverseJoinWith(String[] arr, String sep) {
        if (arr.length == 1) {
            return arr[0];
        }
        String reversed = "";
        for (int i = arr.length - 1; i >= 1; i--) {
            reversed += arr[i] + sep;
        }
        if (arr.length > 0) {
            reversed += arr[0];
        } // don't put seperator at end
        return reversed;
    }

    /**
     * Write a function named logicalOr that takes an array of boolean values and
     * returns false if all elements in the array are false, else it returns true.
     * If the array is empty, the function should return false.
     */
    boolean logicalOr(boolean[] arr) {
        // fun ternary solution
        int count = 0;
        for (boolean b : arr) {
            count += b ? 1 : 0;
        }
        return count > 0;
    }

    /**
     * Write a method called allOutsideRange that takes an array of double and two
     * other doubles called low and high, and returns true if all of the numbers in
     * the array are either less than low (exclusive) or greater than high
     * (exclusive). If the array is empty, this should produce true. You can assume
     * that low ≤ high.
     */
    boolean allOutsideRange(double[] arr, double low, double high) {
        for (double d : arr) {
            if (d >= low && d <= high) {
                return false;
            }
        }
        return true;
    }

    /**
     * Write a class called Pair with two int fields, a and b, and include a
     * constructor. (Add Pair at the top level, outside the ArrayExamples class).
     * Then write a method (in ArrayExamples, not in Pair) called addMulti that
     * takes an array of int and returns a Pair where the a field is set to the sum
     * of all integers in the array and the b is set to the multiplication of all
     * integers in the array. Assume the array has at least one element, and integer
     * sum/multiplication wouldn’t go beyond the range of an int.
     */
    Pair addMulti(int[] arr) {
        int sum = 0;
        int product = 1;
        for (int i : arr) {
            sum += i;
            product *= i;
        }
        return new Pair(sum, product);
    }

    /**
     * Write a method called lastSortedString that takes an array of Strings and
     * returns the String that is the last when all strings are ordered
     * alphabetically (Computer scientists have a fancy name for alphabetical:
     * lexicographic. You will need the compareTo method on Strings here. Try it out
     * on a few examples if you’re not sure what it will do!). You can assume that
     * the array has at least one element, and the string will only consist of
     * lowercase alphabets. For example: you are given an array: ["bye", "hello",
     * "goodbye"], your method should return hello, as when all strings are sorted
     * alphabetically, hello would be at the last.
     */
    String lastSortedString(String[] arr) {
        // max algorithm; if s lexicgraphically follows last make last s
        String last = arr[0];
        for (String s : arr) {
            if (s.compareTo(last) > 0) {
                last = s;
            }
        }
        return last;
    }

    /**
     * Write a method called lookup that takes an array of String called keys, an
     * array of int called values, and a String called key (three total parameters).
     * It should find the index in keys where the argument key appears, and then
     * return the int stored in values at that index (Hint: you may want to use
     * .equals or .compareTo for string comparison instead of ==). If the key is not
     * found, the method should return -1. You can assume that lookup will always be
     * given two arrays of the same length, and that there are no duplicate strings
     * in keys. Example: keys contains "UCSD", "UCLA", "UCI" and values contains
     * 36000, 44900, and 33467. For key "UCI", it should return 33467. For key
     * "Stanford", it should return -1.
     */
    int lookup(String[] keys, int[] values, String key) {
        for (int i = 0; i < keys.length; i++) {
            if (keys[i].equals(key)) {
                return values[i];
            }
        }
        return -1;
    }
    // methods to test
    // ---
    // reverseJoinWith
    // logicalOr
    // allOutsideRange
    // addMulti
    // lastSortedString
    // lookup

    // reverseJoinWith
    // ---
    // input: ["a", "b", "c"], ":" | output: "c:b:a"
    // input: ["a"], ":" | output: "a"
    // input: [], ":" | output: ""
    // input: ["a", "b", "c"], "" | output: "cba"
    // input: [], "" | output: ""
    boolean testReverseJoinWith(Tester t) {
        String test1 = reverseJoinWith(new String[] { "a", "b", "c" }, ":");
        String test2 = reverseJoinWith(new String[] { "a" }, ":");
        String test3 = reverseJoinWith(new String[] {}, ":");
        String test4 = reverseJoinWith(new String[] { "a", "b", "c" }, "");
        String test5 = reverseJoinWith(new String[] {}, "");
        System.out.printf("lengths | test1: %s  |  actual: %s\n", test1.length(), "c:b:a".length());
        return t.checkExpect(test1, "c:b:a") && t.checkExpect(test2, "a") && t.checkExpect(test3, "")
                && t.checkExpect(test4, "cba") && t.checkExpect(test5, "");
    }

    // logicalOr
    // ---
    // input: [true, false, true] | output: true
    // input: [false, false, false] | output: false
    // input: [] | output: false
    // input: [true] | output: true
    // input: [false] | output: false
    boolean testLogicalOr(Tester t) {
        boolean test1 = logicalOr(new boolean[] { true, false, true });
        boolean test2 = logicalOr(new boolean[] { false, false, false });
        boolean test3 = logicalOr(new boolean[] {});
        boolean test4 = logicalOr(new boolean[] { true });
        boolean test5 = logicalOr(new boolean[] { false });
        return t.checkExpect(test1, true) && t.checkExpect(test2, false) && t.checkExpect(test3, false)
                && t.checkExpect(test4, true) && t.checkExpect(test5, false);
    }

    // allOutsideRange
    // ---
    // input: [1.0, 2.0, 3.0], 0.0, 4.0 | output: false
    // input: [1.0, 2.0, 3.0], 0.0, 1.0 | output: false
    // input: [1.0, 2.0, 3.0], 4.0, 5.0 | output: true
    // input: [1.0, 2.0, 3.0], 3.0, 4.0 | output: true
    // input: [], 0.0, 1.0 | output: true
    boolean testAllOutsideRange(Tester t) {
        boolean test1 = allOutsideRange(new double[] { 1.0, 2.0, 3.0 }, 0.0, 4.0);
        boolean test2 = allOutsideRange(new double[] { 1.0, 2.0, 3.0 }, 0.0, 1.0);
        boolean test3 = allOutsideRange(new double[] { 1.0, 2.0, 3.0 }, 4.0, 5.0);
        boolean test4 = allOutsideRange(new double[] { 1.0, 2.0, 3.0 }, 3.0, 4.0);
        boolean test5 = allOutsideRange(new double[] {}, 0.0, 1.0);
        // 0 1 2 3 4 5
        // 1:
        return t.checkExpect(test1, false)
                && t.checkExpect(test2, false)
                && t.checkExpect(test3, true)
                && t.checkExpect(test4, false)
                && t.checkExpect(test5, true);
    }

    // addMulti
    // ---
    // input: [1, 2, 3] | output: Pair(6, 6)
    // input: [1, 2, 3, 4] | output: Pair(10, 24)
    // input: [1, 2, 3, 4, 5] | output: Pair(15, 120)
    // input: [1] | output: Pair(1, 1)
    boolean testAddMulti(Tester t) {
        Pair test1 = addMulti(new int[] { 1, 2, 3 });
        Pair test2 = addMulti(new int[] { 1, 2, 3, 4 });
        Pair test3 = addMulti(new int[] { 1, 2, 3, 4, 5 });
        Pair test4 = addMulti(new int[] { 1 });
        return t.checkExpect(test1, new Pair(6, 6)) && t.checkExpect(test2, new Pair(10, 24))
                && t.checkExpect(test3, new Pair(15, 120)) && t.checkExpect(test4, new Pair(1, 1));
    }

    // lastSortedString
    // ---
    // input: ["bye", "hello", "goodbye"] | output: "hello"
    // input: ["a", "b", "c"] | output: "c"
    // input: ["a", "b", "c", "d"] | output: "d"
    // input: ["a"] | output: "a"
    boolean testLastSortedString(Tester t) {
        String test1 = lastSortedString(new String[] { "bye", "hello", "goodbye" });
        String test2 = lastSortedString(new String[] { "a", "b", "c" });
        String test3 = lastSortedString(new String[] { "a", "b", "c", "d" });
        String test4 = lastSortedString(new String[] { "a" });
        return t.checkExpect(test1, "hello") && t.checkExpect(test2, "c") && t.checkExpect(test3, "d")
                && t.checkExpect(test4, "a");
    }

    // lookup
    // ---
    // input: ["UCSD", "UCLA", "UCI"], [36000, 44900, 33467], "UCI" | output: 33467
    // input: ["UCSD", "UCLA", "UCI"], [36000, 44900, 33467], "UCLA" | output: 44900
    // input: ["UCSD", "UCLA", "UCI"], [36000, 44900, 33467], "UCSD" | output: 36000
    // input: ["UCSD", "UCLA", "UCI"], [36000, 44900, 33467], "Stanford" | output:-1
    boolean testLookup(Tester t) {
        int test1 = lookup(new String[] { "UCSD", "UCLA", "UCI" }, new int[] { 36000, 44900, 33467 }, "UCI");
        int test2 = lookup(new String[] { "UCSD", "UCLA", "UCI" }, new int[] { 36000, 44900, 33467 }, "UCLA");
        int test3 = lookup(new String[] { "UCSD", "UCLA", "UCI" }, new int[] { 36000, 44900, 33467 }, "UCSD");
        int test4 = lookup(new String[] { "UCSD", "UCLA", "UCI" }, new int[] { 36000, 44900, 33467 }, "Stanford");
        return t.checkExpect(test1, 33467) && t.checkExpect(test2, 44900) && t.checkExpect(test3, 36000)
                && t.checkExpect(test4, -1);
    }
}