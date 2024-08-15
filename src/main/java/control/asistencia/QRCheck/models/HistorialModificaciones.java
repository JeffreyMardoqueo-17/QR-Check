package control.asistencia.QRCheck.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "historial_modificaciones")
public class HistorialModificaciones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @NotNull(message = "La tabla modificada es requerida")
    @Column(name = "TablaModificada", length = 50, nullable = false)
    private String tablaModificada;

    @Column(name = "CampoModificado", length = 50)
    private String campoModificado;

    @Column(name = "ValorAntiguo", length = 255)
    private String valorAntiguo;

    @Column(name = "ValorNuevo", length = 255)
    private String valorNuevo;

    @Column(name = "FechaModificacion", nullable = false, updatable = false)
    private LocalDateTime fechaModificacion = LocalDateTime.now();

    @NotNull(message = "El usuario que modificó es requerido")
    @ManyToOne
    @JoinColumn(name = "ModificadoPor", nullable = false)
    private Usuario modificadoPor;

    // Constructor vacío
    public HistorialModificaciones() {
    }

    // Getters y Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTablaModificada() {
        return tablaModificada;
    }

    public void setTablaModificada(String tablaModificada) {
        this.tablaModificada = tablaModificada;
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

    public LocalDateTime getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(LocalDateTime fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Usuario getModificadoPor() {
        return modificadoPor;
    }

    public void setModificadoPor(Usuario modificadoPor) {
        this.modificadoPor = modificadoPor;
    }
}
