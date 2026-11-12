
package com.tenistore.servlet;

import com.tenistore.dao.ProductoDAO;
import com.tenistore.modelo.Producto;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/productos")
public class ProductoServlet extends HttpServlet {

    private final ProductoDAO productoDAO = new ProductoDAO();

    // GET: consultar y buscar productos
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String filtro = request.getParameter("filtro");

        if (filtro == null) {
            filtro = "";
        }

        List<Producto> productos;

        if (filtro.trim().isEmpty()) {
            productos = productoDAO.listar();
        } else {
            productos = productoDAO.buscar(filtro.trim());
        }

        request.setAttribute("filtro", filtro);
        request.setAttribute("productos", productos);

        request.setAttribute("mensaje",
                productos.isEmpty()
                ? "No se encontraron productos"
                : "Productos encontrados: " + productos.size());

        request.getRequestDispatcher("/productos.jsp")
                .forward(request, response);
    }

    // POST: registrar, actualizar y eliminar
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String accion = request.getParameter("accion");

        try {

            if ("eliminar".equals(accion)) {

                int id = Integer.parseInt(request.getParameter("id"));

                boolean eliminado = productoDAO.eliminar(id);

                request.setAttribute("mensaje",
                        eliminado
                        ? "Producto eliminado correctamente"
                        : "No se pudo eliminar el producto");

            } else if ("actualizar".equals(accion)) {

                Producto producto = obtenerProducto(request);

                producto.setId(Integer.parseInt(
                        request.getParameter("id")));

                boolean actualizado = productoDAO.actualizar(producto);

                request.setAttribute("mensaje",
                        actualizado
                        ? "Producto actualizado correctamente"
                        : "No se pudo actualizar el producto");

            } else if ("registrar".equals(accion)) {

                Producto producto = obtenerProducto(request);

                boolean guardado = productoDAO.insertar(producto);

                request.setAttribute("mensaje",
                        guardado
                        ? "Producto guardado correctamente en MySQL"
                        : "No se pudo guardar el producto");

            } else {

                request.setAttribute("mensaje", "Acción no válida");
            }

        } catch (NumberFormatException e) {

            request.setAttribute("mensaje",
                    "Error: verifica que los números sean correctos");

        } catch (SQLException e) {

            request.setAttribute("mensaje",
                    "Error de MySQL: " + e.getMessage());
        }

        List<Producto> productos = productoDAO.listar();

        request.setAttribute("productos", productos);
        request.setAttribute("filtro", "");

        request.getRequestDispatcher("/productos.jsp")
                .forward(request, response);
    }

    // Obtener los datos del formulario
    private Producto obtenerProducto(HttpServletRequest request) {

        Producto producto = new Producto();

        producto.setCategoria(request.getParameter("categoria"));
        producto.setCodigo(request.getParameter("codigo"));
        producto.setColor(request.getParameter("color"));
        producto.setDescripcion(request.getParameter("descripcion"));
        producto.setGenero(request.getParameter("genero"));
        producto.setMarca(request.getParameter("marca"));
        producto.setMaterial(request.getParameter("material"));
        producto.setNombre(request.getParameter("nombre"));

        producto.setPrecio(Integer.parseInt(
                request.getParameter("precio")));

        producto.setStock(Integer.parseInt(
                request.getParameter("stock")));

        producto.setTalla(Integer.parseInt(
                request.getParameter("talla")));

        return producto;
    }
}
