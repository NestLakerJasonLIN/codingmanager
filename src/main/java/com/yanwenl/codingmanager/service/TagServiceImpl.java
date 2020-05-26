package com.yanwenl.codingmanager.service;

import com.yanwenl.codingmanager.exception.InvalidParametersException;
import com.yanwenl.codingmanager.exception.ResourceDuplicateException;
import com.yanwenl.codingmanager.exception.ResourceNotFoundException;
import com.yanwenl.codingmanager.model.Tag;
import com.yanwenl.codingmanager.repository.TagRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
@Slf4j
public class TagServiceImpl implements TagService {

    private TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    // TODO: move common code btw services into utilities class
    @Override
    public Tag findById(int id) {
        Optional<Tag> result = tagRepository.findById(id);

        if (!result.isPresent()) {
            throw new ResourceNotFoundException(
                    "tag id not found: " + id);
        } else {
            Tag label = result.get();
            return label;
        }
    }

    @Override
    public List<Tag> findByRecordId(int rid) {
        return tagRepository.findByRecordId(rid);
    }

    @Override
    public List<Tag> findByLabelId(int lid) {
        return tagRepository.findByLabelId(lid);
    }

    @Override
    public Tag findByRecordIdAndLabelId(int rid, int lid) {
        List<Tag> result = tagRepository.findByRecordIdAndLabelId(rid, lid);

        if (result != null && result.size() > 1) {
            throw new ResourceDuplicateException(
                    "Multiple tag found for rid: " + rid + " and lid: " +lid);
        } else if (result == null || result.size() == 0) {
            throw new ResourceNotFoundException(
                    "Tag not found for rid: " + rid + " and lid: " + lid);
        }

        return result.get(0);
    }

    @Override
    public void add(Tag tag) {
        // Check if tag already existed
        List<Tag> existedTags = tagRepository.findByRecordId(tag.getRecordId());

        if (existedTags != null) {
            for (Tag existedTag : existedTags) {
                if (existedTag.getLabelId().equals(tag.getLabelId())) {
                    log.warn("Same tag already existed (do not add): " + tag);
                    return;
                }
            }
        }

        log.info("Tag is added: " + tag);

        tag.setId(0);

        tagRepository.save(tag);
    }

    @Override
    public void update(Tag tag) {
        // ID field existed should not be zero in update
        int id = tag.getId();
        if (id == 0) {
            throw new InvalidParametersException(
                    "Tag id 0 not allowed in update request");
        }

        Optional<Tag> result = tagRepository.findById(id);

        if (!result.isPresent()) {
            throw new ResourceNotFoundException(
                    "Tag id not found (do not update): "
                            + id);
        } else {
            log.debug("Tag is updated: " + id);
            tagRepository.save(tag);
        }
    }

    @Override
    public void deleteById(int id) {
        Optional<Tag> result = tagRepository.findById(id);

        if (result.isPresent()) {
            Tag tag = result.get();
            log.info("Tag is deleted: " + tag);
            tagRepository.delete(tag);
        } else {
            throw new ResourceNotFoundException(
                    "Tag id not found: " + id);
        }
    }

    @Override
    public void deleteByRecordId(int rid) {
        tagRepository.deleteByRecordId(rid);
    }

    @Override
    public void deleteByLabelId(int lid) {
        tagRepository.deleteByLabelId(lid);
    }

    @Override
    public Map<Integer, List<Integer>> findLabelGroupByRecord() {
        List<Tag> tags = tagRepository.findAll();

        Map<Integer, List<Integer>> recordIdToLabelIds = new HashMap<>();

        for (Tag tag : tags) {
            recordIdToLabelIds.computeIfAbsent(tag.getRecordId(),
                    k->new ArrayList<>()).add(tag.getLabelId());
        }

        return recordIdToLabelIds;
    }
}
