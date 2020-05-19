package com.yanwenl.codingmanager.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "record")
@Data
@ToString
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="number")
    private Integer number;

    @Column(name="level")
    private String level;

    @Column(name="link")
    private String link;

    @Column(name="tag")
    private String tag;

    @Column(name="method")
    private String method;

    @Column(name="add_date")
    private String addDate;
}
