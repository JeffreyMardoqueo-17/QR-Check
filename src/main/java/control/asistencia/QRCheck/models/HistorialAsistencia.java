package control.asistencia.QRCheck.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "historial_asistencias")
public class HistorialAsistencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @NotNull(message = "La asistencia es requerida")
    @ManyToOne
    @JoinColumn(name = "AsistenciaID", nullable = false)
    private Asistencia asistencia;

    @Column(name = "FechaCambio", nullable = false, updatable = false)
    private LocalDateTime fechaCambio = LocalDateTime.now();

    @Column(name = "CampoModificado", length = 50)
    private String campoModificado;

    @Column(name = "ValorAntiguo", length = 255)
    private String valorAntiguo;

    @Column(name = "ValorNuevo", length = 255)
    private String valorNuevo;

    @NotNull(message = "El usuario que modificó es requerido")
    @ManyToOne
    @JoinColumn(name = "ModificadoPor", nullable = false)
    private Usuario modificadoPor;

    // Constructor vacío
    public HistorialAsistencia() {
    }

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

    public Usuario getModificadoPor() {
        return modificadoPor;
    }

    public void setModificadoPor(Usuario modificadoPor) {
        this.modificadoPor = modificadoPor;
    }
}
