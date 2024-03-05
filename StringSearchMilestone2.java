import java.nio.file.*;
import java.io.IOException;

class FileHelper {
    static String[] getLines(String path) {
        try {
            return Files.readAllLines(Paths.get(path)).toArray(String[]::new);
        } catch (IOException e) {
            System.err.println("Error reading file " + path + ": " + e);
            return new String[] { "Error reading file " + path + ": " + e };
        }
    }
}

class ContainsQuery {
    public String query;

    public ContainsQuery(String query) {
        this.query = query;
        // Remove quotations
        if (query.startsWith("\"") && query.endsWith("\"")) {
            this.query = query.substring(1, query.length() - 1);
        }
        if (query.startsWith("'") && query.endsWith("'")) {
            this.query = query.substring(1, query.length() - 1);
        }
    }

    public boolean matches(String line) {
        return line.contains(query);
    }
}

class StringSearch{
    /**
     * Search for a query in a file and perform args
     * Takes 1-3 args
     * Expects `java StringSearch <file> <query> <transform>
     * 
     * @param args
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println(
                    "Usage: java StringSearch <file> <query> <transform>\nPlease provide at least one argument");
            System.exit(1);
        }

        // Process <file> arg
        String path = args[0];
        String[] lines = FileHelper.getLines(path);

        /**
         * Process <query> arg
         * length=<number> which matches lines with exactly <number> characters
         * greater=<number> which matches lines with more than <number> characters
         * less=<number> which matches lines with less than <number> characters
         * contains=<string> which matches lines containing the <string>
         * (case-sensitive)
         * 
         * starts=<string> which matches lines starting with the <string>
         * ends=<string> which matches lines ending with the <string>
         * not(<some non-not query>) which matches lines that do not match the inner
         * query
         */
        if (args.length < 2) {
            for (String line : lines) {
                System.out.println(line);
            }
            System.exit(0);
        } else {
            // Get the part of the query after the contains=
            String query = args[1].split("=")[1];
            System.out.println("Query: " + query);
            ContainsQuery containsQuery = new ContainsQuery(query);
            for (String line : lines) {
                if (containsQuery.matches(line)) {
                    System.out.println(line);
                }
            }
        }
    }
}
