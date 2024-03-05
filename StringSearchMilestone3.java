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

interface Query {
    boolean matches(String s);
}

class NotQuery implements Query {
    public Query q;

    public NotQuery(Query q) {
        this.q = q;
    }

    public boolean matches(String s) {
        return !q.matches(s);
    }
}

class ContainsQuery implements Query {
    public String query;

    public ContainsQuery(String query) {
        this.query = query;
    }

    public boolean matches(String s) {
        return s.contains(query);
    }
}

class LengthQuery implements Query {
    public int query;

    public LengthQuery(int q) {
        this.query = q;
    }

    public boolean matches(String s) {
        return s.length() == this.query;
    }
}

class GreaterQuery implements Query {
    public int query;

    public GreaterQuery(int q) {
        this.query = q;
    }

    public boolean matches(String s) {
        return s.length() > this.query;
    }
}

class LessQuery implements Query {
    public int query;

    public LessQuery(int q) {
        this.query = q;
    }

    public boolean matches(String s) {
        return s.length() < this.query;
    }
}

class StartsQuery implements Query {
    public String query;

    public StartsQuery(String q) {
        this.query = q;
    }

    public boolean matches(String s) {
        return s.startsWith(this.query);
    }
}

class EndsQuery implements Query {
    public String query;

    public EndsQuery(String q) {
        this.query = q;
    }

    public boolean matches(String s) {
        return s.endsWith(this.query);
    }
}

class StringSearch{
    static int intParse(String queryValue) {
        try {
            return Integer.parseInt(queryValue);
        } catch (NumberFormatException e) {
            System.err.println("Error parsing int: " + e);
            return 0;
        }
    }

    static String stringParse(String queryValue) {
        String ret;
        // Remove quotations
        if (queryValue.startsWith("\"") && queryValue.endsWith("\"")) {
            ret = queryValue.substring(1, queryValue.length() - 1);
        }
        if (queryValue.startsWith("'") && queryValue.endsWith("'")) {
            ret = queryValue.substring(1, queryValue.length() - 1);
        } else {
            ret = queryValue;
        }
        return ret;
    }

    static Query readQuery(String q) {
        String[] parts;
        String type;
        String value;
        if (q.startsWith("not(")) {
            // TODO: Handle not query
            type = "not";
            // not(contains="hello")
            value = q.substring(4, q.length() - 1);
        } else {
            parts = q.split("=");
            type = parts[0]; // query type
            value = parts[1]; // query value
        }
        switch (type) {
            case "length":
                return new LengthQuery(intParse(value));
            case "greater":
                return new GreaterQuery(intParse(value));
            case "less":
                return new LessQuery(intParse(value));
            case "contains":
                return new ContainsQuery(stringParse(value));
            case "starts":
                return new StartsQuery(stringParse(value));
            case "ends":
                return new EndsQuery(stringParse(value));
            case "not":
                return new NotQuery(readQuery(value));
            default:
                return null;
        }
    }
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

        // For only one argument, print all lines then exit
        if (args.length == 1) {
            for (String line : lines) {
                System.out.println(line);
            }
            System.exit(0);
        }

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
        // Only taking one query for milestone 1
        if (args.length == 2) {
            String queryString = args[1];
            // DO NOT PASS NOT() QUERY ITS NOT IMPLEMENTED
            Query query = readQuery(queryString);

            for (String line : lines) {
                if (query.matches(line)) {
                    System.out.println(line);
                }
            }
            System.exit(0);
        }
    }
}
