package control.asistencia.QRCheck.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class DatabaseWebSecurity {


    @Bean
    public UserDetailsManager customUsers(DataSource dataSource) {
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);

        // Configura la consulta para obtener los detalles del usuario
        users.setUsersByUsernameQuery("SELECT email, pass, true FROM usuarios WHERE email = ?");

        // Configura la consulta para obtener las autoridades del usuario
        users.setAuthoritiesByUsernameQuery("SELECT u.email, r.nombre FROM usuario_rol ur " +
                "INNER JOIN usuarios u ON u.id = ur.usuario_id " +
                "INNER JOIN roles r ON r.id = ur.rol_id " +
                "WHERE u.email = ?");

        return users;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                // aperturar el acceso a los recursos estáticos
                .requestMatchers("/assets/**", "/css/**", "/js/**").permitAll()
                // las vistas públicas no requieren autenticación
                .requestMatchers("/", "/privacy", "/terms").permitAll()
                // todas las demás vistas requieren autenticación
                .anyRequest().authenticated());
        http.formLogin(form -> form.permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
