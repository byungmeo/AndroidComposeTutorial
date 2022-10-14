package com.example.businesscard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.businesscard.ui.theme.BusinessCardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BusinessCardTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    //color = MaterialTheme.colors.background
                    // Color(0x{Transparency}{R}{G}{B})
                    color = Color(0xFF005267)
                ) {
                    BusinessCard(
                        name = "ByungDae Kim",
                        title = "Android Developer",
                        school = "Myongji University Computer Science",
                        gitHubID = "@byungmeo",
                        email = "wlsrlfvkr@gmail.com"
                    )
                }
            }
        }
    }
}

@Composable
fun BusinessCard(name: String, title: String, school: String, gitHubID: String, email: String) {
    val image = painterResource(id = R.drawable.android_logo)
    Column(
        modifier = Modifier
            .wrapContentWidth(Alignment.CenterHorizontally)
            .wrapContentHeight(Alignment.CenterVertically)
            .offset(y = -(128.dp))
    ) {
        Image(
            painter = image,
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(128.dp)
                .height(128.dp)
        )
        Text(
            text = name,
            fontSize = 36.sp,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 12.dp)
        )
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF3ddc84),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
    }

    Column(
        modifier = Modifier
            .wrapContentHeight(align = Alignment.Bottom)
            .offset(y = -(48.dp))
    ) {
        Contact(icon = Icons.Filled.Info, content = school)
        Contact(icon = Icons.Filled.Share, content = gitHubID)
        Contact(icon = Icons.Filled.Email, content = email)
    }
}

@Composable
fun Contact(icon: ImageVector, content: String) {
    Row(
        modifier = Modifier
            .padding(bottom = 8.dp)
            .fillMaxWidth()
            .drawBehind {
                val strokeWidth = 3f
                val x = size.width - strokeWidth

                //top line
                drawLine(
                    color = Color.Gray,
                    start = Offset(0f, 0f), //(0,0) at top-left point of the box
                    end = Offset(x, 0f), //top-right point of the box
                    strokeWidth = strokeWidth
                )
            },

    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF3ddc84),
            modifier = Modifier
                .padding(start = 60.dp, end = 24.dp, top = 8.dp)
        )
        Text(
            text = content,
            fontSize = 14.sp,
            color = Color.White,
            modifier = Modifier
                .padding(top = 8.dp)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0)
@Composable
fun DefaultPreview() {
    BusinessCardTheme {
        BusinessCard(
            name = "ByungDae Kim",
            title = "Android Developer",
            school = "South Korea",
            gitHubID = "@byungmeo",
            email = "wlsrlfvkr@gmail.com"
        )
    }
}