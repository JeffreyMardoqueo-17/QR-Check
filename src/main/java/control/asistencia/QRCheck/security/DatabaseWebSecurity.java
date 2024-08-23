//package control.asistencia.QRCheck.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.provisioning.JdbcUserDetailsManager;
//import org.springframework.security.provisioning.UserDetailsManager;
//
//import javax.sql.DataSource;
//
//@Configuration
//@EnableWebSecurity
//
//public class DatabaseWebSecurity {
//
//    @Bean
//    public UserDetailsManager customUsers(DataSource dataSource) {
//        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
//
//        // Consulta para obtener el usuario por email
//        users.setUsersByUsernameQuery("select email as username, pass as password, true from usuarios where email = ?");
//
//        // Consulta para obtener los roles asociados al usuario
//        users.setAuthoritiesByUsernameQuery("select u.email, r.nombre from usuarios u " +
//                "inner join roles r on r.id = u.IdRoles " +
//                "where u.email = ?");
//
//        return users;
//    }
//
//
//}
