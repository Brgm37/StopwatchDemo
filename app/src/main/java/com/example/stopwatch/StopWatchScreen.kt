package com.example.stopwatch

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay


@Composable
fun StopWatchScreen() {
    var stopWatch by rememberSaveable { mutableStateOf(StopWatch.default) }
    var uiCurrentTime by remember { mutableLongStateOf(stopWatch.currentTime) }

    LaunchedEffect(stopWatch) {
        while (stopWatch.isRunning) {
            uiCurrentTime = stopWatch.currentTime
            delay(500)
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TimeView(uiCurrentTime)
            TimeControlView(
                stopWatch = stopWatch,
                onPlay = { if (stopWatch.currentTime > 0) stopWatch = stopWatch.resume() else stopWatch = stopWatch.start() },
                onStop = { stopWatch = stopWatch.stop() },
                onReset = {
                    stopWatch = StopWatch.default
                    uiCurrentTime = stopWatch.currentTime
                }
                )
        }
    }
}

@Composable
fun TimeControlView(
    stopWatch: StopWatch,
    onPlay: () -> Unit,
    onStop: () -> Unit,
    onReset: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        if (stopWatch.isRunning) {
            CustomOutlinedButton(
                onClick = onStop,
                text = "Stop",
                containerColor = Color(0xFF691408),
                contentColor = Color.White,
                disabledContentColor = Color.Black,
                disabledContainerColor = Color.LightGray,
                modifier = Modifier.weight(1f)
            )
        } else {
            if (stopWatch.currentTime > 0) {
                    CustomOutlinedButton(
                        onClick = onPlay,
                        text = "Resume",
                        containerColor = Color(0xFF3F51B5),
                        contentColor = Color.White,
                        disabledContentColor = Color.Black,
                        disabledContainerColor = Color.LightGray,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    CustomOutlinedButton(
                        onClick = onReset,
                        text = "Reset",
                        containerColor = Color(0xFF383535),
                        contentColor = Color.White,
                        disabledContentColor = Color.Black,
                        disabledContainerColor = Color.LightGray,
                        modifier = Modifier.weight(1f)
                    )
            } else {
                CustomOutlinedButton(
                    onClick = onPlay,
                    text = "Start",
                    containerColor = Color(0xFF3F51B5),
                    contentColor = Color.White,
                    disabledContentColor = Color.Black,
                    disabledContainerColor = Color.LightGray,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun CustomOutlinedButton(
    onClick: () -> Unit,
    text: String,
    containerColor: Color,
    contentColor: Color,
    disabledContentColor: Color,
    disabledContainerColor: Color,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        colors = ButtonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContentColor = disabledContentColor,
            disabledContainerColor = disabledContainerColor
        ),
        modifier = modifier
    ) {
        Text(
            text = text,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun TimeView(currentTime: Long) {
    val totalSeconds = currentTime / 1000
    val minutes = (totalSeconds % 3600) / 60
    val hours = totalSeconds / 3600
    val seconds = (totalSeconds % 3600) % 60

    Text(
        text = String.format("%02d:%02d:%02d", hours, minutes, seconds),
        fontSize = 70.sp,
    )
}
