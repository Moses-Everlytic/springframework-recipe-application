package com.nexstudio.recipe.bootstrap;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.nexstudio.recipe.models.Category;
import com.nexstudio.recipe.models.Difficulty;
import com.nexstudio.recipe.models.Ingredient;
import com.nexstudio.recipe.models.Notes;
import com.nexstudio.recipe.models.Recipe;
import com.nexstudio.recipe.models.UnitOfMeasure;
import com.nexstudio.recipe.repositories.CategoryRepository;
import com.nexstudio.recipe.repositories.RecipeRepository;
import com.nexstudio.recipe.repositories.UnitOfMeasureRepository;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class StartupData implements ApplicationListener<ContextRefreshedEvent> {

    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public StartupData(RecipeRepository recipeRepository, CategoryRepository categoryRepository,
            UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        recipeRepository.saveAll(getRecipes());
    }    

    private List<Recipe> getRecipes() {

        List<Recipe> recipes = new ArrayList<>(2);

        // get UOM
        Optional<UnitOfMeasure> eachUOMOptional = unitOfMeasureRepository.findByDescription("Each");
        isUOMPresent(eachUOMOptional);

        Optional<UnitOfMeasure> tableSpoonUOMOptional = unitOfMeasureRepository.findByDescription("Tablespoon");
        isUOMPresent(tableSpoonUOMOptional);

        Optional<UnitOfMeasure> teaSpoonUOMOptional = unitOfMeasureRepository.findByDescription("Teaspoon");
        isUOMPresent(eachUOMOptional);

        Optional<UnitOfMeasure> dashUOMOptional = unitOfMeasureRepository.findByDescription("Dash");
        isUOMPresent(dashUOMOptional);

        Optional<UnitOfMeasure> pintUOMOptional = unitOfMeasureRepository.findByDescription("Pint");
        isUOMPresent(pintUOMOptional);

        Optional<UnitOfMeasure> cupUOMOptional = unitOfMeasureRepository.findByDescription("Cup");
        isUOMPresent(cupUOMOptional);

        UnitOfMeasure eachUOM = eachUOMOptional.get();
        UnitOfMeasure tableSpoonUOM = tableSpoonUOMOptional.get();
        UnitOfMeasure teaSpoonUOM = teaSpoonUOMOptional.get();
        UnitOfMeasure dashUOM = dashUOMOptional.get();
        UnitOfMeasure pintUOM = pintUOMOptional.get();
        UnitOfMeasure cupUOM = cupUOMOptional.get();

        // get Categories
        Optional<Category> mexicanCategoryOptional = categoryRepository.findByDescription("Mexican");
        isCategoryPresent(mexicanCategoryOptional);

        Optional<Category> africanCategoryOptional = categoryRepository.findByDescription("African");
        isCategoryPresent(africanCategoryOptional);

        Category mexicanCategory = mexicanCategoryOptional.get();
        Category africanCategory = africanCategoryOptional.get();

        // Perfect Guacamole Recipe
        Recipe perfectGuacamoleRecipe = new Recipe();
        perfectGuacamoleRecipe.setDescription("Perfect Guacamole");
        perfectGuacamoleRecipe.setPerpTime(10);
        perfectGuacamoleRecipe.setCookTime(0);
        perfectGuacamoleRecipe.setServings(4);
        perfectGuacamoleRecipe.setSource("Simply Recipes");
        perfectGuacamoleRecipe.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
        perfectGuacamoleRecipe.setDifficulty(Difficulty.EASY);
        perfectGuacamoleRecipe.setDirections(
                "1 Cut the avocado, remove flesh: Cut the avocados in half. Remove the pit. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place in a bowl."
                        + "\n"
                        + "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)"
                        + "\n"
                        + "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown."
                        + "\n"
                        + "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness."
                        + "\n"
                        + "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste."
                        + "\n"
                        + "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving."
                        + "\n"
                        + "4 Serve: Serve immediately, or if making a few hours ahead, place plastic wrap on the surface of the guacamole and press down to cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.");

        Notes perfectGuacamoleNotes = new Notes();
        perfectGuacamoleNotes.setRecipeNotes(
                "Simple Guacamole: The simplest version of guacamole is just mashed avocados with salt. Don’t let the lack of availability of other ingredients stop you from making guacamole."
                        + "\n" + "\n"
                        + "Quick guacamole: For a very quick guacamole just take a 1/4 cup of salsa and mix it in with your mashed avocados."
                        + "\n" + "\n"
                        + "Don’t have enough avocados? To extend a limited supply of avocados, add either sour cream or cottage cheese to your guacamole dip. Purists may be horrified, but so what? It tastes great");

        perfectGuacamoleNotes.setRecipe(perfectGuacamoleRecipe);
        perfectGuacamoleRecipe.setNotes(perfectGuacamoleNotes);


        perfectGuacamoleRecipe.getIngredients().add(new Ingredient("ripe avocados", new BigDecimal(2), eachUOM, perfectGuacamoleRecipe));
        perfectGuacamoleRecipe.getIngredients().add(new Ingredient("Kosher salt", new BigDecimal(".5"), teaSpoonUOM, perfectGuacamoleRecipe));
        perfectGuacamoleRecipe.getIngredients()
                .add(new Ingredient("fresh lime or lemon juice", new BigDecimal(2), tableSpoonUOM, perfectGuacamoleRecipe));
        perfectGuacamoleRecipe.getIngredients()
                .add(new Ingredient("minced red onion ir thinly sliced green onion", new BigDecimal(2), tableSpoonUOM, perfectGuacamoleRecipe));
        perfectGuacamoleRecipe.getIngredients()
                .add(new Ingredient("serrano chiles, stems and seeds removed, minced", new BigDecimal(2), eachUOM, perfectGuacamoleRecipe));
        perfectGuacamoleRecipe.getIngredients().add(new Ingredient("Cilantro", new BigDecimal(2), tableSpoonUOM, perfectGuacamoleRecipe));
        perfectGuacamoleRecipe.getIngredients()
                .add(new Ingredient("freshly grated black pepper", new BigDecimal(2), dashUOM, perfectGuacamoleRecipe));
        perfectGuacamoleRecipe.getIngredients()
                .add(new Ingredient("ripe tomato, seeds and pulp removed, chopped", new BigDecimal(".5"), eachUOM, perfectGuacamoleRecipe));

        perfectGuacamoleRecipe.getCategories().add(mexicanCategory);
        perfectGuacamoleRecipe.getCategories().add(africanCategory);

        recipes.add(perfectGuacamoleRecipe);

        // Spicy Grilled Chicken Tacos Recipe
        Recipe tacoRecipe = new Recipe();
        tacoRecipe.setDescription("Spicy Grilled Chicken Tacos");
        tacoRecipe.setPerpTime(20);
        tacoRecipe.setCookTime(15);
        tacoRecipe.setServings(6);
        tacoRecipe.setSource("Simply Recipes");
        tacoRecipe.setUrl("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");
        tacoRecipe.setDifficulty(Difficulty.MODARETE);
        tacoRecipe.setDirections("1 Prepare a gas or charcoal grill for medium-high, direct heat." + "\n" + "\n"
                + "2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over."
                + "\n" + "\n" + "Set aside to marinate while the grill heats and you prepare the rest of the toppings."
                + "\n" + "\n" + "Spicy Grilled Chicken Tacos" + "\n" + "\n"
                + "3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes."
                + "\n" + "\n"
                + "4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side."
                + "\n" + "\n" + "Wrap warmed tortillas in a tea towel to keep them warm until serving." + "\n" + "\n"
                + "5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.");

        Notes tacoNotes = new Notes();
        tacoNotes.setRecipeNotes("We have a family motto and it is this: Everything goes better in a tortilla." + "\n"
                + "Any and every kind of leftover can go inside a warm tortilla, usually with a healthy dose of pickled jalapenos. I can always sniff out a late-night snacker when the aroma of tortillas heating in a hot pan on the stove comes wafting through the house.");

        tacoNotes.setRecipe(tacoRecipe);
        tacoRecipe.setNotes(tacoNotes);

        tacoRecipe.getIngredients().add(new Ingredient("Ancho Chili Powder", new BigDecimal(2), tableSpoonUOM, tacoRecipe));
        tacoRecipe.getIngredients().add(new Ingredient("Dried Oregano", new BigDecimal(1), teaSpoonUOM, tacoRecipe));
        tacoRecipe.getIngredients().add(new Ingredient("Dried Cumin", new BigDecimal(1), teaSpoonUOM, tacoRecipe));
        tacoRecipe.getIngredients().add(new Ingredient("Sugar", new BigDecimal(1), teaSpoonUOM, tacoRecipe));
        tacoRecipe.getIngredients().add(new Ingredient("Salt", new BigDecimal(".5"), teaSpoonUOM, tacoRecipe));
        tacoRecipe.getIngredients().add(new Ingredient("Clove of Garlic, Choppedr", new BigDecimal(1), eachUOM, tacoRecipe));
        tacoRecipe.getIngredients().add(new Ingredient("finely grated orange zestr", new BigDecimal(1), tableSpoonUOM, tacoRecipe));
        tacoRecipe.getIngredients()
                .add(new Ingredient("fresh-squeezed orange juice", new BigDecimal(3), tableSpoonUOM, tacoRecipe));
        tacoRecipe.getIngredients().add(new Ingredient("Olive Oil", new BigDecimal(2), tableSpoonUOM, tacoRecipe));
        tacoRecipe.getIngredients().add(new Ingredient("boneless chicken thighs", new BigDecimal(4), tableSpoonUOM, tacoRecipe));
        tacoRecipe.getIngredients().add(new Ingredient("small corn tortillasr", new BigDecimal(8), eachUOM, tacoRecipe));
        tacoRecipe.getIngredients().add(new Ingredient("packed baby arugula", new BigDecimal(3), cupUOM, tacoRecipe));
        tacoRecipe.getIngredients().add(new Ingredient("medium ripe avocados, slic", new BigDecimal(2), eachUOM, tacoRecipe));
        tacoRecipe.getIngredients().add(new Ingredient("radishes, thinly sliced", new BigDecimal(4), eachUOM, tacoRecipe));
        tacoRecipe.getIngredients().add(new Ingredient("cherry tomatoes, halved", new BigDecimal(".5"), pintUOM, tacoRecipe));
        tacoRecipe.getIngredients().add(new Ingredient("red onion, thinly sliced", new BigDecimal(".25"), eachUOM, tacoRecipe));
        tacoRecipe.getIngredients().add(new Ingredient("Roughly chopped cilantro", new BigDecimal(4), eachUOM, tacoRecipe));
        tacoRecipe.getIngredients()
                .add(new Ingredient("cup sour cream thinned with 1/4 cup milk", new BigDecimal(4), cupUOM, tacoRecipe));
        tacoRecipe.getIngredients().add(new Ingredient("lime, cut into wedges", new BigDecimal(4), eachUOM, tacoRecipe));

        tacoRecipe.getCategories().add(mexicanCategory);

        recipes.add(tacoRecipe);
        return recipes;
    }

    private void isCategoryPresent(Optional<Category> CategoryOptional) {
        if (!CategoryOptional.isPresent()) {
            throw new RuntimeException("Expected Category Not Found");
        }
    }

    private void isUOMPresent(Optional<UnitOfMeasure> UOMOptional) {
        if (!UOMOptional.isPresent()) {
            throw new RuntimeException("Expected UOM Not Found");
        }
    }
}
