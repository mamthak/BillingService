package com.rightminds.biller.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.rightminds.biller.util.CastUtil;
import lombok.Data;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;

@Entity
@Table(name = "INVENTORY")
@Data
public class Inventory {
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inventory_seq_gen")
    @SequenceGenerator(name = "inventory_seq_gen", sequenceName = "inventory_seq_id")
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @CreatedDate
    @Column(name = "CREATED_ON")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    private Date createdOn;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_ON")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    private Date lastModifiedOn;

    public Inventory() {
    }

    public Inventory(String name, String description, Integer quantity) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
    }

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
        this.createdOn = new Date();
    }

    public void setLastModifiedOn() {
        this.lastModifiedOn = new Date();
    }

    public static Inventory fromMap(Map map) {
        Integer id = CastUtil.getInteger(map.get("id"));
        String name = CastUtil.getString(map.get("name"));
        String description = CastUtil.getString(map.get("description"));
        Integer quantity = CastUtil.getInteger(map.get("quantity"));
        if (id == null)
            return new Inventory(name, description, quantity);
        return new Inventory(id, name, description, quantity);
    }

    @Override
    public boolean equals(Object o) {
        return new EqualsBuilder().reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", createdOn=" + createdOn +
                ", lastModifiedOn=" + lastModifiedOn +
                '}';
    }
}
