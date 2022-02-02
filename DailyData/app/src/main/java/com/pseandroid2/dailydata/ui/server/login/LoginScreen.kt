package com.pseandroid2.dailydata.ui.server.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pseandroid2.dailydata.R
import com.pseandroid2.dailydata.ui.project.creation.AppDialog


@Composable
fun LoginScreen(
    viewModel: LoginScreenViewModel = hiltViewModel()
) {

    SignUpDialog(
        isOpen = viewModel.isSignUpDialogOpen,
        onDismissRequest = { viewModel.onEvent(LoginScreenEvent.ShowSignUpDialog(false)) },
        onSignUpClick = { email, password ->
            viewModel.onEvent(LoginScreenEvent.SignUp(email, password))
        }
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp, alignment = Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.userEmail,
                label = {
                    Text(text = "Email")
                },
                onValueChange = { viewModel.onEvent(LoginScreenEvent.OnEmailChange(email = it)) }
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                value = viewModel.userPassword,
                label = {
                    Text(text = "Password")
                },
                onValueChange = { viewModel.onEvent(LoginScreenEvent.OnPasswordChange(password = it)) }
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = viewModel.userEmail.isNotEmpty() && viewModel.userPassword.isNotEmpty(),
                content = {
                    Text(text = "Login")
                },
                onClick = {
                    viewModel.onEvent(LoginScreenEvent.Login)
                    //viewModel.signInWithEmailAndPassword(userEmail.trim(), userPassword.trim())
                }
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.caption,
                text = "Login with"
            )

            OutlinedButton(
                border = ButtonDefaults.outlinedBorder.copy(width = 1.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                onClick = {

                },
                content = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        content = {
                            Icon(
                                tint = Color.Unspecified,
                                painter = painterResource(id = R.drawable.googleg_standard_color_18),
                                contentDescription = null,
                            )
                            Text(
                                style = MaterialTheme.typography.button,
                                color = MaterialTheme.colors.onSurface,
                                text = "Google"
                            )
                            Icon(
                                tint = Color.Transparent,
                                imageVector = Icons.Default.MailOutline,
                                contentDescription = null,
                            )
                        }
                    )
                }
            )
            Row {
                Text(
                    style = MaterialTheme.typography.caption,
                    text = "Need an Account? "
                )
                Text(
                    text = "SIGN UP",
                    style = TextStyle(textDecoration = TextDecoration.Underline),
                    modifier = Modifier.clickable {

                    }
                )
            }
        }
    )
}

@Composable
fun SignUpDialog(
    isOpen : Boolean,
    onDismissRequest : () -> Unit,
    onSignUpClick : (email : String, password : String) -> Unit
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    AppDialog(isOpen = isOpen, onDismissRequest = onDismissRequest) {
        Column(
            modifier = Modifier
                .height(500.dp)
                .width(300.dp)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp, alignment = Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
            content = {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = email,
                    label = {
                        Text(text = "Email")
                    },
                    onValueChange = { email = it }
                )

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation(),
                    value = password,
                    label = {
                        Text(text = "Password")
                    },
                    onValueChange = { password = it }
                )

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    enabled = true,
                    content = {
                        Text(text = "Sign Up")
                    },
                    onClick = {
                        onSignUpClick(email, password)
                    }
                )
            }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Prev() {


}