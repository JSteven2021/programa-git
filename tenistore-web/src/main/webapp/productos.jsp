
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.tenistore.modelo.Producto" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Productos - TeniStore</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f4f4f4;
            margin: 30px;
        }

        h1, h2 {
            color: #222;
        }

        form {
            background: white;
            padding: 20px;
            margin-bottom: 25px;
            border-radius: 8px;
            max-width: 650px;
        }

        label {
            display: block;
            margin-top: 12px;
        }

        input, textarea {
            width: 100%;
            padding: 9px;
            margin-top: 5px;
            box-sizing: border-box;
        }

        button {
            margin-top: 15px;
            padding: 10px 18px;
            background: #222;
            color: white;
            border: none;
            cursor: pointer;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            background: white;
        }

        th, td {
            border: 1px solid #ccc;
            padding: 10px;
            text-align: left;
        }

        th {
            background: #222;
            color: white;
        }

        .acciones {
            display: flex;
            gap: 8px;
        }

        .acciones form {
            padding: 0;
            margin: 0;
        }

        .editar {
            background: #1769aa;
        }

        .eliminar {
            background: #b3261e;
        }

        #formularioEdicion {
            display: none;
        }
    </style>
</head>

<body>

    <h1>TeniStore - Gestión de productos</h1>

    <h2>Consultar productos (GET)</h2>

    <form action="productos" method="GET">
        <label>Buscar producto:</label>
        <input type="text" name="filtro"
               value="<%= request.getAttribute("filtro") == null ? "" : request.getAttribute("filtro") %>">
        <button type="submit">Consultar</button>
    </form>

    <h2>Registrar producto (POST)</h2>

    <form action="productos" method="POST">

        <input type="hidden" name="accion" value="registrar">

        <label>Categoría:</label>
        <input type="text" name="categoria" required>

        <label>Código:</label>
        <input type="text" name="codigo" required>

        <label>Color:</label>
        <input type="text" name="color" required>

        <label>Descripción:</label>
        <textarea name="descripcion" required></textarea>

        <label>Género:</label>
        <input type="text" name="genero" required>

        <label>Marca:</label>
        <input type="text" name="marca" required>

        <label>Material:</label>
        <input type="text" name="material" required>

        <label>Nombre del producto:</label>
        <input type="text" name="nombre" required>

        <label>Precio:</label>
        <input type="number" name="precio" min="0" required>

        <label>Stock:</label>
        <input type="number" name="stock" min="0" required>

        <label>Talla:</label>
        <input type="number" name="talla" min="1" required>

        <button type="submit">Guardar producto</button>

    </form>

    <h2 id="tituloEdicion">Editar producto</h2>

    <form action="productos" method="POST" id="formularioEdicion">

        <input type="hidden" name="accion" value="actualizar">
        <input type="hidden" name="id" id="editId">

        <label>Categoría:</label>
        <input type="text" name="categoria" id="editCategoria" required>

        <label>Código:</label>
        <input type="text" name="codigo" id="editCodigo" required>

        <label>Color:</label>
        <input type="text" name="color" id="editColor" required>

        <label>Descripción:</label>
        <textarea name="descripcion" id="editDescripcion" required></textarea>

        <label>Género:</label>
        <input type="text" name="genero" id="editGenero" required>

        <label>Marca:</label>
        <input type="text" name="marca" id="editMarca" required>

        <label>Material:</label>
        <input type="text" name="material" id="editMaterial" required>

        <label>Nombre:</label>
        <input type="text" name="nombre" id="editNombre" required>

        <label>Precio:</label>
        <input type="number" name="precio" id="editPrecio" min="0" required>

        <label>Stock:</label>
        <input type="number" name="stock" id="editStock" min="0" required>

        <label>Talla:</label>
        <input type="number" name="talla" id="editTalla" min="1" required>

        <button type="submit">Guardar cambios</button>
        <button type="button" onclick="cancelarEdicion()">Cancelar</button>

    </form>

    <h2>Resultado</h2>

    <p>${mensaje}</p>

    <h2>Productos registrados</h2>

    <table>
        <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Categoría</th>
            <th>Código</th>
            <th>Marca</th>
            <th>Precio</th>
            <th>Stock</th>
            <th>Talla</th>
            <th>Acciones</th>
        </tr>

        <%
            List<Producto> productos =
                (List<Producto>) request.getAttribute("productos");

            if (productos != null) {
                for (Producto p : productos) {
        %>

        <tr>
            <td><%= p.getId() %></td>
            <td><%= p.getNombre() %></td>
            <td><%= p.getCategoria() %></td>
            <td><%= p.getCodigo() %></td>
            <td><%= p.getMarca() %></td>
            <td><%= p.getPrecio() %></td>
            <td><%= p.getStock() %></td>
            <td><%= p.getTalla() %></td>

            <td>
                <div class="acciones">

                    <button type="button" class="editar"
                        onclick="editarProducto(
                            '<%= p.getId() %>',
                            '<%= p.getCategoria() %>',
                            '<%= p.getCodigo() %>',
                            '<%= p.getColor() %>',
                            '<%= p.getDescripcion().replace("'", "\\'") %>',
                            '<%= p.getGenero() %>',
                            '<%= p.getMarca() %>',
                            '<%= p.getMaterial() %>',
                            '<%= p.getNombre() %>',
                            '<%= p.getPrecio() %>',
                            '<%= p.getStock() %>',
                            '<%= p.getTalla() %>'
                        )">
                        Editar
                    </button>

                    <form action="productos" method="POST"
                          onsubmit="return confirm('¿Eliminar este producto?');">

                        <input type="hidden" name="accion" value="eliminar">
                        <input type="hidden" name="id"
                               value="<%= p.getId() %>">

                        <button type="submit" class="eliminar">
                            Eliminar
                        </button>

                    </form>

                </div>
            </td>
        </tr>

        <%
                }
            }
        %>

    </table>

    <script>
        function editarProducto(id, categoria, codigo, color,
                descripcion, genero, marca, material, nombre,
                precio, stock, talla) {

            document.getElementById("formularioEdicion").style.display = "block";

            document.getElementById("editId").value = id;
            document.getElementById("editCategoria").value = categoria;
            document.getElementById("editCodigo").value = codigo;
            document.getElementById("editColor").value = color;
            document.getElementById("editDescripcion").value = descripcion;
            document.getElementById("editGenero").value = genero;
            document.getElementById("editMarca").value = marca;
            document.getElementById("editMaterial").value = material;
            document.getElementById("editNombre").value = nombre;
            document.getElementById("editPrecio").value = precio;
            document.getElementById("editStock").value = stock;
            document.getElementById("editTalla").value = talla;

            document.getElementById("formularioEdicion").scrollIntoView({
                behavior: "smooth"
            });
        }

        function cancelarEdicion() {
            document.getElementById("formularioEdicion").style.display = "none";
        }
    </script>

</body>
</html>

