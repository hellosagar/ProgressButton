package dev.sagar.progressbutton

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import dev.sagar.progress_button.ButtonStates
import dev.sagar.progressbutton.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val sampleLiveData: LiveData<ButtonStates> = liveData(Dispatchers.IO) {
        emit(ButtonStates.DISABLED)
        delay(5000L)
        emit(ButtonStates.ENABLED)
        delay(5000L)
        emit(ButtonStates.LOADING)
        delay(5000L)
        emit(ButtonStates.FINISHED)
        delay(5000L)
        emit(ButtonStates.ENABLED)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        initClickListeners()
    }

    private fun initViews() = binding.apply {
        // Passing list of view that we want them to be disabled during the active state
        progressButton.setDisableViews(
            listOf(
                editTextTextPersonName,
                editTextTextPersonName2,
            )
        )
    }

    private fun initClickListeners() = binding.apply {

        progressButton.setOnClickListener {
            Toast.makeText(this@MainActivity, getString(R.string.click), Toast.LENGTH_SHORT).show()
        }

        btnLoading.setOnClickListener {
            // Loading state
            progressButton.loading()
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

        switchToCompose.setOnClickListener {
            Intent(this@MainActivity, ComposeActivity::class.java).also {
                startActivity(it)
            }
        }

        progressButton.attachToLiveData(sampleLiveData, this@MainActivity)
    }
}
