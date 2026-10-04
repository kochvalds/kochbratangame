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
        assertTrue("Characters roster should have at least 17 characters", characters.size >= 17)

        val penisov = characters.find { it.id == "penisov" }
        assertNotNull("Denisov 333 should be in character catalog", penisov)
        assertTrue("Denisov 333 should have skins", (penisov?.skins?.size ?: 0) >= 3)

        val skuf = characters.find { it.id == "skuf" }
        assertNotNull("Skuf should be in character catalog", skuf)

        val durov = characters.find { it.id == "durov" }
        assertNotNull("Durov should be in character catalog", durov)

        val allSkins = CharacterSkinCatalog.SKINS
        assertTrue("Total skins should exceed 25", allSkins.size >= 25)
    }

    @Test
    fun `verify lifestyle assets and russian plates`() {
        val cars = LifestyleCatalog.CARS
        assertEquals("Cars catalog should have exactly 150 cars", 150, cars.size)

        val realEstate = LifestyleCatalog.REAL_ESTATE
        assertTrue("Real estate catalog should have at least 25 properties", realEstate.size >= 25)

        val crypto = LifestyleCatalog.INITIAL_CRYPTO
        assertTrue("Crypto catalog should have coins", crypto.size >= 5)

        val randomPlate = LifestyleCatalog.spinRandomPlate()
        assertNotNull("Random plate should generate", randomPlate)
        assertTrue("Plate number should not be empty", randomPlate.fullPlate.isNotEmpty())
    }
}
