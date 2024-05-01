import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.voltix.wallet.R
import com.voltix.wallet.app.ui.theme.appColor
import com.voltix.wallet.app.ui.theme.dimens
import com.voltix.wallet.app.ui.theme.montserratFamily

@Composable
public fun MultiColorButton(
    text: String="",
    startIcon: Int?=null,
    trailingIcon: Int?=null,
    startIconColor:  Color?=null,
    endIconColor:  Color?=null,
    backgroundColor: Color?=null,
    foregroundColor: Color?=null,
    borderColor: Color?=null,
    iconSize: Dp?=null,
    borderSize: Dp?=null,
    minWidth: Dp?=null,
    minHeight: Dp?=null,
    weight: Float=0.0f,
    textStyle: TextStyle?=null,
    textColor:  Color?=null,
    modifier: Modifier,
    onClick: () -> Unit,
) {
    var innerModifier=modifier
    if (borderSize!=null)
        innerModifier = innerModifier.then(
            Modifier .border(
                width = borderSize,
                brush = Brush.horizontalGradient(
                    listOf(MaterialTheme.appColor.turquoise600Main
                        , MaterialTheme.appColor.persianBlue600Main)),
                shape = CircleShape)
        )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier =
        innerModifier
            .clip(shape = RoundedCornerShape(60.dp))
            .background(
                color = backgroundColor ?: MaterialTheme.appColor.turquoise600Main,
            )
            .clickable(onClick = onClick)
            .defaultMinSize(
                minWidth = minWidth ?: MaterialTheme.dimens.minWidth
                , minHeight = minHeight ?: MaterialTheme.dimens.medium2
            )
    ) {
        Icon(
            painter = painterResource(startIcon ?: R.drawable.check),
            contentDescription = null,
            tint = startIconColor ?: MaterialTheme.appColor.transparent,
            modifier = Modifier.size(iconSize ?: MaterialTheme.dimens.small3)
        )
        Spacer(modifier = Modifier.width(MaterialTheme.dimens.small1))
        Text(
            text = text,
            color = textColor ?: MaterialTheme.appColor.oxfordBlue600Main,
            style = textStyle ?: MaterialTheme.montserratFamily.titleLarge
        )
        Spacer(modifier = Modifier.width(MaterialTheme.dimens.small1))
        Icon(
            painter = painterResource(trailingIcon ?: R.drawable.check),
            contentDescription = null,
            tint = endIconColor ?: MaterialTheme.appColor.transparent,
            modifier = Modifier.size(iconSize ?: MaterialTheme.dimens.small3)
        )
    }
}


fun Modifier.conditional(condition : Boolean, modifier : Modifier.() -> Modifier) : Modifier {
    return if (condition) {
        then(modifier(Modifier))
    } else {
        this
    }
}

@Preview(showBackground = true)
@Composable
fun MultiColorButtonPreview() {
    MultiColorButton(
        text = "Create a New Vault",
        minHeight = MaterialTheme.dimens.minHeightButton,
        backgroundColor = MaterialTheme.appColor.turquoise800,
        textColor = MaterialTheme.appColor.oxfordBlue800,
        startIconColor = MaterialTheme.appColor.oxfordBlue800,
        endIconColor = MaterialTheme.appColor.oxfordBlue800,
        textStyle = MaterialTheme.montserratFamily.titleLarge,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = MaterialTheme.dimens.buttonMargin,
                end = MaterialTheme.dimens.buttonMargin
            )
    ) {}
}