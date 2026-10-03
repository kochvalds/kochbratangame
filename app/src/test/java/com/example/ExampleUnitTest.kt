package com.example

import com.example.model.EvolutionStages
import com.example.model.JawUpgradesCatalog
import com.example.model.PassiveUpgradesCatalog
import com.example.util.FormatUtil
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun evolutionStages_areConfiguredCorrectly() {
        assertEquals(7, EvolutionStages.STAGES.size)
        // Stage 0 is Subfive
        val subfive = EvolutionStages.getStage(0)
        assertEquals("Подзаборный Сойджак", subfive.nameRu)
        assertEquals(1.0, subfive.clickMultiplier, 0.001)

        // Stage 6 is Apex God
        val apex = EvolutionStages.getStage(6)
        assertEquals("Апекс Бог Луксмакса", apex.nameRu)
        assertTrue(apex.clickMultiplier > subfive.clickMultiplier)
        assertTrue(apex.pslScore > subfive.pslScore)
    }

    @Test
    fun jawUpgrades_costAndPowerProgression() {
        assertEquals(7, JawUpgradesCatalog.UPGRADES.size)
        val firstUpgrade = JawUpgradesCatalog.UPGRADES[0]
        val cost0 = JawUpgradesCatalog.calculateCost(firstUpgrade, 0)
        val cost1 = JawUpgradesCatalog.calculateCost(firstUpgrade, 1)
        assertTrue("Cost should increase with level", cost1 > cost0)

        val power2 = JawUpgradesCatalog.calculatePower(firstUpgrade, 2)
        assertEquals(firstUpgrade.baseClickPower * 2, power2, 0.001)
    }

    @Test
    fun passiveUpgrades_areConfigured() {
        assertEquals(7, PassiveUpgradesCatalog.UPGRADES.size)
        val ice = PassiveUpgradesCatalog.UPGRADES[0]
        val auraPerSec = PassiveUpgradesCatalog.calculatePower(ice, 3)
        assertEquals(ice.baseAuraPerSec * 3, auraPerSec, 0.001)
    }

    @Test
    fun formatUtil_formatsNumbersCorrectly() {
        assertEquals("500", FormatUtil.formatNumber(500.0))
        assertEquals("1.50 K", FormatUtil.formatNumber(1500.0))
        assertEquals("2.50 M", FormatUtil.formatNumber(2500000.0))
        assertEquals("3.00 B", FormatUtil.formatNumber(3000000000.0))
    }
}
