import tester.*;

class Triplet {
    public int a;
    public int b;
    public int c;

    public Triplet(int a, int b, int c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
}

public class TripletSelect {
    int[] getBs(Triplet[] triplets) {
        int[] bs = new int[triplets.length];
        for (int i = 0; i < triplets.length; i++) {
            bs[i] = triplets[i].b;
        }
        return bs;
    }

    // tests
    // expect [2, 5, 8]
    Triplet[] test1 = { new Triplet(1, 2, 3), new Triplet(4, 5, 6), new Triplet(7, 8, 9) };
    // expect []
    Triplet[] test2 = {};
    // expect [2]
    Triplet[] test3 = { new Triplet(1, 2, 3) };
    // expect [2, 5, -2, 11]
    Triplet[] test4 = { new Triplet(1, 2, 3), new Triplet(4, 5, 6), new Triplet(7, -2, 9), new Triplet(10, 11, 12) };

    boolean testBs(Tester t) {
        return t.checkExpect(this.getBs(test1), new int[] { 2, 5, 8 })
                && t.checkExpect(this.getBs(test2), new int[] {})
                && t.checkExpect(this.getBs(test3), new int[] { 2 })
                && t.checkExpect(this.getBs(test4), new int[] { 2, 5, -2, 11 });
    }
}
