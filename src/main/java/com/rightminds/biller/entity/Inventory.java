package com.rightminds.biller.entity;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "INVENTORY")
public class Inventory {

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @OneToOne(mappedBy = "inventory")
    private Menu menu;

    @CreatedDate
    @Column(name = "CREATED_ON")
    private ZonedDateTime createdOn;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_ON")
    private ZonedDateTime lastModifiedOn;

    public Inventory(Integer id, String name, String description, Integer quantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
    }

    @PrePersist
    protected void onCreate() {
        setCreatedOn();
        setLastModifiedOn();
    }

    @PreUpdate
    protected void onUpdate() {
        setLastModifiedOn();
    }

    public void setCreatedOn() {
        this.createdOn = ZonedDateTime.now();
    }

    public void setLastModifiedOn() {
        this.lastModifiedOn = ZonedDateTime.now();
    }


}
