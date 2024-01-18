class User {
    /*
     * Stores basic data on YouTube accounts
     * username is the @username on youtube
     * displayName is also referred to as the full name
     * subscribers is number of subscribers
     * videoCount is number of videos
     */
    public String username;
    public String displayName;
    public int subscribers;
    public int videoCount;

    public User(String username, String displayName, int subscribers, int videoCount) {
        this.username = username;
        this.displayName = displayName;
        this.subscribers = subscribers;
        this.videoCount = videoCount;
    }

    public String toText() {
        /*
         * Returns a formatted string in the following format
         * "displayName @username"
         */
        return String.format("%s @%s", displayName, username);
    }
}

class Video {
    /*
     * Class that organizes simple data on a youtube ideo
     * Title is the title of the video
     * User is the creator or publisher of the video as User obj
     * numLikes is an integer that stores the number of likes for the video
     * id is String that stores the unique id the video on youtube
     * Ex: https://www.youtube.com/watch?v=bOCHTHkBoAs
     * The id for the above link is bOCHTHkBoAs
     */
    public String title;
    public User creator;
    public int numLikes;
    public String id;

    public Video(String title, User creator, int numLikes, String id) {
        this.title = title;
        this.creator = creator;
        this.numLikes = numLikes;
        this.id = id;
    }

    boolean longerThan(Video other) {
        /*
         * Returns true if this video's title is longer
         * than Video other, false otherwise
         */

        if (this.title.length() > other.title.length()) {
            return true;
        }
        return false;
    }

    boolean moreLikes(Video other) {
        /*
         * Returns true if this video has more likes
         * than the Video other, false otherwise
         */
        if (this.numLikes > other.numLikes) {
            return true;
        }
        return false;
    }

    public String toText() {
        /*
         * Formats creator data, video title, and numLikes with this format
         * "name @username : title : numLikes"
         * Takes advantage of User.toText() which already formats
         * into "name @username"
         */
        return String.format("%s : %s : %s Likes", this.creator.toText(), this.title, this.numLikes);
    }

    String toLink() {
        /*
         * Formats this.id into a valid youtube link
         */
        return String.format("https://www.youtube.com/watch?v=%s", this.id);
    }
}

class ExampleVideos {
    /*
     * --- Work Checklist
     * 2 classes, each with fields as described above
     * 5 total methods (one in User and four in Video)
     * 2 examples for each method (10 total examples)
     * Four Video objects with the link and question above answered
     * Three User objects (to use to construct the Videos)
     */

    // --- YouTube Accounts
    // Fireship YouTube account as of 1/17/24
    private User fireship = new User("Fireship", "Fireship", 2740000, 601);
    // JacobsSchoolNews YouTube account as of 1/17/24
    private User jacobsnews = new User("JacobsSchoolNews", "JacobsSchoolNews", 3230, 442);
    // SciShow YouTube account as of 1/17/24
    private User scishow = new User("SciShow", "SciShow", 7820000, 3500);

    // -- Videos
    // The Deadliest Toxins on Earth from SciShow as of 1/17/24
    // https://www.youtube.com/watch?v=2z35_1e1MtI
    private Video toxins = new Video("The Deadliest Toxins on Earth", scishow, 114000, "2z35_1e1MtI");
    // 10 Math Concepts for Programmers from Fireship as of 1/17/24
    // https://www.youtube.com/watch?v=bOCHTHkBoAs
    private Video mathConcepts = new Video("10 Math Concepts for Programmers", fireship, 80000, "bOCHTHkBoAs");
    // God-Tier Developer Roadmap from Fireship as of 1/17/24
    // https://www.youtube.com/watch?v=pEfrdAtAmqk
    private Video roadmap = new Video("God-Tier Developer Roadmap", fireship, 223000, "pEfrdAtAmqk");
    // Piranha vs. Arapaima from JacobsSchoolNews
    // https://www.youtube.com/watch?v=eurx-PN2B5w
    private Video piranha = new Video("Piranha vs. Arapaima", jacobsnews, 126, "eurx-PN2B5w");
    //
    // Were there any parts of the Video class that you couldn’t represent with the
    // class design we chose?
    //
    // Yes, for all of the videos the Video class does not represent dislikes,
    // comments, publish date, related videos, etc...

    // -- Examples for each method
    // 1.
    String firetest = fireship.toText(); // Expected: "Fireship @Fireship"
    String scitest = scishow.toText(); // Expected: "SciShow @SciShow"
    // 2.
    boolean longerTest1 = mathConcepts.longerThan(toxins); // Expected: true
    boolean longerTest2 = piranha.longerThan(roadmap); // Expected: false
    // 3.
    boolean moreTest1 = roadmap.moreLikes(toxins); // Expected: true
    boolean moreTest2 = piranha.moreLikes(mathConcepts); // Expected: false
    // 4.
    String killerString = toxins.toText(); // Expected: "SciShow @SciShow : The Deadliest Toxins on Earth : 114000
                                           // Likes"
    String piranhaSring = piranha.toText();
    // Expected:
    // "JacobsSchoolNews @JacobsSchoolNews : Piranha vs. Arapaima : 126 Likes"
    //
    // 5.
    String conceptsLink = mathConcepts.toLink(); // Expected: "https://www.youtube.com/watch?v=bOCHTHkBoAs"
    String roadmapLink = roadmap.toLink(); // Expected: "https://www.youtube.com/watch?v=pEfrdAtAmqk"
}