
package com.tenistore.dao;

import com.tenistore.conexion.ConexionBD;
import com.tenistore.modelo.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    // CREATE: registrar producto
    public boolean insertar(Producto producto) {
        String sql = "INSERT INTO productos "
                + "(categoria, codigo, color, descripcion, genero, marca, "
                + "material, nombre, precio, stock, talla) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setString(1, producto.getCategoria());
            sentencia.setString(2, producto.getCodigo());
            sentencia.setString(3, producto.getColor());
            sentencia.setString(4, producto.getDescripcion());
            sentencia.setString(5, producto.getGenero());
            sentencia.setString(6, producto.getMarca());
            sentencia.setString(7, producto.getMaterial());
            sentencia.setString(8, producto.getNombre());
            sentencia.setInt(9, producto.getPrecio());
            sentencia.setInt(10, producto.getStock());
            sentencia.setInt(11, producto.getTalla());

            return sentencia.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al insertar producto: " + e.getMessage());
            return false;
        }
    }

    // READ: consultar todos los productos
    public List<Producto> listar() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement sentencia = conexion.prepareStatement(sql);
             ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {
                Producto producto = new Producto(
                        resultado.getInt("id"),
                        resultado.getString("categoria"),
                        resultado.getString("codigo"),
                        resultado.getString("color"),
                        resultado.getString("descripcion"),
                        resultado.getString("genero"),
                        resultado.getString("marca"),
                        resultado.getString("material"),
                        resultado.getString("nombre"),
                        resultado.getInt("precio"),
                        resultado.getInt("stock"),
                        resultado.getInt("talla")
                );

                productos.add(producto);
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar productos: " + e.getMessage());
        }

        return productos;
    }

    // UPDATE: actualizar producto
    public boolean actualizar(Producto producto) {
        String sql = "UPDATE productos SET categoria=?, codigo=?, color=?, "
                + "descripcion=?, genero=?, marca=?, material=?, nombre=?, "
                + "precio=?, stock=?, talla=? WHERE id=?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setString(1, producto.getCategoria());
            sentencia.setString(2, producto.getCodigo());
            sentencia.setString(3, producto.getColor());
            sentencia.setString(4, producto.getDescripcion());
            sentencia.setString(5, producto.getGenero());
            sentencia.setString(6, producto.getMarca());
            sentencia.setString(7, producto.getMaterial());
            sentencia.setString(8, producto.getNombre());
            sentencia.setInt(9, producto.getPrecio());
            sentencia.setInt(10, producto.getStock());
            sentencia.setInt(11, producto.getTalla());
            sentencia.setInt(12, producto.getId());

            return sentencia.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar producto: " + e.getMessage());
            return false;
        }
    }

    // DELETE: eliminar producto
    public boolean eliminar(int id) {
        String sql = "DELETE FROM productos WHERE id=?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, id);
            return sentencia.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar producto: " + e.getMessage());
            return false;
        }
    }
}
