package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.model.CharacterSkinCatalog
import com.example.model.EvolutionStages
import com.example.model.GameStats
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
    fun `verify characters and skins roster including kyrgyz anton`() {
        val characters = CharacterSkinCatalog.CHARACTERS
        assertTrue("Characters roster should have at least 100 characters", characters.size >= 100)

        val gleb = characters.find { it.id == "gleb_sportik" }
        assertNotNull("Gleb Sportik should be in character catalog", gleb)
        assertEquals("Gleb should have exactly 5 skins", 5, gleb?.skins?.size)

        val zahar = characters.find { it.id == "zahar_baryga" }
        assertNotNull("Zakhar Baryga should be in character catalog", zahar)
        assertEquals("Zakhar should have exactly 5 skins", 5, zahar?.skins?.size)

        val anton = characters.find { it.id == "kyrgyz_anton" }
        assertNotNull("Kyrgyz Anton should be in character catalog", anton)
        assertEquals("Kyrgyz Anton should have 5 skins", 5, anton?.skins?.size)

        val banyaHybrid = characters.find { it.isHybrid }
        assertNotNull("Banya Hybrids should be present in character roster", banyaHybrid)

        val allSkins = CharacterSkinCatalog.SKINS
        assertTrue("Total skins should be at least 500", allSkins.size >= 500)
    }

    @Test
    fun `verify 100 evolution stages exist`() {
        val stages = EvolutionStages.STAGES
        assertEquals("Should have exactly 101 stages from 0 to 100", 101, stages.size)
        val stage100 = EvolutionStages.getStage(100)
        assertEquals(100, stage100.stage)
        assertEquals("Абсолютный Бог Мультивселенной Луксмакса", stage100.nameRu)
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
    fun `verify bug fix for default plate and inventory tab`() {
        val stats = GameStats()
        assertEquals("vaz_2107", stats.ownedCarIds)
        assertEquals("О 741 ТР 77", stats.equippedPlate)
        assertTrue("Initial plate should NOT be E333KX", !stats.equippedPlate.contains("Е333КХ"))

        val inventoryTab = com.example.ui.GameTab.INVENTORY
        assertEquals("Инвентарь", inventoryTab.title)
    }
}
