// =============================
// TENNIS STORE
// =============================

let cantidad = 0;

const botones = document.querySelectorAll(".agregar-carrito");
const carrito = document.getElementById("contador");

botones.forEach((boton) => {
    boton.addEventListener("click", () => {
        cantidad++;

        if (carrito) {
            carrito.innerHTML = cantidad;
        }

        alert("Producto agregado al carrito.");
    });
});
