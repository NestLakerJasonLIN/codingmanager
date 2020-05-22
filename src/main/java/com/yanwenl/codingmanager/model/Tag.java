package com.yanwenl.codingmanager.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;


@Entity
@Table(name = "tag")
@Data
@ToString
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="record_id")
    private int recordId;

    @Column(name="label_id")
    private Integer labelId;
}
