class Stats {
    public static double[] toDoubles(String[] strings) {
        double[] numbers = new double[strings.length];
        for (int i = 0; i < strings.length; i++) {
            numbers[i] = Double.parseDouble(strings[i]);
        }
        return numbers;
    }

    public static double product(double[] numbers) {
        double product = 1;
        for (double n : numbers) {
            product *= n;
        }
        return product;
    }

    public static double mean(double[] numbers) {
        double sum = 0;
        for (double n : numbers) {
            sum += n;
        }
        return sum / numbers.length;
    }

    public static double total(double[] numbers) {
        double sum = 0;
        for (double n : numbers) {
            sum += n;
        }
        return sum;
    }

    public static double max(double[] numbers) {
        double max = numbers[0];
        for (double n : numbers) {
            if (n > max) {
                max = n;
            }
        }
        return max;
    }

    public static double min(double[] numbers) {
        double min = numbers[0];
        for (double n : numbers) {
            if (n < min) {
                min = n;
            }
        }
        return min;
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.exit(0);
        }
        String option = args[0];
        String[] rest = java.util.Arrays.copyOfRange(args, 1, args.length);
        if (option.equals("--product")) {
            System.out.println(product(toDoubles(rest)));
        } else if (option.equals("--mean")) {
            System.out.println(mean(toDoubles(rest)));
        } else if (option.equals("--total")) {
            System.out.println(total(toDoubles(rest)));
        } else if (option.equals("--max")) {
            System.out.println(max(toDoubles(rest)));
        } else if (option.equals("--min")) {
            System.out.println(min(toDoubles(rest)));
        } else {
            System.out.println("Bad option " + option);
        }
    }
}