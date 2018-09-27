# Witch-World

##### CSE 455 (Software Engineering): Group project with Aleksei Pulchritudoff and Joey Ngo.
##### Instructor: Bhrigu Celly 
##### Role: Main Developer/Programmer
##### Time Span: 10 Weeks (1 quarter)
##### Management Tool: Trello and Basecamp
##### System: Desktop (Windows & Linux), Android
##### Language: Java

![trello_sprint](https://github.com/Jaime-Cristobal/Witch-World/blob/master/trello_sprint.png)

![demo2](https://github.com/Jaime-Cristobal/Witch-World/blob/master/demogif2.gif?raw=true)

##### The objective is to gather as many watches as possible to keep the score timer above 0.
##### Once the timer is 0, the sun starts to move up and once it reaches the very top, it will result in a lost.
##### The LibGDX library in this project will handle launches through both desktop and Android. The build from 
##### https://libgdx.badlogicgames.com/ will automatically create these classes.
#####  --------------------------------------------------------------------------------------------------------------
###### Java along with the LibGDX framework library and the Box2D extension was used to develop this game. 
###### The code was compiled and tested using Android Studio.

###### Assets with the exception of music, sound effects, and menu/interactive buttons is credited to our group.

###### The design was done using the "screen" interface provided by the LibGDX library which was composed of
###### implementing the following functions: show(), render(float delta), public resize(int width, int height), pause(),
###### resume(), hide(), and dispose().

###### I followed the design of initializing an object once, and recycling and reseting the values of that object using
###### show(), which is called everytime the class implemented when the "screen" interface is accessed. 
###### ---------------------------------------------------------------------------------------------------------------
###### Some Problems:
######      -> For why the collisions are off, the contact between joints in the body should be tracked
######         instead of relying on non-sensored bodies tracked with a filter identifier.
######      -> Door movements are slightly off because the if statement is checked on precise int values.
######         Position values on the doors are float and will not meet those precise values.
######      -> Uneccesary classes need to be merged. Too much composition. Also horrible/not enough comments due to being rushed.
###### ----------------------------------------------------------------------------------------------------------------
###### Note: Music and sound effects were taken from freesounds.org
######       Buttons and font are credited to https://github.com/czyzby/gdx-skins
