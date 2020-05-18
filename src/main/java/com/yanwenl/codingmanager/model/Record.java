package com.yanwenl.codingmanager.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "record")
@Data
@ToString
public class Record {
    // TODO: use enum instead of string for level and tagType
    private enum LEVEL {EASY, MEDIUM, HARD}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="number")
    private int number;

    @Column(name="level")
    private String level;

    @Column(name="link")
    private String link;

    @Column(name="tag_type")
    private String tagType;
}
