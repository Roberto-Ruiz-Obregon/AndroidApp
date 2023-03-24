package com.example.kotlin.robertoruizapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.kotlin.robertoruizapp.signup.SignUp

class SignUpActivity : AppCompatActivity() {
    private lateinit var viewModel: SignUpActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val name: EditText = findViewById(R.id.editTextName)
        val edad: EditText = findViewById<EditText>(R.id.editTextAge)
        val sex: EditText = findViewById<EditText>(R.id.editTextSex)
        val education: EditText = findViewById<EditText>(R.id.editTextEducation)
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

        fun signUpUser() {

            val ageInt: Int = edad.text.toString().toIntOrNull() ?: 0
            val pcInt: Int = postalCode.text.toString().toIntOrNull() ?: 0
            val user = SignUp(ageInt, education.toString(), email.toString(), sex.toString(), job.toString(),
                name.toString(), password.toString(), cnfPassword.toString(), pcInt,)
            viewModel.signUpNewUser(user)

        }

        btnRegister.setOnClickListener {
            signUpUser()
        }

    }
}