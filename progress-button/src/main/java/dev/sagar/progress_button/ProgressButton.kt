package dev.sagar.progress_button

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.card.MaterialCardView

@SuppressLint("ClickableViewAccessibility")
class ProgressButton @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : MaterialCardView(context, attributeSet, defStyle) {

    /** Core Items */
    private var attrs: AttributeSet? = null
    private var styleAttr = 0
    private var view: View? = null

    /** Core Components */
    private lateinit var cardView: MaterialCardView
    private lateinit var progressBar: ProgressBar
    private lateinit var textView: TextView
    private lateinit var scaleUp: Animation
    private lateinit var scaleDown: Animation
    private lateinit var vibrator: Vibrator
    private var disableViews: List<View> = mutableListOf()


    /** Attributes  */
    private var defaultText: String = "Login"
    private var finishText: String = "Finish"
    private var defaultColor: Int = 0
    private var pressedColor: Int = 0
    private var disabledColor: Int = 0
    private var finishedColor: Int = 0
    private var btnStrokeColor: Int = Color.TRANSPARENT
    private var btnStrokeWidth: Int = 0
    private var btnCornerRadius: Float = 10.0F
    private var btnElevation: Float = 0.0F
    private var btnTextSize: Float = 0F
    private var btnTextColor: Int = Color.WHITE
    private var isVibrationEnabled: Boolean = false
    private var vibrationMillisecond: Long = 30L

    init {
        this.attrs = attributeSet
        this.styleAttr = defStyle
        initView()
    }


