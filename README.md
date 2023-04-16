<span style="font-size:24px">Clothing Suggester App</span>

The Clothing Suggester App is a personal clothing helper app that suggests suitable clothing to wear based on the current weather in your city. It uses an API to fetch the weather data and displays clothing suggestions based on predefined temperature ranges. The app also keeps track of the clothes you have worn in the past few days to avoid repeating outfits.

Features
Fetches current weather data from an API using OkHttp library.
Allows users to enter and manage a set of images of the clothes they own.
Suggests appropriate clothing based on the current weather temperature and the clothes you have worn in the past few days.
Supports different weather conditions (e.g., sunny, warm, cold) with respective clothing suggestions.
Allows users to pick clothes from their gallery using Lottie animation and MotionLayout for smooth transitions.
Uses SharedPreferences to keep track of the clothes worn in the past few days.

![image_1](https://user-images.githubusercontent.com/87489620/232333526-5df06d8b-3d2b-4654-8e1e-a0bb5e8f4131.png)

![image_2](https://user-images.githubusercontent.com/87489620/232333530-d64ab0d4-dbdf-4ab1-be14-c43370eb76a1.png)

![image_3](https://user-images.githubusercontent.com/87489620/232333532-6a440142-6d53-4216-a3aa-200198c65324.png)

Usage
Clone the repository to your local machine.
Import the project into Android Studio.
Build and run the app on an emulator or physical device.
Allow the app to access location and internet permissions.
The app will fetch the current weather data and display the suggested clothing based on the temperature and your previous clothing choices.
Users can manage their clothes by adding, editing, or deleting images in the app using Lottie animation and MotionLayout for smooth transitions.
Users can also pick clothes from their gallery by tapping the gallery button with smooth transitions using Lottie animation and MotionLayout.
The app will keep track of the clothes you have worn in the past few days and suggest appropriate clothing to avoid repeating outfits.
Libraries Used
OkHttp: For making API calls and handling network requests.
SharedPreferences: For storing and retrieving the clothes worn in the past few days.
Glide: For loading and displaying images from URLs and gallery.
Lottie: For adding animations in the app.
MotionLayout: For creating smooth transitions and animations between different layout states.
Material Design components: For UI elements such as buttons, cards, etc.
License
This project is licensed under the MIT License.

Contributing
Contributions are welcome! Please feel free to submit any issues or pull requests.

Acknowledgements
OpenWeatherMap API for providing weather data.
OkHttp for handling network requests.
Glide for loading and displaying images.
Lottie for adding animations in the app.
MotionLayout for creating smooth transitions and animations.
Icons and images used in the app are for demonstration purposes only and are not for commercial use.
Author
Mohamed Elgohary

Contact
mohamed.abdelazim.elgohary@gmail.com

That's it! You can customize the README file to suit your specific app's requirements and provide detailed instructions on how to build, run, and use the application. Don't forget to include appropriate credits and acknowledgements for any third-party libraries or resources used in your application.
