package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.ui.theme.DarkCardBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.ElectricPurple
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.PureGold
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.viewmodel.GameUiState

@Composable
fun CraniofacialScannerDialog(
    uiState: GameUiState,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .clip(RoundedCornerShape(24.dp))
                .border(1.dp, NeonCyan, RoundedCornerShape(24.dp)),
            color = DarkSurface
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "📐 ЧЕРЕПНО-ЛИЦЕВОЙ СКАНЕР",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Black,
                            color = NeonCyan
                        )
                        Text(
                            text = "Анализ пропорций профиля и челюсти",
                            fontSize = 11.sp,
                            color = TextSecondary
                        )
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.testTag("close_scanner_dialog")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Закрыть",
                            tint = TextMuted
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // PSL score summary card
                Card(
                    colors = CardDefaults.cardColors(containerColor = DarkSurfaceVariant),
                    shape = RoundedCornerShape(16.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, PureGold.copy(alpha = 0.5f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(text = "ОБЩАЯ ОЦЕНКА ВНЕШНОСТИ", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextMuted)
                            Text(text = "${uiState.currentEvolution.pslScore} / 10.0 PSL", fontSize = 20.sp, fontWeight = FontWeight.Black, color = PureGold)
                            Text(text = uiState.currentEvolution.nameRu, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = NeonCyan)
                        }
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(NeonCyan.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "🗿", fontSize = 28.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Breakdown of craniofacial metrics
                MetricProgressRow(
                    title = "Гониальный угол нижней челюсти",
                    value = if (uiState.evolutionStage >= 5) "90° (Идеал)" else "${125 - uiState.evolutionStage * 5}°",
                    progress = (0.35f + uiState.evolutionStage * 0.1f).coerceIn(0f, 1f),
                    color = NeonCyan
                )

                MetricProgressRow(
                    title = "Наклон глазной щели (Кантус)",
                    value = if (uiState.evolutionStage >= 3) "+5.5° Положительный" else "-2.0° Нейтральный",
                    progress = (0.25f + uiState.evolutionStage * 0.12f).coerceIn(0f, 1f),
                    color = ElectricPurple
                )

                MetricProgressRow(
                    title = "Выдвижение подбородка (Pogonion)",
                    value = if (uiState.evolutionStage >= 4) "Вперед на 4мм" else "Скошенный",
                    progress = (0.3f + uiState.evolutionStage * 0.11f).coerceIn(0f, 1f),
                    color = PureGold
                )

                MetricProgressRow(
                    title = "Тени щек (Hollow Cheeks Index)",
                    value = if (uiState.evolutionStage >= 5) "Глубокая впадина" else "Щеки сойджака",
                    progress = (0.2f + uiState.evolutionStage * 0.13f).coerceIn(0f, 1f),
                    color = Color(0xFFFF5252)
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Looksmax Advice Box
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(DarkSurfaceVariant)
                        .padding(12.dp)
                ) {
                    Column {
                        Text(
                            text = "💡 РЕКОМЕНДАЦИЯ АПЕКС-МОГГЕРА:",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black,
                            color = PureGold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = when (uiState.evolutionStage) {
                                0, 1 -> "Срочно делай бонсмешинг и начни мьюинг прямо сейчас. Фоидки не прощают отсутствие челюсти!"
                                2, 3 -> "Хороший прогресс. Заряди адреналин в кочалке и прокачай Хантер Айз для сокрушительного взгляда."
                                4, 5 -> "Челюсть близка к идеалу. Могай фоидок сериями комбо и готовься к стадии Апекс Бога."
                                else -> "Ты достиг вершины генетики. Реальность подчиняется твоему гониальному углу. Stay mogging!"
                            },
                            fontSize = 11.sp,
                            color = TextPrimary,
                            lineHeight = 16.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = NeonCyan,
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text(
                        text = "ВЕРНУТЬСЯ В ИГРУ 🤫🧏",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }
        }
    }
}

@Composable
private fun MetricProgressRow(
    title: String,
    value: String,
    progress: Float,
    color: Color
) {
    Column(modifier = Modifier.padding(vertical = 6.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = title, fontSize = 11.sp, color = TextPrimary)
            Text(text = value, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = color)
        }
        Spacer(modifier = Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp)),
            color = color,
            trackColor = DarkCardBorder
        )
    }
}
