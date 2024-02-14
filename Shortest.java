class Shortest {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.exit(0);
        }
        String shortest = args[0];
        for (String s : args) {
            if (s.length() < shortest.length()) {
                shortest = s;
            }
        }
        System.out.println(shortest);
    }
}