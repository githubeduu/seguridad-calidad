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
  