package studyapp.model;

public class Quiz {
    private final int id;
    private final String title;
    private final String description;

    /**
     * Constructs a quiz with id, title, and description
     */
    public Quiz(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    /**
     * Returns the quiz ID
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the quiz title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the quiz description
     */
    public String getDescription() {
        return description;
    }
}
