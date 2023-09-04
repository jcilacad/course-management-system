package com.ilacad.AdvancedMappings.service;

import com.ilacad.AdvancedMappings.entity.Course;
import com.ilacad.AdvancedMappings.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private CourseRepository courseRepository;

    @Autowired
    public StudentServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Course> findAllCourses() {
        return courseRepository.findAll();
    }
}
