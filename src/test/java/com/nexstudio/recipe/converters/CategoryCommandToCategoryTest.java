package com.nexstudio.recipe.converters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.nexstudio.recipe.commands.CategoryCommand;
import com.nexstudio.recipe.models.Category;

import org.junit.Before;
import org.junit.Test;

public class CategoryCommandToCategoryTest {
    public static final Long ID_VALUE = 1L;
    public static final String DESCRIPTION = "description";

    CategoryCommandToCategory convert;

    @Before
    public void setUp() throws Exception {
        convert = new CategoryCommandToCategory();
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(convert.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(convert.convert(new CategoryCommand()));
    }

    @Test
    public void shouldConvert() throws Exception {
        //given 
        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId(ID_VALUE);
        categoryCommand.setDescription(DESCRIPTION);

        //when
        Category category = convert.convert(categoryCommand);

        //then
        assertEquals(ID_VALUE, category.getId());
        assertEquals(DESCRIPTION, category.getDescription());
    }
}
