package com.rightminds.biller.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rightminds.biller.util.CastUtil;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.rightminds.biller.util.CastUtil.getInteger;

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
    @JsonProperty("created")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm a z")
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

    @Override
    public String toString() {
        return "BillItem{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", discount=" + discount +
                ", total=" + total +
                ", createdOn=" + createdOn +
                ", lastModifiedOn=" + lastModifiedOn +
                '}';
    }

    public static BillItem fromMap(Map<String, String> map) {
        Integer quantity = map.get("quantity") != null ? getInteger(map.get("quantity")) : 1;
        BigDecimal discount = map.get("discount") != null ? CastUtil.getBigDecimal(map.get("discount")) : BigDecimal.ZERO;
        BigDecimal total = CastUtil.getBigDecimal(map.get("total"));
        Bill bill = map.get("billid") != null ? new Bill(getInteger(map.get("billid"))) : null;
        Item item = map.get("itemid") != null ? new Item(getInteger(map.get("itemid"))) : null;
        return new BillItem(bill, item, quantity, discount, total);
    }

    public BillItem withTotal(BigDecimal total) {
        return new BillItem(bill, item, quantity, discount, total);
    }
}
