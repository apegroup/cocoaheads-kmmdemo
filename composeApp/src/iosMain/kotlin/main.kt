import androidx.compose.ui.window.ComposeUIViewController // ktlint-disable filename
import com.umain.cocoaheads.App
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    return ComposeUIViewController { App() }
}
