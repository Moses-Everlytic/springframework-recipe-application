package com.nexstudio.recipe.converters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.nexstudio.recipe.commands.CategoryCommand;
import com.nexstudio.recipe.models.Category;

import org.junit.Before;
import org.junit.Test;

public class CategoryToCategoryCommandTest {
    public static final Long ID_VALUE = 1L;
    public static final String DESCRIPTION = "description";

    CategoryToCategoryCommand convert;

    @Before
    public void setUp() throws Exception {
        convert = new CategoryToCategoryCommand();
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(convert.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(convert.convert(new Category()));
    }

    @Test
    public void shouldConvert() throws Exception {
        // given
        Category category = new Category();
        category.setId(ID_VALUE);
        category.setDescription(DESCRIPTION);

        // when
        CategoryCommand categoryCommand = convert.convert(category);

        // then
        assertEquals(ID_VALUE, categoryCommand.getId());
        assertEquals(DESCRIPTION, categoryCommand.getDescription());
    }
}
