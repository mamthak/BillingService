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
@Table(name = "ORDERITEM")
@Data
public class OrderItem {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_item_seq_gen")
    @SequenceGenerator(name = "order_item_seq_gen", sequenceName = "order_item_seq_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ORDERID", referencedColumnName = "ID")
    @JsonIgnore
    private Order order;

    @ManyToOne
    @JoinColumn(name = "ITEMID", referencedColumnName = "ID")
    private Item item;

    @Column(name = "QUANTITY")
    private BigDecimal quantity;

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

    public OrderItem() {

    }

    public OrderItem(Order order, Item item, BigDecimal quantity, BigDecimal discount, BigDecimal total) {
        this.order = order;
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


    public static OrderItem fromMap(Map<String, Object> map) {
        BigDecimal quantity = CastUtil.getBigDecimal(map.get("quantity"));
        BigDecimal discount = CastUtil.getBigDecimal(map.get("discount"));
        BigDecimal total = CastUtil.getBigDecimal(map.get("total"));
        Order order = map.get("order") != null ? Order.fromMap((Map) map.get("order")) : null;
        Item item = map.get("item") != null ? Item.fromMap((Map) map.get("item")) : null;
        return new OrderItem(order, item, quantity, discount, total);
    }

    public OrderItem withTotal(BigDecimal total) {
        return new OrderItem(order, item, quantity, discount, total);
    }
}
