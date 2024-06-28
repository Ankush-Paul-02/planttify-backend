package com.devmare.hack4bengal.business.service;

import com.devmare.hack4bengal.data.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User findAuthenticatedUser();
}
