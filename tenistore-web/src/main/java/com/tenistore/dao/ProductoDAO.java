

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
    public boolean insertar(Producto producto) throws SQLException {

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
        }
    }

    // READ: consultar todos los productos para JSP
    public List<Producto> listar() {

        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement sentencia = conexion.prepareStatement(sql);
             ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {
                productos.add(mapearProducto(resultado));
            }

        } catch (SQLException e) {
            System.err.println("Error al consultar productos: " + e.getMessage());
        }

        return productos;
    }

    // SEARCH: buscar productos para JSP
    public List<Producto> buscar(String filtro) {

        List<Producto> productos = new ArrayList<>();

        String sql = "SELECT * FROM productos "
                + "WHERE nombre LIKE ? "
                + "OR codigo LIKE ? "
                + "OR marca LIKE ? "
                + "OR categoria LIKE ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            String busqueda = "%" + filtro + "%";

            sentencia.setString(1, busqueda);
            sentencia.setString(2, busqueda);
            sentencia.setString(3, busqueda);
            sentencia.setString(4, busqueda);

            try (ResultSet resultado = sentencia.executeQuery()) {
                while (resultado.next()) {
                    productos.add(mapearProducto(resultado));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar productos: " + e.getMessage());
        }

        return productos;
    }

    // READ: consultar todos los productos para la API
    public List<Producto> listarParaApi() throws SQLException {

        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement sentencia = conexion.prepareStatement(sql);
             ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {
                productos.add(mapearProducto(resultado));
            }
        }

        return productos;
    }

    // READ: consultar un producto por su ID para la API
    public Producto buscarPorId(int id) throws SQLException {

        String sql = "SELECT * FROM productos WHERE id = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, id);

            try (ResultSet resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    return mapearProducto(resultado);
                }
            }
        }

        return null;
    }

    // Convertir resultado SQL en Producto
    private Producto mapearProducto(ResultSet resultado) throws SQLException {

        return new Producto(
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
    }

    // UPDATE: actualizar producto para JSP
    public boolean actualizar(Producto producto) {

        String sql = "UPDATE productos SET categoria=?, codigo=?, color=?, "
                + "descripcion=?, genero=?, marca=?, material=?, nombre=?, "
                + "precio=?, stock=?, talla=? WHERE id=?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            prepararActualizacion(sentencia, producto);
            return sentencia.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar producto: " + e.getMessage());
            return false;
        }
    }

    // UPDATE: actualizar producto para la API
    public boolean actualizarParaApi(Producto producto) throws SQLException {

        String sql = "UPDATE productos SET categoria=?, codigo=?, color=?, "
                + "descripcion=?, genero=?, marca=?, material=?, nombre=?, "
                + "precio=?, stock=?, talla=? WHERE id=?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            prepararActualizacion(sentencia, producto);
            return sentencia.executeUpdate() > 0;
        }
    }

    // Preparar los datos de actualización
    private void prepararActualizacion(
            PreparedStatement sentencia, Producto producto) throws SQLException {

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
    }

    // DELETE: eliminar producto para JSP
    public boolean eliminar(int id) {

        String sql = "DELETE FROM productos WHERE id=?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, id);
            return sentencia.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar producto: " + e.getMessage());
            return false;
        }
    }

    // DELETE: eliminar producto para la API
    public boolean eliminarParaApi(int id) throws SQLException {

        String sql = "DELETE FROM productos WHERE id=?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, id);
            return sentencia.executeUpdate() > 0;
        }
    }
}
