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

class StringSearch {
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

        for (String line : lines) {
            System.out.println(line);
        }
    }
}
