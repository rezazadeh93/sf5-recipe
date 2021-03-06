package com.spring5.recipe.controllers;

import com.spring5.recipe.commands.RecipeCommand;
import com.spring5.recipe.service.ImageService;
import com.spring5.recipe.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ImageControllerTest {
    public static final String RECIPE_ID_TEST = "2";
    @Mock
    ImageService imageService;

    @Mock
    RecipeService recipeService;

    @InjectMocks
    ImageController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    @Test
    void getImageForm() throws Exception {
        //given
        when(recipeService.findCommandById(anyLong())).thenReturn(new RecipeCommand());

        //when & then
        mockMvc.perform(get("/recipe/1/image"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/imageuploadform"))
                .andExpect(model().attributeExists("recipe"));

        verify(recipeService, times(1)).findCommandById(anyLong());

    }

    @Test
    void testImageNumberFormat() throws Exception {
        // when(recipeService.findById(any())).thenThrow(NumberFormatException.class);

        mockMvc.perform(get("/recipe/test/recipeimage"))
                .andExpect(status().is4xxClientError())
                .andExpect(view().name("400error"));
    }

    @Test
    void handleImagePost() throws Exception {
        //given
        MockMultipartFile multipartFile = new MockMultipartFile(
                "imageRecipe",
                "testingImage.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Testing Recipe Image".getBytes());

        //when & then
        mockMvc.perform(multipart("/recipe/"+ RECIPE_ID_TEST + "/image").file(multipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/recipe/" + RECIPE_ID_TEST + "/show"));

        verify(imageService, times(1)).saveNewImageFile(anyLong(), any());
    }

    @Test
    void renderImageFromDB() throws Exception {
        //given
        RecipeCommand command = new RecipeCommand();
        command.setId(1L);

        String s = "fake image text";
        Byte[] bytesBoxed = new Byte[s.getBytes().length];

        int i = 0;

        for (byte primByte : s.getBytes()){
            bytesBoxed[i++] = primByte;
        }

        command.setImage(bytesBoxed);

        when(recipeService.findCommandById(anyLong())).thenReturn(command);

        //when
        MockHttpServletResponse response = mockMvc.perform(get("/recipe/1/recipeimage"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        byte[] reponseBytes = response.getContentAsByteArray();

        assertEquals(s.getBytes().length, reponseBytes.length);
    }
}