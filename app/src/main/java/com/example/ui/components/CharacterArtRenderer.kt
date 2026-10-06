package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.CharacterDef
import com.example.model.CharacterRarity
import com.example.model.SkinDef

@Composable
fun CharacterArtCard(
    character: CharacterDef,
    skin: SkinDef? = null,
    modifier: Modifier = Modifier,
    size: Dp = 90.dp
) {
    val activeSkin = skin ?: character.skins.firstOrNull()
    val rarity = character.rarity
    val borderColor = Color(rarity.colorHex)

    val cornerRadius = 14.dp

    Box(
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(cornerRadius))
            .background(Color(0xFF10141E))
            .border(2.dp, borderColor, RoundedCornerShape(cornerRadius)),
        contentAlignment = Alignment.Center
    ) {
        val hasRealDrawable = activeSkin != null && activeSkin.drawableRes != 0 && (
            activeSkin.drawableRes == R.drawable.char_kyrgyz_anton ||
            activeSkin.drawableRes == R.drawable.char_banya_hybrid ||
            activeSkin.drawableRes == R.drawable.char_kochvalds_default ||
            activeSkin.drawableRes == R.drawable.char_kochvalds_beast ||
            activeSkin.drawableRes == R.drawable.char_koch_berserk ||
            activeSkin.drawableRes == R.drawable.char_koch_bonesmasher ||
            activeSkin.drawableRes == R.drawable.char_koch_olympia ||
            activeSkin.drawableRes == R.drawable.char_penisov_333 ||
            activeSkin.drawableRes == R.drawable.char_penisov_gold ||
            activeSkin.drawableRes == R.drawable.char_boris_ofnik ||
            activeSkin.drawableRes == R.drawable.char_boris_firm ||
            activeSkin.drawableRes == R.drawable.char_boris_turnik ||
            activeSkin.drawableRes == R.drawable.char_vaska_barber ||
            activeSkin.drawableRes == R.drawable.char_vaska_razor ||
            activeSkin.drawableRes == R.drawable.char_vaska_gold ||
            activeSkin.drawableRes == R.drawable.char_temshik ||
            activeSkin.drawableRes == R.drawable.char_vlados_default ||
            activeSkin.drawableRes == R.drawable.char_vlados_sigma ||
            activeSkin.drawableRes == R.drawable.char_vlados_cyber ||
            activeSkin.drawableRes == R.drawable.char_vlados_emperor ||
            activeSkin.drawableRes == R.drawable.img_hero_gigachad ||
            activeSkin.drawableRes == R.drawable.img_koch_gym ||
            activeSkin.drawableRes == R.drawable.char_prof_mew
        )

        if (hasRealDrawable && activeSkin != null) {
            Image(
                painter = painterResource(id = activeSkin.drawableRes),
                contentDescription = character.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            // Procedural Drawn Character Illustration
            ProceduralCharacterCanvas(
                charId = character.id,
                rarity = rarity,
                modifier = Modifier.fillMaxSize()
            )
        }

        // Overlay Rarity Badge on Top Right
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(4.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(borderColor)
                .padding(horizontal = 4.dp, vertical = 1.dp)
        ) {
            Text(
                text = if (character.isHybrid) "ГИБРИД" else rarity.titleRu.take(4).uppercase(),
                fontSize = 8.sp,
                fontWeight = FontWeight.Black,
                color = Color.Black
            )
        }
    }
}

