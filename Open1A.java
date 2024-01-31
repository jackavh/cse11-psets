// Statement A: In Java, two different classes can define a field with the same
// name and type.

class A {
    public int x;

    public A(int x) {
        this.x = x;
    }
}

class B {
    public int x;

    public B(int x) {
        this.x = x;
    }
}

class Open1A {
    static A testA = new A(3);
    static B testB = new B(5);

    // They are different objects and so even though though both have a .x field,
    // those fields are different
    String example = String.format("A ref:%s   |   B ref:%s", testA.hashCode(), testB.hashCode());
    boolean sameObject = testA.hashCode() == testB.hashCode(); // false
}