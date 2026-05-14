package br.com.perfumestore.runner;

import br.com.perfumestore.dao.PerfumeDao;
import br.com.perfumestore.dto.PerfumeDTO;
import br.com.perfumestore.model.RoleEntity;
import br.com.perfumestore.model.UserEntity;
import br.com.perfumestore.repository.RoleRepository;
import br.com.perfumestore.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class StartupRunner implements CommandLineRunner {

    private final PerfumeDao perfumeDao;

    public StartupRunner(PerfumeDao perfumeDao) {

        this.perfumeDao = perfumeDao;

    }

    @Override
    public void run(String... args) throws Exception {
        perfumeDao.save(new PerfumeDTO("212 VIP Men", "Carolina Herrera", "Amadeirado", "100ml", 450.00));
        perfumeDao.save(new PerfumeDTO("Sauvage", "Dior", "Cítrico Amadeirado", "100ml", 720.00));
        perfumeDao.save(new PerfumeDTO("Malbec Clássico", "O Boticário", "Amadeirado", "100ml", 190.00));
        System.out.println("Perfumes cadastrados com sucesso!");
        System.out.println(perfumeDao.findAll());
    }

    @Bean
    public CommandLineRunner init(RoleRepository roleRepo, UserRepository userRepo, PasswordEncoder encoder) {
        return args -> {
            if (roleRepo.findByName("ROLE_ADMIN").isEmpty()) {
                roleRepo.save(new RoleEntity(null, "ROLE_ADMIN"));
            }
            if (roleRepo.findByName("ROLE_USER").isEmpty()) {
                roleRepo.save(new RoleEntity(null, "ROLE_USER"));
            }

            if (userRepo.findByUsername("admin").isEmpty()) {
                RoleEntity adminRole = roleRepo.findByName("ROLE_ADMIN").orElseThrow();
                RoleEntity userRole = roleRepo.findByName("ROLE_USER").orElseThrow();

                UserEntity admin = new UserEntity();
                admin.setUsername("admin");
                admin.setPassword(encoder.encode("admin"));
                admin.setRoles(Set.of(adminRole, userRole));

                userRepo.save(admin);
            }

            if (userRepo.findByUsername("user").isEmpty()) {
                RoleEntity userRole = roleRepo.findByName("ROLE_USER").orElseThrow();

                UserEntity user = new UserEntity();
                user.setUsername("user");
                user.setPassword(encoder.encode("user"));
                user.setRoles(Set.of(userRole));

                userRepo.save(user);
            }
        };
    }

}
