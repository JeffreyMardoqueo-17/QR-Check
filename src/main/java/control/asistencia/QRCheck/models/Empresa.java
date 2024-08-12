package control.asistencia.QRCheck.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "empresas")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @NotNull(message = "El nombre es requerido")
    @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres")
    @Column(name = "Nombre", length = 100)
    private String nombre;

    @NotNull(message = "La dirección es requerida")
    @Size(max = 255, message = "La dirección no puede exceder los 255 caracteres")
    @Column(name = "Direccion", length = 255)
    private String direccion;

    @NotNull(message = "La latitud es requerida")
    @Column(name = "Latitud", precision = 10, scale = 8)
    private double latitud;

    @NotNull(message = "La longitud es requerida")
    @Column(name = "Longitud", precision = 11, scale = 8)
    private double longitud;

    @NotNull(message = "El rango permitido es requerido")
    @Column(name = "RangoPermitido")
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

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public Integer getRangoPermitido() {
        return rangoPermitido;
    }

    public void setRangoPermitido(Integer rangoPermitido) {
        this.rangoPermitido = rangoPermitido;
    }
}
