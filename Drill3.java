class VideoComment {
    /*
     * In the file Drill3.java, write a class called VideoComment that has three
     * fields: one field called text of type String, one field called likes of type
     * int, and one field called replies of type int. Give it a constructor of three
     * arguments that initializes those fields. In it, write the following methods:
     */
    public String text;
    public int likes;
    public int replies;

    public VideoComment(String text, int likes, int replies) {
        this.text = text;
        this.likes = likes;
        this.replies = replies;
    }

    public boolean hasMention(String username) {
        /*
         * hasMention which takes a String called username and checks if the string @
         * followed by that username apppears in the Comment contents, returning true if
         * it does and false otherwise. (There are some interesting cases for this
         * method. For example, to check if we have the username “dummy” in
         * “@dummy1 @dummy2”, hasMention() should return false in this case because
         * username dummy1 and dummy2 is not the same as dummy, while “hello @dummy
         * world” and “CSE 11 is a cool class @dummy” should return true)
         */

        if (this.text.contains("@" + username + " ")) {
            return true;
        }
        int mentionLength = username.length() + 1;
        // if the substring at the very end of this.text is "@username" then return true
        // these two checks work for hasMention because a mention either ends in a space
        // or end of string
        if (this.text.substring(this.text.length() - mentionLength, this.text.length()).equals("@" + username)) {
            return true;
        }
        return false;
    }

    public boolean hasReply() {
        /*
         * hasReply which takes no arguments and returns true if the comment has one or
         * more replies, false otherwise.
         */
        return this.replies > 0;
    }

    public String firstMention() {
        /*
         * takes no arguments and returns a String containing the substring between the
         * first appearance of the @ character in the text and the first space character
         * after that. Do not include the @ character and the space character. If there
         * is no space after the @, or if there’s no @, then an empty string "" should
         * be returned.
         * 
         * this implementation also checks for mentions at the end of string.
         */

        int atIdx = -1;
        for (int i = 0; i < this.text.length(); i++) {
            char c = this.text.charAt(i);
            if (c == '@') {
                atIdx = i;
            }
            if (atIdx != -1 && c == ' ') {
                return this.text.substring(atIdx, i);
            }
        }
        if (atIdx != -1) {
            return this.text.substring(atIdx);
        }
        return "";
    }
}

class CommentReply {
    /*
     * CommentReply that has four fields: one called replyTo of type VideoComment,
     * one called text of type String, one called likes of type int, and one field
     * called replies of type int. Give it a constructor of four arguments that
     * initializes these fields. In it, write the following methods:
     */
    public VideoComment replyTo;
    public String text;
    public int likes;
    public int replies;

    public CommentReply(VideoComment replyTo, String text, int likes, int replies) {
        this.replyTo = replyTo;
        this.text = text;
        this.likes = likes;
        this.replies = replies;
    }

    public boolean morePopularReply() {
        /*
         * morePopularReply which takes no arguments and returns true if this
         * CommentReply has more likes than the VideoComment it is replying to
         */
        return this.likes > replyTo.likes;
    }

    public int allLikes() {
        /*
         * allLikes which takes no arguments and returns the sum of the likes on this
         * CommentReply and on the VideoComment it is replying to
         */
        return this.likes + replyTo.likes;
    }

    public boolean hasMention(String username) {
        /*
         * hasMention which takes a String called username and checks if the string @
         * followed by that username apppears in this CommentReply’s contents or in the
         * VideoComment that is being replied to.
         */
        boolean replyToHasMention = this.replyTo.hasMention(username);
        if (this.text.contains("@" + username + " ")) {
            return true;
        }
        int mentionLength = username.length() + 1;
        // if the substring at the very end of this.text is "@username" then return true
        // these two checks work for hasMention because a mention either ends in a space
        // or end of string
        if (this.text.substring(this.text.length() - mentionLength, this.text.length()).equals("@" + username)) {
            return true;
        }
        return false || replyToHasMention;
    }
}

class Drill3 {
    // Tests
    VideoComment v1 = new VideoComment("This is a test comment with no mention", 124, 0);
    VideoComment v2 = new VideoComment("@dummy1 @dummy2", 23, 76);
    VideoComment v3 = new VideoComment("This is a comment with a @mention that should return a firstMention", 23, 76);

    boolean bv1 = v1.hasMention("Doesn't matter"); // expect: false
    boolean bv2 = v2.hasMention("mention"); // expect: true
    boolean bv3 = v3.hasMention("mention"); // expect: true

    boolean rv1 = v1.hasReply(); // expect: false
    boolean rv2 = v2.hasReply(); // expect: true
    boolean rv3 = v3.hasReply(); // expect: true

    String mv1 = v1.firstMention(); // expect: ""
    String mv2 = v2.firstMention(); // expect: "@mention"
    String mv3 = v3.firstMention(); // expect: "@mention"

    CommentReply c1 = new CommentReply(v1, "This is a test comment a @mention", 178, 2);
    CommentReply c2 = new CommentReply(v2, "This is a test comment with no mention", 2, 5);
    CommentReply c3 = new CommentReply(v3, "This is a test comment with a @mention in a longer string", 22, 2);

    boolean pc1 = c1.morePopularReply(); // expect: true
    boolean pc2 = c2.morePopularReply(); // expect: false
    boolean pc3 = c3.morePopularReply(); // expect: false

    int ac1 = c1.allLikes(); // expect: 302
    int ac2 = c2.allLikes(); // expect: 25
    int ac3 = c3.allLikes(); // expect: 45

    boolean mc1 = c1.hasMention("mention"); // expect: true
    boolean mc2 = c2.hasMention("mention"); // expect: true
    boolean mc3 = c3.hasMention("mention"); // expect: true
}