package com.outdoors.sliide

import com.outdoors.sliide.utils.validateEmail
import com.outdoors.sliide.utils.validateField
import org.junit.Test

class TestUtil {
    @Test
    fun testValidateField()
    {
        assert(validateField("name"))
        assert(validateField("    name"))
        assert(validateField("    "))
        assert(validateField("a    "))
        assert(!validateField(""))
    }

    //Patterns.EMAIL_ADDRESS return null for unit testing
    @Test
    fun testEmailValidation()
    {
        //assert(validateEmail("test@test.com"))
        //assert(validateEmail("test.test@test.com"))
        //assert(validateEmail("test+test@test.com"))
        //assert(!validateEmail("test test@test.com"))
        //assert(!validateEmail("test.@test.com"))
    }
}