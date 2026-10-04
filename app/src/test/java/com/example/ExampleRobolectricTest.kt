package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.model.CharacterSkinCatalog
import com.example.model.LifestyleCatalog
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Looksmax Clicker", appName)
    }

    @Test
    fun `verify characters and skins roster`() {
        val characters = CharacterSkinCatalog.CHARACTERS
        assertTrue("Characters roster should have at least 7 characters", characters.size >= 7)

        val penisov = characters.find { it.id == "penisov" }
        assertNotNull("Denisov 333 should be in character catalog", penisov)
        assertTrue("Denisov 333 should have skins", (penisov?.skins?.size ?: 0) >= 2)

        val boris = characters.find { it.id == "boris" }
        assertNotNull("Boris Ofnik should be in character catalog", boris)
        assertTrue("Boris should have multiple skins", (boris?.skins?.size ?: 0) >= 3)

        val vaska = characters.find { it.id == "vaska" }
        assertNotNull("Vaska Strigun should be in character catalog", vaska)
        assertTrue("Vaska should have multiple skins", (vaska?.skins?.size ?: 0) >= 3)

        val koch = characters.find { it.id == "kochvalds" }
        assertNotNull("Kochvalds should be in character catalog", koch)
        assertTrue("Kochvalds should have multiple skins", (koch?.skins?.size ?: 0) >= 5)

        val allSkins = CharacterSkinCatalog.SKINS
        assertTrue("Total skins should exceed 17", allSkins.size >= 17)
    }

    @Test
    fun `verify lifestyle assets and russian plates`() {
        val cars = LifestyleCatalog.CARS
        assertTrue("Cars catalog should have at least 5 cars", cars.size >= 5)

        val realEstate = LifestyleCatalog.REAL_ESTATE
        assertTrue("Real estate catalog should have properties", realEstate.size >= 5)

        val crypto = LifestyleCatalog.INITIAL_CRYPTO
        assertTrue("Crypto catalog should have coins", crypto.size >= 5)

        val randomPlate = LifestyleCatalog.spinRandomPlate()
        assertNotNull("Random plate should generate", randomPlate)
        assertTrue("Plate number should not be empty", randomPlate.fullPlate.isNotEmpty())
    }
}
