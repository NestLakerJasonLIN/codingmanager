package com.yanwenl.codingmanager.repository;

import com.yanwenl.codingmanager.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TagRepository extends JpaRepository<Tag, Integer> {
    List<Tag> findByRecordId(int rid);

    List<Tag> findByLabelId(int lid);

    List<Tag> findByRecordIdAndLabelId(int rid, int lid);

    void deleteByRecordId(int rid);

    void deleteByLabelId(int lid);
}
