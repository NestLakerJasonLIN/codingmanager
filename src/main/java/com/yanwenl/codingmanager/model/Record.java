package com.yanwenl.codingmanager.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "record")
@Data
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="number")
    private Integer number;

    @Column(name="link")
    private String link;

    @Column(name="add_date")
    private String addDate;

    @Column(name="level")
    private String level;

    @Column(name="user_name")
    private String userName;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name = "record_label",
                joinColumns = @JoinColumn(name = "record_id"),
                inverseJoinColumns = @JoinColumn(name = "label_id"))
    private List<Label> labels;

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", number=" + number +
                ", link='" + link + '\'' +
                ", addDate='" + addDate + '\'' +
                ", level='" + level + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
