package org.imaginativeworld.whynotcompose.cms.ui.common.button

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import org.imaginativeworld.whynotcompose.cms.theme.CMSAppTheme

@Composable
fun GeneralOutlinedIconButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    contentDescription: String? = null,
    onClick: () -> Unit
) {
    OutlinedIconButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Icon(
            icon,
            contentDescription = contentDescription
        )
    }
}

@Preview
@Composable
fun GeneralOutlinedIconButtonPreview() {
    CMSAppTheme {
        Surface {
            GeneralOutlinedIconButton(
                icon = Icons.Outlined.Star
            ) {}
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun GeneralOutlinedIconButtonPreviewDark() {
    CMSAppTheme {
        Surface {
            GeneralOutlinedIconButton(
                icon = Icons.Outlined.Star
            ) {}
        }
    }
}
