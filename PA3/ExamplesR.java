/**
 * Create a class named R that has a field of type String and a field of type R.
 * Give it a constructor that initializes both fields.
 */
class R {
    String s;
    R test;

    public R(String s, R test) {
        this.s = s;
        this.test = test;
    }
}

class ExamplesR {
    /**
     * 1.
     * Construct an example R object. Were you able to? Explain your example if you
     * were able to, and explain why you think it’s not possible if you weren’t.
     */

    // Here is first attempt, trying to create a new R object for each R parameter
    // This obviously doesn't work because there would need to be an infinite number
    // of R objects not enough compute on earth for that
    //
    // R example = new R("a", new R("b", new R("c", ... )))
    //
    // Here is second attempt, but throws error:
    // Cannot reference a field before it is definedJava(570425419)
    // When running, get:
    // ExamplesR.java:30: error: self-reference in initializer
    //
    // R example = new R("a", example);
    //
    // There is no way for a class to reference an instance of itself in its
    // constructor, because for that class to be defined, it has to be defined

    /**
     * 2.
     * On YouTube, it’s possible to reply to a reply to a Comment (that’s not a
     * typo, it’s a reply to a reply). This is true of many systems, like email,
     * Facebook comments, Piazza followups, and so on. With the class structure in
     * Drill3 with VideoComment and CommentReply (that is, without changing the
     * fields as described above), could you construct an example of a reply to a
     * reply to a Comment? Why or why not?
     * 
     * So a Comment has a VideoComment reference.
     * A VideoComment desn't reference anything.
     * A comment does not have a reference to another comment
     * 
     * No, you could not construct a reply to a reply to a Comment using Drill3.
     * The maximum depth Drill3 can have is a reply to a comment because
     * the CommentReply class has no way to say that is is the comment of another
     * CommentReply class, only of a VideoComment class.
     */
}