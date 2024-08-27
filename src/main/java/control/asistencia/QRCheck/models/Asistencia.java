package control.asistencia.QRCheck.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "asistencias")
public class Asistencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @NotNull(message = "El usuario es requerido")
    @ManyToOne(fetch = FetchType.LAZY) // Usa LAZY para evitar cargar innecesariamente
    @JoinColumn(name = "UsuarioID", nullable = false)
    private Usuario usuario;

    @NotNull(message = "La fecha es requerida")
    @Column(name = "Fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "HoraEntrada")
    private LocalTime horaEntrada;

    @Column(name = "HoraSalida")
    private LocalTime horaSalida;

    @Column(name = "DentroDelRango")
    private Boolean dentroDelRango;

    // Constructor vacío
    public Asistencia() {
    }

    // Constructor con parámetros
    public Asistencia(Integer id, Usuario usuario, LocalDate fecha, LocalTime horaEntrada, LocalTime horaSalida, Boolean dentroDelRango) {
        this.id = id;
        this.usuario = usuario;
        this.fecha = fecha;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.dentroDelRango = dentroDelRango;
    }

    // Getters y Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(LocalTime horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public LocalTime getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(LocalTime horaSalida) {
        this.horaSalida = horaSalida;
    }

    public Boolean getDentroDelRango() {
        return dentroDelRango;
    }

    public void setDentroDelRango(Boolean dentroDelRango) {
        this.dentroDelRango = dentroDelRango;
    }
}
