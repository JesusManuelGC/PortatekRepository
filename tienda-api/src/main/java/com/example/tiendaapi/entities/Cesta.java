package com.example.tiendaapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "cesta")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_cesta")
    private Integer codCesta;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "descuento_promocion")
    private Integer descuentoPromocion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cod_pedidos", referencedColumnName = "cod_pedidos")
    private Pedido pedido;

    @OneToMany(mappedBy = "cesta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductoEnCesta> productosEnCesta;
}
