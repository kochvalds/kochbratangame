package com.example.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
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
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.ui.theme.DarkCardBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.PureGold
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun GitHubExportDialog(
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val repoUrl = "https://github.com/kochvalds/kochbratangame"

    val gitCommands = """
git init
git add .
git commit -m "feat: Looksmax Clicker Game kochvalds/kochbratangame"
git branch -M main
git remote add origin https://github.com/kochvalds/kochbratangame.git
git push -u origin main
    """.trimIndent()

    val buildCommands = """
# Сборка debug APK:
gradle assembleDebug

# Запуск тестов:
gradle :app:testDebugUnitTest
    """.trimIndent()

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .clip(RoundedCornerShape(24.dp))
                .border(1.dp, PureGold, RoundedCornerShape(24.dp)),
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
                            text = "🚀 ИНСТРУКЦИЯ GITHUB",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Black,
                            color = PureGold
                        )
                        Text(
                            text = "kochvalds/kochbratangame",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = NeonCyan
                        )
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.testTag("close_github_dialog")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Закрыть",
                            tint = TextMuted
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Как выложить проект в открытый доступ на GitHub и запустить его на телефоне или компьютере:",
                    fontSize = 12.sp,
                    color = TextPrimary
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Step 1: Git push commands
                Text(
                    text = "1. Пуш в репозиторий kochvalds/kochbratangame:",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = PureGold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(DarkSurfaceVariant)
                        .border(1.dp, DarkCardBorder, RoundedCornerShape(12.dp))
                        .padding(12.dp)
                ) {
                    Column {
                        Text(
                            text = gitCommands,
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace,
                            color = NeonCyan
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedButton(
                            onClick = {
                                copyToClipboard(context, gitCommands, "Git команды скопированы!")
                            },
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ContentCopy,
                                contentDescription = null,
                                modifier = Modifier.size(14.dp),
                                tint = PureGold
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Скопировать Git команды",
                                fontSize = 11.sp,
                                color = PureGold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Step 2: Build & Run
                Text(
                    text = "2. Сборка и установка APK:",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = PureGold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(DarkSurfaceVariant)
                        .border(1.dp, DarkCardBorder, RoundedCornerShape(12.dp))
                        .padding(12.dp)
                ) {
                    Column {
                        Text(
                            text = buildCommands,
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace,
                            color = TextPrimary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedButton(
                            onClick = {
                                copyToClipboard(context, buildCommands, "Команды сборки скопированы!")
                            },
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ContentCopy,
                                contentDescription = null,
                                modifier = Modifier.size(14.dp),
                                tint = NeonCyan
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Скопировать команду сборки",
                                fontSize = 11.sp,
                                color = NeonCyan
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Step 3: Architecture & features
                Text(
                    text = "3. Фичи проекта:",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = PureGold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "• Клик по лицу фоидки с тактильным откликом и комбо-системой\n" +
                            "• Бонсмешинг молотком и 7 уровней прокачки челюсти\n" +
                            "• 7 стадий эволюции внешности (от Сойджака до Апекс Гигачада)\n" +
                            "• Автоматизация накопления ауры (Мьюинг, Хантер Айз, Кантус)\n" +
                            "• Режим «Кочнуть Братана» с адреналином и пампом x2.5\n" +
                            "• Локальное сохранение данных в SQLite через Room",
                    fontSize = 11.sp,
                    color = TextSecondary,
                    lineHeight = 16.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PureGold,
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text(
                        text = "ПОНЯТНО, ВПЕРЕД МОГАТЬ! 🤫🧏",
                        fontWeight = FontWeight.Black,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}

private fun copyToClipboard(context: Context, text: String, message: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
    val clip = ClipData.newPlainText("Looksmax Clicker Commands", text)
    clipboard?.setPrimaryClip(clip)
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}
