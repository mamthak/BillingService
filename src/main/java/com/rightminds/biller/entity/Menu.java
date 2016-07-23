package com.rightminds.biller.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

import static com.rightminds.biller.util.CastUtil.getBigDecimal;
import static com.rightminds.biller.util.CastUtil.getInteger;
import static com.rightminds.biller.util.CastUtil.getString;

@Entity
@Table(name = "MENU")
@Data
public class Menu {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "menu_seq_gen")
    @SequenceGenerator(name = "menu_seq_gen", sequenceName = "menu_seq_id")
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @OneToOne
    private Category category;

    @CreatedDate
    @Column(name = "CREATED_ON")
    private Date createdOn;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_ON")
    private Date lastModifiedOn;

    public Menu() {
    }

    public Menu(String name, String description, BigDecimal amount, Category category) {
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.category = category;
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

    public static Menu fromMap(Map map) {
        String name = getString(map.get("name"));
        String description = getString(map.get("description"));
        BigDecimal amount = getBigDecimal(map.get("amount"));
        Category category = map.get("inventory") != null ? Category.fromMap((Map) map.get("inventory")) : null;

        return new Menu(name, description, amount, category);
    }

}