    private fun initView() {
        cardView = this
        view = this
        inflate(context, R.layout.progress_button_view, this)
        val arr = context.obtainStyledAttributes(
            attrs, R.styleable.ProgressButton,
            styleAttr, 0
        )

        textView = findViewById(R.id.tvProgressTitle)
        progressBar = findViewById(R.id.progressBar)
        scaleDown = AnimationUtils.loadAnimation(context, R.anim.scale_down)
        scaleUp = AnimationUtils.loadAnimation(context, R.anim.scale_up)
        vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        defaultText = arr.getString(R.styleable.ProgressButton_default_text) ?: "Login"
        finishText = arr.getString(R.styleable.ProgressButton_finish_text) ?: "Finish"
        btnCornerRadius = arr.getDimension(R.styleable.ProgressButton_corner_radius, 10.0F)
        btnElevation = arr.getDimension(R.styleable.ProgressButton_btn_elevation, 0.0F)
        defaultColor = arr.getColor(
            R.styleable.ProgressButton_default_color, ContextCompat.getColor(
                cardView.context,
                R.color.blue_500
            )
        )
        pressedColor = arr.getColor(
            R.styleable.ProgressButton_pressed_color, ContextCompat.getColor(
                cardView.context,
                R.color.blue_700
            )
        )
        disabledColor = arr.getColor(
            R.styleable.ProgressButton_disabled_color, ContextCompat.getColor(
                cardView.context,
                R.color.blue_200
            )
        )
        finishedColor = arr.getColor(
            R.styleable.ProgressButton_finished_color, ContextCompat.getColor(
                cardView.context,
                R.color.green_500
            )
        )
        btnStrokeColor = arr.getColor(
            R.styleable.ProgressButton_stroke_color, Color.TRANSPARENT
        )
        btnStrokeWidth = arr.getDimension(
            R.styleable.ProgressButton_stroke_width, 0F
        ).toInt()

        btnTextSize = arr.getDimension(
            R.styleable.ProgressButton_btn_text_size, 16F
        )

        btnTextColor = arr.getColor(
            R.styleable.ProgressButton_btn_text_color, Color.WHITE
        )

        isVibrationEnabled = arr.getBoolean(
            R.styleable.ProgressButton_is_vibrate, false
        )

        vibrationMillisecond = arr.getInt(
            R.styleable.ProgressButton_vibration_millisecond, 30
        ).toLong()


        setBtnTextSize(btnTextSize)
        setBtnTextColor(btnTextColor)

        cardView.setCardBackgroundColor(defaultColor)

        setDefaultText(defaultText)

        setFinishedText(finishText)

        setDefaultColor(defaultColor)

        setPressedColor(pressedColor)

        setDisabledColor(disabledColor)

        setFinishedColor(finishedColor)

        setPressedColor(pressedColor)

        setBtnStrokeColor(btnStrokeColor)

        setBtnStrokeWidth(btnStrokeWidth)

        setCornerRadius(btnCornerRadius)

        setButtonElevation(btnElevation)

        this.setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    cardView.startAnimation(scaleDown)
                    changeColorOnActionDown(false)
                }
                MotionEvent.ACTION_UP -> {
                    cardView.startAnimation(scaleUp)
                    changeColorOnActionUp(false)
                }
            }
            false
        }

        arr.recycle()
    }

    /**
     * Function invoked on click of cardview/button
     */
    fun setOnClickListener(listener: () -> Unit) {
        cardView.setOnClickListener {
            if (isVibrationEnabled) vibrateClick()
            listener.invoke()
        }
    }

    /**
     * Enable Vibration
     */
    private fun enableVibration() {
        isVibrationEnabled = true
    }

    /**
     * Disable Vibration
     */
    private fun disableVibration() {
        isVibrationEnabled = false
    }

    /**
     * set vibration time in millisecond
     */
    private fun setVibrationTimeInMillisecond(time: Long) {
        vibrationMillisecond = time
    }

    /**
     * Set text color
     */
    private fun setBtnTextColor(color: Int) {
        textView.setTextColor(color)
    }

    /**
     * Set text size
     */
    private fun setBtnTextSize(textSize: Float) {
        textView.textSize = textSize
    }

    /**
     * Set finished text to button
     */
    private fun setFinishedText(text: String) {
        finishText = text
    }

    /**
     * Set default button color
     */
    private fun setDefaultColor(color: Int) {
        defaultColor = color
    }

    /**
     * Set default button color
     */
    private fun setDisabledColor(color: Int) {
        disabledColor = color
    }

    /**
     * Set pressed button color
     */
    private fun setPressedColor(color: Int) {
        pressedColor = color
    }

    /**
     * Set finished button color
     */
    private fun setFinishedColor(color: Int) {
        finishedColor = color
    }

    /**
     * Set stroke color
     */
    private fun setBtnStrokeColor(color: Int) {
        btnStrokeColor = color
        cardView.strokeColor = btnStrokeColor
    }

    /**
     * Set stroke width
     */
    private fun setBtnStrokeWidth(width: Int) {
        btnStrokeWidth = width
        cardView.strokeWidth = btnStrokeWidth
    }

    /**
     * Set Corner radius of button
     */
    private fun setCornerRadius(radius: Float) {
        btnCornerRadius = radius
        cardView.radius = radius
    }

    /**
     * Set button elevation
     */
    private fun setButtonElevation(elevation: Float) {
        btnElevation = elevation
        cardView.elevation = btnElevation
    }

    /**
     * Set text to button
     */
    private fun setDefaultText(title: String?) {
        textView.text = title
    }

    private fun vibrateClick() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(
                VibrationEffect.createOneShot(
                    vibrationMillisecond,
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(30)
        }
    }

    /**
     * Disable/Enable button
     */
    fun isEnabled(isEnabled: Boolean) {
        if (isEnabled) {
            cardView.setCardBackgroundColor(
                defaultColor
            )
        } else {
            cardView.setCardBackgroundColor(
                disabledColor
            )
        }
        textView.text = defaultText
        cardView.isEnabled = isEnabled
        progressBar.gone()
        textView.visible()
    }

    fun setDisableViews(views: List<View>) {
        disableViews = views
    }

    /**
     * Set the button state to activate for loading purpose
     */
    fun activate() {
        cardView.setCardBackgroundColor(
            disabledColor
        )
        textView.invisible()
        progressBar.visible()

        for (editText in disableViews) {
            editText.isEnabled = false
        }

        cardView.isEnabled = false
    }

    /**
     * Set the button state to finished - when task is completed
     */
    fun finished() {
        for (editText in disableViews) {
            editText.isEnabled = true
        }

        cardView.isEnabled = true
        cardView.setCardBackgroundColor(
            finishedColor
        )
        textView.text = finishText
        textView.visible()
        progressBar.gone()
    }

    /**
     * Reset the button state
     */
    fun reset() {
        for (editText in disableViews) {
            editText.isEnabled = true
        }
        cardView.isEnabled = true
        cardView.setCardBackgroundColor(
            defaultColor
        )
        textView.text = defaultText
        textView.visible()
        progressBar.gone()
    }

    /**
     * Change card background color w.r.t inverseColor on action down
     */
    private fun changeColorOnActionDown(inverseColor: Boolean) {
        if (inverseColor) {
            cardView.setCardBackgroundColor(
                ContextCompat.getColor(
                    cardView.context,
                    R.color.gray_200
                )
            )
        } else {
            cardView.setCardBackgroundColor(
                pressedColor
            )
        }
    }

    /**
     * Change card background color w.r.t inverseColor on action up
     */
    private fun changeColorOnActionUp(inverseColor: Boolean) {
        if (inverseColor) {
            cardView.setCardBackgroundColor(
                ContextCompat.getColor(
                    cardView.context,
                    R.color.white
                )
            )
        } else {
            cardView.setCardBackgroundColor(
                defaultColor
            )
        }
    }

}