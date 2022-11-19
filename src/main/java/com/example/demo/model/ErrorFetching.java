package com.example.demo.model;


import javax.persistence.*;
/**
 * Entity object, stored in database, contains information about errors during fetching weather information from external service
 */
@Entity
@Table(name = "error_fetching")
@Access(AccessType.FIELD)
public class ErrorFetching {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    String uniqueId;

    String Message;

    public ErrorFetching(String uniqueId, String message) {
        this.uniqueId = uniqueId;
        Message = message;
    }

    public ErrorFetching() {

    }

    public String getUniqueId() {
        return uniqueId;
    }

    public String getMessage() {
        return Message;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
