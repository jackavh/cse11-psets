import tester.*;

/**
 * Specification:
 * 
 * ---
 * Class User
 * a class called User. The class User represents users, who are the authors of
 * Videos/or just a User watching videos.
 * 
 * A User contains two fields:
 * 
 * username, which is a String
 * displayName, which is a String
 * 
 * ---
 * Interface Comment
 * write an interface called Comment with three methods inside Youtube.java:
 * 
 * public boolean isCommentByAuthor(User author);
 * public int totalLikes();
 * public String unrollCommentThread();
 * 
 * ---
 * Class VideoComment
 * VideoComment, implements Comment and has four fields:
 * text, a String
 * likes, an int
 * replies an int
 * author, a User
 * This class should implement the methods as follows:
 * 
 * isCommentByAuthor should return true when the given author (in the argument)
 * is the same as the author of this VideoComment, false otherwise.
 * totalLikes should return the number of likes on this VideoComment object
 * unrollCommentThread should return a string in the following format:
 * 
 * <author username>
 * <l> likes; <r> replies
 * <text>
 * 
 * where <author> is replaced by the author’s name, <l> is replaced by the
 * number of likes on the VideoComment, <r> is replaced by the number of replies
 * on the VideoComment and <text> is replaced by the text of the Comment. The
 * string should end in a new line ("\n" character).
 * 
 * It should also add a new method public int totalInteractions();. This method
 * takes in no arguments, and return the sum of total number of replies and
 * likes for the given Comment.
 * 
 * ---
 * Class ReplyComment
 * ReplyComment, which should implement Comment and has four fields:
 * text, a String
 * likes, an int
 * author, a User
 * replyTo, a Comment
 * 
 * This class should implement the methods as follows:
 * 
 * isCommentByAuthor should return true when the given author is the same as the
 * author of this ReplyComment and the replyTo Comment is also by the same
 * author.
 * totalLikes should return the total number of likes on this ReplyComment
 * object plus the total likes of its replyTo Comment.
 * unrollCommentThread should return a string in the following format:
 * 
 * <replyTo contents>
 * <author username>
 * <l> likes
 * <text>
 * 
 * where the bottom three parts are the same format as in VideoComment, and the
 * first part is the unrolled version of the replyTo Comment. Similar to the
 * method in VideoComment, this should also end in a new line character.
 */

class User {
    public String username;
    public String displayName;

    public User(String username, String displayName) {
        this.username = username;
        this.displayName = displayName;
    }
}

interface Comment {
    public boolean isCommentByAuthor(User author);

    public int totalLikes();

    public String unrollCommentThread();
}

class VideoComment implements Comment {
    public String text;
    public int likes;
    public int replies;
    public User author;

    /**
     * * isCommentByAuthor should return true when the given author (in the
     * argument)
     * is the same as the author of this VideoComment, false otherwise.
     */
    @Override
    public boolean isCommentByAuthor(User author) {
        return this.author.equals(author);
    }

    /**
     * * totalLikes should return the number of likes on this VideoComment object
     */
    @Override
    public int totalLikes() {
        return this.likes;
    }

    /**
     * * unrollCommentThread should return a string in the following format:
     * 
     * <author username>
     * <l> likes; <r> replies
     * <text>
     */
    @Override
    public String unrollCommentThread() {
        return String.format("%s\n%d likes; %d replies\n%s\n",
                this.author.username, this.likes, this.replies, this.text);
    }

    /**
     * This method takes in no arguments, and return the sum of total number of
     * replies and likes for the given Comment.
     */
    public int totalInteractions() {
        return this.replies + this.likes;
    }

    public VideoComment(String text, int likes, int replies, User author) {
        this.text = text;
        this.likes = likes;
        this.replies = replies;
        this.author = author;
    }
}

class ReplyComment implements Comment {
    public String text;
    public int likes;
    public User author;
    public Comment replyTo;

    /**
     * isCommentByAuthor should return true when the given author is the same as the
     * author of this ReplyComment and the replyTo Comment is also by the same
     * author.
     */
    @Override
    public boolean isCommentByAuthor(User author) {
        return this.author.equals(author) && this.replyTo.isCommentByAuthor(author);
    }

