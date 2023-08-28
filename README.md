# Bambuser Live Video Shopping Player

Add a repository to your dependency resolution management:


## About

`BambuserLiveVideoShoppingPlayer` lets you add a Bambuser Live Video Shopping (LVS) Player to your app.

The LVS player can be used to watch live and recorded shows, with UI overlays that lets users interact with the show.

The LVS player can be configured to great extent and also lets you listen for player emitted events and perform player-specific functions.

`BambuserLiveVideoShoppingPlayer` supports Android 8+.

## Installation

First, add a new maven repository into your dependency resolution management:

```
repositories {
        google()
        mavenCentral()
        // other repositories you might have...
        
        maven {
            url = uri("https://repo.repsy.io/mvn/bambuser/default")
        }
    }
```
Then add the LVS Player SDK into your `app/build.gradle`:

`implementation "com.bambuser:player-sdk:${LATEST_RELEASE_VERSION}"`

## Getting started

A `LVSPlayer` can be created in the following way:

```  
LVSPlayer.startActivity(
    context = Context // A context from Android,
	showId = The ID of the show to watch,  
	configuration = Configuration(...), // Optional value to adjust the player  
	eventObserver = EventObserver(...), // Optional value to listen to events from the player  
)
```

### Configuration

Several attributes of the player can be configured by providing a `Configuration` when the `LVSPlayer` is created.
The supported properties are:

| Property                   | Type      | functionality                                                                                                                                                                        |
|----------------------------|-----------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `isEUServer`               | Boolean   | Define if the show that is to be viewed is stored on a EU server or not                                                                                                              |
| `showHighlightedProducts`  | Boolean   | Toggle the visibility of products during a show                                                                                                                                      |
| `showChat`                 | Boolean   | Toggle the visibility of the chat during a show                                                                                                                                      |
| `showProductsOnEndCurtain` | Boolean   | Toggle the visibility of products on the end of show curtain                                                                                                                         |
| `enablePictureInPicture`   | Boolean   | Allow the player to be opened in Picture in Picture (PiP) mode                                                                                                                       |
| `enableProductDetailsPage` | Boolean   | Allow a Product Details Page (PDP) to be opened when a product highlight is clicked <br> If disabled an OnProductHighlightClicked-Event will still be sent when a product is clicked |
| `showShoppingCart`         | Boolean   | Toggle the visibility of a icon that opens the current shopping cart                                                                                                                 |
| `showLikes`                | Boolean   | Toggle the feature to send and view likes during a show                                                                                                                              |

### Events
If an `EventObserver` is provided when the `LVSPlayer` is created the following events can be observed:

| Event                             | Emitted when                                                                                                                           | Parameters                                                                                                                                          |
|-----------------------------------|----------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------|
| `OnPlay`                          | The show starts playing                                                                                                                | -                                                                                                                                                   |
| `OnPaused`                        | The show is paused by the viewer                                                                                                       | -                                                                                                                                                   |
| `OnSeek`                          | The viewer seeks during a show                                                                                                         | -                                                                                                                                                   |
| `OnProductHighlightClicked`       | A product is clicked                                                                                                                   | `productId: String` - The id of the product that was clicked                                                                                        |
| `OnSeekToProductHighlightClicked` | The seek button on a product card is clicked                                                                                           | `productId: String` - The id of the product that was clicked                                                                                        |
| `OnAddToCalendar`                 | The add to calendar button on the pre-live curtain is clicked, if the show has a scheduled start time                                  | `date: Long` - The shows scheduled start time <br> `title: Sting` - The title of the show <br> `description: String?` - The description of the show |
| `OnShare`                         | The share button is clicked                                                                                                            | -                                                                                                                                                   |
| `OnReplay`                        | The replay button on the ended-show curtain is clicked                                                                                 | -                                                                                                                                                   |
| `OnCartClicked`                   | The cart button is clicked                                                                                                             | -                                                                                                                                                   |
| `OnMessageSent`                   | A chat message is sent through the player                                                                                              | -                                                                                                                                                   |
| `OnTermsAndConditionsLinkClicked` | The user clicks on the link to view the terms and conditions for the chat                                                              | `link: String` - The link that was clicked                                                                                                          |
| `OnChatMessageLinkClicked`        | The user clicks on a link in a chat message                                                                                            | `link: String` - The link that was clicked                                                                                                          |
| `OnSendLike`                      | The user sends a like using the heart-button, multiple likes within a short time span will be lumped together into the same event      | `count: Int` - The amount of likes that was sent                                                                                                    |
| `OnMessageReceived`               | A new message was received from the backend                                                                                            | -                                                                                                                                                   |
| `OnMinimizeClicked`               | The user clicks on the minimize button, this will make the player enter PiP mode if it is enabled, otherwise the player will be closed | -                                                                                                                                                   |
| `PDPEvent.OnOpenClicked`          | The user clicks on the open button inside of the light PDP                                                                             | `productId: String` - The id of the product to be opened                                                                                            |
| `OnError`                         | The player encounters an error                                                                                                         | `lvsPlayerError: LVSPlayerError` - The error that occurred                                                                                          |
| `OnClose`                         | The player was closed                                                                                                                  | -                                                                                                                                                   |

> At the moment `onError` will cause the `LVSPlayer` to close down, this behavior will be improved in a future release. 


## Demo app

The `app` folder contains a demo app which can be used as an example on how to setup and configure a `LVSPlayer`.
