package com.ccat.springtest.dao;

import com.ccat.springtest.validation.CustomEmailValidator;
import com.ccat.springtest.validation.FormValidationGroup;
import com.ccat.springtest.validation.PersistenceValidationGroup;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name="offers")
public class Offer {

    @Id //PK
    @GeneratedValue //Auto-Increment
    private int id;

//    @Size(min=3,max=100,message="Name must be between 3 and 100 characters.") //Hibernate Validation
//    private String name;
//
//    @NotNull
////    @Pattern(regexp=".*\\@.*\\..*", message="Invalid Email.")
//    @Email(message="Invalid Email Address.")
////    @CustomEmailValidator(min=6, message="The Email is not valid.")
//    private String email;

    @ManyToOne //Many to One Relationship + join_column
    @JoinColumn(name="username")
    private User user;

    @Column(name="information")
    @Size(min=20,max=255,message="Text must be between 20 and 255 characters.",
            groups={PersistenceValidationGroup.class, FormValidationGroup.class})
    private String information;

    //CONSTRUCTORS:
    public Offer() {
        this.user = new User(); //create empty User Object
    }

    public Offer(User user, String information) {
        this.user = user;
        this.information = information;
    }

    public Offer(int id, User user, String information) {
        this.id = id;
        this.user = user;
        this.information = information;
    }

    //GETTERS and SETTERS:
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUsername() {
        return user.getUsername();
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Offer offer = (Offer) o;

        if (id != offer.id) return false;
        if (user != null ? !user.equals(offer.user) : offer.user != null) return false;
        return information != null ? information.equals(offer.information) : offer.information == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (information != null ? information.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Offer{" +
                "id=" + id +
                ", user=" + user +
                ", information='" + information + '\'' +
                '}';
    }
}
