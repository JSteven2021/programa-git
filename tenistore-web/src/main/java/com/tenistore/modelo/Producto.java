
package com.tenistore.modelo;

public class Producto {

    private int id;
    private String categoria;
    private String codigo;
    private String color;
    private String descripcion;
    private String genero;
    private String marca;
    private String material;
    private String nombre;
    private int precio;
    private int stock;
    private int talla;

    public Producto() {
    }

    public Producto(int id, String categoria, String codigo,
            String color, String descripcion, String genero,
            String marca, String material, String nombre,
            int precio, int stock, int talla) {

        this.id = id;
        this.categoria = categoria;
        this.codigo = codigo;
        this.color = color;
        this.descripcion = descripcion;
        this.genero = genero;
        this.marca = marca;
        this.material = material;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.talla = talla;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getTalla() {
        return talla;
    }

    public void setTalla(int talla) {
        this.talla = talla;
    }
}