@Composable
fun ProceduralCharacterCanvas(
    charId: String,
    rarity: CharacterRarity,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Dynamic Background Gradient based on character type and rarity
        val bgColors = when (rarity) {
            CharacterRarity.COMMON -> listOf(Color(0xFF263238), Color(0xFF1E272C))
            CharacterRarity.RARE -> listOf(Color(0xFF006064), Color(0xFF002228))
            CharacterRarity.SUPER_RARE -> listOf(Color(0xFF0D47A1), Color(0xFF051B38))
            CharacterRarity.EPIC -> listOf(Color(0xFF4A148C), Color(0xFF1F0738))
            CharacterRarity.MYTHIC -> listOf(Color(0xFFB71C1C), Color(0xFF420505))
            CharacterRarity.LEGENDARY -> listOf(Color(0xFFFF6F00), Color(0xFF4E2600))
            CharacterRarity.CHROMATIC -> listOf(Color(0xFF00C853), Color(0xFF004D20))
            CharacterRarity.APEX -> listOf(Color(0xFFFF0077), Color(0xFF330018))
        }

        drawRect(
            brush = Brush.radialGradient(
                colors = bgColors,
                center = Offset(w / 2f, h / 2f),
                radius = w * 0.8f
            )
        )

        // Torso / Shoulders (Chiseled V-Taper)
        val torsoPath = Path().apply {
            moveTo(w * 0.12f, h)
            lineTo(w * 0.32f, h * 0.72f)
            lineTo(w * 0.68f, h * 0.72f)
            lineTo(w * 0.88f, h)
            close()
        }
        val shirtColor = when (charId.hashCode() % 5) {
            0 -> Color(0xFF1E88E5)
            1 -> Color(0xFFE53935)
            2 -> Color(0xFF43A047)
            3 -> Color(0xFFFB8C00)
            else -> Color(0xFF3949AB)
        }
        drawPath(torsoPath, color = shirtColor)

        // Muscular Neck
        drawRect(
            color = Color(0xFFD7CCC8),
            topLeft = Offset(w * 0.40f, h * 0.58f),
            size = Size(w * 0.20f, h * 0.20f)
        )

        // Jawline & Face (Sharp 90° Gonial Angle & Mewing Chin)
        val facePath = Path().apply {
            moveTo(w * 0.30f, h * 0.30f)
            lineTo(w * 0.70f, h * 0.30f)
            lineTo(w * 0.68f, h * 0.54f)
            lineTo(w * 0.55f, h * 0.65f) // Sharp right jaw
            lineTo(w * 0.50f, h * 0.68f) // Diamond chin
            lineTo(w * 0.45f, h * 0.65f) // Sharp left jaw
            lineTo(w * 0.32f, h * 0.54f)
            close()
        }
        val skinTone = Color(0xFFFFCC80)
        drawPath(facePath, color = skinTone)
        drawPath(facePath, color = Color(0xFFFFA726), style = Stroke(width = w * 0.02f))

        // Hair / Headdress
        val isKyrgyz = charId.contains("kyrgyz") || charId.contains("anton")
        val isBanya = charId.contains("hybrid") || charId.contains("banya")

        if (isKyrgyz) {
            // Kyrgyz Kalpak Hat (White felt with black brim)
            val hatPath = Path().apply {
                moveTo(w * 0.25f, h * 0.32f)
                lineTo(w * 0.50f, h * 0.08f)
                lineTo(w * 0.75f, h * 0.32f)
                close()
            }
            drawPath(hatPath, color = Color(0xFFF5F5F5))
            drawPath(hatPath, color = Color.Black, style = Stroke(width = w * 0.04f))
            drawRect(color = Color.Black, topLeft = Offset(w * 0.22f, h * 0.30f), size = Size(w * 0.56f, h * 0.06f))
        } else if (isBanya) {
            // Birch Leaves Wreath / Banya Hat
            drawCircle(color = Color(0xFF43A047), radius = w * 0.12f, center = Offset(w * 0.32f, h * 0.20f))
            drawCircle(color = Color(0xFF2E7D32), radius = w * 0.12f, center = Offset(w * 0.68f, h * 0.20f))
            drawCircle(color = Color(0xFF66BB6A), radius = w * 0.15f, center = Offset(w * 0.50f, h * 0.16f))
        } else {
            // Sigma Haircut (Textured fade / pompadour)
            val hairColor = Color(0xFF3E2723)
            val hairPath = Path().apply {
                moveTo(w * 0.28f, h * 0.32f)
                lineTo(w * 0.32f, h * 0.18f)
                lineTo(w * 0.68f, h * 0.18f)
                lineTo(w * 0.72f, h * 0.32f)
                lineTo(w * 0.68f, h * 0.26f)
                lineTo(w * 0.32f, h * 0.26f)
                close()
            }
            drawPath(hairPath, color = hairColor)
        }

        // Hunter Eyes / Dark Aviator Sunglasses
        val glassesPath = Path().apply {
            moveTo(w * 0.35f, h * 0.40f)
            lineTo(w * 0.47f, h * 0.40f)
            lineTo(w * 0.45f, h * 0.48f)
            lineTo(w * 0.37f, h * 0.48f)
            close()
        }
        val glassesPath2 = Path().apply {
            moveTo(w * 0.53f, h * 0.40f)
            lineTo(w * 0.65f, h * 0.40f)
            lineTo(w * 0.63f, h * 0.48f)
            lineTo(w * 0.55f, h * 0.48f)
            close()
        }
        drawPath(glassesPath, color = Color(0xFF212121))
        drawPath(glassesPath2, color = Color(0xFF212121))
        // Glasses bridge
        drawLine(color = Color(0xFFFFD54F), start = Offset(w * 0.47f, h * 0.42f), end = Offset(w * 0.53f, h * 0.42f), strokeWidth = w * 0.03f)

        // Chiseled Mouth / Neutral Sigma Mewing Line
        drawLine(
            color = Color(0xFFBF360C),
            start = Offset(w * 0.44f, h * 0.56f),
            end = Offset(w * 0.56f, h * 0.56f),
            strokeWidth = w * 0.03f
        )
    }
}
