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
        assertEquals("Characters roster should have exactly 100 characters", 100, characters.size)

        val gleb = characters.find { it.id == "gleb_sportik" }
        assertNotNull("Gleb Sportik should be in character catalog", gleb)
        assertEquals("Gleb should have exactly 5 skins", 5, gleb?.skins?.size)

        val zahar = characters.find { it.id == "zahar_baryga" }
        assertNotNull("Zakhar Baryga should be in character catalog", zahar)
        assertEquals("Zakhar should have exactly 5 skins", 5, zahar?.skins?.size)

        val penisov = characters.find { it.id == "penisov" }
        assertNotNull("Denisov 333 should be in character catalog", penisov)
        assertEquals("Denisov should have exactly 5 skins", 5, penisov?.skins?.size)

        val allSkins = CharacterSkinCatalog.SKINS
        assertEquals("Total skins should be exactly 500", 500, allSkins.size)
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

    @Test
    fun `verify game stats persistence and inventory tab`() {
        val stats = com.example.model.GameStats()
        assertEquals("vaz_2107", stats.ownedCarIds)
        assertEquals("Е333КХ 777", stats.equippedPlate)

        val inventoryTab = com.example.ui.GameTab.INVENTORY
        assertEquals("Инвентарь", inventoryTab.title)
    }
}
