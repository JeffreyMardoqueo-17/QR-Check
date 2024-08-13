package control.asistencia.QRCheck.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "HistorialAsistencia")
public class HistorialAsistencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @NotNull(message = "El ID de asistencia es requerido")
    @ManyToOne
    @JoinColumn(name = "AsistenciaID", nullable = false)
    private Asistencia asistencia;

    @Column(name = "FechaCambio", nullable = false)
    private LocalDateTime fechaCambio;

    @Column(name = "CampoModificado", length = 50)
    private String campoModificado;

    @Column(name = "ValorAntiguo", length = 255)
    private String valorAntiguo;

    @Column(name = "ValorNuevo", length = 255)
    private String valorNuevo;

    @NotNull(message = "El ID del trabajador que modificó es requerido")
    @ManyToOne
    @JoinColumn(name = "ModificadoPor", nullable = false)
    private Trabajador modificadoPor;

    // Getters y Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Asistencia getAsistencia() {
        return asistencia;
    }

    public void setAsistencia(Asistencia asistencia) {
        this.asistencia = asistencia;
    }

    public LocalDateTime getFechaCambio() {
        return fechaCambio;
    }

    public void setFechaCambio(LocalDateTime fechaCambio) {
        this.fechaCambio = fechaCambio;
    }

    public String getCampoModificado() {
        return campoModificado;
    }

    public void setCampoModificado(String campoModificado) {
        this.campoModificado = campoModificado;
    }

    public String getValorAntiguo() {
        return valorAntiguo;
    }

    public void setValorAntiguo(String valorAntiguo) {
        this.valorAntiguo = valorAntiguo;
    }

    public String getValorNuevo() {
        return valorNuevo;
    }

    public void setValorNuevo(String valorNuevo) {
        this.valorNuevo = valorNuevo;
    }

    public Trabajador getModificadoPor() {
        return modificadoPor;
    }

    public void setModificadoPor(Trabajador modificadoPor) {
        this.modificadoPor = modificadoPor;
    }
}
