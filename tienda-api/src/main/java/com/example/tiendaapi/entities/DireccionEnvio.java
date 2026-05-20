package com.example.tiendaapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "direccion_envio")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DireccionEnvio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_envio")
    private Integer codEnvio;

    @Column(name = "direccion_punto_recogida", length = 50)
    private String direccionPuntoRecogida;

    @Column(name = "tipo_recogida", length = 50)
    private String tipoRecogida;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cod_pedido", referencedColumnName = "cod_pedidos")
    private Pedido pedido;
}
