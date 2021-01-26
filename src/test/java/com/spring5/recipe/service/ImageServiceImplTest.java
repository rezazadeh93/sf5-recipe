package com.spring5.recipe.service;

import com.spring5.recipe.domain.Recipe;
import com.spring5.recipe.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImageServiceImplTest {
    public static final long RECIPE_ID_TEST = 2L;
    @Mock
    RecipeRepository recipeRepository;

    @InjectMocks
    ImageServiceImpl imageService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testSaveNewImageFile() throws IOException {
        //given
        MockMultipartFile multipartFile = new MockMultipartFile(
                "imageRecipe",
                "testing.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Testing Image Recipe File".getBytes()
        );

        Optional<Recipe> recipe = Optional.of(new Recipe());
        recipe.get().setId(RECIPE_ID_TEST);

        when(recipeRepository.findById(anyLong())).thenReturn(recipe);

        ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);

        //when
        imageService.saveNewImageFile(RECIPE_ID_TEST, multipartFile);

        //then
        verify(recipeRepository, times(1)).save(argumentCaptor.capture());
        Recipe savedRecipe = argumentCaptor.getValue();
        assertEquals(multipartFile.getBytes().length, savedRecipe.getImage().length);
    }
}