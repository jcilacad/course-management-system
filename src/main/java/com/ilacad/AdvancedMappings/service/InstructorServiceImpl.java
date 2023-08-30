package com.ilacad.AdvancedMappings.service;

import com.ilacad.AdvancedMappings.dto.InstructorDto;
import com.ilacad.AdvancedMappings.entity.Instructor;
import com.ilacad.AdvancedMappings.entity.InstructorDetail;
import com.ilacad.AdvancedMappings.repository.InstructorDetailRepository;
import com.ilacad.AdvancedMappings.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InstructorServiceImpl implements InstructorService {

    InstructorRepository instructorRepository;
    InstructorDetailRepository instructorDetailRepository;

    @Autowired
    public InstructorServiceImpl(InstructorRepository instructorRepository, InstructorDetailRepository instructorDetailRepository) {
        this.instructorRepository = instructorRepository;
        this.instructorDetailRepository = instructorDetailRepository;
    }

    @Override
    public void saveInstructor(InstructorDto instructorDto) {

        // Get each value from instructor DTO
        String firstName = instructorDto.getFirstName();
        String lastName = instructorDto.getLastName();
        String email = instructorDto.getEmail();
        String youtubeChannel = instructorDto.getYoutubeChannel();
        String hobby = instructorDto.getHobby();

        // Create a new instance of instructor detail and instructor
        InstructorDetail instructorDetail = new InstructorDetail(youtubeChannel, hobby);
        Instructor instructor = new Instructor(firstName, lastName, email);
        instructor.setInstructorDetail(instructorDetail);

        // Save the instance of instructor
        instructorRepository.save(instructor);
    }

    @Override
    public void updateInstructor(InstructorDto instructorDto) {

        Long id = getIdByEmail(instructorDto.getEmail());
        Instructor foundInstructor = findInstructorById(id);

        foundInstructor.setFirstName(instructorDto.getFirstName());
        foundInstructor.setLastName(instructorDto.getLastName());
        foundInstructor.setEmail(instructorDto.getEmail());
        foundInstructor.getInstructorDetail().setYoutubeChannel(instructorDto.getYoutubeChannel());
        foundInstructor.getInstructorDetail().setHobby(instructorDto.getHobby());

        instructorRepository.save(foundInstructor);
    }

    @Override
    public List<Instructor> getAllInstructors() {
        return instructorRepository.findAll();
    }

    @Override
    public boolean isEmailExists(String email) {
        return instructorRepository.existsByEmail(email);
    }

    @Override
    public Long getIdByEmail(String email) {

        Long id;
        Optional<Instructor> result = instructorRepository.findByEmail(email);

        if (result.isPresent()) {
            id = result.get().getId();
        } else {
            throw new RuntimeException("Did not find email - " + email);
        }
        return id;
    }

    @Override
    public Instructor findInstructorById(Long id) {

        Instructor instructor;
        Optional<Instructor> result = instructorRepository.findById(id);

        if (result.isPresent()) {
            instructor = result.get();
        } else {
            throw new RuntimeException("Did not find id - " + id);
        }

        return instructor;

    }

    @Override
    public void deleteInstructorById(Long id) {
        instructorRepository.deleteById(id);
    }

    @Override
    public void deleteOtherDetailsById(Long id) {

        Instructor instructor = findInstructorById(id);
        instructor.setInstructorDetail(null);

        instructorRepository.save(instructor);
        instructorDetailRepository.deleteById(id);
    }
}
