package com.example.mypresentationcard

import android.icu.text.ListFormatter.Width
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mypresentationcard.ui.theme.MyPresentationCardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyPresentationCardTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    InformationDeployer()
                }
            }
        }
    }
}

@Composable
fun Name(modifier : Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(painter = painterResource(id = R.drawable.yo), contentDescription = null)
        Text(text = "Santiago Emilio Flores Reina", fontSize = 30.sp, color = Color.Green)
        Text(text = "Presentation Card", fontSize = 15.sp, color = Color.Green)
    }
}

@Composable
fun Contact() {
    Column(modifier = Modifier.padding(20.dp)) {
        Text(text = "Contact information", color = Color.Green)
        Text(text = "+34 000 00 00 00", color = Color.Green)
        Text(text = "email@example.com", color = Color.Green)
        Text(text = "@instagram", color = Color.Green)
    }
}

@Composable
fun InformationDeployer(modifier : Modifier = Modifier) {

    Box (
        modifier = Modifier
            .paint(
                painter = painterResource(id = R.drawable.fondo3),
                contentScale = ContentScale.FillBounds,

            )
            .fillMaxSize(),

        ) {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.fillMaxSize()
        ) {
            Name()
            Contact()
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PresentationPreview(modifier : Modifier = Modifier) {
    InformationDeployer()

}