package lk.ijse.dep7.fileuploadingbackend.dto;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbTransient;

import java.io.Serializable;
import java.util.Base64;

public class StudentDTO implements Serializable {
    private String id;
    private String name;
    private String address;
    private String contact;
    @JsonbTransient
    private byte[] picture;

    public StudentDTO() {
    }

    public StudentDTO(String name, String address, String contact, byte[] picture) {
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.picture = picture;
    }

    public StudentDTO(String id, String name, String address, String contact, byte[] picture) {
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

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    @JsonbProperty("picture")
    public String getPicDataURL() {
        if (picture != null){
            String base64ImageData = Base64.getEncoder().encodeToString(picture);
            return "data:image/*;base64," + base64ImageData;
        }
        return null;
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
