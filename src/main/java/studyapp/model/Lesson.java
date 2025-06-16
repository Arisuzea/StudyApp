package studyapp.model;

public class Lesson {
    private int id;
    private String title;
    private String shortDescription;
    private String content;
    private String topic;
    private String difficulty;

    public Lesson(int id, String title, String shortDescription, String content, String topic, String difficulty) {
        this.id = id;
        this.title = title;
        this.shortDescription = shortDescription;
        this.content = content;
        this.topic = topic;
        this.difficulty = difficulty;
    }

    public Lesson(String title, String shortDescription, String content, String topic, String difficulty) {
        this.title = title;
        this.shortDescription = shortDescription;
        this.content = content;
        this.topic = topic;
        this.difficulty = difficulty;
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

    public String getTopic() {
        return topic;
    }

    public String getDifficulty() {
        return difficulty;
    }
}
