package org.imaginativeworld.whynotcompose.cms.ui.compositions.button

import android.content.res.Configuration
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import org.imaginativeworld.whynotcompose.cms.theme.CMSAppTheme

@Composable
fun GeneralTextButton(
    modifier: Modifier = Modifier,
    caption: String,
    icon: ImageVector? = null,
    onClick: () -> Unit
) {
    TextButton(
        modifier = modifier,
        onClick = onClick,
        contentPadding = if (icon == null) {
            ButtonDefaults.TextButtonContentPadding
        } else {
            ButtonDefaults.ButtonWithIconContentPadding
        }
    ) {
        if (icon != null) {
            Icon(
                icon,
                contentDescription = caption,
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        }
        Text(caption)
    }
}

@Preview
@Composable
fun GeneralTextButtonPreview() {
    CMSAppTheme {
        Surface {
            GeneralTextButton(
                caption = "Click Me",
                icon = Icons.Outlined.Star
            ) {}
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun GeneralTextButtonPreviewDark() {
    CMSAppTheme {
        Surface {
            GeneralTextButton(
                caption = "Click Me",
                icon = Icons.Outlined.Star
            ) {}
        }
    }
}
