package com.rightminds.biller.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.rightminds.biller.util.CastUtil.*;

@Entity
@Table(name = "RESTAURANTORDER")
@Data
public class Order {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq_gen")
    @SequenceGenerator(name = "order_seq_gen", sequenceName = "order_seq_id")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "CUSTOMERID", referencedColumnName = "ID")
    private Customer customer;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SERVICECHARGE")
    private BigDecimal serviceCharge;

    @Column(name = "SERVICETAX")
    private BigDecimal serviceTax;

    @Column(name = "SUBTOTAL")
    private BigDecimal subTotal;

    @Column(name = "TOTAL")
    private BigDecimal total;

    @Column(name = "CASH")
    private BigDecimal cash;

    @Column(name = "CARD")
    private BigDecimal card;

    @Column(name = "STATUS")
    private boolean status;

    @CreatedDate
    @Column(name = "CREATEDON")
    private Date createdOn;

    @LastModifiedDate
    @Column(name = "LASTMODIFIEDON")
    private Date lastModifiedOn;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private List<OrderItem> orderItems = new ArrayList<>();

    public Order() {

    }

    public Order(Customer customer, String name, BigDecimal serviceCharge,
                 BigDecimal serviceTax, BigDecimal subTotal, BigDecimal total, BigDecimal cash,
                 BigDecimal card, boolean status) {
        this.customer = customer;
        this.name = name;
        this.serviceCharge = serviceCharge;
        this.serviceTax = serviceTax;
        this.subTotal = subTotal;
        this.total = total;
        this.cash = cash;
        this.card = card;
        this.status = status;
    }

    public Order(Integer id, Customer customer, String name, BigDecimal serviceCharge, BigDecimal serviceTax,
                 BigDecimal subTotal, BigDecimal total, BigDecimal cash, BigDecimal card, boolean status) {
        this(customer, name, serviceCharge, serviceTax, subTotal, total, cash, card, status);
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


    public static Order fromMap(Map map) {
        Customer customer = map.get("customer") != null ? Customer.fromMap((Map) map.get("customer")) : null;
        String name = getString(map.get("name"));
        BigDecimal serviceCharge = getBigDecimal(map.get("serviceCharge"));
        BigDecimal serviceTax = getBigDecimal(map.get("serviceTax"));
        BigDecimal subTotal = getBigDecimal(map.get("subTotal"));
        BigDecimal total = getBigDecimal(map.get("total"));
        BigDecimal cash = getBigDecimal(map.get("cash"));
        BigDecimal card = getBigDecimal(map.get("card"));
        boolean status = getBoolean(map.get("status"));
        Integer id = getInteger(map.get("id"));
        if (id == null) {
            return new Order(customer, name, serviceCharge, serviceTax, subTotal, total, cash, card, status);
        }
        return new Order(id, customer, name, serviceCharge, serviceTax, subTotal, total, cash, card, status);
    }
}
