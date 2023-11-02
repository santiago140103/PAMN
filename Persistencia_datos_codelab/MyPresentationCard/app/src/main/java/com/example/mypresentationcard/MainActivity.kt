package com.example.mypresentationcard

import android.content.Context
import android.icu.text.ListFormatter.Width
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import com.example.mypresentationcard.ui.theme.MyPresentationCardTheme
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.datastore.preferences.preferencesDataStore



private val Context.datastore by preferencesDataStore(name = "settings")

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
    fun save(value: String) {
        val dataStoreKey = stringPreferencesKey("Message")
        lifecycleScope.launch(Dispatchers.IO){
            datastore.edit { settings ->
                settings[dataStoreKey] = value
            }
        }
    }

    suspend fun read(): String? {
        val dataStoreKey = stringPreferencesKey("Message")
        val nameDeferred = CompletableDeferred<String>()
        lifecycleScope.launch(Dispatchers.IO) {
            val data = datastore.data.first()
            val name = data[dataStoreKey] ?: "" // Acceder al valor utilizando la clave
            withContext(Dispatchers.Main) {
                nameDeferred.complete(name)
            }
        }

        return nameDeferred.await()

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
            LeaveMessage()
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ButtonCoroutine(
    text: TextFieldValue,
    onTextChanged: (TextFieldValue) -> Unit,
    onButtonClick: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextField(
            value = text,
            onValueChange = { newTextFieldValue ->
                onTextChanged(newTextFieldValue)
            },
            modifier = Modifier
                .weight(0.65f)
                .padding(8.dp)
        )

        Button(
            onClick = {
                // Llamar a la lambda onButtonClick y pasar el texto actual
                val textValue = text.text
                onButtonClick(textValue)
            },
            modifier = Modifier
                .weight(0.35f)
                .padding(8.dp)
        ) {
            Text("Guardar")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaveMessage() {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    val context = LocalContext.current // Obtén el contexto actual
    val activity = context as MainActivity

    LaunchedEffect(Unit) {
        var loadedString = activity.read().toString()
        text = TextFieldValue(loadedString)
    }

    ButtonCoroutine(text = text, onTextChanged = { text = it}, onButtonClick = { text ->
    activity.save(text)})

}


@Preview(showBackground = true)
@Composable
fun PresentationPreview(modifier : Modifier = Modifier) {
    InformationDeployer()
}