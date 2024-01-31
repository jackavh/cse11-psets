class C1 {
    C2 other;

    C1(C2 other) {
        this.other = other;
    }
}

class C2 {
    int x;
    double y;

    C2(int x, double y) {
        this.x = x;
        this.y = y;
    }
}

class Drill2 {
    public C2 first = new C2(10, 3.5);
    // otherC2 defines something other than the first C2
    public C2 otherC2 = new C2(1, 2.0);
    public C1 second = new C1(otherC2);
    public C1 third = new C1(first); // assigns third as a reference to temp C1 obj
}