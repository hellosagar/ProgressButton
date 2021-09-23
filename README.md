<h1 align="center">Progress Button █▒▒▒▒▒▒▒</h1>

<p align="center">
<a href="https://github.com/hellosagar/ProgressButton/blob/master/LICENSE" target="blank">
<img src="https://img.shields.io/github/license/hellosagar/ProgressButton?style=flat-square" alt="ProgressButton licence" />
</a>
<a href="https://github.com/hellosagar/ProgressButton/fork" target="blank">
<img src="https://img.shields.io/github/forks/hellosagar/ProgressButton?style=flat-square" alt="ProgressButton forks"/>
</a>
<a href="https://github.com/hellosagar/ProgressButton/stargazers" target="blank">
<img src="https://img.shields.io/github/stars/hellosagar/ProgressButton?style=flat-square" alt="ProgressButton stars"/>
</a>
<a href="https://github.com/hellosagar/ProgressButton/issues" target="blank">
<img src="https://img.shields.io/github/issues/hellosagar/ProgressButton?style=flat-square" alt="ProgressButton issues"/>
</a>
<a href="https://github.com/hellosagar/ProgressButton/pulls" target="blank">
<img src="https://img.shields.io/github/issues-pr/hellosagar/ProgressButton?style=flat-square" alt="ProgressButton pull-requests"/>
</a>

</p>

<p align="center">
    <a href="https://github.com/hellosagar/ProgressButton/issues/new/choose">Report Bug</a>
    ·
    <a href="https://github.com/hellosagar/ProgressButton/issues/new/choose">Request Feature</a>
</p>

### Need easy way to refelect all the button states?
Progress Button  is a android library for hanling different types state like active, finished, enabled, disabled and reset with a single line of code.

## 🚀 Demo [![jitPack](https://jitpack.io/v/hellosagar/ProgressButton.svg)](https://jitpack.io/#hellosagar/ProgressButton)

