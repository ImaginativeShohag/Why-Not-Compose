# Why Not Compose!

A collection of animations, compositions, UIs using Jetpack Compose. You can say Jetpack Compose
cookbook or play-ground if you want!

Feel free to request features or suggestions for improvements.

[![Developer](https://img.shields.io/badge/Maintainer-ImaginativeShohag-green)](https://github.com/ImaginativeShohag)
[![Buy Me A Coffee](https://img.shields.io/badge/-buy_me_a_coffee-gray?logo=buy-me-a-coffee)](https://www.buymeacoffee.com/ImShohag)
[![GitHub release](https://img.shields.io/github/release/ImaginativeShohag/Why-Not-Compose.svg)](https://github.com/ImaginativeShohag/Why-Not-Compose/releases)
[![Google Play](https://img.shields.io/badge/GET%20IT%20ON-Google%20Play-brightgreen)](https://play.google.com/store/apps/details?id=org.imaginativeworld.whynotcompose)
![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat)
[![ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)](https://ktlint.github.io/)

## Notable Features & Libraries

- MVI Pattern
- Navigation Component
- Hilt
- Everywhere dark mode support
- A lot of Ready to use compositions
- Material 3
- Gradle Kotlin DSL
- CI/CD
    - ktlint
    - CodeQL
    - Publish to Google Play
- Material 3 examples
- Animated Splash Screen (Introduced in Android 12)

[<img src="https://play.google.com/intl/en_us/badges/static/images/badges/en_badge_web_generic.png" style="width:256px">](https://play.google.com/store/apps/details?id=org.imaginativeworld.whynotcompose)

## Screenshots

### Animations

| <img src="images/animated-text-and-bubbles.gif" style="width:320px"> | <img src="images/the-story.gif" style="width:320px"> | <img src="images/running-car.gif" style="width:320px"> |
|:--------------------------------------------------------------------:|:----------------------------------------------------:|:------------------------------------------------------:|
|           <img src="images/emudi.gif" style="width:320px">           |                                                      |                                                        |

### Compositions

|     ![Preview](images/app-bar.png)      |   ![Preview](images/button.png)    |       ![Preview](images/card.png)        |
|:---------------------------------------:|:----------------------------------:|:----------------------------------------:|
|    ![Preview](images/check-box.png)     |  ![Preview](images/drop-down.png)  |       ![Preview](images/grid.png)        |
|    ![Preview](images/list-item.png)     |    ![Preview](images/lists.png)    | ![Preview](images/loading-indicator.png) |
|   ![Preview](images/radio-button.png)   |  ![Preview](images/scaffold.png)   |     ![Preview](images/snackbar.png)      |
|      ![Preview](images/switch.png)      | ![Preview](images/text-fields.png) |   ![Preview](images/swipe-refresh.gif)   |
| ![Preview](images/swipe-to-dismiss.gif) |                                    |                                          |

### UIs

| ![Preview](images/map-view.png) | ![Preview](images/otp-code-verify.png) | ![Preview](images/web-view.png) |
|:-------------------------------:|:--------------------------------------:|:-------------------------------:|

### Tutorials

- Counter (Beginner)
- Counter with ViewModel (Beginner)
- AnimatedVisibility (Beginner)
- Lottie (Beginner)
- Select image and crop for upload (Intermediate)
- Capture image and crop for upload (Intermediate)
- Permission (Beginner)
- Data Fetch and Paging (Advanced)
- Tic-Tac-Toe (Advanced)
- OneSignal and Broadcast (Intermediate)
- ExoPlayer (Advanced)
- CMS (Advanced)
  - Memory and storage caching
- [Deep Link](https://developer.android.com/training/app-links) (Intermediate)
- Navigation Data Pass (Intermediate)
- Reactive Model (Beginner)

|    ![Counter](images/counter.gif)    | ![Animated Visibility](images/animated-visibility.gif) | ![Lottie](images/lottie.gif) |
|:------------------------------------:|:------------------------------------------------------:|:----------------------------:|
| ![Exo Player](images/exo-player.gif) |         ![Tic-Tac-Toe](images/tic-tac-toe.gif)         |    ![CMS](images/cms.gif)    |
| ![Deep Links](images/deep-links.gif) |                                                        |                              |

# TODO

- Migrate to Material 3
    - [x] CMS
    - [x] Full app
- [ ] Add accompanist WebView
- [ ] x, y, z translation simulation
- [ ] Shadow manipulation with device gyroscope
- [ ] Composition: Bottom Sheet
- [ ] Fix custom `LazyGridScope.items`
- [ ] List with `LazyGrid`
- [ ] Paging with `LazyGrid`
- [ ] Update to new storage permission
- [x] Auto Theme mode from system
- [ ] Create color ready for dark and light mode
- [ ] AlarmManager example
- [ ] Update `CheckBox` code (see example from AOSP)
- [ ] Month-Picker component
- [ ] Upgrade OneSignal lib
- [x] Check map example issue: on click freeze
- [ ] New: File browser using MediaStore
- [ ] Get sidebar from Emudi app (https://www.youtube.com/watch?v=HNSKJIQtb4c)
- [x] Update `popBackStack()`
- [ ] Migrate ExoPlayer: https://developer.android.com/media/media3/exoplayer/migration-guide

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

### Go REST API Key

Open the `local.properties` in your project level directory, and then add the following code.
Replace `YOUR_API_KEY` with your [Go REST](https://gorest.co.in) API key.

```groovy
CMS_API_KEY=YOUR_API_KEY
```

## Other Interesting Repos

- [Compose Material Catalog](https://github.com/androidx/androidx/tree/androidx-main/compose/integration-tests/material-catalog)
- [Official Compose Samples](https://github.com/android/compose-samples)
- [Now in Android App](https://github.com/android/nowinandroid)
- [ChrisBanes/Tivi](https://github.com/chrisbanes/tivi)
- [Gurupreet/ComposeCookBook](https://github.com/Gurupreet/ComposeCookBook)
- [spencergriffin/exoplayer-compose](https://github.com/spencergriffin/exoplayer-compose)
- [godaddy/compose-color-picker](https://github.com/godaddy/compose-color-picker)
- [Jetpack Compose Awesome](https://github.com/jetpack-compose/jetpack-compose-awesome)

## Credits

- [Android official compose samples](https://cs.android.com/androidx/platform/tools/dokka-devsite-plugin/+/master:testData/compose/samples/)
- [Iconly icon](https://freebiesbug.com/figma-freebies/iconly/)

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