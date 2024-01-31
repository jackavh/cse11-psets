import tester.*;

// Statement C: In Java, two different methods in the same class can have the
// same parameter name, and arguments passed to one of those methods don’t
// affect the parameter in the other.

class Arithmetic {
    public static int add5(int x) {
        x += 5;
        return x;
    }

    public static int sub5(int x) {
        // x is what was passed as argument
        int test = add5(x);
        // if add5 modifies the x here, then the
        // return statement should just return x
        return x - 5;
    }
}

class Open1C {
    // If these tests pass we know one method doesn't modify anothers parameters
    boolean testParameterReferencing(Tester t) {
        return t.checkExpect(Arithmetic.sub5(10), 5) && // expect 5
                t.checkExpect(Arithmetic.add5(5), 10) && // expect 10
                t.checkExpect(Arithmetic.sub5(0), -5); // expect -5
    }

}