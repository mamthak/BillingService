package com.rightminds.biller.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rightminds.biller.util.CastUtil;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.rightminds.biller.AllConstants.DATE_TIME_FORMAT;
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

    @Column(name = "NAME")
    private String name;

    @Column(name = "VALUE")
    private String value;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "DEFAULTVALUE")
    private String defaultValue;

    @CreatedDate
    @Column(name = "CREATEDON")
    @JsonProperty("created")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    private Date createdOn;

    @LastModifiedDate
    @Column(name = "LASTMODIFIEDON")
    private Date lastModifiedOn;

    public Configuration() {
    }

    public Configuration(String key, String name, String value, String defaultValue, String description) {
        this.key = key;
        this.name = name;
        this.value = value;
        this.defaultValue = defaultValue;
        this.description = description;
    }

    public Configuration(Integer id, String key, String name, String defaultValue, String value, String description, Date createdOn) {
        this.id = id;
        this.key = key;
        this.name = name;
        this.defaultValue = defaultValue;
        this.value = value;
        this.description = description;
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
        String name = getString(map.get("name"));
        String defaultValue = getString(map.get("defaultValue"));
        String description = getString(map.get("description"));
        String value = getString(map.get("value"));
        if (id != null) {
            Date createdOn = new Date(getLong(map.get("createdOn")));
            return new Configuration(id, key, name, defaultValue, value, description, createdOn);
        }
        return new Configuration(key, name, value, defaultValue, description);
    }

    public Map toMap() {
        Map<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("key", key);
        map.put("description", description);
        map.put("value", value);
        map.put("defaultValue", defaultValue);
        return map;
    }

}
