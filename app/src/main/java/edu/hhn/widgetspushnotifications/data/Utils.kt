package edu.hhn.widgetspushnotifications.data

import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable

class Utils {
    companion object {

        @Composable
        @OptIn(ExperimentalMaterial3Api::class)
        fun getTopAppBarColors(): TopAppBarColors {
            return TopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                scrolledContainerColor = MaterialTheme.colorScheme.primaryContainer,
                navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }

        @Composable
        fun getTextFieldColors(): TextFieldColors {
            return TextFieldColors(
                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
                disabledTextColor = MaterialTheme.colorScheme.background.copy(alpha = 0.38f),
                errorTextColor = MaterialTheme.colorScheme.background.copy(alpha = 0.38f),
                focusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                disabledContainerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.38f),
                errorContainerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.38f),
                cursorColor = MaterialTheme.colorScheme.onPrimary,
                errorCursorColor = MaterialTheme.colorScheme.error,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                disabledIndicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.38f),
                errorIndicatorColor = MaterialTheme.colorScheme.error,
                focusedLeadingIconColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedLeadingIconColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.38f),
                disabledLeadingIconColor = MaterialTheme.colorScheme.background.copy(alpha = 0.38f),
                errorLeadingIconColor = MaterialTheme.colorScheme.error,
                focusedTrailingIconColor = MaterialTheme.colorScheme.onBackground,
                unfocusedTrailingIconColor = MaterialTheme.colorScheme.onBackground,
                disabledTrailingIconColor = MaterialTheme.colorScheme.onBackground,
                errorTrailingIconColor = MaterialTheme.colorScheme.error,
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                unfocusedLabelColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                disabledLabelColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                errorLabelColor = MaterialTheme.colorScheme.error,
                focusedPlaceholderColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.38f),
                disabledPlaceholderColor = MaterialTheme.colorScheme.background.copy(alpha = 0.38f),
                errorPlaceholderColor = MaterialTheme.colorScheme.error,
                focusedSupportingTextColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedSupportingTextColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.38f),
                disabledSupportingTextColor = MaterialTheme.colorScheme.background.copy(alpha = 0.38f),
                errorSupportingTextColor = MaterialTheme.colorScheme.error,
                focusedPrefixColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedPrefixColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.38f),
                disabledPrefixColor = MaterialTheme.colorScheme.background.copy(alpha = 0.38f),
                errorPrefixColor = MaterialTheme.colorScheme.error,
                focusedSuffixColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedSuffixColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.38f),
                disabledSuffixColor = MaterialTheme.colorScheme.background.copy(alpha = 0.38f),
                errorSuffixColor = MaterialTheme.colorScheme.error,
                textSelectionColors = TextSelectionColors(
                    handleColor = MaterialTheme.colorScheme.inversePrimary,
                    backgroundColor = MaterialTheme.colorScheme.inversePrimary
                )
            )
        }
    }
}
