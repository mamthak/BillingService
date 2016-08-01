package com.rightminds.biller.entity;

import com.rightminds.biller.util.CastUtil;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;

import static com.rightminds.biller.util.CastUtil.getLong;
import static com.rightminds.biller.util.CastUtil.getString;

@Entity
@Table(name = "CONFIGURATION")
@Data
public class Configuration {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "config_seq_gen")
    @SequenceGenerator(name = "config_seq_gen", sequenceName = "config_seq_id")
    private Integer id;

    @Column(name = "KEY")
    private String key;

    @Column(name = "VALUE")
    private String value;

    @CreatedDate
    @Column(name = "CREATEDON")
    private Date createdOn;

    @LastModifiedDate
    @Column(name = "LASTMODIFIEDON")
    private Date lastModifiedOn;

    public Configuration() {
    }

    public Configuration(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public Configuration(Integer id, String key, String value, Date createdOn) {
        this.id = id;
        this.key = key;
        this.value = value;
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

    public static Configuration fromMap(Map map) {
        Integer id = CastUtil.getInteger(map.get("id"));
        String key = getString(map.get("key"));
        String value = getString(map.get("value"));
        if (id != null) {
            Date createdOn = new Date(getLong(map.get("createdOn")));
            return new Configuration(id, key, value, createdOn);
        }
        return new Configuration(key, value);
    }


}
