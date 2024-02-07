// Statement B: In Java, one class can define two fields with the same name
as long as they have different types.

class Test {
int same;
String same;
// problems already

public Test(int same1, String same2) {
this.same = same1;
this.same = same2;
// problems!
}
}

class Open1B {
// None of this works because the variable names cannot be the same
// The error stops at line 5 when I try to declare String same with msg
//
// Open1B.java:5: error: variable same is already defined in class Test
Test testing = new Test(5, "no way");
int what = testing.same;
String the = testing.same;
}