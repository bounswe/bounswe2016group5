![](http://asmarterplanet.com/mobile-enterprise/files/mobile-application-project-management.png)

#Summary 
This project is an exploratory learning platform which gives the users an opportunity for interactive learning. There are no boring explanations, parrot-fashion definitions as commonplaces but a place that people can exalt and share their wisdom. 


#Background 
The aim of this document is explaining the requirements of ‘An exploratory learning platform’ with glossary part, detailed explanations of requirements and references part. There are two parts of ‘Requirements’ section; ‘Functional Requirements’ and ‘Non-Functional Requirements’.
 
Glossary part consists of keywords that are used very much in ‘Requirements’ part and their explanations.  This section is totally for giving prior knowledge about our project.

Requirements part is divided two main parts. The part of ‘Functional requirements’ covers behavior of system and services the system provides. Basically, there will be topics includes applications  features for users. _There are four subtopics in this part: User Requirements, Topic Requirements, Search Requirements and Dashboard Requirements_. The part of ‘Non-Functional requirements’ consists of information about system properties, constraints and process requirements. In other words, this part will be technical perspective of the project requirements.   

![](http://agafonovslava.com/image.axd?picture=%2F2013%2F11%2Frequirements.jpg)

#Requirements 

##1. Functional Requirements 
  * **1.1 User Requirements**
    * **1.1.1 Unregistered Users**
      * 1.1.1.1 Unregistered users shall register to the system by filling registration form to become a registered user. 
        * 1.1.1.1.1 Registration form contains mandatory fields which are username, password, email address and non mandatory fields which are real name, occupation, location and time zone selection.
      * 1.1.1.2 Unregistered users shall be able to only view topics and all comments except topic materials.
      * 1.1.1.3 Unregistered users shall be able to view user profiles if user allows.
    * **1.1.2 Registered Users**
      * 1.1.2.1 Registered users shall login to the system via their registered email address/username and password.
      *	1.1.2.2 Registered users shall have a profile page.
      *	1.1.2.3 Registered users shall be able to edit their profile page.
      *	1.1.2.5 Registered users shall be able to reset their password.
      * 1.1.2.5.1 System shall send the new password to the user's e-mail address.
      * 1.1.2.3	Registered users shall be able to open a topic.
      *	1.1.2.5 Registered users shall be able to follow other registered users' channels.
      *	1.1.2.5 Registered users shall be able to rate topics.

  * **1.2 System Requirements**
    * **1.2.1 Topic Requirements**
      * **1.2.1.1 Starting Topic Requirements**
        * 1.2.1.1.1 Topics shall have at least 1, at most 10 semantic tags.
        * 1.2.1.1.2 Topics shall have a header and body.

      * **1.2.1.2 Topic Home Page Requirements**
        * 1.2.1.2.1 Topic page shall display the topic's header, body, topic image, owner name and image.
        * 1.2.1.2.2 Home page shall include different 3 different tab pages which are comments, materials and quizzes.
        * 1.2.1.2.3 Home page shall include "Report Topic" button.

      * **1.2.1.3 Comment Requirements**
        * 1.2.1.3.1 Comments shall have at least 1 at most 10000 characters.
        * 1.2.1.3.2 Comments which have quotations shall include reference of that quotation.
        * 1.2.1.3.3 Users shall be able to tag another users in comments.
        * 1.2.1.3.4 Comments should be tagged as "instructive" or "helpful" by topic owner, in that case these comments are displayed in a different way.
        * 1.2.1.3.5 Comments shall be located in Comments tab page.

      * **1.2.1.4 Rating Requirements**
         * 1.2.1.4.1 Rating system shall have only up-vote and down-vote choices.
         * 1.2.1.4.2 Each registered user shall be able to rate a topic at most once.
         * 1.2.1.4.3.1 Topics which have negative rate, shall show itself with "-1" mark.
         * 1.2.1.4.3.1 Topics which have positive rate, shall show itself with net rate.
         * 1.2.1.4.3 Users shall be able rate comments.
         * 1.2.1.4.3.1 Comments which have negative rate, shall show itself with "-1" mark.
         * 1.2.1.4.3.1 Comments which have positive rate, shall show itself with net rate.

      * **1.2.1.5 Edit Requirements**
        * 1.2.1.5.1 Users shall be able to edit comments, in which case comment gets label "edited - (date)".

      * **1.2.1.6 Privilege Requirements** 
        * 1.2.1.6.0 When a registered user opens a topic system gives this user privileges.
        * 1.2.1.6.1 The privileges shall only valid for this opened topic.
        * 1.2.1.6.2 Topic owner should delete any comments on that topic. (TO BE DECIDED)
        * 1.2.1.6.3 Topic owner shall be able to block any registered user to write comment a to his/her topic.
        * 1.2.1.6.4 Topic owner shall be able to delete his/her topic.

      * **1.2.1.7 Quiz Requirements**
        * 1.2.1.7.1 Topic owner shall be able to prepare quizzes related to his/her topic.
        * 1.2.1.7.2 Quizzes shall have at least 3 question at most 20 questions.
          * 1.2.1.7.2.1 Questions shall have 2 formats as multiple choices with one correct answer or more correct answers.
        * 1.2.1.7.3 Users shall see their quiz results if they finished the quiz.

      * **1.2.1.8 Materials Requirements**
        * 1.2.1.8.1 Materials shall be documents(.pdf, .doc, .ppt, .xls, .txt) or multimedia(embedded videos, images(.jpg, .jpeg, .png)
        * 1.2.1.8.2 Materials shall be added by only topic owner.
        * 1.2.1.8.3 Topic owners shall be able to edit and delete the materials.
        * 1.2.1.8.4 Materials shall be downloaded in order to open, there won't be built-in interface.
        * 1.2.1.8.5 Materials shall be located in materials tab page.

      * **1.2.1.9 Notification Requirements**
        * 1.2.1.9.1 Notifications shall pop to the user when topic owner marks his/her comment as "instructive", when topic owner leaves a comment or uploads a quiz or material on the following topic.
        * 1.2.1.9.2 Notifications shall be displayed in homepage in a dropdown list.
        * 1.2.1.9.3 Notifications shall pop to the user when his/her following users post a new topic.
        * 1.2.1.9.4 Notifications should pop when user is mentioned in a post, this should be turned off from settings.

      * **1.2.1.10 Report Requirements** 
        * 1.2.1.10.1 A registered user may report a topic by selecting one of the reasons.
        * 1.2.1.10.2 The reasons for reporting a content includes Sexual content, Violent or repulsive content, Hateful or abusive content, Harmful dangerous acts, child abuse, Spam or misleading, Infringes rights.
        * 1.2.1.10.3 An admin shall be able to delete a topic which has reported and includes reported issues.

      * **1.2.1.11 Relationship Requirements**
        * 1.2.1.11.1 Each topic page shall have a related topic section. Topic owner shall be able to add topics which are related to the topic.
        * 1.2.1.11.2 Topic owner shall be able to distinguish some topics as suggested prerequisites.

    * **1.2.2 Search Requirements**
      * 1.2.2.1 Search system shall have options to search within topic headers, usernames and tags.
      * 1.2.2.2 Search system shall show searching results with respect to their ratings,semantic relation and date added.
      * 1.2.2.3 Search system shall show results upon user's choice; newest, oldest, high rated.
      * 1.2.2.4 Search system shall use semantic tag tree in order to get relevant results.

    * **1.2.3 Messaging Requirements**
      * 1.2.3.1 Messages shall have at most 10000 characters.
      * 1.2.3.2 Messages shall have only texts.
      * 1.2.3.3 Any registered user shall be able to send message to any registered user.

    * **1.2.4 Recommendation Requirements**
      * 1.2.4.1 Recommendations shall infer from users' followed topics' tags' channels' and other users' ,who follow same topic, followed topics' tags' channels'.

##2. Non-Functional Requirements
  * **2.1 Language**
    * 2.1.1 System shall have English user interface.
  * **2.2 Supportability and Maintainability**
    * 2.2.1 System shall have a modular design to integrate new features easily.
  * **2.3 Usability**
    * 2.3.1 System shall provide a user manual for clients, so any literate person shall be able to use the website. 
  * **2.4 Reliability**
    * 2.4.1 System should fix the crash problem at 8 hours maximum.
    * 2.4.2 System shall be backed up every day.
  * **2.5 Security**
    * 2.5.1 System shall allow user password with min. 8 max. 16 characters.
    * 2.5.2 System shall obligate user password to contain at least one number.
    * 2.5.3 System shall guarantee the personal data protection by using a hashing algorithm.
    * 2.5.4 System shall give user an option if it wants to delete account, leave contribution or delete all.
  * **2.6 Portability**
    * 2.6.1 System shall be able to work on the following web browsers:
      * Microsoft Edge - Versions later than or equal to 25.10586.0.0
      * Google Chrome - Versions later than or equal to 41.0.2272.101
      * Mozilla Firefox - Versions later than or equal to 36.0.4
      * Safari - Versions later than or equal 9.0.3

    * 2.6.2 System  shall have an Android client that should work on the versions later than or equal to 4.0
     
![](http://www.zeiss.com/content/microscopy/international/website/en_de/desktop/service-support/glossary/jcr%3Acontent/stagepar/stage/slide/stageimage/image.img.jpg/1391094697983.jpg/glossary-stage.jpg)
#Glossary

 * **Registered User:** A person who can create account and enter the system as a user.

 * **Unregistered User:** A person who can temporarily enter website and see limited things according to policies.

 * **Admin:** A person who responsible for managing   a computer or network , updating security settings, providing valuable help about  the problem etc. . Also, that person  has full access to the system because of  his/her responsibilities. Basically, instructor.

 * **Profile:** A brief written description that provides information about that user. Also, some photos and sharings may include in profile.

 * **Account:** An account is a collection of information that tells the system which information you can access, what changes you can make to the system, and your personal preferences, such as your profile page and sharings.

 * **Password:** A secret series of characters that enables a user to access his/her own account. The password helps ensure that unauthorized users do not access the that account. In addition, data files and programs may require a password.

 * **Username(email):** A unique sequence of characters used to identify a user and allow access to a his/her own  account.

 * **Database:** A database is an organized collection of data. The data is typically organized to model aspects of reality in a way that supports processes requiring information.

 * **Encryption:** Encryption is the process of encoding messages or information in such a way that only authorized parties can read it.

 * **Interface:** The visual part of website or application where user can interact with application.  

 * **Topic:** A text based content which has a header and a body. A Topic can contain comments section, materials section(can include multimedia and documents) and quiz section.

 * **Topic Owner:**  A registered user who opens a topic. Basically, instructor.

 * **Comment:** A text based content just a body. Comments can include multimedia reference from Topic's own material section. 


***