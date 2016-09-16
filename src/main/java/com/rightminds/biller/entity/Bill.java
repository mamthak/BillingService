package com.rightminds.biller.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rightminds.biller.model.BillStatus;
import lombok.Data;
import org.hibernate.annotations.Formula;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.rightminds.biller.AllConstants.DATE_TIME_FORMAT;
import static com.rightminds.biller.model.BillStatus.COMPLETED;
import static com.rightminds.biller.model.BillStatus.IN_PROGRESS;
import static com.rightminds.biller.util.CastUtil.*;
import static java.math.BigDecimal.ZERO;

@Entity
@Table(name = "BILL")
@Data
public class Bill {

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
    @JsonProperty("servicecharge")
    private BigDecimal serviceCharge;

    @Column(name = "SERVICETAX")
    @JsonProperty("servicetax")
    private BigDecimal serviceTax;

    @Column(name = "SUBTOTAL")
    @JsonProperty("subtotal")
    @Formula(value = "(SELECT SUM(BI.TOTAL) " +
            "                       FROM BILLITEM BI " +
            "                       WHERE BI.BILLID=ID)")
    private BigDecimal subTotal;

    @Column(name = "DISCOUNT")
    private BigDecimal discount = ZERO;

    @Column(name = "TOTAL")
    private BigDecimal total;

    @Column(name = "CASH")
    private BigDecimal cash;

    @Column(name = "CARD")
    private BigDecimal card;

    @Column(name = "STATUS")
    private BillStatus status;

    @CreatedDate
    @Column(name = "CREATEDON")
    @JsonProperty("created")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT, timezone = "Asia/Kolkata")
    private Date createdOn;

    @LastModifiedDate
    @Column(name = "LASTMODIFIEDON")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT, timezone = "Asia/Kolkata")
    private Date lastModifiedOn;

    @OneToMany(mappedBy = "bill", fetch = FetchType.EAGER)
    @JsonProperty("orders")
    // TODO: Fetch bill items with delete = false
    private List<BillItem> billItems = new ArrayList<>();

    public Bill() {

    }

    public Bill(Integer id) {
        this.id = id;
    }

    public Bill(Customer customer, String name, BigDecimal serviceCharge,
                BigDecimal serviceTax, BigDecimal subTotal, BigDecimal discount, BigDecimal total, BigDecimal cash,
                BigDecimal card, BillStatus status, Date createdOn, List<BillItem> billItems) {
        this.customer = customer;
        this.name = name;
        this.serviceCharge = serviceCharge;
        this.serviceTax = serviceTax;
        this.subTotal = subTotal;
        this.discount = discount;
        this.total = total;
        this.cash = cash;
        this.card = card;
        this.status = status;
        this.createdOn = createdOn;
        this.billItems = billItems;
    }

    public Bill(Integer id, Customer customer, String name, BigDecimal serviceCharge, BigDecimal serviceTax,
                BigDecimal subTotal, BigDecimal discount, BigDecimal total, BigDecimal cash, BigDecimal card,
                BillStatus status, Date createdOn, List<BillItem> billItems) {
        this(customer, name, serviceCharge, serviceTax, subTotal, discount, total, cash, card, status, createdOn, billItems);
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

    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", customer=" + customer +
                ", name='" + name + '\'' +
                ", serviceCharge=" + serviceCharge +
                ", serviceTax=" + serviceTax +
                ", subTotal=" + subTotal +
                ", discount=" + discount +
                ", total=" + total +
                ", cash=" + cash +
                ", card=" + card +
                ", status=" + status +
                ", createdOn=" + createdOn +
                ", lastModifiedOn=" + lastModifiedOn +
                '}';
    }

    public static Bill fromMap(Map map) {
        Customer customer = map.get("customer") != null ? Customer.fromMap((Map) map.get("customer")) : null;
        String name = getString(map.get("name"));
        BigDecimal serviceCharge = getBigDecimal(map.get("servicecharge"));
        BigDecimal serviceTax = getBigDecimal(map.get("servicetax"));
        BigDecimal subTotal = getBigDecimal(map.get("subtotal"));
        BigDecimal discount = getBigDecimal(map.get("discount"));
        List<BillItem> billItems = map.get("orders") != null ? ((List<BillItem>) ((List) map.get("orders")).stream().map(billItem -> BillItem.fromMap((Map) billItem)).collect(Collectors.toList())) : null;
        BigDecimal total = getBigDecimal(map.get("total"));
        BigDecimal cash = getBigDecimal(map.get("cash"));
        BigDecimal card = getBigDecimal(map.get("card"));
        Date createdOn = getDate(map.get("created"));
        BillStatus status = map.get("status") != null ? BillStatus.valueOf(getString(map.get("status")).toUpperCase()) : IN_PROGRESS;
        Integer id = getInteger(map.get("id"));
        if (id == null) {
            return new Bill(customer, name, serviceCharge, serviceTax, subTotal, discount, total, cash, card, status, createdOn, billItems);
        }
        return new Bill(id, customer, name, serviceCharge, serviceTax, subTotal, discount, total, cash, card, status, createdOn, billItems);
    }

    public Bill withComputedValues(BigDecimal serviceCharge, BigDecimal serviceTax, BigDecimal total) {
        return new Bill(id, customer, name, serviceCharge, serviceTax, subTotal, discount, total, cash, card, COMPLETED, createdOn, billItems);
    }
}
