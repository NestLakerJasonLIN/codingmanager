package com.yanwenl.codingmanager.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "label")
@Data
public class Label {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="field")
    @Setter(AccessLevel.NONE)
    private String field;

    @Column(name="type")
    private String type;

    @Column(name="user_name")
    private String userName;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name = "record_label",
            joinColumns = @JoinColumn(name = "label_id"),
            inverseJoinColumns = @JoinColumn(name = "record_id"))
    private List<Record> records;

    public void setField(String field) {
        this.field = field.toLowerCase();
    }

    @Override
    public String toString() {
        return "Label{" +
                "id=" + id +
                ", field='" + field + '\'' +
                ", type='" + type + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
