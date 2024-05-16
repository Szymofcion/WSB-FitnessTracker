package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;

    private final UserMapper userMapper;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @PostMapping
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto) {
        User addedUser = userService.createUser(userMapper.toEntity(userDto));
        UserDto addedUserDto = userMapper.toDto(addedUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedUserDto);
    }
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId) {
        return userService.getUser(userId)
                .map(userMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsersByEmail(@RequestParam String emailFragment) {
        List<User> users = userService.searchUsersByEmail(emailFragment);
        return ResponseEntity.ok(users);
    }
}