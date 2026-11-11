
package com.tenistore.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String URL =
            "jdbc:mysql://localhost:3306/tenisstore?useSSL=false&serverTimezone=America/Bogota";

    private static final String USUARIO = "root";
    private static final String CLAVE = "";

    public static Connection conectar() throws SQLException {

        Connection conexion = DriverManager.getConnection(
                URL,
                USUARIO,
                CLAVE
        );

        System.out.println("¡Conexión exitosa con TeniStore!");

        return conexion;
    }
}
