# Why Not Compose!

A collection of animations, compositions, UIs using Jetpack Compose. You can say Jetpack Compose
cookbook or play-ground if you want!

Feel free to request features or suggestions for improvements.

## Notable Features & Libraries

- MVI Pattern
- Navigation Component
- Hilt
- Everywhere dark mode support
- A lot of Ready to use compositions
- Gradle Kotlin DSL

## Screenshots

![Preview](images/animated-text-and-bubbles.gif)

### Animations

| ![Preview](images/animated-text-and-bubbles.png) | ![Preview](images/emudi.png) | ![Preview](images/running-car.png) |
| :------: | :------: | :------: |
| The Story |  |  |

### Compositions

| ![Preview](images/app-bar.png) | ![Preview](images/button.png) | ![Preview](images/card.png) |
| :------: | :------: | :------: |
| ![Preview](images/check-box.png) | ![Preview](images/drop-down.png) | ![Preview](images/grid.png) |
| ![Preview](images/list-item.png) | ![Preview](images/lists.png) | ![Preview](images/loading-indicator.png) |
| ![Preview](images/radio-button.png) | ![Preview](images/scaffold.png) | ![Preview](images/snackbar.png) |
| ![Preview](images/switch.png) | ![Preview](images/text-fields.png) | SwipeRefresh |
| SwipeToDismiss |  |  |

### UIs

| ![Preview](images/map-view.png) | ![Preview](images/otp-code-verify.png) | ![Preview](images/web-view.png) |
| :------: | :------: | :------: |

### Tutorials

- Counter (Beginner)
- Counter with ViewModel (Beginner)
- AnimatedVisibility (Beginner)

# TODO

- [x] Gradle Kotlin DSL
- [x] Spotless
- [x] Composition: SwipeToDismiss
- [x] UI: WebView: Swipe to Refresh in WebView
- [x] Tutorial: Counter
- [x] Tutorial: Counter with ViewModel
- [x] Tutorial: AnimatedVisibility
- [ ] Tutorial: Select image for upload
- [ ] Tutorial: Permission
- [ ] Tutorial: ExoPlayer sample
- [ ] Tutorial: Data fetch and LazyColumn
- [ ] Tutorial: Navigation
- [ ] Tutorial: Navigation with Data pass
- [ ] Tutorial: Material You

# Note

- For dependency version check I am currently
  using [Gradle Versions Plugin](https://github.com/ben-manes/gradle-versions-plugin#using-a-gradle-init-script)
  . I added this in the Gradle init script and can check versions using the following commend.

```bash
./gradlew dependencyUpdates
```

- The project using [spotless](https://github.com/diffplug/spotless/tree/main/plugin-gradle)
  with [klint](https://github.com/pinterest/ktlint). Apply spotless using the following command.

```bash
./gradlew spotlessApply
```

## Setup

### Map API Key

Open the `local.properties` in your project level directory, and then add the following code.
Replace `YOUR_API_KEY` with your API key.

```groovy
MAPS_API_KEY=YOUR_API_KEY
```

## Other Interesting Repos

- [Gurupreet/ComposeCookBook](https://github.com/Gurupreet/ComposeCookBook)
- [spencergriffin/exoplayer-compose](https://github.com/spencergriffin/exoplayer-compose)
- [godaddy/compose-color-picker](https://github.com/godaddy/compose-color-picker)

## Credits

- [Android official compose samples](https://cs.android.com/androidx/platform/tools/dokka-devsite-plugin/+/master:testData/compose/samples/)
- [Iconly icon](https://freebiesbug.com/figma-freebies/iconly/)
- [Loripsum.net](https://loripsum.net)

## Licence

- **Emudi** is a trademark of [Softzino Technologies](https://softzino.com/).

```
Copyright 2021 Md. Mahmudul Hasan Shohag

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```