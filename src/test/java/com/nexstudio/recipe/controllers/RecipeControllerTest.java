package com.nexstudio.recipe.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.nexstudio.recipe.commands.RecipeCommand;
import com.nexstudio.recipe.exceptions.NotFoundException;
import com.nexstudio.recipe.models.Recipe;
import com.nexstudio.recipe.services.RecipeService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
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
		mockMvc = MockMvcBuilders.standaloneSetup(recipeController)
				.setControllerAdvice(new ControllerExceptionHandler())
				.build();
	}

	@Test
	public void shouldRedirectToHomePage() throws Exception {
		mockMvc.perform(get("/recipe"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/"));
	}

	@Test
	public void testGetRecipe() throws Exception {
		Recipe recipe = new Recipe();
		recipe.setId(1L);

		when(recipeService.findById(anyLong())).thenReturn(recipe);

		mockMvc.perform(get("/recipe/show/1"))
			.andExpect(status().isOk())
			.andExpect(view().name("recipe/show"))
			.andExpect(model().attributeExists("recipe"));
	}

	@Test
	public void shouldGetNewRecipeForm() throws Exception {
		mockMvc.perform(get("/recipe/new"))
			.andExpect(status().isOk())
			.andExpect(view().name("recipe/recipeform"))
			.andExpect(model().attributeExists("recipe"));
	}

	@Test
	public void shouldPostNewRecipeForm() throws Exception {
		RecipeCommand command = new RecipeCommand();
		command.setId(2L);

		when(recipeService.saveRecipeCommand(any())).thenReturn(command);

		mockMvc.perform(
				post("/recipe")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("id", "")
				.param("description", "descriptionValue")
				.param("directions", "directionsText")
			)
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/recipe/show/2"));
	}

	
	@Test
	public void shouldPostNewRecipeFormValidationFail() throws Exception {
		RecipeCommand command = new RecipeCommand();
		command.setId(2L);

		when(recipeService.saveRecipeCommand(any())).thenReturn(command);

		mockMvc.perform(
				post("/recipe")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("id", "")
				.param("cookTime", "3000")
			)
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("recipe"))
			.andExpect(view().name("recipe/recipeform"));
	}

	@Test
	public void shouldGetUpdateView() throws Exception {
		RecipeCommand command = new RecipeCommand();
		command.setId(1L);

		when(recipeService.findCommandById(anyLong())).thenReturn(command);

		mockMvc.perform(get("/recipe/update/1"))
			.andExpect(status().isOk())
			.andExpect(view().name("recipe/recipeform"))
			.andExpect(model().attributeExists("recipe"));
	}

	@Test
	public void shouldShowById() throws Exception {
		Recipe recipe = new Recipe();
		recipe.setId(1L);

		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();

		when(recipeService.findById(anyLong())).thenReturn(recipe);

		mockMvc.perform(get("/recipe/show/1"))
				.andExpect(status().isOk())
				.andExpect(view().name("recipe/show"))
				.andExpect(model().attributeExists("recipe"));
	}

	@Test
	public void shouldDeleteAction() throws Exception {
		mockMvc.perform(get("/recipe/delete/1"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/"));
	}

	@Test
    public void testGetRecipeNotFound() throws Exception {
        when(recipeService.findById(anyLong())).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/recipe/show/1"))
                .andExpect(status().isNotFound())
				.andExpect(view().name("404error"));
    }

	@Test
	public void testGetRecipeNumberFormatException() throws Exception {
		mockMvc.perform(get("/recipe/show/asdf"))
				.andExpect(status().isBadRequest())
				.andExpect(view().name("400error"));
	}
}
