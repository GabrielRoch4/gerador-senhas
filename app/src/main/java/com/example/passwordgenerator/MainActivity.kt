package com.example.passwordgenerator
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var generatedPasswordTextView: TextView
    private lateinit var copyPasswordButton: Button
    private lateinit var generatePasswordButton: Button
    private lateinit var seekBar: SeekBar
    private lateinit var useLettersSwitch: Switch
    private lateinit var useNumbersSwitch: Switch
    private lateinit var useCapitalSwitch: Switch
    private lateinit var useSpecialCharactersSwitch: Switch
    private lateinit var useEmojiSwitch: Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        generatedPasswordTextView = findViewById(R.id.generatedPassword)
        copyPasswordButton = findViewById(R.id.copyPasswordButton)
        generatePasswordButton = findViewById(R.id.generatePasswordButton)
        seekBar = findViewById(R.id.editPasswordLength)
        useLettersSwitch = findViewById(R.id.editUseLetters)
        useNumbersSwitch = findViewById(R.id.editUseNumbers)
        useCapitalSwitch = findViewById(R.id.editUseCapital)
        useSpecialCharactersSwitch = findViewById(R.id.editUseSpecialCharac)
        useEmojiSwitch = findViewById(R.id.editUseEmoji)

        generatedPasswordTextView.visibility = View.GONE
        copyPasswordButton.visibility = View.GONE

        generatePasswordButton.setOnClickListener {
            val password = generatePassword()
            generatedPasswordTextView.text = password

            generatedPasswordTextView.visibility = View.VISIBLE
            copyPasswordButton.visibility = View.VISIBLE
        }

        copyPasswordButton.setOnClickListener {
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("generated password", generatedPasswordTextView.text.toString())
            clipboard.setPrimaryClip(clip)

            Toast.makeText(this, "Senha copiada para a área de transferência", Toast.LENGTH_SHORT).show()
        }
    }

    private fun generatePassword(): String {
        val length = seekBar.progress
        val useLetters = useLettersSwitch.isChecked
        val useNumbers = useNumbersSwitch.isChecked
        val useCapital = useCapitalSwitch.isChecked
        val useSpecialCharacters = useSpecialCharactersSwitch.isChecked
        val useEmoji = useEmojiSwitch.isChecked

        val letters = "abcdefghijklmnopqrstuvwxyz"
        val numbers = "0123456789"
        val capitalLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val specialCharacters = "!@#$%^&*()-_=+[]{}"
        val emojis = "\uD83D\uDE00\uD83D\uDE01\uD83D\uDE02\uD83D\uDE03\uD83D\uDE04" // Emojis

        var characterPool = ""
        if (useLetters) characterPool += letters
        if (useNumbers) characterPool += numbers
        if (useCapital) characterPool += capitalLetters
        if (useSpecialCharacters) characterPool += specialCharacters
        if (useEmoji) characterPool += emojis

        if (characterPool.isEmpty()) {
            return "Selecione ao menos um tipo de caractere!"
        }

        return (1..length).map {
            characterPool[Random.nextInt(characterPool.length)]
        }.joinToString("")
    }
}
