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

    @Column(name="link")
    private String link;

    @Column(name="add_date")
    private String addDate;

    @Column(name="level")
    private String level;

    @Column(name="user_name")
    private String userName;

    // TODO: use oneToMany for all labels of this record
}
