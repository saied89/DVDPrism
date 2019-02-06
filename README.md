<p>
  <img src="https://github.com/saied89/DVDPrism/blob/master/logo.svg" width="200" height="200"/>
</p>

# DVD Prism

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/fee81b90074b40cc9b7dfda01b09fbb7)](https://app.codacy.com/app/saied89/DVDPrism?utm_source=github.com&utm_medium=referral&utm_content=saied89/DVDPrism&utm_campaign=Badge_Grade_Settings)
[![Build Status](https://travis-ci.com/saied89/DVDPrism.svg?branch=master)](https://travis-ci.com/saied89/DVDPrism)
[![codecov](https://codecov.io/gh/saied89/DVDPrism/branch/master/graph/badge.svg)](https://codecov.io/gh/saied89/DVDPrism) 
[![codebeat badge](https://codebeat.co/badges/3e3e2365-f99c-414a-89ee-1efae89fa841)](https://codebeat.co/projects/github-com-saied89-dvdprism-master)

If you prefer to watch movies in your home, you'll have to ignore the hype around the newest theatrical releases and follow the DVD releases instead. If so this application is for you.

This project will helps users keep track of all DVD releases. It consists of an android app and a Ktor backend. The backend will pull data from sources, merge them and deliver data to the application through a REST API. The app will present the data to the user in a stylish and modern manner while also having some side functions.


I strive for high quality and highly tested code in this project. I made this project to practice and apply the latest technologies, tools, etc that emerge in the Androidosphere away from constraints of professional projects while also dipping my toes in the backend developement, DevOps and Vector graphics. I want to investigate if all these cool new technologies play well together, What are the challenging bits and what workarounds are available.

There are several broad goals in this project of which some are achieved, others are close to compeletion while others will be persued in phase 2 after the release of the application.

### Transition APIs and the new MotionLayout
#### Transition

![transition](https://github.com/saied89/DVDPrism/blob/master/gifs/transition.gif?raw=true)

#### MotionLayout
The new MotionLayout API enables View components to transition smoothly between two states. The effort required in rather minimal and is only marginally harder than an making two seperate layout files.

![motionLayout](https://github.com/saied89/DVDPrism/blob/master/gifs/motionLayout.gif?raw=true)
### Using all of Architectural components
This app makes extensive use of Android Architectural Components with data flow of the application consisting of Room, Paging, LiveData and Lifecycle. I have made the the ground works for integrating the Navigation component by using an empty MainActivity which which be done in a later date.
### Coroutines & networking with Ktor Client
There is strong emphasis on maximum usage of Kotlin in this project. Throughout this project Async operations are handled by coroutines and there is no RxJava dependency. The backend is written using Ktor Server and networking in app is handled with Ktor Client.
### Code sharing between app and backend through a common module
The data models and some basic utils are shared via a common module.
### Completely tested app with good code coverage and integration of CI/CD




