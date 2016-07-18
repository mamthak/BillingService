package com.rightminds.biller.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Table(name = "MENU")
public class Menu {

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @OneToOne
    @JoinColumn(name = "inventory_id", referencedColumnName = "id")
    private Inventory inventory;

    @CreatedDate
    @Column(name = "CREATED_ON")
    private ZonedDateTime createdOn;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_ON")
    private ZonedDateTime lastModifiedOn;

    public Menu(Integer id, String name, String description, BigDecimal amount, Inventory inventory) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.inventory = inventory;
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
