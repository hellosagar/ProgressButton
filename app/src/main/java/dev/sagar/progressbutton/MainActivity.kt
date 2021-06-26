package dev.sagar.progressbutton

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dev.sagar.progressbutton.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        initClickListeners()
    }

    private fun initViews() = binding.apply {
        // Passing list of view that we want them to be disabled during the active state
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
            // Activate state
            progressButton.activate()
        }

        btnFinish.setOnClickListener {
            // Finish state
            progressButton.finished()
        }

        btnEnable.setOnClickListener {
            // Enable state
            progressButton.enable()
        }

        btnDisable.setOnClickListener {
            // Enable state
            progressButton.disable()
        }

        btnReset.setOnClickListener {
            // Reset state
            progressButton.reset()
        }

    }

}