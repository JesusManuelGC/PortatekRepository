-- CREAR LA BASE DE DATOS SOLO SI NO EXISTE
CREATE DATABASE IF NOT EXISTS ProyectoBD CHARACTER SET utf8mb4; 
USE ProyectoBD; 

-- CREAR TABLAS SOLO SI NO EXISTEN

CREATE TABLE IF NOT EXISTS usuarios( 
    cod_usuario int PRIMARY KEY auto_increment, 
    nombre VARCHAR(30), 
    dni VARCHAR(9), 
    pais VARCHAR(30), 
    ciudad VARCHAR(50), 
    num_calle VARCHAR(50),
    username VARCHAR(50) UNIQUE,
    password VARCHAR(255),
    enabled BOOLEAN DEFAULT TRUE
); 

CREATE TABLE IF NOT EXISTS roles(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) UNIQUE
);

CREATE TABLE IF NOT EXISTS usuario_roles(
    cod_usuario INT,
    role_id BIGINT,
    PRIMARY KEY (cod_usuario, role_id),
    FOREIGN KEY (cod_usuario) REFERENCES usuarios(cod_usuario) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS pedidos( 
    cod_pedidos int PRIMARY KEY auto_increment, 
    importe int, 
    fecha_pedido date, 
    fecha_prevista_entrega date, 
    fecha_entrega date, 
    cod_usuario int, 
    CONSTRAINT CK_Fechas_Pedido_Prevista_Entrega CHECK(fecha_pedido<=fecha_prevista_entrega AND fecha_pedido<=fecha_entrega), 
    CONSTRAINT FK_usuarios_pedidos FOREIGN KEY(cod_usuario) REFERENCES usuarios(cod_usuario) ON UPDATE CASCADE 
); 

CREATE TABLE IF NOT EXISTS cesta( 
    cod_cesta int PRIMARY KEY auto_increment, 
    cantidad int, 
    descuento_promocion int, 
    cod_pedidos int, 
    CONSTRAINT FK_pedidos_cesta FOREIGN KEY(cod_pedidos) REFERENCES pedidos(cod_pedidos) ON UPDATE CASCADE 
); 

CREATE TABLE IF NOT EXISTS productos( 
    cod_producto int PRIMARY KEY auto_increment, 
    descripcion varchar(100), 
    tipo_producto varchar(50), 
    categoria_producto varchar(50),
    stock int, 
    precio_unitario int, 
    imagen VARCHAR(255),
    CONSTRAINT CK_Productos CHECK(tipo_producto IN('placa base','tarjeta gráfica','procesador','disco duro','refrigeración','ram','torre/caja PC','fuente de alimentación','PC sobremesa','portátil','monitor','teclado','ratón')),
    CONSTRAINT CK_CategoriaProducto CHECK(categoria_producto IN('componente','ordenador','periférico')) 
); 

CREATE TABLE IF NOT EXISTS productos_en_cesta( 
    cod_producto int, 
    cod_cesta int, 
    fecha_seleccion date, 
    precio_total int, 
    CONSTRAINT PK_productos_en_cesta PRIMARY KEY(cod_producto,cod_cesta), 
    CONSTRAINT FK_producto_productos_en_cesta FOREIGN KEY(cod_producto) REFERENCES productos(cod_producto) ON UPDATE CASCADE, 
    CONSTRAINT FK_cesta_productos_en_cesta FOREIGN KEY(cod_cesta) REFERENCES cesta(cod_cesta) ON UPDATE CASCADE 
); 

CREATE TABLE IF NOT EXISTS factura( 
    cod_factura int PRIMARY KEY auto_increment, 
    descripcion varchar(1000), 
    fecha_factura date, 
    cod_pedidos int, 
    forma_pago varchar(25), 
    importe_total int, 
    CONSTRAINT CK_FormaImporte CHECK(forma_pago IN('Tarjeta','Bizum','Paypal')), 
    CONSTRAINT FK_pedidos_factura FOREIGN KEY(cod_pedidos) REFERENCES pedidos(cod_pedidos) ON UPDATE CASCADE 
); 

CREATE TABLE IF NOT EXISTS direccion_envio( 
    cod_envio int PRIMARY KEY auto_increment, 
    direccion_punto_recogida varchar(50), 
    tipo_recogida varchar(50), 
    cod_pedido int, 
    CONSTRAINT FK_pedidos_direccion FOREIGN KEY(cod_pedido) REFERENCES pedidos(cod_pedido) ON UPDATE CASCADE 
);
