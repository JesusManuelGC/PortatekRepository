package com.example.tiendaapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_producto")
    private Integer codProducto;

    @Column(name = "descripcion", length = 100)
    private String descripcion;

    @Column(name = "tipo_producto", length = 50)
    private String tipoProducto; // placa base, tarjeta gráfica, procesador, disco duro, refrigeración, ram, torre/caja PC, fuente de alimentación, PC sobremesa, portátil, monitor, teclado, ratón

    @Column(name = "categoria_producto", length = 50)
    private String categoriaProducto; // componente, ordenador, periférico

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "precio_unitario")
    private Integer precioUnitario;

    @Column(name = "imagen")
    private String imagen; // Store the filename or URL of the image
}
