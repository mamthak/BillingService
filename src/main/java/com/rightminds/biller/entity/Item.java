package com.rightminds.biller.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.rightminds.biller.AllConstants.DATE_TIME_FORMAT;
import static com.rightminds.biller.util.CastUtil.*;

@Entity
@Table(name = "ITEM")
@Data
public class Item {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_seq_gen")
    @SequenceGenerator(name = "item_seq_gen", sequenceName = "item_seq_id")
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "IMAGEPATH")
    @JsonProperty("imagepath")
    private String imagePath;

    @Column(name = "PRICE")
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "CATEGORYID", referencedColumnName = "ID")
    private Category category;

    @Column(name = "ISINVENTORY")
    @JsonProperty("isinventory")
    private boolean isInventory;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @Column(name = "DELETED")
    private boolean deleted = false;

    @CreatedDate
    @Column(name = "CREATEDON")
    @JsonProperty("created")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    private Date createdOn;

    @LastModifiedDate
    @Column(name = "LASTMODIFIEDON")
    private Date lastModifiedOn;

    public Item() {
    }

    public Item(Integer id) {
        this.id = id;
    }

    public Item(String name, String description, String imagePath, BigDecimal price, Category category, boolean isInventory, Integer quantity) {
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
        this.price = price;
        this.category = category;
        this.isInventory = isInventory;
        this.quantity = quantity;
    }

    public Item(String name, String description, String imagePath, BigDecimal price, Category category) {
        this(name, description, imagePath, price, category, false, 0);
    }

    public Item(Integer id, String name, String description, String imagePath,  BigDecimal price, Category category) {
        this(name, description, imagePath, price, category, false, 0);
        this.id = id;
    }

    public Item(Integer id, String name, String description, String imagePath, BigDecimal amount, Category category, boolean isInventory, Integer quantity) {
        this(name, description, imagePath, amount, category, isInventory, quantity);
        this.id = id;
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

    public static Item fromMap(Map map) {
        Integer id = getInteger(map.get("id"));
        String name = getString(map.get("name"));
        String description = getString(map.get("description"));
        String imagePath = getString(map.get("imagepath"));
        BigDecimal amount = getBigDecimal(map.get("price"));
        Category category = new Category(getInteger(map.get("categoryid")));
        boolean isInventory = getBoolean(map.get("isinventory"));
        Integer quantity = getInteger(map.get("quantity"));
        if (id == null)
            return new Item(name, description, imagePath, amount, category, isInventory, quantity);
        return new Item(id, name, description, imagePath, amount, category, isInventory, quantity);
    }

    public Item withUpdatedQuantity(int quantity) {
        int updatedQuantity = this.quantity - quantity;
        return new Item(id, name, description, imagePath, price, category, true, updatedQuantity);
    }

}
