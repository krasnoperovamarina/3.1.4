package com.example.spring_boot.util;
import com.example.spring_boot.dao.UserRepository;
import com.example.spring_boot.model.Role;
import com.example.spring_boot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Component
class DBStart {

    final UserRepository userRepository;

    @Autowired
    public DBStart(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    private void postConstruct() {
        Set<Role> rolesAdmin = new HashSet<>();
        rolesAdmin.add(new Role(1, "ADMIN"));

        Set<Role> rolesUser = new HashSet<>();
        rolesUser.add(new Role(2, "USER"));

        User admin = new User("Ivan", "Ivanov", 20, "ivan@mail.ru",
                "$2a$12$2yMGo862SFwBl52r50X0eOlVQAL2y/NzSjq.wv8KO5xGdG8Q..SUa", rolesAdmin);

        User user = new User("test", "test", 20, "test@mail.ru",
                "$2a$12$fsPMbR6FykV.ML.BpkGWLOv4jUJOVoavAP3a2jKRdj1hyLMlWkQYy", rolesUser);
        if (Objects.nonNull(admin.getUsername())) {
            admin.setName(admin.getName());
            userRepository.save(admin);
        }
        if (Objects.nonNull(user.getUsername())) {
            admin.setName(user.getName());
            userRepository.save(user);
        }
    }
}
