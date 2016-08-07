package com.rightminds.biller.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rightminds.biller.util.CastUtil;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@Entity
@Table(name = "BILLITEM")
@Data
public class BillItem {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_item_seq_gen")
    @SequenceGenerator(name = "order_item_seq_gen", sequenceName = "order_item_seq_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "BILLID", referencedColumnName = "ID")
    @JsonIgnore
    private Bill bill;

    @ManyToOne
    @JoinColumn(name = "ITEMID", referencedColumnName = "ID")
    private Item item;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @Column(name = "DISCOUNT")
    private BigDecimal discount;

    @Column(name = "TOTAL")
    private BigDecimal total;

    @CreatedDate
    @Column(name = "CREATEDON")
    private Date createdOn;

    @LastModifiedDate
    @Column(name = "LASTMODIFIEDON")
    private Date lastModifiedOn;

    public BillItem() {

    }

    public BillItem(Bill bill, Item item, Integer quantity, BigDecimal discount, BigDecimal total) {
        this.bill = bill;
        this.item = item;
        this.quantity = quantity;
        this.discount = discount;
        this.total = total;
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


    public static BillItem fromMap(Map<String, Object> map) {
        Integer quantity = CastUtil.getInteger(map.get("quantity"));
        BigDecimal discount = CastUtil.getBigDecimal(map.get("discount"));
        BigDecimal total = CastUtil.getBigDecimal(map.get("total"));
        Bill bill = map.get("order") != null ? Bill.fromMap((Map) map.get("order")) : null;
        Item item = map.get("item") != null ? Item.fromMap((Map) map.get("item")) : null;
        return new BillItem(bill, item, quantity, discount, total);
    }

    public BillItem withTotal(BigDecimal total) {
        return new BillItem(bill, item, quantity, discount, total);
    }
}