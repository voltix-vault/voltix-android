package com.voltix.wallet.presenter.base_components.tabbars.animated_tabbar

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.voltix.wallet.R
import com.voltix.wallet.app.ui.theme.dimens
import com.voltix.wallet.app.ui.theme.montserratFamily

@Composable
fun AnimatedTopBar(
    modifier: Modifier = Modifier,
    navController: NavController,
    centerText: String,
    hasCenterArrow : Boolean = false,
    isBottomLayerVisible : Boolean = false,
    onCenterTextClick: ()->Unit = {},
    onBackClick: (() -> Unit)? = {},
    @DrawableRes startIcon: Int? = null,
    @DrawableRes endIcon: Int? = null,
) {
    val textColor = MaterialTheme.colorScheme.onBackground
    val rotation = remember { Animatable(0f) }

    LaunchedEffect(isBottomLayerVisible) {
            rotation.animateTo(
                targetValue = if (isBottomLayerVisible) 0f else -180f,
                animationSpec = tween(300, easing = LinearEasing)
            )
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = CenterVertically
    ) {
        startIcon?.let { id ->
            Image(painter = painterResource(id = id), contentDescription = null,modifier = Modifier.clickable {
               (onBackClick?:navController::popBackStack)()
            })
        } ?: Spacer(modifier = Modifier)
        Row(verticalAlignment = CenterVertically) {
            AnimatedContent(
                targetState = centerText,
                transitionSpec = {
                    (slideInVertically { -it } togetherWith slideOutVertically { it })
                        .using(
                            SizeTransform(clip = true)
                        )
                }, label = "center text"
            ) {
                Text(
                    text = it,
                    color = textColor,
                    modifier = Modifier.clickable(onClick = onCenterTextClick),
                    style = MaterialTheme.montserratFamily.headlineMedium.copy(fontSize = MaterialTheme.dimens.medium1.value.sp)
                )
            }

            if(hasCenterArrow)
                Icon(
                    painter = painterResource(id = R.drawable.caret_down),
                    contentDescription = null,
                    modifier = Modifier.padding(start = MaterialTheme.dimens.marginSmall).width(MaterialTheme.dimens.medium1).rotate(rotation.value),
                    tint = Color.White)
        }

        endIcon?.let { id ->
            Image(painter = painterResource(id = id), contentDescription = null)
        } ?: Spacer(modifier = Modifier)
    }
}
