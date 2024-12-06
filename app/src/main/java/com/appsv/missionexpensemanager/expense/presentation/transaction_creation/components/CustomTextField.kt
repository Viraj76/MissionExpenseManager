package com.appsv.missionexpensemanager.expense.presentation.transaction_creation.components


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.appsv.missionexpensemanager.core.presentation.ui.theme.LightGrayishBlue

@Preview(showBackground = true)
@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    value  : String = "",

    trailingIcon: @Composable (() -> Unit)? = null,
    label: @Composable (() -> Unit)? = null,
    readOnly : Boolean= false,
    onValueChange : (String) -> Unit ={},
) {
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        label = label,
        readOnly = readOnly,
        trailingIcon = trailingIcon,
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors().copy(
            focusedIndicatorColor = LightGrayishBlue,
            unfocusedIndicatorColor = LightGrayishBlue
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        )
    )

}