# Image Overlay

my first java project

this is a java program that lets you overlay an image/video above other windows
it also has the option to resize, change opacity, save/load layouts, and to enable/disable clickthrough

This was only tested on Windows 10 on Java 8 (java 11 makes the gui and images look pixelated and blurry)


if the image looks blurry after resizing it many times then change the size multiplier back to 1 in order to get the original image that you uploaded 
the video overlay only supports youtube 
url should be in this format: https://www.youtube.com/watch?v=IDGOESHERE

![image](https://user-images.githubusercontent.com/45801973/135698289-1c093fa6-af6a-478f-8a90-a91491f98d8b.png)

1. load/save layouts (layouts are stored in a .ser format to %appdata%\ImageOverlay
2. open an image/video depending on the current mode (see number 7)
3. change opacity of window
4. force overlay to be shown above other windows
5. makes the overlay window "unclickable" and sends the cancelled clicks to teh background window
6. stretches the image size to the window size
7. toggles between image and video mode
8. multiplier that respects aspect ratio (change to 1 to restore original image + quality)
