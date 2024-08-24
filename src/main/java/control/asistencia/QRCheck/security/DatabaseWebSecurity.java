package control.asistencia.QRCheck.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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

		// Consulta para obtener el usuario por email
		users.setUsersByUsernameQuery(
				"SELECT email AS username, pass AS password, true AS enabled " +
						"FROM usuarios WHERE email = ?");

		// Consulta para obtener los roles asociados al usuario
		users.setAuthoritiesByUsernameQuery(
				"SELECT u.email AS username, r.nombre AS authority " +
						"FROM usuarios u " +
						"INNER JOIN roles r ON r.id = u.id_roles " +
						"WHERE u.email = ?");

		return users;
	}


	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
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
}