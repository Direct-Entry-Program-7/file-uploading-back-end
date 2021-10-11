package lk.ijse.dep7.fileuploadingbackend.dto;

import java.io.Serializable;

public class StudentDTO implements Serializable {
    private String id;
    private String name;
    private String address;
    private String contact;
    private String picture;

    public StudentDTO() {
    }

    public StudentDTO(String name, String address, String contact, String picture) {
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.picture = picture;
    }

    public StudentDTO(String id, String name, String address, String contact, String picture) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.picture = picture;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "StudentDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                ", picture='" + picture + '\'' +
                '}';
    }
}
