import android.content.res.Resources.Theme
import android.view.KeyEvent
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetContent(
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    val categories = listOf(
        "Todo List",
        "Calls/Email",
        "Personal Todo List",
        "Health and Fitness",
        "Daily Schedule",
        "Appointments",
    )

    val showTextField = remember { mutableStateOf(false) }

    val newCategoryText = remember { mutableStateOf("") }

    val focusRequester = remember { FocusRequester() }

    val sheetState = rememberModalBottomSheetState()

    var showDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = {
            showDialog = true
        }) {
            Text("Modal Bottom Sheet")
        }

        if (showDialog) {
            ModalBottomSheet(
                onDismissRequest = {
                    showDialog = false
                },
                modifier= Modifier
                    .fillMaxWidth(1f)
                    .fillMaxHeight(1f),
                sheetState = sheetState,
                shape = shape,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
                tonalElevation = 4.dp,
                scrimColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.4f),
                dragHandle = {
                    // Show a drag handle to allow users to drag the bottom sheet
                    Text("Drag handle")
                },
                content = {
                    // This is the content of the bottom sheet
                    Column(
                        modifier = Modifier.padding(
                            horizontal = 8.dp,
                            vertical = 4.dp
                        ),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        categories.forEach { category ->
                            Text(
                                text = category,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier
                                    .clickable {
                                        onCategorySelected(category)
                                        showTextField.value = false
                                    }
                                    .padding(vertical = 10.dp)
                            )
                        }

                        if (selectedCategory == "Create New" || showTextField.value) {
                            TextField(
                                value = newCategoryText.value,
                                onValueChange = { text ->
                                    newCategoryText.value = text
                                },
                                modifier = Modifier
                                    .fillMaxWidth(.75f)
                                    .height(50.dp)
                                    .wrapContentSize()
                                    .focusRequester(focusRequester)
                                    .onKeyEvent { event ->
                                        if (event.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_ENTER) {
                                            onCategorySelected(newCategoryText.value)
                                            showTextField.value = false
                                            true // Consume the event
                                        } else {
                                            false // Let the event propagate
                                        }
                                    },
                                label = { Text(text = "") },
                                textStyle = TextStyle(color = Color.Black),
                                singleLine = true,
                                colors = TextFieldDefaults.textFieldColors(
                                    Color.Gray
                                )
                            )

                            LaunchedEffect(Unit) {
                                focusRequester.requestFocus()
                            }

                            DisposableEffect(Unit) {
                                onDispose {
                                    focusRequester.freeFocus()
                                }
                            }

                            if (selectedCategory == "Create New") {
                                showTextField.value = true
                            }
                        } else {
                            Text(
                                text = "Create New",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier
                                    .clickable {
                                        showTextField.value = true
                                    }
                                    .padding(vertical = 10.dp)
                            )
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun ThemeSwitcher(
    darkTheme: Boolean = false,
    size: Dp = 150.dp,
    iconSize: Dp = size/3,
    toggleShape:Shape = CircleShape,
    animationSpec:AnimationSpec<Dp> = tween(durationMillis = 300),
    onClick:() ->Unit,
    parentShape: Shape = CircleShape,
){
        val offset by animateDpAsState(
            targetValue =if (darkTheme) 0.dp else size,
            animationSpec = animationSpec, label = ""
        )
    Box(
        modifier = Modifier
            .width(size * 2)
            .height(size)
            .clip(shape = parentShape)
            .clickable { onClick() }
            .background(MaterialTheme.colorScheme.secondaryContainer)
    ){
        Box (   modifier = Modifier
            .size(size)
            .offset(x = offset)
            .clip(shape = parentShape)
            .background(MaterialTheme.colorScheme.secondaryContainer)){}
        Row ( modifier = Modifier
        ){

        }
    }
}