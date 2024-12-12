package com.example.entregaejerciciospmdm.screens

import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/*
Actividad 1:
Hacer que el texto del botón muestre "Cargar perfil", pero cambie a "Cancelar"
cuando se muestre la línea de progreso... Cuando pulsemos "Cancelar" vuelve al texto por defecto.
*/
@Preview
@Composable
fun Actividad1() {
    var showLoading by rememberSaveable { mutableStateOf(false) }

    Column(
        Modifier
            .padding(24.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (showLoading) {
            CircularProgressIndicator(
                color = Color.Red,
                strokeWidth = 10.dp
            )
            LinearProgressIndicator(
                modifier = Modifier.padding(top = 32.dp),
                color = Color.Red,
                trackColor = Color.LightGray
            )
        }

        Button(
            onClick = { showLoading = !showLoading }
        ) {
            Text(text = if(!showLoading) "Cargar Perfil" else "Cancelar")
        }
    }
}

/*
Actividad 2:
Modifica ahora también que se separe el botón de la línea de progreso 30 dp,
pero usando un estado... es decir, cuando no sea visible no quiero que tenga la separación
aunque no se vea.
*/
@Preview
@Composable
fun Actividad2() {
    var showLoading by rememberSaveable { mutableStateOf(false) }

    Column(
        Modifier
            .padding(24.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (showLoading) {
            CircularProgressIndicator(
                color = Color.Red,
                strokeWidth = 10.dp
            )
            LinearProgressIndicator(
                modifier = Modifier.padding(top = 32.dp),
                color = Color.Red,
                trackColor = Color.LightGray
            )
        }

        Spacer(modifier = if (!showLoading) Modifier.height(0.dp) else Modifier.height(30.dp))

        Button(
            onClick = { showLoading = !showLoading }
        ) {
            Text(text = if(!showLoading) "Cargar Perfil" else "Cancelar")
        }
    }
}

/*
Actividad 3:
- Separar los botones entre ellos 10 dp, del indicador de progreso 15 dp y centrarlos horizontalmente.
- Cuando se clique el botón Incrementar, debe añadir 0.1 a la propiedad progress y quitar 0.1
  cuando se pulse el botón Decrementar.
- Evitar que nos pasemos de los márgenes de su propiedad progressStatus (0-1)
*/
@Preview
@Composable
fun Actividad3() {

    var progressValue by rememberSaveable { mutableStateOf(0f) }

    Column(
        Modifier.fillMaxSize().padding(15.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LinearProgressIndicator(
            progress = progressValue,
            modifier = Modifier.padding(15.dp)
        )

        Row(
            Modifier.fillMaxWidth().padding(15.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                if (progressValue < 1f) progressValue += .1f
                },
                modifier = Modifier.padding(end = 10.dp)
            ) {
                Text(text = "Incrementar")
            }

            Button(
                onClick = { if (progressValue > 0f) progressValue -= .1f }
            ) {
                Text(text = "Reducir")
            }
        }
    }
}


/*
Actividad 4:
Sitúa el TextField en el centro de la pantalla y haz que reemplace el valor de una coma por un punto
y que no deje escribir más de un punto decimal...
*/
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun Actividad4() {
    var myVal by rememberSaveable { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        TextField(
            value = myVal,
            onValueChange = { input ->
                val replacedImput = input.replace(",",".")
                val firstPointIndex = replacedImput.indexOf(".")

                if (firstPointIndex == -1 || replacedImput.indexOf(".", firstPointIndex + 1) == -1) myVal = replacedImput
            },
            label = { Text(text = "Importe") }
        )
    }
}


/*
Actividad 5:
Haz lo mismo, pero elevando el estado a una función superior y usando un componente OutlinedTextField
al que debes añadir un padding alrededor de 15 dp y establecer colores diferentes en los bordes
cuando tenga el foco y no lo tenga.
A nivel funcional no permitas que se introduzcan caracteres que invaliden un número decimal.
*/
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun Actividad5() {
    var myVal by rememberSaveable { mutableStateOf("") }
    var focus by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    OutlinedTextFieldContent(
        value = myVal,
        onValueChange = { input ->
            val replacedInput = input.replace(",",".")
            val isValid = replacedInput.matches(Regex("^\\d*(\\.\\d*)?\$"))
            if (isValid) myVal = replacedInput
        },
        focus,
        interactionSource
    )

    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            when (interaction) {
                is FocusInteraction.Focus -> focus = true
                is FocusInteraction.Unfocus -> focus = false
            }
        }
    }
}

@Composable
fun OutlinedTextFieldContent(value: String, onValueChange: (String) -> Unit, focus: Boolean, interactionSource: MutableInteractionSource) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Importe") },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = if (focus) Color.Red else Color.Gray,
            unfocusedBorderColor = Color.Gray
        ),
        interactionSource = interactionSource
    )
}
