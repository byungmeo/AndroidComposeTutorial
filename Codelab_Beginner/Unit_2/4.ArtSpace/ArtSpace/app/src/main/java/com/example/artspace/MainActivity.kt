package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ArtSpaceScreen()
                }
            }
        }
    }
}

@Composable
fun ArtSpaceScreen() {
    var imageIndex by remember { mutableStateOf(0) }
    val imageResource = when(imageIndex) {
        0 -> painterResource(R.drawable.changjo)
        1 -> painterResource(R.drawable.cafe)
        else -> painterResource(R.drawable.yeokbuk)
    }
    val title = when(imageIndex) {
        0 -> "Myongji University Creation Gallery"
        1 -> "Vintage Coffee Shop"
        else -> "Yongin Yeokbuk Street"
    }
    val photographer = when(imageIndex) {
        0 -> "띵무위키"
        1 -> "bbom100892"
        else -> "띵무위키"
    }
    val year = when(imageIndex) {
        0 -> "(2022)"
        1 -> "(2022)"
        else -> "(2022)"
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 12.dp)
    ){
        Box(
            contentAlignment = Center,
            modifier = Modifier.weight(5F)
        ) {
            Image(
                painter = imageResource,
                contentDescription = null,
                modifier = Modifier
                    .border(width = 3.dp, color = Color(0, 0, 0))
                    .padding(all = 24.dp)
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .weight(2F)
        ) {
            Column(
                modifier = Modifier
                    .border(width = 1.dp, color = Color(0, 0, 0))
                    .padding(all = 12.dp)
            ) {
                Text(
                    text = title,
                    fontSize = 24.sp
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = photographer,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = year
                    )
                }

            }
            Spacer(modifier = Modifier.size(24.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Button(
                    onClick = { if(--imageIndex < 0) imageIndex = 2 },
                    modifier = Modifier
                        .requiredWidth(160.dp)
                ) {
                    Text(text = "Previous")
                }
                Button(
                    onClick = { if(++imageIndex >= 3) imageIndex = 0 },
                    modifier = Modifier
                        .requiredWidth(160.dp)
                ) {
                    Text(text = "Next")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ArtSpaceTheme {
        ArtSpaceScreen()
    }
}