![progress button example](https://raw.githubusercontent.com/hellosagar/ProgressButton/master/gif/demo.gif)


## 🧐 Features

- One line of code to change state
- Easy configurable
- Customizable
- Set vibration on click
- Disable views in active state

## 🛠️ Installation Steps

```gradle  
repositories {  
 maven { url 'https://jitpack.io' }  
}  
  
dependencies {  
 implementation 'com.github.hellosagar:ProgressButton:0.32'
}  
```

## 💻 Usage

In XMl you need to define the button with your parameters to achieve the desired the design

#### Notes -  
* To use vibration on click please add the following permission in android manifest
```xml
    <uses-permission android:name="android.permission.VIBRATE"/>
```

Here is the sample code

```xml
<dev.sagar.progress_button.ProgressButton
        android:id="@+id/button"
        android:layout_width="240dp"
        android:layout_height="54dp"
        app:btn_elevation="12dp"
        app:corner_radius="12dp"
        app:default_text="YOOYOOYOYOYO"
        app:disabled_color="@android:color/holo_purple"
        app:finish_text="Im done"
        app:is_vibrate="false"
        app:finished_color="@color/gray_700"
        app:pressed_color="@color/black_500"
        app:stroke_color="@android:color/holo_orange_dark"
        app:stroke_width="3dp" />
```

## Color Parameters

| Explanation                  | Parameter Name          | Type       | Default Value                 |
| ---------------------------- | ----------------------- | ---------- | --------------                |
| Set Default Color            | **default_color**       | color      | #0052FE                       |
| Set Disabled Color           | **disabled_color**      | color      | #537CD3                       |  
| Set Pressed Color            | **pressed_color**       | color      | #0845D1                       |
| Set Finished Color           | **finished_color**      | color      | #27AE60                       | 
| Set Ripple Color             | **ripple_color**        | color      | @android:color/transparent    | 
| Set Text Color               | **btn_text_color**      | color      | #FFFFFF                       | 
| Set Stroke Color             | **stroke_color**        | color      | @android:color/transparent    | 

## Text Parameters

| Explanation                  | Parameter Name      | Type       | Default Value    |
| ---------------------------- | ------------------  | ---------- | ---------------- |
| Set Default Text            | **default_color**    | text       | Button           |  
| Set Finished Text           | **finish_text**      | text       | Finish           |   
| Set Text Size               | **btn_text_size**    | dimension  | 14sp             | 


## Vibration Parameters

| Explanation                  | Parameter Name      | Type       | Default Value    |
| ---------------------------- | ------------------  | ---------- | ---------------- |
| Is Vibration enabled         | **is_vibrate**      | boolean    | false            |  
| Set Vibration time in (ms)   | **finish_text**     | integer    | 30               |   


## Misc Button Parameters

| Explanation                  | Parameter Name      | Type       | Default Value    |
| ---------------------------- | ------------------  | ---------- | ---------------- |
| Set Stroke Width             | **stroke_width**    | dimension  | 0dp              |  
| Set Corner Radius            | **corner_radius**   | dimension  | 10dp             | 
| Set Button Elevation         | **btn_elevation**   | dimension  | 0dp              | 


Here is the code sample to understand on how to change the button state

```kotlin
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

    }

}
```

You can also directly integrate livedata to your button. This will automatically change the state of
button according to the livedata.

```kotlin

//This is an enum that contains all button states, use this for livedata
enum class ButtonStates {
    LOADING, ENABLED, DISABLED, FINISHED
}

//liveData variable
val buttonState = MutableLiveData<ButtonStates>()

//In Fragment or Activity
binding.progressButton.attachToLiveData(buttonState)

```

Just post the button state to the livedata like
`livedata.postValue(ButtonStates.LOADING)` and button will change to loading state automatically

## Jetpack Compose

If you are using Jetpack Compose, all you have to do it to call the ProgressButton composable

### Composable function

```kotlin
@Composable
fun ProgressButton(
    buttonState: ButtonState,
    text: String,
    completedText: String,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(4.dp),
    progressBarStrokeWidth: Dp = 3.dp,
    progressButtonColors: ProgressButtonColors = ProgressButtonColors(),
    progressButtonElevation: ProgressButtonElevation = ProgressButtonElevation(),
    textStyle: TextStyle = defaultTextStyle,
    onClick: () -> Unit
)
```

### Parameters

| Explanation  | Paramter name  | Type  | Default value |
|------|------|------|------|
| Current state of button           | buttonState  | ButtonState   | none   |
| Text shown in button              | text         | String        | none   |
| Text shown in completed State     | text         | String        | none   |
| Modifier for button               | modifier     | Modifier      | Modifier   |
| Shape of button                   | shape        | Shape         | RoundedCornerShape with 4dp radius   |
| Stroke width of the progress bar inside button          | progressBarStrokeWidth  | Dp   | 3.dp   |
| Colors used in button           | progressButtonColors  | ProgressButtonColors   | ProgressButtonColors()   |
| Elevation used in button states           | progressButtonElevation  | ProgressButtonElevation   | ProgressButtonElevation()   |
| Text Style of text inside button           | textStyle  | TextStyle   | TextStyle(color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)   |
| Lambda function to be called on button press           | onClick  | () -> Unit   | none   |

### Helper Classes

```kotlin
//This class holds data of the colors used in button
data class ProgressButtonColors(
    val backgroundColor: Color = DEFAULT_COLOR, //#0052FE
    val disabledColor: Color = DISABLED_COLOR,  //#537CD3
    val finishedColor: Color = FINISHED_COLOR,  //#27AE60
    val contentColor: Color = CONTENT_COLOR     //#FFFFFF
)

//This class holds data for the elevation used in button 
data class ProgressButtonElevation(
    val defaultElevation: Dp = DEFAULT_ELEVATION,              //8dp
    val elevationOnPress: Dp = DEFAULT_ELEVATION_ON_PRESS      //12dp
)
```

### Sample code

```kotlin
fun MainScreenContent() {
    //mutable State to holf button state
    var buttonState by remember {
        mutableStateOf(ButtonState.DISABLED)
    }
    ProgressButton(
        buttonState = buttonState,
        text = "Button",
        completedText = "Finished",
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
    }

}
```

🌟 You are all set!

## 🍰 Contribute

Feel free to fork this project, to optimise the code or to add new features.

## 📝 TODO

* Lottie support

## 🛡️ License

This project is licensed under the MIT License - see the [`LICENSE`](LICENSE) file for details.

## 🙌 Support

This project needs a 🌟 from you

<p align="center">
Developed with ❤️ in India 🇮🇳 
</p>

