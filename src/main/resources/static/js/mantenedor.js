let userIdToDelete = null;
let userIdToUpdate = null;

function setUserIdToDelete(button) {
    userIdToDelete = button.getAttribute("data-id");
}

function setUserIdToUpdate(button) {
    userIdToUpdate = button.getAttribute("data-id");
    document.getElementById("updateUserId").value = userIdToUpdate;
}

function deleteUser(id) {
    fetch(`http://localhost:8082/usuario/${id}`, {
        method: "DELETE",
    })
        .then((response) => {
            if (response.ok) {
                alert("Usuario eliminado exitosamente.");
                window.location.reload(); // Recargar la página
            } else {
                alert("Error al eliminar el usuario.");
            }
        })
        .catch((error) => console.error("Error:", error));
}


function updateUser() {
    const formData = new FormData(document.getElementById("updateUserForm"));
    const data = Object.fromEntries(formData.entries());

    fetch(`http://localhost:8082/usuario/${userIdToUpdate}`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
    })
    .then((response) => {
        if (response.ok) {
            alert("Usuario actualizado correctamente.");
            location.reload();
        } else {
            alert("Error al actualizar el usuario.");
        }
    });
}

document.addEventListener("DOMContentLoaded", () => {
    const updateModal = document.getElementById("updateModal");
    updateModal.addEventListener("show.bs.modal", function (event) {
        const button = event.relatedTarget; // Botón que disparó el modal
        const id = button.getAttribute("data-id");
        const nombre = button.getAttribute("data-nombre");
        const rut = button.getAttribute("data-rut");
        const direccion = button.getAttribute("data-direccion");
        const comuna = button.getAttribute("data-comuna");

        // Precargar datos en los inputs del modal
        document.getElementById("updateUserId").value = id;
        document.getElementById("updateUserName").value = nombre;
        document.getElementById("updateRut").value = rut;
        document.getElementById("updateDireccion").value = direccion;
        document.getElementById("updateComuna").value = comuna;
    });
});
