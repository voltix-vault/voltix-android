import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.platform.LocalDensity
import com.voltix.wallet.app.ui.theme.appColor
import com.voltix.wallet.app.ui.theme.menloFamily

@Composable
fun MiddleEllipsisText(
    text: String,
    maxWidth: Int,
) {
    val density = LocalDensity.current
    val textWidth = with(density) { maxWidth.toDp().value }
    val ellipsis = "..."
    val textWithEllipsis = remember(text, textWidth) {
        buildAnnotatedString {
            if (textWidth > 0) {
                val measureWidth = with(density) {
                    (textWidth / density.density).toInt()
                }
                val textLength = text.length
                val halfWidth = measureWidth / 2
                if (textLength > measureWidth) {
                    val startIndex = textLength / 2 - halfWidth
                    val endIndex = textLength / 2 + halfWidth
                    append(text.substring(0, startIndex))
                    append(ellipsis)
                    append(text.substring(endIndex))
                } else {
                    append(text)
                }
            }
        }
    }

    Text(
        text = textWithEllipsis,
        textAlign = TextAlign.Center,
        maxLines = 1,
        color = MaterialTheme.appColor.turquoise600Main,
        style = MaterialTheme.menloFamily.bodyLarge,
        overflow = TextOverflow.Ellipsis,
    )
}

@Preview
@Composable
fun PreviewMiddleEllipsisText() {
    MiddleEllipsisText(text = "This is a long text to demonstrate middle ellipsis", maxWidth = 150)
}
