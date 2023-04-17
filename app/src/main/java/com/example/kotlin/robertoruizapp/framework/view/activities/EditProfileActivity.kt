package com.example.kotlin.robertoruizapp.framework.view.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.kotlin.robertoruizapp.R
import com.example.kotlin.robertoruizapp.data.network.model.Profile.EditProfileRequest
import com.example.kotlin.robertoruizapp.framework.viewmodel.EditProfileActivityViewModel

class EditProfileActivity : AppCompatActivity() {
    private lateinit var viewModel: EditProfileActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val generos = arrayOf("Hombre", "Mujer", "Prefiero no decir")
        val gender = findViewById<Spinner>(R.id.spinnerSex)
        gender.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, generos)

        val estudios = arrayOf("Ninguno", "Primaria", "Secundaria", "Preparatoria", "Universidad")
        val studies = findViewById<Spinner>(R.id.spinnerEducation)
        studies.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, estudios)

        val name: EditText = findViewById(R.id.editTextName)
        val edad: EditText = findViewById(R.id.editTextAge)
        val job: EditText = findViewById(R.id.editTextJob)
        val postalCode: EditText = findViewById(R.id.editTextPostalCode)
        val email: EditText = findViewById(R.id.editTextEmail)
        val password: EditText = findViewById(R.id.editTextPassword)
        val cnfPassword: EditText = findViewById(R.id.editTextCnfPassword)
        fun initViewModel() {
            viewModel = ViewModelProvider(this)[EditProfileActivityViewModel::class.java]
            viewModel.getProfileUserObserver().observe(this) {

                if (it == null) {
                    Toast.makeText(
                        this@EditProfileActivity,
                        "Failed to edit User",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        this@EditProfileActivity,
                        "Successfully edited User",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
        initViewModel()
        val btnConfirm = findViewById<Button>(R.id.buttonConfirm)
        val btnCancel = findViewById<Button>(R.id.buttonCancel)
        fun editUserProfile() {
            val ageInt: Int = edad.text.toString().toIntOrNull() ?: 0
            val pcInt: Int = postalCode.text.toString().toIntOrNull() ?: 0
            val selectedGender = gender.selectedItem.toString()
            val selectedStudies = studies.selectedItem.toString()
            val user = EditProfileRequest(
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
            viewModel.editProfileUser(user)
        }

        btnConfirm.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            editUserProfile()
            startActivity(intent)
        }

        btnCancel.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }

    }
}