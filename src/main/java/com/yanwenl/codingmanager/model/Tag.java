package com.yanwenl.codingmanager.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "tag")
@Data
@ToString
public class Tag {

    // TODO: move default to application.properties
    enum TYPE {RESOLVED, UNRESOLVED, OPTIMIZED, REFACTOR}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private TYPE type;
    private int recordNumber;
}
