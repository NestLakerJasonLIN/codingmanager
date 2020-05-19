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
    public enum LEVEL {EASY, MEDIUM, HARD}
    public enum TAG_TYPE {REFACTOR, OPTIMIZER, ANOTHER_SOLUTION, CORNER_CASE}
    public enum METHOD_TYPE {double_pointer, pruned_backtrack}

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

    @Column(name="tag_type")
    private String tagType;

    @Column(name="method_type")
    private String methodType;

    @Column(name="add_date")
    private String addDate;
}
