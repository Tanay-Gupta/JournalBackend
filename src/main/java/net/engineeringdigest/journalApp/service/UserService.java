package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveEntry(User entry) {

        String encryptedPassword = passwordEncoder.encode(entry.getPassword());
        entry.setPassword(encryptedPassword);
        entry.setRoles(Arrays.asList("USER"));
        userRepository.save(entry);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(ObjectId myId) {
        return userRepository.findById(myId);
    }

    public boolean deleteUserById(ObjectId myId) {
        if (userRepository.existsById(myId)) {
            userRepository.deleteById(myId);
            return true;
        }
        return false;
    }
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }
}
