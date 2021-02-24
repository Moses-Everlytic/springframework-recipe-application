package com.nexstudio.recipe.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.nexstudio.recipe.commands.RecipeCommand;
import com.nexstudio.recipe.models.Recipe;
import com.nexstudio.recipe.services.RecipeService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class RecipeControllerTest {
	@Mock
	RecipeService recipeService;

	RecipeController recipeController;

	MockMvc mockMvc;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

		recipeController = new RecipeController(recipeService);
		mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
	}

	@Test
	public void testGetRecipe() throws Exception {
		Recipe recipe = new Recipe();
		recipe.setId(1L);

		when(recipeService.findById(anyLong())).thenReturn(recipe);

		mockMvc.perform(MockMvcRequestBuilders.get("recipe/show/1"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("recipe/show"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("recipe"));
	}

	@Test
	public void shouldGetNewRecipeForm() throws Exception {
		RecipeCommand command = new RecipeCommand();

		mockMvc.perform(MockMvcRequestBuilders.get("recipe/new"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("recipe/recipeform"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("recipe"));
	}

	@Test
	public void shouldPostNewRecipeForm() throws Exception {
		RecipeCommand command = new RecipeCommand();
		command.setId(2L);

		when(recipeService.saveRecipeCommand(any())).thenReturn(command);

		mockMvc.perform(
				MockMvcRequestBuilders.post("/recipe")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("id", "")
				.param("description", "descriptionValue")
			)
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/recipe/show/2"));
	}

	@Test
	public void shouldGetUpdateView() throws Exception {
		RecipeCommand command = new RecipeCommand();
		command.setId(2L);

		when(recipeService.findCommandById(anyLong())).thenReturn(command);

		mockMvc.perform(MockMvcRequestBuilders.get("recipe/update/2"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("recipe/recipeform"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("recipe"));
	}

	@Test
	public void shouldShowById() throws Exception {
		Recipe recipe = new Recipe();
		recipe.setId(1L);

		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();

		when(recipeService.findById(anyLong())).thenReturn(recipe);

		mockMvc.perform(MockMvcRequestBuilders.get("/recipe/show/1"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("recipe/show"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("recipe"));
	}
}
