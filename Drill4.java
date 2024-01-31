import tester.*;

class Drill4 {
    /**
     * phaseOfWater which takes an int and returns
     * "vapor" if the number is greater than or equal to 100,
     * "liquid" if the number is less than 100 and greater than 0,
     * and "solid" if the number is less than or equal to 0.
     * 
     * @param temp
     * @return string based on water's phase properties given only temperature
     */
    static String phaseOfWater(int temp) {
        if (temp >= 100) {
            return "vapor";
        }
        if (temp <= 0) {
            return "solid";
        }
        return "liquid";
    }

    /**
     * maxProduct which takes three ints and returns the largest product between any
     * two of them. For example, maxProduct applied to 1, -4, and -5 should return
     * 20 because the product between -4 and -5 is -5 which is greater than the
     * product of 1 and -5 (Product: -5), as well as 1 and -4 (Product: -4)
     * 
     * @param a
     * @param b
     * @param c
     * @return the greatest product between every combination of a b c
     */
    static int maxProduct(int a, int b, int c) {
        int[] nums = { a, b, c };
        int max = a * b; // init to first product
        // why not use loops on a hilariously small search space?
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] * nums[j] > max) {
                    max = nums[i] * nums[j];
                }
            }
        }
        return max;
    }

    /**
     * pipeVolume which takes three doubles representing the inner radius of a
     * cylindrical pipe, and the outer radius, as well as the height of the pipe. It
     * returns the volume of the pipe as a double. Assume both inputs are positive
     * and that the first number is smaller than the second (inner radius always
     * smaller). Recall that the volume of a cylinder is πr2h. You can use Math.PI,
     * a field conveniently defined for us by Java, as a constant for the value of
     * π.
     */
    static double pipeVolume(double ir, double or, double h) {
        // volume of outer cylinder - volume of inner cylinder
        double inner = Math.PI * Math.pow(ir, 2) * h;
        double outer = Math.PI * Math.pow(or, 2) * h;
        return outer - inner;
    }

    boolean testWaterPhase(Tester t) {
        return t.checkExpect(phaseOfWater(348756234), "vapor") && // INSTANT THIRD DEGREE BURNS WHEN MAKING TEA
                t.checkExpect(phaseOfWater(32), "liquid") && // you could drink this
                t.checkExpect(phaseOfWater(-273), "solid"); // 0 kelvin
    }

    boolean testProduct(Tester t) {
        return t.checkExpect(maxProduct(1, 2, 3), 6) &&
                t.checkExpect(maxProduct(8, -2, -3), 6) &&
                t.checkExpect(maxProduct(-345, -6, 42), 2070);
    }

    boolean testPipe(Tester t) {
        // found out here that double precision matters, entered 15.7, 109.9, and 169.56
        // at first for expected values, then tester output that expects tolerance to
        // 0.001
        return t.checkExpect(pipeVolume(2, 3, 1), 15.707963267948966) &&
                t.checkExpect(pipeVolume(3, 4, 5), 109.95574287564276) &&
                t.checkExpect(pipeVolume(4, 5, 6), 169.64600329384882);
    }
}