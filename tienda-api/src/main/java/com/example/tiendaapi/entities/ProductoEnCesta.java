package com.example.tiendaapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(
        name = "productos_en_cesta",
        uniqueConstraints = @UniqueConstraint(columnNames = {"cod_producto", "cod_cesta"})
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoEnCesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto_en_cesta")
    private Integer idProductoEnCesta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cod_producto", referencedColumnName = "cod_producto")
    private Producto producto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cod_cesta", referencedColumnName = "cod_cesta")
    private Cesta cesta;

    @Column(name = "fecha_seleccion")
    private LocalDate fechaSeleccion;

    @Column(name = "precio_total")
    private Integer precioTotal;
}
