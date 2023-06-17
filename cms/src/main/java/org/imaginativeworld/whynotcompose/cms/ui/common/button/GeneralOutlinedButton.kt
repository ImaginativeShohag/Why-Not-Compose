package org.imaginativeworld.whynotcompose.cms.ui.common.button

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun GeneralOutlinedButton(
    modifier: Modifier = Modifier,
    caption: String,
    icon: ImageVector? = null,
    onClick: () -> Unit
) {
    OutlinedButton(
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
