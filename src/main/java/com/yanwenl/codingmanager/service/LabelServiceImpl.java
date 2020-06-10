package com.yanwenl.codingmanager.service;

import com.yanwenl.codingmanager.common.Utilities;
import com.yanwenl.codingmanager.exception.InvalidParametersException;
import com.yanwenl.codingmanager.exception.ResourceNotFoundException;
import com.yanwenl.codingmanager.model.Label;
import com.yanwenl.codingmanager.repository.LabelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
@Slf4j
public class LabelServiceImpl implements LabelService {

    private LabelRepository labelRepository;

    @Autowired
    public LabelServiceImpl(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

    @Override
    public List<Label> findByUserName(String userName) {
        return labelRepository.findLabelByUserName(userName);
    }

    @Override
    public Map<String, List<Label>> findByUserNameGroupByField(String userName) {
        List<Label> labels = findByUserName(userName);

        Map<String, List<Label>> labelByField = Utilities.groupLabelsByField(labels);

        log.debug("Find labelByField: " + labelByField);

        return labelByField;
    }

    @Override
	public Label findById(int id) {
        Optional<Label> result = labelRepository.findById(id);

        if (!result.isPresent()) {
            throw new ResourceNotFoundException(
                    "label id not found: " + id);
        } else {
            Label label = result.get();
            return label;
        }
    }

    @Override
	public List<Label> findByField(String field) {
        return labelRepository.findLabelByField(field);
    }

    @Override
    public List<Label> findByType(String type) {
        return labelRepository.findLabelByType(type);
    }

    @Override
	public void add(Label label) {
        String field = label.getField();
        String type = label.getType();

        List<Label> existedLabels = labelRepository.findLabelByField(field);

        if (existedLabels != null) {
            for (Label existedLabel : existedLabels) {
                if (existedLabel.getType().equals(type)) {
                    log.warn("Label already existed (do not add): " + label);
                    return;
                }
            }
        }

        log.info("Label is added: " + label);

        // TODO: check duplicate

        label.setId(0);

        labelRepository.save(label);
    }

    @Override
	public void update(Label label) {
        // ID field existed should not be zero in update
        int id = label.getId();
        if (id == 0) {
            throw new InvalidParametersException(
                    "Label id 0 not allowed in update request");
        }

        Optional<Label> result = labelRepository.findById(id);

        if (!result.isPresent()) {
            throw new ResourceNotFoundException(
                    "Label id not found (do not update): "
                            + id);
        } else {
            log.debug("Label is updated: " + id);
            Label existedLabel = result.get();
            label.setRecords(existedLabel.getRecords());

            // TODO: check duplicate

            labelRepository.save(label);
        }
    }

    @Override
	public void deleteById(int id) {
        Optional<Label> result = labelRepository.findById(id);

        if (result.isPresent()) {
            Label label = result.get();
            log.info("Label is deleted: " + label);
            labelRepository.delete(label);
        } else {
            throw new ResourceNotFoundException(
                    "Label id not found: " + id);
        }
    }
}

