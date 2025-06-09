# 8-Queens Puzzle Game

## Overview:
This is a simple app where a user can play a so call 8-Queens Puzzle Game. The goal of the game is to place N queens on the board of size N such that no two queens attach one another.

## Features:
The app provides options to pick a board size from 4 to 8 but if necessary is very easily extensible to higher board sizes as long as the screen is large enough to render them.
The then starts a game and can place Queens on the board or remove them. Every queen placement is validated and if there's a conflicting Queen already on the board the user is visually shown the conflict and the Queen isn't placed on the board.
After finding one of the solutions to the game, the user is presented with a congratulations screen acompanied by confetti animation.
The whole app utilizes some easy to implement Compose animations to make it look a bit more alive.
There's a support for both portrait and landscape orientation although that would probably need a bit more tweaking to be working well with all screen sizes and also split screen.

## Build:
After cloning the repository, there's no particular setup necessary besides Gradle sync. After that the app can be run using a command:
```
./gradlew assembleDebug
./gradlew installDebug
```

and all the unit tests can be run:
```
./gradlew testDebugUnitTest
```

## 3rd Party Dependencies
#### Production code
- [koin](https://github.com/InsertKoinIO/koin) - dependency injection framework
- [konfetti](https://github.com/DanielMartinus/Konfetti) - lightweight library for the confetti animation

#### Testing
- [mockk](https://mockk.io/) - library to mock dependencies for testing
- [truth](https://github.com/google/truth) - library that provides more readable 
- [turbine](https://github.com/cashapp/turbine) - library for much easier and thorough testing of flows
- [junitParams](https://github.com/Pragmatists/JUnitParams) - library for testing the same logic on different set of parameters - very useful for testing different board sizes or inputs

## Architecture:
The app is split into modules for separation of concerns. Each module exposes only the minimal necessary usually behind interfaces especially the `game.domain` module
- `:app` - contains just the basic glue for the whole app with `koin` setup and `MainActivity` and `MainApplication` and the core for navigation
- `:game:domain` - contains the whole business logic of how the board is calculated and then inputs verified
- `:game:presentation` - contains the UI and VM logic for the game-play
- `:navigation` - contains just a very simple abstraction of Navigation that is not quite showing its benefits as the app only has two screens but I liked the approach described in a [Jetpack Compose Advanced Navigation in a Multi Modules Project](https://proandroiddev.com/exploring-jetpack-compose-advanced-navigation-in-a-multi-modules-project-30dc91e02292) Medium article so I tried to give it a go.
- `:ui` - holds the `Theme`, `Colors` and `typography` definition along some reusable Composable components.

## Testing:
All of the business logic in all modules is fully test covered - especially the domain and presentation logic. There can definitely be more various test cases but the current test coverage gives me sufficient confidence that the logic works as expected.
