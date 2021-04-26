# Step to reproduce

This project is actually the example folder from this plugin: 

assets_audio_player: ^3.0.3+2

I just took it and modify a screen to compare two progress Bar.

- So go to the folder 'Example'
- launch this specific file: 'lib/main_multiples.dart'
- You will see a screen with multiple audios having their own progressBar
- See at the line 210, a column gathering stacking both progress bar:

It compares two progress Bar:
- the Neuromorphic Slider from this plugin :
- your own progressBar in Orange color.

You check on the label Duration, you will immediztely notice the issue: they are stuck on the same duration everytime, which is here 01:24


<img src="https://user-images.githubusercontent.com/5865600/116122550-3a7bf300-a6c2-11eb-979e-c8b6c9c4b6e3.png" width="400">

Im running the project on Android 30 and 29.
