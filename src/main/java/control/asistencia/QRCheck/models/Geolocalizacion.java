package control.asistencia.QRCheck.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "Geolocalizacion")
public class Geolocalizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @NotNull(message = "El ID del trabajador es requerido")
    @ManyToOne
    @JoinColumn(name = "TrabajadorID", nullable = false)
    private Trabajador trabajador;

    @NotNull(message = "La latitud es requerida")
    @Column(name = "Latitud", nullable = false)
    private Double latitud;

    @NotNull(message = "La longitud es requerida")
    @Column(name = "Longitud", nullable = false)
    private Double longitud;

    @Column(name = "Timestamp", nullable = false)
    private LocalDateTime timestamp;

    // Getters y Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Trabajador getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
