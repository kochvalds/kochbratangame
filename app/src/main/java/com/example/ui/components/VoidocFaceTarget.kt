package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.PanTool
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.BonesmashCrimson
import com.example.ui.theme.DarkCardBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.ElectricPurple
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.PureGold
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.util.FormatUtil
import com.example.viewmodel.GameUiState
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun VoidocFaceTarget(
    uiState: GameUiState,
    onMogClick: (x: Float, y: Float) -> Unit,
    onBonesmashClick: () -> Unit,
    onActivateKoch: () -> Unit,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val scaleAnim = remember { Animatable(1f) }
    val rotationAnim = remember { Animatable(0f) }

    val infiniteTransition = rememberInfiniteTransition(label = "pulseRing")
    val ringScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "ringScale"
    )

    val quotes = remember(uiState.activeSkin.characterId) {
        when (uiState.activeSkin.characterId) {
            "gleb_sportik" -> listOf(
                "«Пять подходов на брусьях, чисто спорт!»",
                "«Протеин выпит, мышцы залиты кровью!»",
                "«Никакого читмила, только чистый воркаут!»",
                "«Жми до отказа, братуха!»"
            )
            "zahar_baryga" -> listOf(
                "«Оригинал из Дубая, чек на руках!»",
                "«Есть кроссовки любого размера, договоримся!»",
                "«Бартер или нал, братуха? Сделка века!»",
                "«Айфоны без блокировок, аура капает на карту!»"
            )
            "penisov" -> listOf(
                "«Е333КХ 777 на связи! Суета наведена!»",
                "«Блатные номера решают любой вопрос на дороге!»",
                "«333 регион — чисто для избранных моггеров!»",
                "«Газ в пол, фоидки глотают пыль из-под колес!»",
                "«Золотой номер — золотая аура, братуха!»",
                "«Турбо-Писюн 333 заряжен на максимум!»"
            )
            "skuf" -> listOf(
                "«Танки сами себя не победят, братан!»",
                "«Пивко открыто, диван продавлен, могаем не вставая!»",
                "«Пока вы мьюите, я беру Мастера на Малиновке!»"
            )
            "maga" -> listOf(
                "«С прогиба кину любого фоидку!»",
                "«Сломанные уши — знак настоящего тигра!»",
                "«Борцовский партер — высшая форма луксмакса!»"
            )
            "dimon_drift" -> listOf(
                "«Заварил редуктор — заварил победу!»",
                "«Валим боком в 2 часа ночи на парковке Ашана!»",
                "«Запах жженой резины — лучший парфюм сигмы!»"
            )
            "durov" -> listOf(
                "«Принял ледяную ванну, покачал пресс!»",
                "«Могай этот мир в абсолютном молчании!»",
                "«Свобода и гониальный угол 90 градусов!»"
            )
            "bazooka" -> listOf(
                "«Руки-базуки на месте! Двоечка в воздух!»",
                "«Банка 60 см заряжает ауру на миллион!»"
            )
            "boris" -> listOf(
                "«Поясни за шмот, братуха! Линзы на капюшоне заряжены!»",
                "«Околофутбол не спит, могаем соперника на трибунах!»",
                "«Выход силой на две руки — это база каждого офника!»",
                "«Кроссы Spezial и патч на рукаве — стиль победы!»",
                "«Чистый дворовой моггинг, фоидки разбегаются!»"
            )
            "vaska" -> listOf(
                "«Стригу под ноль с идеальным переходом!»",
                "«Окантовка висков бритвенно острая, PSL +2 сразу!»",
                "«Баззкат — прическа истинного гигачада!»",
                "«Слышишь звук машинки? Это твоя аура растёт!»",
                "«Опасная бритва срезает любой отрицательный кантус!»"
            )
            "kochvalds" -> listOf(
                "«Давай ещё подход, братан! Жми до отказа!»",
                "«Банка растёт с каждым тапом, чисто коч!»",
                "«Адреналин зашкаливает, режим кочалки заряжен!»",
                "«Турник, брусья и чистая аура силы!»",
                "«Ни шагу назад, только прогрессивная перегрузка!»"
            )
            "vlados" -> listOf(
                "«Язык к нёбу и держи спину ровно!»",
                "«Полосатая рубашка заряжена на максимальный моггинг!»",
                "«Острые скулы не прощают фоидок!»",
                "«90 градусов гониального угла — это закон жизни!»",
                "«Мьюинг 24/7, братан!»"
            )
            "temshik" -> listOf(
                "«Братан, эта темка принесет миллион ауры!»",
                "«Крипта растёт, аура капает каждую секунду!»",
                "«Суета наведена, фиксируем профит!»",
                "«Главное — быть на связи и могать рынок!»"
            )
            "prof_mew" -> listOf(
                "«Научно подтверждаю: идеальный положительный кантус!»",
                "«Биомеханика лица вышла на пиковый уровень!»",
                "«Ортотропия побеждает гравитацию!»",
                "«Симметрия черепа превысила 99.8%!»"
            )
            else -> listOf(
                "«О нет, он снова мьюит прямо на меня!»",
                "«Перестань могать моё лицо, у меня отрицательный кантус!»",
                "«Его гониальный угол режет мне глаза!»",
                "«Где твоя челюсть?! Не бей по лицу молотком!»",
                "«Пощади, я тоже начну качать массеты!»"
            )
        }
    }

    var currentQuoteIndex by remember { mutableStateOf(0) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Target Dialogue Speech bubble
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(DarkSurface)
                .border(1.dp, ElectricPurple.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                .padding(horizontal = 14.dp, vertical = 8.dp)
        ) {
            val safeQuote = quotes.getOrElse(currentQuoteIndex % quotes.size) { quotes.first() }
            Text(
                text = "🎭 ${uiState.activeSkin.name.uppercase()}: $safeQuote",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = TextPrimary,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Big Face Circle (The target to mog)
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(260.dp)
        ) {
            // Animated outer glow ring
            Box(
                modifier = Modifier
                    .size(250.dp)
                    .scale(ringScale)
                    .clip(CircleShape)
                    .border(
                        3.dp,
                        Brush.sweepGradient(
                            listOf(
                                NeonCyan,
                                ElectricPurple,
                                PureGold,
                                if (uiState.isKochModeActive) BonesmashCrimson else NeonCyan
                            )
                        ),
                        CircleShape
                    )
            )

            // Inner touchable face target
            Box(
                modifier = Modifier
                    .size(220.dp)
                    .scale(scaleAnim.value)
                    .clip(CircleShape)
                    .border(2.dp, DarkCardBorder, CircleShape)
                    .testTag("voidoc_face_target")
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        coroutineScope.launch {
                            // Punch reaction animation
                            launch {
                                scaleAnim.snapTo(0.88f)
                                scaleAnim.animateTo(
                                    1f,
                                    spring(dampingRatio = 0.35f, stiffness = 1200f)
                                )
                            }
                            if (Random.nextInt(8) == 0 && quotes.isNotEmpty()) {
                                currentQuoteIndex = Random.nextInt(quotes.size)
                            }
                        }
                        onMogClick(130f + Random.nextInt(-40, 40), 130f + Random.nextInt(-40, 40))
                    },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = uiState.activeSkin.drawableRes),
                    contentDescription = uiState.activeSkin.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(220.dp)
                )

                // Flash overlay on rage
                if (uiState.isKochModeActive) {
                    Box(
                        modifier = Modifier
                            .size(220.dp)
                            .background(BonesmashCrimson.copy(alpha = 0.15f))
                    )
                }

                // Center crosshair label
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 12.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.Black.copy(alpha = 0.75f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "ТАП ДЛЯ МОГГИНГА 🤫🧏",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = NeonCyan
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Action Buttons Row: Quick Bonesmashing Hammer + Koch Workout
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Quick Bonesmash Action
            Button(
                onClick = onBonesmashClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = PureGold,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp)
                    .testTag("quick_bonesmash_button")
            ) {
                Text(
                    text = "🔨 БОНСМЕШИНГ",
                    fontWeight = FontWeight.Black,
                    fontSize = 13.sp
                )
            }

            // Quick Koch Pump Action
            val canKoch = uiState.kochAdrenaline >= 100f || uiState.isKochModeActive
            Button(
                onClick = onActivateKoch,
                enabled = canKoch && !uiState.isKochModeActive,
                colors = ButtonDefaults.buttonColors(
                    containerColor = BonesmashCrimson,
                    contentColor = Color.White,
                    disabledContainerColor = DarkCardBorder,
                    disabledContentColor = TextSecondary
                ),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp)
                    .testTag("quick_koch_button")
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.FitnessCenter,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (uiState.isKochModeActive) "КОЧАЛКА!" else "КОЧНУТЬ (${uiState.kochAdrenaline.toInt()}%)",
                        fontWeight = FontWeight.Black,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}
