package pro.x_way.javafx.Model;

import java.util.Date;

public class Message {
    private int id;
    private String name;
    private String message;
    private Date date;


    public Message(int id, String name, String message, Date date) {
        this.id = id;
        this.name = name;
        this.message = message;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return name;
    }
}
