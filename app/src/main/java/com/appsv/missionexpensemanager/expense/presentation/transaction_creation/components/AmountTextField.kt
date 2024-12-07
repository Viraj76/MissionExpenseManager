import androidx.compose.foundation.background
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.graphics.Color
import com.appsv.missionexpensemanager.core.presentation.ui.theme.getColorsForTheme
import com.appsv.missionexpensemanager.expense.presentation.transaction_creation.components.UnderlinedText

@Composable
fun AmountTextField(
    modifier: Modifier = Modifier,
    amount : String,
    isEnteringAmount: Boolean = false,
    onDone: (String) -> Unit = {},
    onAmountClick: () -> Unit = {},
) {
    var totalAmount by remember { mutableStateOf(TextFieldValue("")) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    var enteredTotalAmount by remember { mutableStateOf("0.00") }

    if (isEnteringAmount) {
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }

        BasicTextField(
            value = totalAmount,
            onValueChange = { newValue ->
                totalAmount = newValue
            },
            textStyle = androidx.compose.ui.text.TextStyle(
                fontSize = 22.sp,
                color = Color(0xFF1D1F1C)
            ),
            singleLine = true,
            modifier = Modifier
                .width(120.dp)
                .background(
                    color = Color.LightGray.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(8.dp)
                .focusRequester(focusRequester),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    onDone(totalAmount.text)
                    enteredTotalAmount = totalAmount.text
                }
            )
        )
    } else {
        val getColor = getColorsForTheme()
        UnderlinedText(
            text = if (amount.isNotEmpty()) amount else "0.00",
            color = getColor.DarkGrayishPurple,
            backgroundColor = Color.Transparent,
            onAmountClick = onAmountClick,
        )
    }
}