    /**
     * totalLikes should return the total number of likes on this ReplyComment
     * object plus the total likes of its replyTo Comment.
     */
    @Override
    public int totalLikes() {
        return this.likes + this.replyTo.totalLikes();
    }

    /**
     * unrollCommentThread should return a string in the following format:
     * 
     * <replyTo contents>
     * <author username>
     * <l> likes
     * <text>
     * 
     * where the bottom three parts are the same format as in VideoComment, and the
     * first part is the unrolled version of the replyTo Comment. Similar to the
     * method in VideoComment, this should also end in a new line character.
     */
    @Override
    public String unrollCommentThread() {
        return String.format("%s\n%s\n%d likes\n%s\n",
                this.replyTo.unrollCommentThread(),
                this.author.username,
                this.likes,
                this.text);
    }

    public ReplyComment(String text, int likes, User author, Comment replyTo) {
        this.text = text;
        this.likes = likes;
        this.author = author;
        this.replyTo = replyTo;
    }
}

class Youtube {
    /**
     * Tests
     * 
     * VideoComment:
     * ---
     * isCommentByAuthor
     * totalLikes
     * unrollCommentThread
     * totalInteractions
     * 
     * ReplyComment:
     * ---
     * isCommentByAuthor
     * totalLikes
     * unrollCommentThread
     * 
     * Total tests: 14
     */

    // Test instances
    User u1 = new User("test_username1", "Test User Full Name 1");
    User u2 = new User("test_username2", "Test User Full Name 2");

    VideoComment vc1 = new VideoComment("This is a great example to use the Tester Library!", 10, 5, u1);
    VideoComment vc2 = new VideoComment("This is definitely a test!", 23, 1, u1);
    ReplyComment rc1 = new ReplyComment("Yeah, I agree!", 7, u2, vc1);
    ReplyComment rc2 = new ReplyComment("Thanks for acknowledgment!", 4, u1, rc1);
    ReplyComment rc3 = new ReplyComment("Yep these are tests!", 1337, u1, vc2);

    // totalLikes Tests
    boolean testTotalLikes(Tester t) {
        return t.checkExpect(vc1.totalLikes(), 10) &&
                t.checkExpect(vc2.totalLikes(), 23) &&
                t.checkExpect(rc1.totalLikes(), 10 + 7) &&
                t.checkExpect(rc2.totalLikes(), 10 + 7 + 4);
    }

    // isCommentByAuthor Tests
    boolean testIsCommentByAuthor(Tester t) {
        return t.checkExpect(vc1.isCommentByAuthor(u1), true) &&
                t.checkExpect(vc2.isCommentByAuthor(u2), false) &&
                t.checkExpect(rc1.isCommentByAuthor(u2), false) &&
                t.checkExpect(rc3.isCommentByAuthor(u1), true);
    }

    // unrollCommentThreadTest
    // Note: Not sure if this is a good method for testing complex strings
    boolean testUnrollCommentThread(Tester t) {
        String test1 = "test_username1\n" +
                "10 likes; 5 replies\n" +
                "This is a great example to use the Tester Library!\n";
        String test2 = "test_username1\n" +
                "23 likes; 1 replies\n" +
                "This is definitely a test!\n";
        String test3 = "test_username1\n" +
                "10 likes; 5 replies\n" +
                "This is a great example to use the Tester Library!\n" +
                "\n" +
                "test_username2\n" +
                "7 likes\n" +
                "Yeah, I agree!\n";
        String test4 = "test_username1\n" +
                "10 likes; 5 replies\n" +
                "This is a great example to use the Tester Library!\n" +
                "\n" +
                "test_username2\n" +
                "7 likes\n" +
                "Yeah, I agree!\n" +
                "\n" +
                "test_username1\n" +
                "4 likes\n" +
                "Thanks for acknowledgment!\n";
        return t.checkExpect(vc1.unrollCommentThread(), test1) &&
                t.checkExpect(vc2.unrollCommentThread(), test2) &&
                t.checkExpect(rc1.unrollCommentThread(), test3) &&
                t.checkExpect(rc2.unrollCommentThread(), test4);
    }

    // totalInteractions Tests
    boolean testTotalInteractions(Tester t) {
        return t.checkExpect(vc1.totalInteractions(), 15) &&
                t.checkExpect(vc2.totalInteractions(), 24);
    }
}