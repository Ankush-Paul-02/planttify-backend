package com.devmare.hack4bengal.business.service.impl;

import com.devmare.hack4bengal.business.dto.CreateGroupDto;
import com.devmare.hack4bengal.business.service.GroupService;
import com.devmare.hack4bengal.business.service.UserService;
import com.devmare.hack4bengal.data.enums.Role;
import com.devmare.hack4bengal.data.exceptions.UserInfoException;
import com.devmare.hack4bengal.data.model.Group;
import com.devmare.hack4bengal.data.model.Roles;
import com.devmare.hack4bengal.data.model.User;
import com.devmare.hack4bengal.data.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final UserService userService;

    @Override
    public Group createGroup(CreateGroupDto createGroupDto) {
        Optional<Group> optionalGroup = groupRepository.findByName(createGroupDto.getName());
        if (optionalGroup.isPresent()) {
            throw new UserInfoException("Group with name " + createGroupDto.getName() + " already exists");
        }
        Set<Roles> roles = userService.findAuthenticatedUser().getRoles();
        roles.stream().map(
                role -> role.getName().equals(Role.ADMIN) || role.getName().equals(Role.SUPER_ADMIN)
        ).findFirst().orElseThrow(() -> new UserInfoException("User is not an admin or super admin!"));

        List<User> users = new ArrayList<>();
        users.add(userService.findAuthenticatedUser());

        Group group = Group.builder()
                .name(createGroupDto.getName())
                .owner(userService.findAuthenticatedUser())
                .members(users)
                .build();
        group = groupRepository.save(group);
        return group;
    }
}
