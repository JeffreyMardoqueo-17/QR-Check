package control.asistencia.QRCheck.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "Asistencia")
public class Asistencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AsistenciaID")
    private Integer asistenciaID;

    @NotNull(message = "El trabajador es requerido")
    @ManyToOne
    @JoinColumn(name = "TrabajadorID", nullable = false)
    private Trabajador trabajador;

    @NotNull(message = "La fecha es requerida")
    @Column(name = "Fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "HoraEntrada")
    private LocalTime horaEntrada;

    @Column(name = "HoraSalida")
    private LocalTime horaSalida;

    @Column(name = "DentroDelRango")
    private Boolean dentroDelRango;

    // Getters y Setters

    public Integer getAsistenciaID() {
        return asistenciaID;
    }

    public void setAsistenciaID(Integer asistenciaID) {
        this.asistenciaID = asistenciaID;
    }

    public Trabajador getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
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
