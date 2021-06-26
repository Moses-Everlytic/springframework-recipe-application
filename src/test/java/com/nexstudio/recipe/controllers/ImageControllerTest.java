package com.nexstudio.recipe.controllers;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nexstudio.recipe.commands.RecipeCommand;
import com.nexstudio.recipe.services.ImageService;
import com.nexstudio.recipe.services.RecipeService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;    
    
public class ImageControllerTest {
    @Mock
	ImageService imageService;

    @Mock
    RecipeService recipeService;

    ImageController controller;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        controller = new ImageController(imageService, recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(new ControllerExceptionHandler()).build();
    }

    @Test
    public void testGetImageForm() throws Exception {
        //give
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);

        //when
        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/image/1"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().attributeExists("recipe"))
            .andExpect(MockMvcResultMatchers.view().name("recipe/image/imageuploadform"));

    }
        
    @Test
    public void testHandleImagePost() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("imageFile", "testing.txt", "text/plain", "Nexstudio Application".getBytes());

        this.mockMvc.perform(MockMvcRequestBuilders.multipart("/recipe/image/1").file(multipartFile))
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
            .andExpect(MockMvcResultMatchers.header().string("Location", "/recipe/show/1"));

        verify(imageService, times(1)).saveImageFile(anyLong(), any());
    }
}
    