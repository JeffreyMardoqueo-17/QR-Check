package control.asistencia.QRCheck.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "trabajadores_puestos")
public class TrabajadorPuesto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "TrabajadorID", nullable = false)
    private Trabajador trabajador;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "PuestoID", nullable = false)
    private PuestoTrabajo puestoTrabajo;

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

    public PuestoTrabajo getPuestoTrabajo() {
        return puestoTrabajo;
    }

    public void setPuestoTrabajo(PuestoTrabajo puestoTrabajo) {
        this.puestoTrabajo = puestoTrabajo;
    }
}
