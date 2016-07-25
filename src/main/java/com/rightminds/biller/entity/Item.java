package com.rightminds.biller.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

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

    @Column(name = "PRICE")
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "CATEGORYID", referencedColumnName = "ID")
    private Category category;

    @Column(name = "ISINVENTORY")
    private boolean isInventory;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @CreatedDate
    @Column(name = "CREATEDON")
    private Date createdOn;

    @LastModifiedDate
    @Column(name = "LASTMODIFIEDON")
    private Date lastModifiedOn;

    public Item() {
    }

    public Item(String name, String description, BigDecimal price, Category category, boolean isInventory, Integer quantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.isInventory = isInventory;
        this.quantity = quantity;
    }

    public Item(String name, String description, BigDecimal price, Category category) {
        this(name, description, price, category, false, 0);
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
        String name = getString(map.get("name"));
        String description = getString(map.get("description"));
        BigDecimal amount = getBigDecimal(map.get("price"));
        Category category = map.get("category") != null ? Category.fromMap((Map) map.get("category")) : null;
        boolean isInventory = getBoolean(map.get("isInventory"));
        Integer quantity = getInteger(map.get("quantity"));

        return new Item(name, description, amount, category, isInventory, quantity);
    }

}
