# Image Overlay

My first Java project!

This is a Java program that lets you overlay an image/video above other windows. I mainly made this to easily reference guides/maps while playing video games.

It also has the option to resize, change opacity, save/load layouts, and to enable/disable clickthrough.

## Troubleshooting

- This was only tested on Windows 10 on Java 8; Java 11 may make the picture look pixelated due its DPI scaling

- If the image looks blurry after resizing it many times then change the size multiplier back to 1 in order to get the original image that you uploaded 

the video overlay only supports youtube 
url should be in this format: https://www.youtube.com/watch?v=IDGOESHERE

![image](https://user-images.githubusercontent.com/45801973/135698289-1c093fa6-af6a-478f-8a90-a91491f98d8b.png)

1. Load/save layouts (layouts are stored in a .ser format to %appdata%\ImageOverlay
2. Open an image/video depending on the current mode (see number 7)
3. Change opacity of window
4. Force overlay to be shown above other windows
5. Makes the overlay window "unclickable" and sends the cancelled clicks to teh background window
6. Stretches the image size to the window size
7. Toggles between image and video mode
8. Multiplier that respects aspect ratio (change to 1 to restore original image + quality)
