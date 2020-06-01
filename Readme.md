# The Movie Database APP

Hi! This is my first project in  **TBC Advanced Android Development Course**. this application retrieves data from **The Movie Database** REST API service. user has possibility to see trending movies and tv shows, search desired one in database that has huge number of movies in cinematic industry. every movie or TV show has its own Metadata (detailed information) including trailers on **Youtube**. So, with that being said, it's very easy to search and find perfect movie to watch after user make it's acquaintance in details. Also, user has an ability to create account (**Firebase Systems** is used for now) and set private metadata and of course more features (personalized collections and favorites) will be added.  

# Lets start discussing with little details 

### Logo and introduction
<img src="https://i.imgur.com/yzAwHfm.png" width="200px">
<img src="https://i.imgur.com/py8FRRK.png" width="200px">

Splash screen is first page which last only 2.5 seconds (2500 ms). Logo is not only Minimalistic but also goes well with modern trends due to very beautiful gradient. (Its original TMD Logo)

Lets discuss activities by little chart that describes every activitiies' possible directions.

<img src="https://i.imgur.com/GcpNmZP.png" width="1000">


# Danger !!! More Technical Zone

- What and how many libraries i used ? Wellp...

      // Retrofit for http requests
      implementation 'com.squareup.retrofit2:retrofit:2.8.1'
	  implementation 'com.squareup.retrofit2:converter-gson:2.8.1'  
	  implementation 'com.squareup.retrofit2:converter-scalars:2.8.1'  
	  // Recycler View
	  implementation 'androidx.recyclerview:recyclerview:1.1.0'  
	  implementation 'androidx.cardview:cardview:1.0.0'  
	  // Glide for Images
	  implementation 'com.github.bumptech.glide:glide:4.11.0'  
	  annotationProcessor 'com.github.bumptech.glide:compiler:4.11.0'  
	  // Firebase for Authentication Systems
	  implementation 'com.google.firebase:firebase-auth:19.3.1'  
	  implementation 'com.google.firebase:firebase-database:19.3.0'  
	  implementation 'com.google.firebase:firebase-storage:19.1.1'  
	  implementation('com.github.nkzawa:socket.io-client:0.4.1') {  
	  exclude group: 'org.json', module: 'json'  
	  }  
	  // Circle Image View for ...ummm what is this for.... hmm.... 
	  implementation 'de.hdodenhof:circleimageview:3.1.0'

- What and how many components (main) i used? Wellp...
	1. ViewPager with Layouts.
		> ***Note:*** I used **ViewPager** in order to build Slider which could contain layouts with Images and Movie/TV's Metadata. I didn't use Viewpager based on **Fragments** due to simplicity and performance.
	2. Several Recycler View's
		>  I used **Recycler Views** in order to make list containers that could contain list of items for TrendingMovies, TrendingTVShows, SimilarMovies, Cast, ect... 
		***Note:*** I didnt used data binding for recycler view because of task's simplicity. 
	3. Fragments
		>***Note:*** I used Fragments only for Authorization feature. for inflating FragmentLogin and FragmentSignUp on one activity at the same time. 
	4. **FirebaseAuth** for Authorisation
	5. **FirebaseDatabase** for saving user's Metadata
	6. **FirebaseStorage** for uploading files (profile image from gallery) in server for later retrievement


# *Design Patterns, Project structure...*

## MVC or MVVM
we all know that there are some discussions about MVC versus MVVM. i used both of them, but i felt that MVC pattern was much more suitable for this tiny project.  However, i used MVVM and made some ViewModels for tasks that i felt useful. for exapmle both details page activities. i felt that MVVM was not usefull anymore.
## Project Structure Graph
> **Note**: 
> Green - Main Packages
>Yellow - Behavioral/feature-based Packages
>Blue - Functional packages/classes 

<img src="https://i.imgur.com/6iI5iQ3.png">

# Screens

<img src="https://i.imgur.com/kLjD1cW.png" width="50px">
<img src="https://i.imgur.com/YlBH6dG.png" width="50px">
<img src="https://i.imgur.com/TCgzy0n.png" width="50px">
<img src="https://i.imgur.com/2CGzuy3.png" width="50px">
<img src="https://i.imgur.com/EpInUaA.png" width="50px">
<img src="https://i.imgur.com/GwzZD3C.png" width="50px">
<img src="https://i.imgur.com/OaIZTZW.png" width="50px">


# Credits

 <img src="https://www.themoviedb.org/assets/1/v4/logos/primary-green-d70eebe18a5eb5b166d5c1ef0796715b8d1a2cbc698f96d311d62f894ae87085.svg" width="100px"> https://developers.themoviedb.org

<img src="https://avatar.cdnpk.net/23.jpg" width="100px"> https://freepik.com

