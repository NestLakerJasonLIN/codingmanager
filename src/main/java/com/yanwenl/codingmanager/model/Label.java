package com.yanwenl.codingmanager.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "label")
@Data
@ToString
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

    public void setField(String field) {
        this.field = field.toLowerCase();
    }
}
