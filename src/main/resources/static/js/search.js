function filterRecipes() {
    const input = document.getElementById('searchInput');
    const filter = input.value.toLowerCase();
    const table = document.getElementById('recipeTableBody');
    const tr = table.getElementsByTagName('tr');

    for (let i = 0; i < tr.length; i++) {
        const tdNombre = tr[i].getElementsByTagName('td')[0]; // Columna de Nombre
        if (tdNombre) {
            const txtValue = tdNombre.textContent || tdNombre.innerText;
            tr[i].style.display = txtValue.toLowerCase().indexOf(filter) > -1 ? "" : "none";
        }       
    }
}
