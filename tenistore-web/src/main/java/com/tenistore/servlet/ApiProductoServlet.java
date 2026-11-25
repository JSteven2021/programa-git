
package com.tenistore.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.tenistore.dao.ProductoDAO;
import com.tenistore.modelo.Producto;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@WebServlet("/api/productos/*")
public class ApiProductoServlet extends HttpServlet {

    private final ProductoDAO productoDAO = new ProductoDAO();
    private final Gson gson = new Gson();

    // GET: consultar todos o un producto
    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        configurarRespuesta(response);

        try {
            Integer id = obtenerId(request);

            if (id == null) {
                List<Producto> productos = productoDAO.listarParaApi();
                enviarJson(response, HttpServletResponse.SC_OK, productos);
                return;
            }

            Producto producto = productoDAO.buscarPorId(id);

            if (producto == null) {
                enviarError(response, HttpServletResponse.SC_NOT_FOUND,
                        "No se encontró el producto");
                return;
            }

            enviarJson(response, HttpServletResponse.SC_OK, producto);

        } catch (IllegalArgumentException e) {
            enviarError(response, HttpServletResponse.SC_BAD_REQUEST,
                    "El ID del producto no es válido");

        } catch (SQLException e) {
            e.printStackTrace();
            enviarError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Error al consultar los productos");
        }
    }

    // POST: registrar un producto
    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        configurarRespuesta(response);

        if (obtenerId(request) != null) {
            enviarError(response, HttpServletResponse.SC_BAD_REQUEST,
                    "Para registrar use /api/productos sin ID");
            return;
        }

        try {
            Producto producto = leerProducto(request);

            if (!validarProducto(producto)) {
                enviarError(response, HttpServletResponse.SC_BAD_REQUEST,
                        "Los datos del producto son incompletos o inválidos");
                return;
            }

            boolean registrado = productoDAO.insertar(producto);

            if (registrado) {
                enviarJson(response, HttpServletResponse.SC_CREATED,
                        Map.of("mensaje", "Producto registrado correctamente"));
            } else {
                enviarError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "No fue posible registrar el producto");
            }

        } catch (JsonSyntaxException | IllegalStateException e) {
            enviarError(response, HttpServletResponse.SC_BAD_REQUEST,
                    "El formato JSON no es válido");

        } catch (SQLException e) {
            e.printStackTrace();
            enviarError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Error al registrar el producto");
        }
    }

    // PUT: actualizar un producto
    @Override
    protected void doPut(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        configurarRespuesta(response);

        try {
            Integer id = obtenerId(request);

            if (id == null) {
                enviarError(response, HttpServletResponse.SC_BAD_REQUEST,
                        "Debe indicar el ID del producto");
                return;
            }

            Producto producto = leerProducto(request);

            if (!validarProducto(producto)) {
                enviarError(response, HttpServletResponse.SC_BAD_REQUEST,
                        "Los datos del producto son incompletos o inválidos");
                return;
            }

            producto.setId(id);

            boolean actualizado = productoDAO.actualizarParaApi(producto);

            if (actualizado) {
                enviarJson(response, HttpServletResponse.SC_OK,
                        Map.of("mensaje", "Producto actualizado correctamente"));
            } else {
                enviarError(response, HttpServletResponse.SC_NOT_FOUND,
                        "No se encontró el producto para actualizar");
            }

        } catch (IllegalArgumentException e) {
            enviarError(response, HttpServletResponse.SC_BAD_REQUEST,
                    "El ID o el JSON no son válidos");

        } catch (JsonSyntaxException | IllegalStateException e) {
            enviarError(response, HttpServletResponse.SC_BAD_REQUEST,
                    "El formato JSON no es válido");

        } catch (SQLException e) {
            e.printStackTrace();
            enviarError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Error al actualizar el producto");
        }
    }

    // DELETE: eliminar un producto
    @Override
    protected void doDelete(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        configurarRespuesta(response);

        try {
            Integer id = obtenerId(request);

            if (id == null) {
                enviarError(response, HttpServletResponse.SC_BAD_REQUEST,
                        "Debe indicar el ID del producto");
                return;
            }

            boolean eliminado = productoDAO.eliminarParaApi(id);

            if (eliminado) {
                enviarJson(response, HttpServletResponse.SC_OK,
                        Map.of("mensaje", "Producto eliminado correctamente"));
            } else {
                enviarError(response, HttpServletResponse.SC_NOT_FOUND,
                        "No se encontró el producto para eliminar");
            }

        } catch (IllegalArgumentException e) {
            enviarError(response, HttpServletResponse.SC_BAD_REQUEST,
                    "El ID del producto no es válido");

        } catch (SQLException e) {
            e.printStackTrace();
            enviarError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Error al eliminar el producto");
        }
    }

    // Leer el producto enviado en formato JSON
    private Producto leerProducto(HttpServletRequest request)
            throws IOException {

        StringBuilder contenido = new StringBuilder();

        try (BufferedReader lector = request.getReader()) {
            String linea;

            while ((linea = lector.readLine()) != null) {
                contenido.append(linea);
            }
        }

        if (contenido.toString().isBlank()) {
            throw new IllegalStateException("El cuerpo JSON está vacío");
        }

        return gson.fromJson(contenido.toString(), Producto.class);
    }

    // Validar datos básicos
    private boolean validarProducto(Producto producto) {

        return producto != null
                && producto.getCategoria() != null
                && !producto.getCategoria().isBlank()
                && producto.getCodigo() != null
                && !producto.getCodigo().isBlank()
                && producto.getNombre() != null
                && !producto.getNombre().isBlank()
                && producto.getPrecio() >= 0
                && producto.getStock() >= 0
                && producto.getTalla() > 0;
    }

    // Obtener el ID de la dirección
    private Integer obtenerId(HttpServletRequest request) {

        String ruta = request.getPathInfo();

        if (ruta == null || ruta.equals("/") || ruta.isBlank()) {
            return null;
        }

        String valor = ruta.substring(1);

        if (valor.contains("/")) {
            throw new IllegalArgumentException("Ruta no válida");
        }

        int id = Integer.parseInt(valor);

        if (id <= 0) {
            throw new IllegalArgumentException("ID no válido");
        }

        return id;
    }

    // Configurar formato de respuesta
    private void configurarRespuesta(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }

    // Enviar JSON
    private void enviarJson(
            HttpServletResponse response,
            int estado,
            Object datos) throws IOException {

        response.setStatus(estado);
        response.getWriter().write(gson.toJson(datos));
    }

    // Enviar errores JSON
    private void enviarError(
            HttpServletResponse response,
            int estado,
            String mensaje) throws IOException {

        enviarJson(response, estado, Map.of("error", mensaje));
    }
}
