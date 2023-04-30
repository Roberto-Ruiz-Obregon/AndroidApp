package com.example.kotlin.robertoruizapp.framework.view.activities

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin.robertoruizapp.R
/**
 * SelectImage class that manages the activity actions
 */
class SelectImage: AppCompatActivity() {

    private lateinit var button: Button
    private lateinit var imageView: ImageView

    companion object {
        val IMAGE_REQUEST_CODE = 100
    }
    /**
     * Sets the information for the current fragment when creating the view
     *
     * @param savedInstanceState the state of the view
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragmento_forma_de_pago)

        button = findViewById(R.id.choose_image_btn)
        imageView = findViewById(R.id.image_view)

        button.setOnClickListener {
            pickImageGallery()

        }
    }

    /**
     * Opens the Selector Images of the photo gallery
     */
    private fun pickImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)

    }

    /**
     * If the requestCode and result code are equal selected image is loaded
     *
     * @param requestCode request code of companion Object
     * @param resultCode result code of the Activity
     * @param data information of the Image
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK){
            imageView.setImageURI((data?.data)
            )
        }
    }

}