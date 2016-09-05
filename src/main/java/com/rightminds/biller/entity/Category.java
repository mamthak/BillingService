package com.rightminds.biller.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rightminds.biller.AllConstants;
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
@Table(name = "CATEGORY")
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_seq_gen")
    @SequenceGenerator(name = "category_seq_gen", sequenceName = "category_seq_id")
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "IMAGEPATH")
    @JsonProperty("imagepath")
    private String imagePath;

    @Column(name = "DELETED")
    private boolean deleted = false;

    @CreatedDate
    @Column(name = "CREATEDON")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = AllConstants.DATE_TIME_FORMAT)
    @JsonProperty("created")
    private Date createdOn;

    @LastModifiedDate
    @Column(name = "LASTMODIFIEDON")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = AllConstants.DATE_TIME_FORMAT)
    private Date lastModifiedOn;

    public Category() {
    }

    public Category(Integer id) {
        this.id = id;
    }

    public Category(String name, String description, String imagePath, Date createdOn) {
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
        this.createdOn = createdOn;
    }

    public Category(Integer id, String name, String description, String imagePath, Date createdOn) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
        this.createdOn = createdOn;
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

    public static Category fromMap(Map map) {
        Integer id = CastUtil.getInteger(map.get("id"));
        String name = CastUtil.getString(map.get("name"));
        String imagePath = CastUtil.getString(map.get("imagepath"));
        String description = CastUtil.getString(map.get("description"));
        Date createdOn = CastUtil.getDate(map.get("created"));
        if (id == null)
            return new Category(name, description, imagePath, createdOn);
        return new Category(id, name, description, imagePath, createdOn);
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
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createdOn=" + createdOn +
                ", lastModifiedOn=" + lastModifiedOn +
                '}';
    }
}
