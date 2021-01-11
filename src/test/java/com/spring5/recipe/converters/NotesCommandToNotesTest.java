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
    void nullableTest() {
        assertNull(converter.convert(null));
    }

    @Test
    void notNullTest() {
        assertNotNull(converter.convert(new NotesCommand()));
    }

    @Test
    void convert() {
        NotesCommand source = new NotesCommand();
        source.setId(ID_VALUE);
        source.setRecipeNote(DESCRIPTION);

        Notes note = converter.convert(source);

        assertNotNull(note);
        assertEquals(ID_VALUE, note.getId());
        assertEquals(DESCRIPTION, note.getRecipeNote());
    }
}