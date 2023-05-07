package com.example.kotlin.robertoruizapp.framework.view.activities

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.kotlin.robertoruizapp.R
import com.example.kotlin.robertoruizapp.data.network.model.signup.SignUp
import com.example.kotlin.robertoruizapp.framework.viewmodel.SignUpActivityViewModel

/**
 * SignUpActivity class that manages the activity actions
 *
 */
class SignUpActivity : AppCompatActivity() {
    private lateinit var viewModel: SignUpActivityViewModel

    /**
     * Sets the information for the current activity when creating the view
     *
     * @param savedInstanceState the state of the view
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val generos = arrayOf("Hombre", "Mujer", "Prefiero no decir")
        val gender = findViewById<Spinner>(R.id.spinnerSex)
        gender.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, generos)

        val estudios = arrayOf(
            "Ninguno",
            "Primaria",
            "Secundaria",
            "Preparatoria",
            "Universidad",
            "Maestria",
            "Doctorado"
        )
        val studies = findViewById<Spinner>(R.id.spinnerEducation)
        studies.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, estudios)

        val name: EditText = findViewById(R.id.editTextName)
        val edad: EditText = findViewById<EditText>(R.id.editTextAge)
        val job: EditText = findViewById<EditText>(R.id.editTextJob)
        val postalCode: EditText = findViewById<EditText>(R.id.editTextPostalCode)
        val email: EditText = findViewById<EditText>(R.id.editTextEmail)
        val password: EditText = findViewById<EditText>(R.id.editTextPassword)
        val cnfPassword: EditText = findViewById<EditText>(R.id.editTextCnfPassword)

        fun initViewModel() {
            viewModel = ViewModelProvider(this)[SignUpActivityViewModel::class.java]
            viewModel.getSignUpNewUserObserver().observe(this) {

                if (it == null) {
                    Toast.makeText(
                        this@SignUpActivity,
                        "Failed to register User",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        this@SignUpActivity,
                        "Successfully register User",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        initViewModel()

        val btnRegister = findViewById<Button>(R.id.buttonRegister)
        val btnGoLogin = findViewById<Button>(R.id.buttonGoLogin)
        fun signUpUser() {

            val ageInt: Int = edad.text.toString().toIntOrNull() ?: 0
            val pcInt: Int = postalCode.text.toString().toIntOrNull() ?: 0
            val selectedGender = gender.selectedItem.toString()
            val selectedStudies = studies.selectedItem.toString()
            val user = SignUp(
                name.text.toString(),
                ageInt,
                selectedGender,
                job.text.toString(),
                selectedStudies,
                pcInt,
                email.text.toString(),
                password.text.toString(),
                cnfPassword.text.toString(),
            )
            try {
                viewModel.signUpNewUser(user)
            } catch (e: Exception) {
                Toast.makeText(
                    applicationContext,
                    "$e",
                    Toast.LENGTH_LONG
                ).show()
            }


        }

        btnRegister.setOnClickListener {
            if (validateInput(
                    name.text.toString(),
                    edad.text.toString(),
                    gender.selectedItem.toString(),
                    job.text.toString(),
                    studies.selectedItem.toString(),
                    postalCode.text.toString(),
                    email.text.toString(),
                    password.text.toString(),
                    cnfPassword.text.toString()
                )) {
                signUpUser()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }



        btnGoLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

        }

    }
    private fun validateInput(
        name: String,
        age: String,
        selectedGender: String,
        job: String,
        selectedStudies: String,
        postalCode: String,
        email: String,
        password: String,
        cnfPassword: String
    ):Boolean {
        if (name.isEmpty()) {
            Toast.makeText(this, "El nombre no puede estar vacío.", Toast.LENGTH_SHORT).show()
            return false
        }

        if (age.isEmpty()) {
            Toast.makeText(this, "La edad no puede estar vacía.", Toast.LENGTH_SHORT).show()
            return false
        }

        if (selectedGender == "Seleccione su género") {
            Toast.makeText(this, "Por favor, seleccione su Preferencia.", Toast.LENGTH_SHORT).show()
            return false
        }

        if (job.isEmpty()) {
            Toast.makeText(this, "La Ocupación no puede estar vacía.", Toast.LENGTH_SHORT).show()
            return false
        }

        if (selectedStudies == "Seleccione su nivel de estudios") {
            Toast.makeText(this, "Por favor, seleccione su nivel de estudios.", Toast.LENGTH_SHORT).show()
            return false
        }

        if (postalCode.isEmpty()) {
            Toast.makeText(this, "El código postal no puede estar vacío.", Toast.LENGTH_SHORT).show()
            return false
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Ingrese un correo electrónico válido.", Toast.LENGTH_SHORT).show()
            return false
        }

        if (password.isEmpty() || cnfPassword.isEmpty()) {
            Toast.makeText(this, "La contraseña no puede estar vacía.", Toast.LENGTH_SHORT).show()
            return false
        }

        if (password != cnfPassword) {
            Toast.makeText(this, "Las contraseñas no coinciden.", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

}