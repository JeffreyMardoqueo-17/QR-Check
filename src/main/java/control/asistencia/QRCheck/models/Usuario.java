package control.asistencia.QRCheck.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") // Asegúrate de que el nombre de la columna coincida con la base de datos
    private Integer id;

    @NotNull(message = "El nombre es requerido")
    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @NotNull(message = "El apellido es requerido")
    @Column(name = "apellido", length = 100, nullable = false)
    private String apellido;

    @NotNull(message = "El email es requerido")
    @Email(message = "Debe ser un email válido")
    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;

    @NotNull(message = "La contraseña es requerida")
    @Column(name = "pass", length = 255, nullable = false)
    private String pass;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_rol",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id"))
    private List<Roles> rol;

    @NotNull(message = "La latitud es requerida")
    @Column(name = "latitud", nullable = false)
    private Double latitud;

    @NotNull(message = "La longitud es requerida")
    @Column(name = "longitud", nullable = false)
    private Double longitud;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @Column(name = "fecha_modificacion")
    private LocalDateTime fechaModificacion;

    @ManyToOne
    @JoinColumn(name = "creado_por", nullable = false)
    private Usuario creadoPor;

    @ManyToOne
    @JoinColumn(name = "modificado_por")
    private Usuario modificadoPor;

    @ManyToOne
    @JoinColumn(name = "id_empresa", nullable = true)
    private Empresa empresa;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public @NotNull(message = "El nombre es requerido") String getNombre() {
        return nombre;
    }

    public void setNombre(@NotNull(message = "El nombre es requerido") String nombre) {
        this.nombre = nombre;
    }

    public @NotNull(message = "El apellido es requerido") String getApellido() {
        return apellido;
    }

    public void setApellido(@NotNull(message = "El apellido es requerido") String apellido) {
        this.apellido = apellido;
    }

    public @NotNull(message = "El email es requerido") @Email(message = "Debe ser un email válido") String getEmail() {
        return email;
    }

    public void setEmail(@NotNull(message = "El email es requerido") @Email(message = "Debe ser un email válido") String email) {
        this.email = email;
    }

    public @NotNull(message = "La contraseña es requerida") String getPass() {
        return pass;
    }

    
    public void setPass(@NotNull(message = "La contraseña es requerida") String pass) {
        this.pass = pass;
    }


    public List<Roles> getRol() {
        return rol;
    }

    public void setRol(List<Roles> rol) {
        this.rol = rol;
    }

    public @NotNull(message = "La latitud es requerida") Double getLatitud() {
        return latitud;
    }

    public void setLatitud(@NotNull(message = "La latitud es requerida") Double latitud) {
        this.latitud = latitud;
    }

    public @NotNull(message = "La longitud es requerida") Double getLongitud() {
        return longitud;
    }

    public void setLongitud(@NotNull(message = "La longitud es requerida") Double longitud) {
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

    public Usuario getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(Usuario creadoPor) {
        this.creadoPor = creadoPor;
    }

    public Usuario getModificadoPor() {
        return modificadoPor;
    }

    public void setModificadoPor(Usuario modificadoPor) {
        this.modificadoPor = modificadoPor;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }


}
