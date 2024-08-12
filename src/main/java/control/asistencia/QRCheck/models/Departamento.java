package control.asistencia.QRCheck.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "departamentos")
public class Departamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DepartamentoID")
    private Integer departamentoId;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "EmpresaID", nullable = false)
    private Empresa empresa;

    @NotNull(message = "El nombre es requerido")
    @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres")
    @Column(name = "Nombre", length = 100)
    private String nombre;

    // Getters y Setters

    public Integer getDepartamentoId() {
        return departamentoId;
    }

    public void setDepartamentoId(Integer departamentoId) {
        this.departamentoId = departamentoId;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
