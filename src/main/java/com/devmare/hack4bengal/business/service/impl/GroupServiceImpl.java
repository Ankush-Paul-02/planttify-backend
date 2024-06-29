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
import com.devmare.hack4bengal.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;
import java.time.Year;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public Group createGroup(CreateGroupDto createGroupDto) {
        Optional<Group> optionalGroup = groupRepository.findByName(createGroupDto.getName());
        if (optionalGroup.isPresent()) {
            throw new UserInfoException("Group with name " + createGroupDto.getName() + " already exists");
        }
        Set<Roles> roles = userService.findAuthenticatedUser().getRoles();
        log.info("User roles: {}", roles);

        boolean isAdminOrSuperAdmin = roles.stream()
                .anyMatch(role -> role.getName() == Role.ADMIN || role.getName() == Role.SUPER_ADMIN);

        if (!isAdminOrSuperAdmin) {
            throw new UserInfoException("User is not an admin or super admin!");
        }


        Set<String> usersIds = new HashSet<>();
        usersIds.add(userService.findAuthenticatedUser().getId());

        Clock clock = Clock.systemUTC();
        Instant now = clock.instant();

        int currentYear = Year.from(now.atZone(clock.getZone())).getValue();

        Group group = Group.builder()
                .name(createGroupDto.getName())
                .owner(userService.findAuthenticatedUser())
                .year(Year.of(currentYear))
                .description(createGroupDto.getDescription())
                .location(createGroupDto.getLocation())
                .membersIds(usersIds)
                .build();
        group = groupRepository.save(group);
        return group;
    }

    @Override
    public String joinGroup(
            String groupId,
            String userId
    ) {
        Optional<Group> optionalGroup = groupRepository.findById(groupId);
        if (optionalGroup.isEmpty()) {
            throw new UserInfoException("Group with id " + groupId + " not found");
        }
        Group group = optionalGroup.get();
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new UserInfoException("User with id " + userId + " not found");
        }
        User user = optionalUser.get();

        Set<String> membersIds = group.getMembersIds();
        if (membersIds.contains(userId)) {
            throw new UserInfoException("User with id " + userId + " already in group with id " + groupId);
        }

        group.getMembersIds().add(user.getId());
        groupRepository.save(group);
        return "User with id " + userId + " joined group with id " + groupId + " successfully";
    }

    @Override
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }
}
