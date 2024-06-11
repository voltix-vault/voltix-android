package com.vultisig.wallet.ui.components.library.form

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text2.BasicTextField2
import androidx.compose.foundation.text2.input.TextFieldLineLimits
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.vultisig.wallet.R
import com.vultisig.wallet.common.UiText
import com.vultisig.wallet.common.asString
import com.vultisig.wallet.ui.components.UiIcon
import com.vultisig.wallet.ui.components.UiSpacer
import com.vultisig.wallet.ui.theme.Theme
import com.vultisig.wallet.ui.theme.cursorBrush


@Composable
internal fun FormTokenCard(
    selectedTitle: String,
    @DrawableRes selectedIcon: Int,
    availableToken: String,
    isExpanded: Boolean,
    onClick: () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    FormCard {
        TokenCard(
            title = selectedTitle,
            icon = selectedIcon,
            availableToken = availableToken,
            actionIcon = R.drawable.caret_down,
            onClick = onClick,
        )

        AnimatedVisibility(visible = isExpanded) {
            Column {
                content()
            }
        }
    }
}

@Composable
internal fun TokenCard(
    title: String,
    @DrawableRes icon: Int,
    @DrawableRes actionIcon: Int? = null,
    availableToken: String? = null,
    onClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .defaultMinSize(minHeight = 48.dp)
            .padding(
                vertical = 8.dp,
                horizontal = 12.dp
            )
            .clickable(onClick = onClick),
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(32.dp)
        )

        UiSpacer(size = 8.dp)

        Text(
            text = title,
            color = Theme.colors.neutral100,
            style = Theme.menlo.body1,
        )

        UiSpacer(weight = 1f)

        Text(
            text = availableToken ?: "",
            color = Theme.colors.neutral100,
            style = Theme.menlo.body1,
        )

        if (actionIcon != null) {
            UiSpacer(size = 8.dp)

            UiIcon(
                drawableResId = actionIcon,
                size = 20.dp,
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun FormTextFieldCard(
    title: String,
    hint: String,
    error: UiText?,
    keyboardType: KeyboardType,
    textFieldState: TextFieldState,
    actions: (@Composable RowScope.() -> Unit)? = null,
) {
    var focusState by remember {
        mutableStateOf<FocusState?>(null)
    }
    TextFieldValidator(
        errorText = error,
        focusState = focusState
    ) {
        FormEntry(
            title = title,
        ) {
            FormTextField(
                hint = hint,
                keyboardType = keyboardType,
                textFieldState = textFieldState,
                actions = actions,
                onFocusStateChanged = {
                    focusState = it
                }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun FormTextField(
    hint: String,
    keyboardType: KeyboardType,
    textFieldState: TextFieldState,
    actions: (@Composable RowScope.() -> Unit)? = null,
    onFocusStateChanged: (FocusState) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 48.dp)
            .padding(
                horizontal = 12.dp,
                vertical = 14.dp
            ),
    ) {
        BasicTextField2(
            state = textFieldState,
            lineLimits = TextFieldLineLimits.SingleLine,
            textStyle = Theme.menlo.body1
                .copy(color = Theme.colors.neutral100),
            cursorBrush = Theme.cursorBrush,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
            ),
            modifier = Modifier
                .weight(1f)
                .onFocusEvent(onFocusStateChanged),
            decorator = { textField ->
                if (textFieldState.text.isEmpty()) {
                    Text(
                        text = hint,
                        color = Theme.colors.neutral100,
                        style = Theme.menlo.body1,
                    )
                }
                textField()
            }
        )

        UiSpacer(size = 8.dp)

        actions?.invoke(this)
    }
}

@Composable
internal fun FormEntry(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit = {},
) {
    FormTitleContainer(
        title = title,
        modifier = modifier,
    ) {
        FormCard(content = content)
    }
}

@Composable
internal fun FormTitleContainer(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit = {},
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = title,
            color = Theme.colors.neutral100,
            style = Theme.montserrat.body1,
        )
        UiSpacer(size = 10.dp)
        content()
    }
}

@Composable
internal fun FormCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit = {},
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Theme.colors.oxfordBlue600Main,
        ),
        shape = RoundedCornerShape(10.dp),
        content = content,
    )
}

@Composable
internal fun FormDetails(
    title: String,
    value: String,
) {
    Row {
        Text(
            text = title,
            color = Theme.colors.neutral100,
            style = Theme.montserrat.body1,
        )
        UiSpacer(weight = 1f)
        Text(
            text = value,
            color = Theme.colors.neutral100,
            style = Theme.menlo.body1
        )
    }
}


@Composable
internal fun TextFieldValidator(
    errorText: UiText?,
    focusState: FocusState?,
    content: @Composable () -> Unit,
) {
    var errorMessage by remember {
        mutableStateOf<UiText?>(null)
    }
    var isFocused by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(focusState) {
        if (focusState?.isFocused == true) {
            isFocused = true
        } else {
            if (isFocused) {
                errorMessage = errorText
            }
            isFocused = false
        }
    }
    Column {
        content()
        UiSpacer(size = 8.dp)
        AnimatedContent(
            targetState = errorMessage,
            label = "error message"
        ) { errorMessage ->
            if (errorMessage != null)
                Text(
                    text = errorMessage.asString(),
                    color = Theme.colors.error,
                    style = Theme.menlo.body1
                )
        }

    }
}