package com.devmare.hack4bengal.controller;

import com.devmare.hack4bengal.business.domain.DefaultResponse;
import com.devmare.hack4bengal.business.dto.CreateGroupDto;
import com.devmare.hack4bengal.business.dto.GroupJoinDto;
import com.devmare.hack4bengal.business.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/group")
public class GroupController {

    private final GroupService groupService;

    @PostMapping("/create")
    public ResponseEntity<DefaultResponse> createGroup(
            @RequestBody CreateGroupDto createGroupDto
    ) {
        return ResponseEntity.ok(
                new DefaultResponse(
                        DefaultResponse.Status.SUCCESS,
                        Map.of(
                                "data",
                                groupService.createGroup(createGroupDto)
                        ),
                        "Group created successfully"
                )
        );
    }

    @PostMapping("/join")
    public ResponseEntity<DefaultResponse> joinGroup(
            @RequestBody GroupJoinDto groupJoinDto
    ) {
        return ResponseEntity.ok(
                new DefaultResponse(
                        DefaultResponse.Status.SUCCESS,
                        Map.of(
                                "data",
                                groupService.joinGroup(
                                        groupJoinDto.getGroupId(),
                                        groupJoinDto.getUserId()
                                )
                        ),
                        "Group joined successfully"
                )
        );
    }

    @PostMapping("/all")
    public ResponseEntity<DefaultResponse> getAllGroups() {
        return ResponseEntity.ok(
                new DefaultResponse(
                        DefaultResponse.Status.SUCCESS,
                        Map.of(
                                "data",
                                groupService.getAllGroups()
                        ),
                        "All groups fetched successfully"
                )
        );
    }
}
