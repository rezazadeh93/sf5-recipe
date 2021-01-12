package com.spring5.recipe.converters;

import com.spring5.recipe.commands.NotesCommand;
import com.spring5.recipe.domain.Notes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotesCommandToNotesTest {
    private final static Long ID_VALUE = 1L;
    private final static String DESCRIPTION = "description notes test";

    NotesCommandToNotes converter;

    @BeforeEach
    void setUp() {
        converter = new NotesCommandToNotes();
    }

    @Test
    void testNullParameter() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new NotesCommand()));
    }

    @Test
    void convert() {
        //given
        NotesCommand source = new NotesCommand();
        source.setId(ID_VALUE);
        source.setRecipeNote(DESCRIPTION);

        //when
        Notes note = converter.convert(source);

        //then
        assertNotNull(note);
        assertEquals(ID_VALUE, note.getId());
        assertEquals(DESCRIPTION, note.getRecipeNote());
    }
}