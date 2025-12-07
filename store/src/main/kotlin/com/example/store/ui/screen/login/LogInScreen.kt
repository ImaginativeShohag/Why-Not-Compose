import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.store.theme.StoreAppTheme
import com.example.store.ui.compositions.StoreAppBar
import com.example.store.ui.screen.login.LoginViewModel
import org.imaginativeworld.whynotcompose.common.compose.theme.AppleSystemColor

@Composable
fun LoginScreen(
    onLogin: () -> Unit = {},
    toggleUIMode: () -> Unit = {},
) {

    LoginSkeleton(
        onLogin = onLogin,
        toggleUIMode = toggleUIMode
    )
}

@Suppress("ktlint:compose:modifier-missing-check")
@Composable
fun LoginSkeleton(
    onLogin: () -> Unit,
    toggleUIMode: () -> Unit = {}
) {
    var username by remember { mutableStateOf("John") }
    var password by remember { mutableStateOf("223355") }
    var usernameError by rememberSaveable { mutableStateOf("") }
    var passwordError by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    val gradientBrush = Brush.linearGradient(
        colors = listOf(
            AppleSystemColor.Purple,
            AppleSystemColor.Blue
        )
    )

    Scaffold(
        topBar = {
            StoreAppBar(
                toggleUIMode = toggleUIMode
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(gradientBrush),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Welcome to",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.surface,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Store Overflow",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.surface
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                TextField(
                    label = {
                        Text(
                            text = "Username",
                            color = MaterialTheme.colorScheme.outline
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(12.dp),
                    value = username,
                    onValueChange = {
                        username = it
                        usernameError = validateUsername(it)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                TextField(
                    value = password,
                    onValueChange = {
                        password = it
                        passwordError = validatePassword(it)
                    },
                    label = {
                        Text(
                            text = "Password",
                            color = MaterialTheme.colorScheme.outline
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        IconButton(
                            onClick = { passwordVisible = !passwordVisible }
                        ) {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                contentDescription = "Toggle password visibility"
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = onLogin,
                        modifier = Modifier
                            .wrapContentSize(),
                        enabled = true,
                        colors = ButtonColors(
                            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                            contentColor = MaterialTheme.colorScheme.surface,
                            disabledContainerColor = MaterialTheme.colorScheme.outlineVariant,
                            disabledContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        ),
                        shape = RoundedCornerShape(30.dp)
                    ) {
                        Text(
                            text = "Login",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier
                                .padding(
                                    vertical = 6.dp,
                                    horizontal = 6.dp
                                )
                        )
                    }
                }
            }
        }
    }
}

fun validateUsername(username: String): String = when {
    username.isEmpty() -> ""
    username.length < 3 -> "Username should be at least 3 characters long"
    else -> ""
}

fun validatePassword(password: String): String = when {
    password.isEmpty() -> ""
    password.length < 6 -> "Password should be at least 6 characters long"
    else -> ""
}

@PreviewLightDark
@Composable
private fun PreviewLoginScreen() {
    StoreAppTheme {
        LoginSkeleton(
            onLogin = {},
            toggleUIMode = {}
        )
    }
}
