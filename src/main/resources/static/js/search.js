function filterRecipes() {
  const searchInput = document
    .getElementById("searchInput")
    .value.toLowerCase();

  const recipes = document.querySelectorAll("#recipeContainer .card");

  recipes.forEach((recipe) => {
    const name = recipe.querySelector(".receta-nombre").innerText.toLowerCase();
    const type = recipe
      .querySelector(".receta-tipoCocina")
      .innerText.toLowerCase();
    const ingredients = recipe
      .querySelector(".receta-ingredientes")
      .innerText.toLowerCase();
    const difficulty = recipe
      .querySelector(".receta-dificultad")
      .innerText.toLowerCase();
    console.log(difficulty);

    if (
      name.includes(searchInput) ||
      type.includes(searchInput) ||
      ingredients.includes(searchInput) ||
      difficulty.includes(searchInput)
    ) {
      recipe.style.display = "block";
    } else {
      recipe.style.display = "none";
    }
  });
}
