/**
 * Count occurances of the first string in the rest of the strings
 * in args[].
 * Cases:
 * len = 0 => No keyword is given
 * len = 1 => Keyword with prefix --
 * len = 1 => Count keywords
 */
class Debug {
    public static void main(String[] args) {
        int len = args.length;
        if (len == 0)
            System.out.println("No keyword is given");
        else if (!args[0].substring(0, 2).equals("--")) {
            System.out.println("Write a keyword with the prefix --");
        } else {
            String keyword = args[0].substring(2);
            int count = 0;
            for (int i = 1; i < len; i++) {
                if (keyword.equals(args[i]))
                    count++;
            }
            System.out.println("The answer is " + count);
        }
    }
}