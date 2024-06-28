package com.devmare.hack4bengal.data.model;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "groups")
public class Group {

    @Id
    private String id;

    @NotBlank(message = "Name is mandatory")
    @Indexed(unique = true)
    private String name;

    @NotBlank(message = "Owner is mandatory")
    private User owner;

    private Set<String> membersIds;

}
