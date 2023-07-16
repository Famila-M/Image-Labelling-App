package com.example.firstapp

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import java.io.IOException
import android.widget.Button
import com.google.mlkit.vision.label.ImageLabel
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions

class MainActivity : AppCompatActivity() {
    var outputText: String? = null
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var text1: TextView = findViewById(R.id.text1)

        val image: ImageView = findViewById(R.id.imageView2)
        val button: Button = findViewById(R.id.imagelabeling)
        val image_name = "sample1.jpg"
        val bitmap: Bitmap? = assetsToBitmap (image_name)
        bitmap?.apply { image.setImageBitmap(this) }
        button.setOnClickListener {

            val labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS)
            val input_image = InputImage.fromBitmap(bitmap!!, 0)
            val outputText = ""
            labeler.process(input_image).addOnSuccessListener { labels -> FindImageContents(labels,text1) }
            button.alpha = 0.6F

        }}

    private fun FindImageContents(labels: List<ImageLabel>, text1: TextView) {
        for (label in labels){
            outputText += "class=${label.text}:conf=${label.confidence}\n"
        }
        text1.text = outputText
        text1.alpha = 0.6F
    }

//
    fun Context.assetsToBitmap(imagename: String): Bitmap?{
        return try {
            with(assets.open(imagename)){BitmapFactory.decodeStream(this)}
        }
        catch (e: IOException) {null}
    }
}