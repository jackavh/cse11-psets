import tester.*;

interface Number {
    int numerator();

    int denominator();

    Number add(Number other);

    Number multiply(Number other);

    Number getMax(Number other);

    String toString();

    double toDouble();
}

/**
 * Helper class with methods to simplify Numbers
 */
class Simplifier {
    /**
     * Returns the greatest common divisor of a and b using recursive Euler's
     * Algorithm
     * 
     * @param a int
     * @param b int
     * @return int : greatest common divisor
     */
    public static int gcd(int a, int b) {
        if (b == 0) {
            return a;
        } else {
            return gcd(b, a % b);
        }
    }

    /**
     * Returns the most simplified form of a fraction based on
     * 
     * @param n numerator
     * @param d denominator
     * @return Number : in the most simplified form
     */
    public static Number simplified(int n, int d) {
        if (n % d == 0) { // if numerator is divisible by denominator
            return new WholeInteger(n / d); // return whole number
        } else {
            int gcd = gcd(n, d); // get gcd
            return new Fraction(n / gcd, d / gcd); // return simplified fraction
        }

    }
}

class Fraction implements Number {
    public int n;
    public int d;

    /**
     * Constructor for Fraction
     * simplifies the fraction as much as possible
     */
    public Fraction(int n, int d) {
        int gcd = Simplifier.gcd(n, d);
        // if no common factors shared, then gcd is 1
        this.n = n / gcd;
        this.d = d / gcd;
    }

    /**
     * always return value of n
     */
    @Override
    public int numerator() {
        return this.n;
    }

    /**
     * always return value of d
     */
    @Override
    public int denominator() {
        return this.d;
    }

    /**
     * Implents adding between Fraction and WholeNumber
     * simplifies to whole number if denom. evenly divides numerator
     */
    @Override
    public Number add(Number other) {
        int num = this.numerator() * other.denominator() + other.numerator() * this.denominator();
        int den = this.denominator() * other.denominator();
        return Simplifier.simplified(num, den);
    }

    /**
     * Implements multiplication between Fraction and WholeNumber
     */
    @Override
    public Number multiply(Number other) {
        int num = this.numerator() * other.numerator();
        int den = this.denominator() * other.denominator();
        return Simplifier.simplified(num, den);
    }

    /**
     * Returns the larger of the two numbers
     */
    @Override
    public Number getMax(Number other) {
        return (this.toDouble() > other.toDouble()) ? this : other;
    }

    /**
     * Returns a string formatted"numerator/denominator"
     */
    @Override
    public String toString() {
        return String.format("%d/%d", this.n, this.d);
    }

    /**
     * Converts n/d to a proper double
     */
    @Override
    public double toDouble() {
        return (double) this.n / (double) this.d;
    }
}

class WholeInteger implements Number {
    public int n;

    public WholeInteger(int n) {
        this.n = n;
    }

    /**
     * always return value of n
     */
    @Override
    public int numerator() {
        return this.n;
    }

    /**
     * denominator should always be 1
     */
    @Override
    public int denominator() {
        return 1;
    }

    /**
     * support adding between both WholeNumber and Fraction
     */
    @Override
    public Number add(Number other) {
        int num = this.numerator() * other.denominator() + other.numerator() * this.denominator();
        int den = this.denominator() * other.denominator();
        return Simplifier.simplified(num, den);
    }

    /**
     * multiplication between WholeNumber and Fraction
     */
    @Override
    public Number multiply(Number other) {
        int num = this.numerator() * other.numerator();
        int den = this.denominator() * other.denominator();
        return Simplifier.simplified(num, den);
    }

    /**
     * Returns the larger of the two numbers
     */
    @Override
    public Number getMax(Number other) {
        // if true return this, else return other
        return (this.toDouble() > other.toDouble()) ? this : other;
    }

    /**
     * returns n as a string
     */
    public String toString() {
        return String.format("%d", this.n);
    }

    /**
     * returns n as a double
     */
    @Override
    public double toDouble() {
        return (double) this.n;
    }
}

class ExamplesNumber {
    Number f1 = new Fraction(1, 2);
    Number f2 = new Fraction(1, 3);
    Number w1 = new WholeInteger(1);
    Number w2 = new WholeInteger(2);

    boolean testNumerator(Tester t) {
        return t.checkExpect(f1.numerator(), 1) &&
                t.checkExpect(w1.numerator(), 1);
    }

    boolean testDenominator(Tester t) {
        return t.checkExpect(f1.denominator(), 2) &&
                t.checkExpect(w1.denominator(), 1);
    }

    boolean testAdd(Tester t) {
        return t.checkExpect(f1.add(f2), new Fraction(5, 6)) &&
                t.checkExpect(w1.add(w2), new WholeInteger(3)) &&
                t.checkExpect(f1.add(w1), new Fraction(3, 2));
    }

    boolean testMultiply(Tester t) {
        return t.checkExpect(f1.multiply(f2), new Fraction(1, 6)) &&
                t.checkExpect(w1.multiply(w2), new WholeInteger(2)) &&
                t.checkExpect(f1.multiply(w1), new Fraction(1, 2));
    }

    boolean testGetMax(Tester t) {
        return t.checkExpect(f1.getMax(f2), f1) &&
                t.checkExpect(w1.getMax(w2), w2) &&
                t.checkExpect(f1.getMax(w1), w1);
    }

    boolean testToString(Tester t) {
        return t.checkExpect(f1.toString(), "1/2") &&
                t.checkExpect(w1.toString(), "1");
    }

    boolean testToDouble(Tester t) {
        // found checkInexact which does something cool
        return t.checkInexact(f1.toDouble(), 0.5, 0.01) &&
                t.checkInexact(w1.toDouble(), 1.0, 0.01);
    }

    // *-------------*
    // | EXPLORATION |
    // *-------------*
    // helpers
    Fraction tenth = new Fraction(1, 10);
    Fraction fifth = new Fraction(2, 10); // simplifies to 1/5
    Fraction almostThird = new Fraction(3, 10); // darn
    // exploration calculation manifestation
    double doublesAreNotPrecise = 0.1 + 0.2 + 0.3;
    double doublesAreWeird = 0.1 + (0.2 + 0.3);
    // b.c. expressions eval left to right next line is pretty much same as
    // doublesAreNotPrecise = 0.1 + 0.2 + 0.3;
    String outcomeOne = (tenth.add(fifth)).add(almostThird).toString(); // calls stack like (0.1 + 0.2) + 0.3
    String outcomeTwo = tenth.add(fifth.add(almostThird)).toString(); // calls stack like 0.1 + (0.2 + 0.3)
    // so basically floating point math would be better with fractions instead of
    // mantissa exponent
}