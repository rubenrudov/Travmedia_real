package il.co.travmedia_real.models;

public class Post {
    private String title;
    private String author;
    private String content;
    private String imageLink;

    public Post() {

    }

    public Post(String title, String author, String content, String imageLink) {
        this.title = title;
        this.author = author;
        this.content = content;
        this.imageLink = imageLink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    @Override
    public String toString() {
        return "Post{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", content='" + content + '\'' +
                ", imageLink='" + imageLink + '\'' +
                '}';
    }
}
