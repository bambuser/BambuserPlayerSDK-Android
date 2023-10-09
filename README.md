<div>
  <br/><br />
  <p align="center">
    <a href="https://bambuser.com" target="_blank" align="center">
        <img src="https://brand.bambuser.net/current/logo/bambuser-black-512.png" width="280">
    </a>
  </p>
  <br/><br />
</div>

# Bambuser Live Video Shopping Player

## About

This project contains a sample Android application showcasing the usage of Bambuser's Player SDK.

It can be significantly configured and lets you listen for player-emitted events and perform player-specific functions.

## Requirements
This library targets Android 34, and the minimal support API is 26 (Android 8+)

## Setup
First, add a new maven repository to your dependency resolution management:

```
repositories {
        google()
        mavenCentral()
        // other repositories you might have...
        
        maven {
            url "https://repo.repsy.io/mvn/bambuser/default"
        }
    }
```
Then add the dependency into your `app/build.gradle`:

```
implementation "com.bambuser:player-sdk:(insert the latest version)"
```

## Getting started

An LVS player is a dedicated Android Activity that can be initialized in the following way:

```  
LVSPlayer.startActivity(
    context = Your Android context,
    showId = The ID of the show to watch,  
    configuration = Configuration(...), // Configuration that enable/disable features and UI elements in the player
    eventObserver = EventObserver(...), // Optional interface that receives the player events   
)
```

### Configuration

The player's attributes can be configured by providing a `Configuration` when the `LVSPlayer` is created.
The supported properties are:

| Property                   | Type      | Functionality                                                                                                                                                                         |
|----------------------------|-----------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `isEUServer`               | Boolean   | Define if the show that is to be viewed is stored on an EU server or not                                                                                                              |
| `showHighlightedProducts`  | Boolean   | Toggle the visibility of products during a show                                                                                                                                       |
| `showChat`                 | Boolean   | Toggle the visibility of the chat during a show                                                                                                                                       |
| `showProductsOnEndCurtain` | Boolean   | Toggle the visibility of products on the end of the show curtain                                                                                                                      |
| `enablePictureInPicture`   | Boolean   | Allow the player to be opened in Picture in Picture (PiP) mode                                                                                                                        |
| `enableProductDetailsPage` | Boolean   | Allow a Product Details Page (PDP) to be opened when a product highlight is clicked <br> If disabled, an OnProductHighlightClicked-Event will still be sent when a product is clicked |
| `showShoppingCart`         | Boolean   | Toggle the visibility of an icon that opens the current shopping cart                                                                                                                 |
| `showLikes`                | Boolean   | Toggle the feature to send and view likes during a show                                                                                                                               |

### Events
If an `EventObserver` is provided when the `LVSPlayer` is created, the following events can be observed:

| Event                             | Emitted when                                                                                                                            | Parameters                                                                                                                                          |
|-----------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------|
| `OnPlay`                          | The show starts playing                                                                                                                 | -                                                                                                                                                   |
| `OnPaused`                        | The show is paused by the viewer                                                                                                        | -                                                                                                                                                   |
| `OnSeek`                          | The viewer seeks during a show                                                                                                          | -                                                                                                                                                   |
| `OnProductHighlightClicked`       | A product is clicked                                                                                                                    | `productId: String` - The id of the product that was clicked                                                                                        |
| `OnSeekToProductHighlightClicked` | The seek button on a product card is clicked                                                                                            | `productId: String` - The id of the product that was clicked                                                                                        |
| `OnAddToCalendar`                 | The add to calendar button on the pre-live curtain is clicked if the show has a scheduled start time                                    | `date: Long` - The shows scheduled start time <br> `title: Sting` - The title of the show <br> `description: String?` - The description of the show |
| `OnShare`                         | The share button is clicked                                                                                                             | -                                                                                                                                                   |
| `OnReplay`                        | The replay button on the ended-show curtain is clicked                                                                                  | -                                                                                                                                                   |
| `OnCartClicked`                   | The cart button is clicked                                                                                                              | -                                                                                                                                                   |
| `OnMessageSent`                   | A chat message is sent through the player                                                                                               | -                                                                                                                                                   |
| `OnTermsAndConditionsLinkClicked` | The user clicks on the link to view the terms and conditions for the chat                                                               | `link: String` - The link that was clicked                                                                                                          |
| `OnChatMessageLinkClicked`        | The user clicks on a link in a chat message                                                                                             | `link: String` - The link that was clicked                                                                                                          |
| `OnSendLike`                      | The user sends a like using the heart button, multiple likes within a short time span will be lumped together into the same event       | `count: Int` - The amount of likes that were sent                                                                                                   |
| `OnMessageReceived`               | A new message was received from the backend                                                                                             | -                                                                                                                                                   |
| `OnMinimizeClicked`               | The user clicks on the minimize button. This will make the player enter PiP mode if it is enabled. Otherwise, the player will be closed | -                                                                                                                                                   |
| `PDPEvent.OnOpenClicked`          | The user clicks on the open button inside of the light PDP                                                                              | `productId: String` - The id of the product to be opened                                                                                            |
| `OnError`                         | The player encounters an error                                                                                                          | `lvsPlayerError: LVSPlayerError` - The error that occurred                                                                                          |
| `OnClose`                         | The player was closed                                                                                                                   | -                                                                                                                                                   |

> Currently, whenever an `OnError` event is emitted, the `LVSPlayer` activity is destroyed. This behavior is likely going to change in future releases. 


## Work Manager
You might find issues when using custom worker factories in your Android application after initializing the `LVSPlayer`. If that is your case, merge your factories with our `LVSWorkerFactory` with the help of the `DelegatingWorkerFactory`. See the example below:

```
val factory = DelegatingWorkerFactory()
	.apply {
            addFactory(yourCustomFactory) // Add your factories
            addFactory(LVSWorkerFactory()) // This is our own worker factory
        }

val conf = Configuration.Builder()
	.setWorkerFactory(factory)
	// Other configurations you might have...
        .build()

WorkManager.initialize(yourContext, conf)
```

## Demo app

The `app` folder contains a demo app, which can be used as an example of how to set up and configure an `LVSPlayer`.
