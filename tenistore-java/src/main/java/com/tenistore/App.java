
package com.tenistore;

import com.tenistore.dao.ProductoDAO;
import com.tenistore.modelo.Producto;
import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;

import java.util.List;

public class App {

    public static void main(String[] args) {

        try {
            System.out.println("=== SISTEMA TENISTORE ===");

            ProductoDAO productoDAO = new ProductoDAO();

            System.out.println("Verificando conexión y consulta...");

            List<Producto> productos = productoDAO.listar();

            System.out.println("Consulta finalizada correctamente");
            System.out.println("Cantidad de productos: " + productos.size());

        } finally {
            AbandonedConnectionCleanupThread.checkedShutdown();
        }
    }
}
