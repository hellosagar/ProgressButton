package dev.sagar.progress_button

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.google.android.material.card.MaterialCardView
import dev.sagar.progress_button.DefaultParams.CORNER_RADIUS
import dev.sagar.progress_button.DefaultParams.ELEVATION
import dev.sagar.progress_button.DefaultParams.FINISHED_TEXT
import dev.sagar.progress_button.DefaultParams.IS_VIBRATION_ENABLED
import dev.sagar.progress_button.DefaultParams.RIPPLE_COLOR
import dev.sagar.progress_button.DefaultParams.STROKE_COLOR
import dev.sagar.progress_button.DefaultParams.STROKE_WIDTH
import dev.sagar.progress_button.DefaultParams.TEXT_COLOR
import dev.sagar.progress_button.DefaultParams.TEXT_SIZE
import dev.sagar.progress_button.DefaultParams.TITLE_TEXT
import dev.sagar.progress_button.DefaultParams.VIBRATION_TIME

@SuppressLint("ClickableViewAccessibility")
class ProgressButton @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : MaterialCardView(context, attributeSet, defStyle) {

    private val TAG = "ProgressButton"

    /** Core Components */
    private var cardView: MaterialCardView = this
    private var progressBar: ProgressBar
    private var textView: TextView
    private var scaleUp: Animation
    private var scaleDown: Animation
    private var vibrator: Vibrator? = null
    private var disableViews: List<View> = mutableListOf()

    /** Attributes  */
    private var defaultText: String
    private var finishText: String
    private var defaultColor: Int
    private var pressedColor: Int
    private var disabledColor: Int
    private var finishedColor: Int
    private var btnStrokeColor: Int
    private var btnStrokeWidth: Int
    private var btnCornerRadius: Float
    private var btnElevation: Float
    private var btnTextSize: Float
    private var btnTextColor: Int
    private var isVibrationEnabled: Boolean
    private var vibrationMillisecond: Long
    private var rippleColor: Int

    // liveData for button states
    private var buttonStatesLiveData: LiveData<ButtonStates>? = null

    private var currentState = ButtonStates.ENABLED

