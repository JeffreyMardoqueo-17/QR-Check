package control.asistencia.QRCheck.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "trabajadores")
public class Trabajador extends Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "EmpresaID", nullable = false)
    private Empresa empresa;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "DepartamentoID", nullable = false)
    private Departamento departamento;

    @NotNull(message = "La latitud actual es requerida")
    @Column(name = "LatitudActual", precision = 10, scale = 8)
    private double latitudActual;

    @NotNull(message = "La longitud actual es requerida")
    @Column(name = "LongitudActual", precision = 11, scale = 8)
    private double longitudActual;

    // Getters y Setters

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public double getLatitudActual() {
        return latitudActual;
    }

    public void setLatitudActual(double latitudActual) {
        this.latitudActual = latitudActual;
    }

    public double getLongitudActual() {
        return longitudActual;
    }

    public void setLongitudActual(double longitudActual) {
        this.longitudActual = longitudActual;
    }
}
