package be.boets.addresstool.admin;

import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/")
public class AdminController {

    private final Environment environment;

    public AdminController(Environment environment) {
        this.environment = environment;
    }

    @GetMapping("currentVersion")
    public ResponseEntity<Admin> getCurrentVersion() {
        Admin admin = Admin.AdminBuilder.anAdmin()
                .withMavenVersion(environment.getProperty("spring.application.version"))
                .build();
        return ResponseEntity.ok(admin);
    }
}
