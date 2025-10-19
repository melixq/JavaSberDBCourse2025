package ru.databaseProj.firstLab.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.databaseProj.firstLab.repositories.UserRepository;
import ru.databaseProj.firstLab.entities.UserEntity;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService  {
    private final UserRepository userRepository;

    public UserEntity save(UserEntity user) {
        return userRepository.save(user);
    }

    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }

    public UserEntity findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("USER with ID: " + id + " not found!"));
    }

    public UserEntity update(Long id, UserEntity userData) {
        var user = findById(id);
        user.setName(userData.getName());
        user.setEmail(userData.getEmail());
        return save(user);
    }

    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NoSuchElementException("USER with ID: " + id + " not found!");
        }
        userRepository.deleteById(id);
    }

    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
