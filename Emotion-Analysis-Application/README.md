# Emotion-Analysis-Application


1 Introduction  

With the development of AI, it become more and more hot topic. And the mobile phone is necessities in peoples’ life. So I want to merge those two thing together to create some interesting things and make people to know the powerful of AI. Human emotion changes daily and everyone had different emotion at different time. So it would be very interesting, if they can know the emotion of people in a photo. Because you may smile in a photo but the underlying emotion may be different. This APP can bring a lot of fun to people, but it only is a simple applying of AI. AI would impact human society sooner or later. This would be a good chance to know the social attitude to AI. In legislative, this app is in an acceptable applying area. In business, this app is an exploration of AI business.

1.1 Application Vision  

Currently, there is few apps can use to detect emotion through the app. So I think this app should be a good chance to let people knowing about what the AI can do. This app develops for users entertainment.

1.2 Scope 

1.The app can use  mobile phones' camera to take a photo.

2.The app can also select photos from the mobile phone photo album.

3.The app can upload the selected photo to the server for emotion recognition.

4.The app can display all the emotion recognition result to your app.

5.The app can forward the result with the picture to your Facebook or WeChat(optional).

1.3 Document Version History  

This is second version of the design document.
In this version, I reorganized the APP architecture to MVP. Based on this architecture, I built MVP package for each activity. So the application structure was change massively.
Because the Azure emotion API merged to face API. This API feature had a big change. So I modified the data structure of this app. 

2 Functionality Overview 

This app mainly includes four features and one optional feature. Those main features are used to fulfil emotion recognition and display function. The optional feature realize forwarding function.

2.1 Feature Summary 

1.Camera feature, this app can take a photo by mobile camera.

2.Album feature, this app will display all of the photos in mobile phone album.

3.Emotion recognition feature, this app will upload the photo to server for photo emotion analysing.

4.Display feature, this app will display the analysing results for users.

5.Sharing feature(optional), the user can forward the result to their Facebook or WeChat(optional).
