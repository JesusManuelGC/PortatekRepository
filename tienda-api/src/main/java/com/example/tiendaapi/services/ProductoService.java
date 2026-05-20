package com.example.tiendaapi.services;

import com.example.tiendaapi.dtos.ProductoCreateDTO;
import com.example.tiendaapi.dtos.ProductoDTO;
import com.example.tiendaapi.entities.Producto;
import com.example.tiendaapi.mappers.ProductoMapper;
import com.example.tiendaapi.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProductoMapper productoMapper;

    @Value("${file.upload-dir:uploads}")
    private String uploadDir;

    public List<ProductoDTO> getAllProductos() {
        return productoRepository.findAll().stream()
                .map(productoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ProductoDTO getProductoById(Integer id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto not found"));
        return productoMapper.toDTO(producto);
    }

    public ProductoDTO createProducto(ProductoCreateDTO createDTO, MultipartFile image) throws IOException {
        Producto producto = new Producto();
        producto.setDescripcion(createDTO.getDescripcion());
        producto.setTipoProducto(createDTO.getTipoProducto());
        producto.setCategoriaProducto(createDTO.getCategoriaProducto());
        producto.setStock(createDTO.getStock());
        producto.setPrecioUnitario(createDTO.getPrecioUnitario());
        producto.setImagen(createDTO.getImagen());
        
        if (image != null && !image.isEmpty()) {
            String filename = saveImage(image);
            producto.setImagen(filename);
        }
        
        Producto savedProducto = productoRepository.save(producto);
        return productoMapper.toDTO(savedProducto);
    }

    public ProductoDTO updateProducto(Integer id, ProductoCreateDTO updateDTO, MultipartFile image) throws IOException {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto not found"));
        
        producto.setDescripcion(updateDTO.getDescripcion());
        producto.setTipoProducto(updateDTO.getTipoProducto());
        producto.setCategoriaProducto(updateDTO.getCategoriaProducto());
        producto.setStock(updateDTO.getStock());
        producto.setPrecioUnitario(updateDTO.getPrecioUnitario());
        
        if (image != null && !image.isEmpty()) {
            String filename = saveImage(image);
            producto.setImagen(filename);
        }
        
        Producto updatedProducto = productoRepository.save(producto);
        return productoMapper.toDTO(updatedProducto);
    }

    public void deleteProducto(Integer id) {
        productoRepository.deleteById(id);
    }

    private String saveImage(MultipartFile file) throws IOException {
        Path path = Paths.get(uploadDir);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
        
        String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path filePath = path.resolve(filename);
        Files.copy(file.getInputStream(), filePath);
        
        return filename;
    }
}
