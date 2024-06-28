package com.devmare.hack4bengal.data.model;

import com.devmare.hack4bengal.data.enums.Role;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "roles")
public class Roles {

    @Id
    private String id;

    private Role name;
}