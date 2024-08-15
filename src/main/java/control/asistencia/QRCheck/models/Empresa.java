package control.asistencia.QRCheck.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "empresas")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @NotNull(message = "El nombre es requerido")
    @Column(name = "Nombre", length = 100, nullable = false)
    private String nombre;

    @NotNull(message = "La dirección es requerida")
    @Column(name = "Direccion", length = 255, nullable = false)
    private String direccion;

    @NotNull(message = "La latitud es requerida")
    @Column(name = "Latitud", nullable = false)
    private Double latitud;

    @NotNull(message = "La longitud es requerida")
    @Column(name = "Longitud", nullable = false)
    private Double longitud;

    @NotNull(message = "El rango permitido es requerido")
    @Column(name = "RangoPermitido", nullable = false)
    private Integer rangoPermitido;

    // Getters y Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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

    public Integer getRangoPermitido() {
        return rangoPermitido;
    }

    public void setRangoPermitido(Integer rangoPermitido) {
        this.rangoPermitido = rangoPermitido;
    }
}
