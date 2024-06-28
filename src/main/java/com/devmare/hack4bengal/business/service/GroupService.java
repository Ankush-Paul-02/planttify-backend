package com.devmare.hack4bengal.business.service;

import com.devmare.hack4bengal.business.dto.CreateGroupDto;
import com.devmare.hack4bengal.data.model.Group;

public interface GroupService {

    Group createGroup(CreateGroupDto createGroupDto);

    String joinGroup(String groupId, String userId);
}
