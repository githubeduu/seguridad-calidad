function toggleInputFields() {
    const tipo = document.getElementById("tipo").value;
    const fileGroup = document.getElementById("fileGroup");
    const youtubeGroup = document.getElementById("youtubeGroup");
  
    if (tipo === "youtube") {
      fileGroup.classList.add("d-none");
      youtubeGroup.classList.remove("d-none");
    } else {
      fileGroup.classList.remove("d-none");
      youtubeGroup.classList.add("d-none");
    }
  }
  

  const updateModal = document.getElementById('updateModal');
  updateModal.addEventListener('show.bs.modal', function (event) {
      const button = event.relatedTarget; // Botón que disparó el evento
      document.getElementById('updateUserId').value = button.getAttribute('data-id');
      document.getElementById('updateDireccion').value = button.getAttribute('data-direccion');
      document.getElementById('updateComuna').value = button.getAttribute('data-comuna');
  
      // Preseleccionar el rol en el dropdown
      const rolId = button.getAttribute('data-rol'); // Valor del rol actual
      document.getElementById('updateRol').value = rolId;
  });
  