import java.util.ArrayList;

public class PositiveMinMaxAvg {
    public static void main(String[] args) {
        // catch less than 2 args
        if (args.length < 2) {
            System.out.println(0);
            System.exit(0);
        }
        // parse args to doubles
        ArrayList<Double> nums = new ArrayList<>();
        for (int i = 0; i < args.length; i++) {
            try {
                double num = Double.parseDouble(args[i]);
                // only add num if it's positive
                if (num >= 0) {
                    nums.add(num);
                }
                // if parseDouble fails it throws a NumberFormatException
            } catch (NumberFormatException e) {
                System.out.println("Arguments must be valid doubles");
                // exit with failure
                System.exit(1);
            }
        }
        // after this nums could be empty if all negative in args
        if (nums.size() == 0) {
            System.out.println(0);
            System.exit(0);
        }

        // find min and max
        double min = nums.get(0);
        double max = nums.get(0);
        for (Double n : nums) {
            if (n < min) {
                min = n;
            }
            if (n > max) {
                max = n;
            }
        }
        System.out.println((min + max) / 2.0);
    }

    /**
     * Test cases (6):
     * From PA6:
     * -1.5 2.5 3.5 5 : expect 3.75
     * (nothing) : expect 0
     * 1 : expect 0
     * 1 2 : expect 1.5
     * 1 7.8 -23.4: expect 4.4
     * -1.5 -2.5 -3.5 -4.5 : expect 0
     */
}
