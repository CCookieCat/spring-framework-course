package com.ccat.springtest.dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name="messages")
public class Message implements Serializable {
    @Id
    @GeneratedValue
    private int id;

    //Sender Data:
    @Size(min=3,max=60)
    private String sender;

    @Email
    @NotBlank
    private String email;

    //Receiver Data:
    private String username;

    //Content:
    @Size(min=5, max=100)
    private String subject;

    @Size(min=5, max=1000)
    private String content;

    //CONSTRUCTORS:
    public Message() {
    }

    public Message(String sender, String email, String username, String subject, String content) {
        this.sender = sender;
        this.email = email;
        this.username = username;
        this.subject = subject;
        this.content = content;
    }

    //GETTERs and SETTERs:
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (id != message.id) return false;
        if (sender != null ? !sender.equals(message.sender) : message.sender != null) return false;
        if (email != null ? !email.equals(message.email) : message.email != null) return false;
        if (username != null ? !username.equals(message.username) : message.username != null) return false;
        if (subject != null ? !subject.equals(message.subject) : message.subject != null) return false;
        return content != null ? content.equals(message.content) : message.content == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (sender != null ? sender.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (subject != null ? subject.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", sender='" + sender + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
