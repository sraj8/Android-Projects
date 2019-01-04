# Broadcast receiver and Permissions Implementation
Developed three android applications as part of mobile development course (CS-478) at University of Illinois at Chicago

### 1. Application A1
App A1 sends one of two kinds of broadcasts. The other two applications will receive the broadcasts; however, these applications will receive the broadcasts only if the sender (i.e. A1) has that permission. App A1 defines an activity containing two read-only text views and two buttons. The buttons, when selected, will broadcast two different intents with actions concerning points of interest in the cities of San Francisco, CA and New York, NY, depending on the button pressed. The text views describe the meaning of the buttons to the device user. Both broadcasts
are ordered broadcasts.

### 2. Application A2 
App A2 defines two broadcast receivers programmatically, one for each of the two broadcasts by A1. Whenever a broadcast intent is received, A2 displays a toast message on the device’s display. The toast message indicates whether the broadcast sender was selecting San Francisco or New York. However, A2’s broadcast receiver is designed in such a way that it will only respond to a broadcast if the broadcast sender has permission.

### 3. Application A3 
App A3 also receives A1’s broadcasts if the sender has permission. Depending on the intent received, A3 will launch one of two activities. The first activity displays information about points of interest in San Francisco. The second activity should just display a toast
message indicating the New York information is under construction.
