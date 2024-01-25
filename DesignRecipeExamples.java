import tester.*;

class DesignRecipeExamples {
    // Problem 1
    int volume(int length, int width, int height) {
        /*
         * Calculates the volume of a cuboid given by length width and height
         * Inputs are integers
         * Outputs an integer
         */
        return length * width * height;
    }

    int volumeTest1 = this.volume(2, 2, 2); // expected: 8
    int volumeTest2 = this.volume(1, 2, 3); // expected : 6

    // Problem 2
    int quadraticEquation(int a, int b, int c, int x) {
        /*
         * Gives the output of the quadratic equation
         * Given integer inputs a, b, c, x
         * Outputs an integer
         */
        return (a * x * x) + (b * x) + (c);
    }

    int quadraticTest1 = this.quadraticEquation(1, 2, 3, 4); // expected: 27
    int quadraticTest2 = this.quadraticEquation(4, 3, 2, 1); // expected: 9

    // Problem 3
    int USDtoDKK(int usd) {
        /*
         * Converts a double, usd, in $USD into Danish Krone
         * Using the conversion rate as of 1/16/24
         * where $1USD = $6.86DKK
         * Because the problem requires ints, round to nearest integer
         * so assume $1USD = $7DKK
         */
        int conversionRate = 7;
        return usd * conversionRate;
    }

    double conversionTest1 = this.USDtoDKK(3); // expected: 21
    double conversionTest2 = this.USDtoDKK(4); // expected: 28

    // Problem 4
    int totalFeet(int miles, int feet) {
        /*
         * Takes feet and miles and outputs the
         * total number of feet in the given arguments
         * given that 1 mile = 5280 feet
         */
        return feet + (miles * 5280);
    }

    int feets1 = this.totalFeet(quadraticTest2, quadraticTest1); // expected: 47,547
    // The output below of 1 is wrong in the sense that distance is magnitude
    // so ideally, totalFeet() should take the absolute value of inputs miles
    // and feet before performing the combination and returning a value
    int feets2 = this.totalFeet(-1, 5281); // expected: 1
}
