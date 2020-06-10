package com.yanwenl.codingmanager.repository;

import com.yanwenl.codingmanager.model.Label;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LabelRepository extends JpaRepository<Label, Integer> {

    List<Label> findLabelByUserName(String userName);

    List<Label> findLabelByField(String field);

    List<Label> findLabelByType(String type);
}
