package com.example.mycontact.config;

import com.example.mycontact.entities.Role;
import com.example.mycontact.entities.User;
import com.example.mycontact.repository.RoleRepository;
import com.example.mycontact.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initRoles(RoleRepository roleRepository,
                                       UserRepository userRepository,
                                       PasswordEncoder passwordEncoder) {
        return args -> {
            Role roleAdmin = getOrCreateRole("ROLE_ADMIN", roleRepository);
            Role roleUser = getOrCreateRole("ROLE_USER", roleRepository);

            User admin = userRepository.findByEmailWithRoles("admin@gmail.com");
            if (admin == null) {
                admin = new User();
                admin.setFirstName("System");
                admin.setLastName("Administrator");
                admin.setEmail("admin@gmail.com");
                admin.setPassword(passwordEncoder.encode("12345"));

                Set<Role> roles = new HashSet<>();
                roles.add(roleAdmin);
                roles.add(roleUser);
                admin.setRoles(roles);

                userRepository.save(admin);
            }
        };
    }

    private Role getOrCreateRole(String roleName, RoleRepository roleRepository) {
        Role role = roleRepository.findByRoleName(roleName);
        if (role == null) {
            role = new Role();
            role.setRoleName(roleName);
            role = roleRepository.save(role);
        }
        return role;
    }
}