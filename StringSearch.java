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

interface Transform {
    String transform(String s);
}

class UpperTransform implements Transform {
    public String transform(String s) {
        return s.toUpperCase();
    }
}

class LowerTransform implements Transform {
    public String transform(String s) {
        return s.toLowerCase();
    }
}

class FirstTransform implements Transform {
    public int query;

    public FirstTransform(int q) {
        this.query = q;
    }

    public String transform(String s) {
        if (s.length() > this.query) {
            return s.substring(0, this.query);
        }
        return s;
    }
}

class LastTransform implements Transform {
    public int query;

    public LastTransform(int q) {
        this.query = q;
    }

    public String transform(String s) {
        if (s.length() > this.query) {
            return s.substring(s.length() - this.query);
        }
        return s;
    }
}

class ReplaceTransform implements Transform {
    public String find;
    public String replace;

    public ReplaceTransform(String find, String replace) {
        this.find = find;
        this.replace = replace;
    }

    public String transform(String s) {
        return s.replaceAll(this.find, this.replace);
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

class StringSearch {
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

    static Transform readTransform(String t) {
        String[] parts = t.split("=");
        String type = parts[0];
        String value = ""; // empty string makes big bag red squiggly go away
        // Not always a value associated with a transform
        if (parts.length > 1) {
            value = parts[1];
        }
        if (type.equals("upper")) {
            return new UpperTransform();
        } else if (type.equals("lower")) {
            return new LowerTransform();
        } else if (type.equals("first")) {
            return new FirstTransform(intParse(value));
        } else if (type.equals("last")) {
            return new LastTransform(intParse(value));
        } else if (type.equals("replace")) {
            String[] replaceParts = value.split(";");
            String find = replaceParts[0];
            String replace = replaceParts[1];
            return new ReplaceTransform(stringParse(find), stringParse(replace));
        } else {
            return null;
        }
    }

    static boolean matchesAll(Query[] qs, String s) {
        boolean match = true;
        for (Query q : qs) {
            match = match && q.matches(s);
        }
        return match;
    }

    static String applyAll(Transform[] ts, String s) {
        for (Transform t : ts) {
            s = t.transform(s);
        }
        return s;
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
            if (query == null) {
                System.err.println("Invalid query");
                System.exit(1);
            }

            for (String line : lines) {
                if (query.matches(line)) {
                    System.out.println(line);
                }
            }
            System.exit(0);
        }

        /**
         * Proceess <transform> arg
         * 
         * upper which transforms the line to uppercase
         * lower which transforms the line to lowercase
         * first=<number> which transforms the line by taking the first <number>
         * characters of the line. If there are fewer than <number> characters, produces
         * the whole line
         * 
         * last=<number> which transforms the line by taking the last <number>
         * characters of the line. If there are fewer than <number> characters, produces
         * the whole line
         * 
         * replace=<string>;<string> which transforms the line by replacing all
         * appearances of the first string with the second (some lines might have no
         * replacements, and won’t be transformed by this transform)
         */
        if (args.length == 3) {
            String[] querieStrings = args[1].split("&");
            Query[] queries = new Query[querieStrings.length];
            for (int i = 0; i < queries.length; i++) {
                queries[i] = readQuery(querieStrings[i]);
                if (queries[i] == null) {
                    System.err.println("Invalid query");
                    System.exit(1);
                }
            }

            String[] transformStrings = args[2].split("&");
            Transform[] transforms = new Transform[transformStrings.length];
            for (int i = 0; i < transforms.length; i++) {
                transforms[i] = readTransform(transformStrings[i]);
                if (transforms[i] == null) {
                    System.err.println("Invalid transform");
                    System.exit(1);
                }
            }
            for (String line : lines) {
                if (matchesAll(queries, line)) {
                    System.out.println(applyAll(transforms, line));
                }
            }
        }
        System.exit(0);
    }
}