    init {
        inflate(context, R.layout.progress_button_view, this)
        val customAttributes = context.obtainStyledAttributes(
            attributeSet, R.styleable.ProgressButton,
            defStyle, 0
        )
        textView = findViewById(R.id.tvProgressTitle)
        progressBar = findViewById(R.id.progressBar)
        scaleDown = AnimationUtils.loadAnimation(context, R.anim.scale_down)
        scaleUp = AnimationUtils.loadAnimation(context, R.anim.scale_up)

        customAttributes.apply {
            defaultText = getString(R.styleable.ProgressButton_default_text) ?: TITLE_TEXT
            finishText = getString(R.styleable.ProgressButton_finish_text) ?: FINISHED_TEXT
            btnCornerRadius = getDimension(R.styleable.ProgressButton_corner_radius, CORNER_RADIUS)
            btnElevation = getDimension(R.styleable.ProgressButton_btn_elevation, ELEVATION)
            defaultColor = getColor(
                R.styleable.ProgressButton_default_color,
                ContextCompat.getColor(
                    context,
                    R.color.blue_500
                )
            )
            pressedColor = getColor(
                R.styleable.ProgressButton_pressed_color,
                ContextCompat.getColor(
                    context,
                    R.color.blue_700
                )
            )
            disabledColor = getColor(
                R.styleable.ProgressButton_disabled_color,
                ContextCompat.getColor(
                    context,
                    R.color.blue_200
                )
            )
            finishedColor = getColor(
                R.styleable.ProgressButton_finished_color,
                ContextCompat.getColor(
                    context,
                    R.color.green_500
                )
            )
            btnStrokeColor = getColor(
                R.styleable.ProgressButton_stroke_color, STROKE_COLOR
            )
            btnStrokeWidth = getDimension(
                R.styleable.ProgressButton_stroke_width, STROKE_WIDTH
            ).toInt()

            btnTextSize = getDimension(
                R.styleable.ProgressButton_btn_text_size, TEXT_SIZE
            )

            btnTextColor = getColor(
                R.styleable.ProgressButton_btn_text_color, TEXT_COLOR
            )

            isVibrationEnabled = getBoolean(
                R.styleable.ProgressButton_is_vibrate, IS_VIBRATION_ENABLED
            )

            vibrationMillisecond = getInt(
                R.styleable.ProgressButton_vibration_millisecond, VIBRATION_TIME
            ).toLong()

            rippleColor = getColor(
                R.styleable.ProgressButton_ripple_color, RIPPLE_COLOR
            )
        }

        // Set Text attributes
        setFinishedText(finishText)
        setDefaultText(defaultText)
        setBtnTextSize(btnTextSize)
        setBtnTextColor(btnTextColor)
        setBtnText(defaultText)

        // set cardview color states
        cardView.setCardBackgroundColor(defaultColor)
        setDefaultColor(defaultColor)
        setPressedColor(pressedColor)
        setDisabledColor(disabledColor)
        setFinishedColor(finishedColor)

        // set stroke attributes
        setBtnStrokeColor(btnStrokeColor)
        setBtnStrokeWidth(btnStrokeWidth)

        // set card attributes
        setCornerRadius(btnCornerRadius)
        setButtonElevation(btnElevation)
        setRippleColor(rippleColor)

        // change color with gesture i.e action up and down
        setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    cardView.startAnimation(scaleDown)
                    changeColorOnActionDown()
                }
                MotionEvent.ACTION_UP -> {
                    cardView.startAnimation(scaleUp)
                    changeColorOnActionUp()
                }
            }
            false
        }

        customAttributes.recycle()
    }

    /**
     * Function invoked on click of cardview/button
     */
    fun setOnClickListener(listener: () -> Unit) {
        cardView.setOnClickListener {
            if (isVibrationEnabled) {
                if ((
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.VIBRATE
                    ) == PackageManager.PERMISSION_GRANTED
                    )
                ) {
                    vibrateOnClick()
                } else {
                    Log.w(
                        TAG,
                        "Please add vibration permission in your manifest, if you want to use vibration and don't tell us that we didn't remind you ^_^"
                    )
                }
            }
            listener.invoke()
        }
    }

    /**
     * Set ripple color
     */
    fun setRippleColor(color: Int) {
        cardView.rippleColor = ColorStateList.valueOf(color)
    }

    /**
     * Enable Vibration
     */
    fun enableVibration() {
        isVibrationEnabled = true
    }

    /**
     * Disable Vibration
     */
    fun disableVibration() {
        isVibrationEnabled = false
    }

    /**
     * Set vibration time in millisecond
     */
    fun setVibrationTimeInMillisecond(time: Long) {
        vibrationMillisecond = time
    }

    /**
     * Set text color
     */
    fun setBtnTextColor(color: Int) {
        textView.setTextColor(color)
    }

    /**
     * Set text size
     */
    fun setBtnTextSize(textSize: Float) {
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
    }

    /**
     * Set finished text to button
     */
    fun setFinishedText(text: String) {
        finishText = text
        setTextWithRespectToState()
    }

    /**
     * Set default button color
     */
    fun setDefaultColor(color: Int) {
        defaultColor = color
    }

    /**
     * Set default button color
     */
    fun setDisabledColor(color: Int) {
        disabledColor = color
    }

    /**
     * Set pressed button color
     */
    fun setPressedColor(color: Int) {
        pressedColor = color
    }

    /**
     * Set finished button color
     */
    fun setFinishedColor(color: Int) {
        finishedColor = color
    }

    /**
     * Set stroke color
     */
    fun setBtnStrokeColor(color: Int) {
        btnStrokeColor = color
        cardView.strokeColor = btnStrokeColor
    }

    /**
     * Set stroke width
     */
    fun setBtnStrokeWidth(width: Int) {
        btnStrokeWidth = width
        cardView.strokeWidth = btnStrokeWidth
    }

    /**
     * Set Corner radius of button
     */
    fun setCornerRadius(radius: Float) {
        btnCornerRadius = radius
        cardView.radius = radius
    }

    /**
     * Set button elevation
     */
    fun setButtonElevation(elevation: Float) {
        btnElevation = elevation
        cardView.elevation = btnElevation
    }

    /**
     * Set default text
     */
    fun setDefaultText(text: String) {
        defaultText = text
        setTextWithRespectToState()
    }

    private fun setTextWithRespectToState() {
        if (currentState == ButtonStates.ENABLED || currentState == ButtonStates.DISABLED) {
            setBtnText(defaultText)
        } else if (currentState == ButtonStates.LOADING) {
            setBtnText(finishText)
        }
    }

    /**
     * Set text to button
     */
    private fun setBtnText(title: String) {
        textView.text = title
    }

    /**
     * Vibrate the device with the following vibrate time in millisecond
     */
    @SuppressLint("MissingPermission")
    private fun vibrateOnClick() {

        if (vibrator == null)
            vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator?.vibrate(
                VibrationEffect.createOneShot(
                    vibrationMillisecond,
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
        } else {
            @Suppress("DEPRECATION")
            vibrator?.vibrate(30)
        }
    }

    /**
     * Enable button
     */
    fun enable() {
        currentState = ButtonStates.ENABLED
        for (editText in disableViews) {
            editText.isEnabled = true
        }
        cardView.setCardBackgroundColor(
            defaultColor
        )
        setBtnText(defaultText)
        cardView.isEnabled = true
        progressBar.gone()
        textView.visible()
    }

    /**
     * Disable button
     */
    fun disable() {
        currentState = ButtonStates.DISABLED
        cardView.setCardBackgroundColor(
            disabledColor
        )
        setBtnText(defaultText)
        cardView.isEnabled = false
        progressBar.gone()
        textView.visible()
    }

    /**
     * Setting list of view that will disable in active state of button
     */
    fun setDisableViews(views: List<View>) {
        disableViews = views
    }

    /**
     * Set the button state to loading for loading purpose
     */
    fun loading() {
        currentState = ButtonStates.LOADING
        for (editText in disableViews) {
            editText.isEnabled = false
        }

        cardView.setCardBackgroundColor(
            disabledColor
        )
        textView.invisible()
        progressBar.visible()

        cardView.isEnabled = false
    }

    /**
     * Set the button state to finished - when task is completed
     */
    fun finished() {
        currentState = ButtonStates.FINISHED
        for (editText in disableViews) {
            editText.isEnabled = true
        }

        cardView.isEnabled = true
        cardView.setCardBackgroundColor(
            finishedColor
        )
        setBtnText(finishText)
        textView.visible()
        progressBar.gone()
    }

    /**
     * Reset the button state
     */
    fun reset() {
        currentState = ButtonStates.ENABLED
        for (editText in disableViews) {
            editText.isEnabled = true
        }
        cardView.isEnabled = true
        cardView.setCardBackgroundColor(
            defaultColor
        )
        setBtnText(defaultText)
        textView.visible()
        progressBar.gone()
    }

    /**
     * Set pressed color on card background color on action down
     */
    private fun changeColorOnActionDown() {
        cardView.setCardBackgroundColor(
            pressedColor
        )
    }

    /**
     * Set default color on card background color on action up
     */
    private fun changeColorOnActionUp() {
        cardView.setCardBackgroundColor(
            defaultColor
        )
    }

    /**
     * Attach livedata to the button to directly observe changes in button states .
     * Helps when u have a livedata in your viewModel and you want to update button state
     * according to the livedata
     */
    fun attachToLiveData(state: LiveData<ButtonStates>, lifecycleOwner: LifecycleOwner) {
        buttonStatesLiveData = state
        buttonStatesLiveData?.observe(lifecycleOwner) {
            when (it) {
                ButtonStates.LOADING -> loading()
                ButtonStates.ENABLED -> enable()
                ButtonStates.DISABLED -> disable()
                ButtonStates.FINISHED -> finished()
                else -> reset()
            }
        }
    }

    /**
     * Added the enum support to change the Button state using [ButtonStates] enum
     */
    fun setState(state: ButtonStates) {
        when (state) {
            ButtonStates.LOADING -> loading()
            ButtonStates.ENABLED -> enable()
            ButtonStates.DISABLED -> disable()
            ButtonStates.FINISHED -> finished()
            else -> reset()
        }
    }
}
