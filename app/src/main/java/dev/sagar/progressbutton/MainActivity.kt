package dev.sagar.progressbutton

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dev.sagar.progress_button.ProgressButton
import dev.sagar.progressbutton.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var progressButton: ProgressButton
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressButton = findViewById(R.id.button)

        initViews()
        initClickListeners()
    }

    private fun initViews() = binding.apply {

        progressButton.setDisableViews(listOf(
            editTextTextPersonName,
            editTextTextPersonName2,
        ))

    }

    private fun initClickListeners() = binding.apply {

        progressButton.setOnClickListener {
            Toast.makeText(this@MainActivity, "On click!", Toast.LENGTH_SHORT).show()
        }

        btnActivate.setOnClickListener {
            progressButton.activate()
        }

        btnFinish.setOnClickListener {
            progressButton.finished()
        }

        btnEnable.setOnClickListener {
            progressButton.enable()
        }

        btnDisable.setOnClickListener {
            progressButton.disable()
        }

        btnReset.setOnClickListener {
            progressButton.reset()
        }

    }

}