package de.tarent.academy.kotlin.session3

sealed class Email
data class UnverifiedEmail(val address: String): Email() {
    fun foo(): String = "Hallo"
}
data class VerifiedEmail(val address: String): Email()


// Error:(8, 12) Kotlin: 'when' expression must be exhaustive, add necessary 'is VerifiedEmail' branch or 'else' branch instead

