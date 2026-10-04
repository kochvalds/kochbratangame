package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.RussianPlate

@Composable
fun RussianPlateView(
    plate: RussianPlate,
    modifier: Modifier = Modifier
) {
    // Russian Standard Car License Plate Box
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .background(Color(0xFFF8F9FA))
            .border(2.5.dp, Color.Black, RoundedCornerShape(6.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            // Main Plate characters: e.g. "Е 333 КХ"
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = plate.seriesFirst,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    fontFamily = FontFamily.Monospace,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(
                    text = plate.number,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Black,
                    fontFamily = FontFamily.Monospace,
                    color = Color.Black,
                    letterSpacing = 2.sp
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(
                    text = plate.seriesRest,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    fontFamily = FontFamily.Monospace,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Vertical divider line
            Box(
                modifier = Modifier
                    .width(2.dp)
                    .height(30.dp)
                    .background(Color.Black)
            )

            Spacer(modifier = Modifier.width(6.dp))

            // Region & RUS & Flag
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = plate.region,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Black,
                    fontFamily = FontFamily.Monospace,
                    color = Color.Black
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "RUS",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    // Russian Flag (White/Blue/Red)
                    Column(
                        modifier = Modifier
                            .width(10.dp)
                            .height(7.dp)
                            .border(0.5.dp, Color.Gray)
                    ) {
                        Box(modifier = Modifier.weight(1f).width(10.dp).background(Color.White))
                        Box(modifier = Modifier.weight(1f).width(10.dp).background(Color(0xFF0039A6)))
                        Box(modifier = Modifier.weight(1f).width(10.dp).background(Color(0xFFD52B1E)))
                    }
                }
            }
        }
    }
}
