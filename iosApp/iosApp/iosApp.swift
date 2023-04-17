import UIKit
import SwiftUI
import ComposeApp

@main
struct iosApp: App {

    init() {
        KoinKt.doInitKoin()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}

struct ContentView: View {
    @ObservedViewModel var viewModel: MainViewModel
    var body: some View {
        ComposeView().ignoresSafeArea(.keyboard)
    }.onAppear {
     viewModel.observeViewModel()
    }
}

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}
