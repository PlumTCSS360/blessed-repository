# Code Review Process

## Welcome Message

	Dear reviewers,
	
	Welcome to our project repositiory and review guide. We have chosen a number of classes that touch upon our most essential user stories that will be experienced by any new user to our application. The code to be reviewed is located within the classes
	
	WelcomeFrame, NewFrame, LoadFrame, Project
	
	Please feel welcome to explore any number of these classes and user stories to gain an understanding of how our application has been developed. We greatly appreciate your review and feedback. If you have any outstanding questions or concerns about our code beyond the scope of this review do not hesitate to contact us at plumpurple360@gmail.com
	
	Thank you,
	
	Team Plum

## User Stories
The code we have selected supports the following stories:

1. As a project manager, I can create a new project so that I can keep different projects organized and separate.

2. As a project manager, I can load an existing project so that I can review/edit the information inside of it.

3. As a project manager, I can import and export project folders and user_info files.


## Code Location

The code to be reviewed is located at:

	[welcomeFrame]https://github.com/PlumTCSS360/blessed-repository/blob/main/src/view/WelcomeFrame.java)
	[NewFrame](https://github.com/PlumTCSS360/blessed-repository/blob/main/src/view/NewFrame.java)

[LoadFrame](https://github.com/PlumTCSS360/blessed-repository/blob/main/src/view/LoadFrame.java)

[Project](https://github.com/PlumTCSS360/blessed-repository/blob/main/src/model/Project.java)



## Instructions for Code Review

Guidelines for reviewing our code:

1. **Error Handling** Check if exceptions are handled correctly

2. **Consistency** Check if a consistent approach has been made throughout the files

3. **Readability** Check if the code is easily understood, with meaningful names for variables and a clear purpose

4. **Testability** Check to see if the written code is easily testable.



## Outstanding Questions and Thoughts from the Team

       +Our UI uses BorderLayout to place New, Load, Export, and Import buttons in the South. I wonder if we can put these buttons a little more practical.

       +We would like to know whether our UI of WelcomeFrame, NewFrame, or LoadFrame works properly on different resolutions. 


       +Our NewFrame class will catch invalid input such as non numerical value and negative value. However, It can’t catch a positive value with more than two floating values such as 2000.1234. We wonder if it's appropriate for us to catch this wrong input. If we had to, we'd like to know how.
