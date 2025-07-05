package com.nolasco.ariadna.poketinder_2025_01

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nolasco.ariadna.poketinder_2025_01.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var sharedPref: SharedPreferencesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = SharedPreferencesRepository().also {
            it.setSharedPreference(this)
        }

        binding.btnRegister.setOnClickListener {
            val email = binding.edtEmail.text.toString().trim()
            val password = binding.edtPassword.text.toString().trim()
            val confirmPassword = binding.edtPassword2.text.toString().trim()

            if (!isValidEmail(email)) {
                showMessage("Correo inválido")
                return@setOnClickListener
            }

            if (password.length < 8) {
                showMessage("La contraseña debe tener al menos 8 caracteres")
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                showMessage("Las contraseñas no coinciden")
                return@setOnClickListener
            }

            // Guardar en SharedPreferences
            sharedPref.saveUserEmail(email)
            sharedPref.saveUserPassword(password)

            showMessage("Registro exitoso")

            // Se redirige al login
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        // Botón "Ya tengo una cuenta"
        binding.btnAlreadyAccount.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        // Botón de retroceso
        binding.btnBackClose.setOnClickListener {
            finish()
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}