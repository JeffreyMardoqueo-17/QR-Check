package control.asistencia.QRCheck.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;

import java.time.LocalDateTime;

@Entity
@Table(name = "administrador")
public class Administrador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @NotNull(message = "El nombre es requerido")
    @Column(name = "Nombre", length = 100, nullable = false)
    private String nombre;

    @NotNull(message = "El apellido es requerido")
    @Column(name = "Apellido", length = 100, nullable = false)
    private String apellido;

    @NotNull(message = "El email es requerido")
    @Email(message = "Email inválido")
    @Column(name = "Email", length = 100, nullable = false, unique = true)
    private String email;

    @NotNull(message = "La contraseña es requerida")
    @Column(name = "Pass", length = 255, nullable = false)
    private String pass;

    @NotNull(message = "El rol es requerido")
    @ManyToOne
    @JoinColumn(name = "IdRoles", nullable = false)
    private Roles rol;

    @NotNull(message = "La latitud es requerida")
    @Column(name = "Latitud", nullable = false)
    private Double latitud;

    @NotNull(message = "La longitud es requerida")
    @Column(name = "Longitud", nullable = false)
    private Double longitud;

    @Column(name = "FechaCreacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "FechaModificacion")
    private LocalDateTime fechaModificacion;

    @ManyToOne
    @JoinColumn(name = "IdEmpresa", nullable = false)
    private Empresa empresa;

    // Getters y Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Roles getRol() {
        return rol;
    }

    public void setRol(Roles rol) {
        this.rol = rol;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(LocalDateTime fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    // Método adicional para obtener el nombre de la empresa
    @Transient
    public String getNombreEmpresa() {
        return empresa != null ? empresa.getNombre() : null;
    }
}
