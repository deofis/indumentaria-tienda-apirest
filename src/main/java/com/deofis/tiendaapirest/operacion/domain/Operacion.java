package com.deofis.tiendaapirest.operacion.domain;

import com.deofis.tiendaapirest.clientes.domain.Cliente;
import com.deofis.tiendaapirest.pagos.domain.FormaPago;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Esta entidad hace referencia a la operación de compra/venta de la tienda.
 */

@Entity
@Data
@Table(name = "operaciones")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Operacion implements Serializable {

    private final static long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long nroOperacion;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_operacion")
    private Date fechaOperacion;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_enviada")
    private Date fechaEnviada;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_recibida")
    private Date fechaRecibida;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "cliente_id")
    @NotNull(message = "Datos del cliente obligatorios")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Cliente cliente;

    @Enumerated(EnumType.STRING)
    private EstadoOperacion estado;

    // Luego crear FormaPago con las distintas formas de pago.
    // Quitado para pruebas   @NotNull(message = "La forma de pago es obligatoria")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "forma_pago_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private FormaPago formaPago;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "operacion_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<DetalleOperacion> items;

    private Double total;
}
