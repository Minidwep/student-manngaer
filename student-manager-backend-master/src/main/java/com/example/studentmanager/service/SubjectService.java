package com.example.studentmanager.service;

import com.example.studentmanager.entity.Direction;
import com.example.studentmanager.entity.Student;
import com.example.studentmanager.entity.Subject;
import com.example.studentmanager.repository.DirectionRepository;
import com.example.studentmanager.repository.SubjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SubjectService {
    private static Logger logger = LoggerFactory.getLogger(SubjectService.class);

    private final SubjectRepository subjectRepository;
    private final DirectionRepository directionRepository;

    @Autowired
    public SubjectService(SubjectRepository subjectRepository,DirectionRepository directionRepository) {

        this.subjectRepository = subjectRepository;
        this.directionRepository = directionRepository;
    }

    public List<Subject> findAll() {
        return this.subjectRepository.findAll();
    }

    public Page<Subject> findAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Subject> result = subjectRepository.findAll(pageable);
        return result;
    }

    public void updateSubject(Subject subject){

        if(subject.getId() != null){
            Subject old = subjectRepository.findById(subject.getId()).get();
            subject.setDirections(old.getDirections());
        }
        subjectRepository.save(subject);
    }

    public void updateDirection(Direction direction){
        directionRepository.save(direction);
    }

    public void deleteSubject(Integer id){
        Subject old = null;
        if(id != null){
            old = subjectRepository.findById(id).get();

            for(Direction d : old.getDirections()){
                deleteDirection(d.getId());
            }
        }
        subjectRepository.deleteById(id);
    }

    public void deleteDirection(Integer id){
        directionRepository.deleteById(id);
    }

    public Page<Subject> search(String key, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return subjectRepository.search(key, pageable);
    }
}
