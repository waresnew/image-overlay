# Image Overlay

this is a java program that lets you display an image while keeping it always on top.
it also has the option to resize, change opacity, and to enable/disable clickthrough

This only works on Windows (only tested with windows 10) and was only tested with java 8 (java 11 makes the gui and images look pixelated and blurry)


if the image looks blurry after resizing it many times then change the size multiplier back to 1 in order to get the original image that you uploaded 


![image](https://user-images.githubusercontent.com/45801973/119240832-6c953f00-bb20-11eb-9a5c-9ebb9937e1c6.png)
1. Load a layout (see 2)
2. Save a layout (saves the gui position, size, current image, current image opacity, current size multiplier, and whether it's always on top to a folder in C:\Users\Username\Appdata\Roaming\ImageOverlay)
3. Open and load an image onto the overlay
4. change opacity of the gui (pick a number from 5-100, 100 being completely opaque)
5. Set whether the gui will be on top of other windows no matter what
6. Set whether you will be able to click through the gui, meaning that you can't interact with it (useful for games where you have to move your mouse around a lot)
  => when you click this, a notification will pop up telling you that an icon has popped up in your system tray. You can right click that icon and click "disable clickthrough" to enable interaction with the gui again 
![image](https://user-images.githubusercontent.com/45801973/119240913-196fbc00-bb21-11eb-938a-5c00c538c06e.png)
7. Option to stretch image to the size of the gui
8. Multiply the size of the image (keeps aspect ratio) (accepts decimals and values below 1)
resizing the window will allow you to "crop" the image so you can tweak it until you find a setup that you're comfortable with
and then you can save the layout with the save button
