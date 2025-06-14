package studyapp.model;

public class Lesson {
    private int id;
    private String title;
    private String shortDescription;
    private String content;

    public Lesson(int id, String title, String shortDescription, String content) {
        this.id = id;
        this.title = title;
        this.shortDescription = shortDescription;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getContent() {
        return content;
    }
}
