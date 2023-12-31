package com.ilacad.AdvancedMappings.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InstructorDto {

    private Long id;

    @NotEmpty(message = "First name must not be empty")
    private String firstName;

    @NotEmpty(message = "Last name must not be empty")
    private String lastName;

    @Email
    @NotEmpty(message = "Email must not be empty")
    private String email;

    private String youtubeChannel;

    private String hobby;

}
