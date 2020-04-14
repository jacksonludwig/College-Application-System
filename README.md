# College Application System (CAS)

## Database Retrieval

Pressing the "update database" button in the main menu will pull the most recent set of data from <https://collegescorecard.ed.gov/>
and then send it to a Cloud Firestore database for more efficient accessing.

**Note:** This is only necessary if there is updated data on the government website that you want to be displayed in the app, as the Cloud Firestore database is already loaded with a set of all colleges.

## User Profiles

Upon launching the app, you can use your email to create a new account. You will be prompted to enter your name and password for the account as well.

If you had previously used the app, entering your email will instead allow your to login with the password you made with the account.

### SAT Scores

After you make an account, you can press the "View Account" button in order to bring up the account window. From here you can click "edit" in order to add your SAT reading and SAT math score to your profile. These scores will be used to calculate your likelihood of acceptance into different colleges by comparing their 25th and 75th percentile scores.

You may also change your account's name from here if you would like.

## Search Module

By pressing any of the three search buttons ("Search by College Name," "Search by College ID," or "Search by State,") you can query colleges from the Cloud Firestore database.

Colleges are displayed in a scrollable list of "cards." You can click on the name of a college to expand it and show more details about a college, such as its tuition costs, where it's located, its website, and more. 

After expanding a college, you can click the details section of the college to bring up a new menu. From this new view, the app will give you an approximate chance of acceptance based on your SAT scores (either "unlikely," "somewhat likely", or "likely.")

Additionally, you can click the "Add to Favorites" button to add the selected college to your profile's favorites.

### Name Search

Pressing this button will bring up a new window that will allow you to real-time search the database for colleges by their name. 
Searches are not case sensitive, ignore extra spaces, and are displayed in ascending alphabetical order (A -> Z).

You may want to press the "enter" key on a keyboard or use your phone's interface to lower the on-screen keyboard when you are done searching so that it does not cover your results.

### ID Search

This option will open up a popup dialog box in which you can type in the IPEDS ID of a college in order to query a single specific school's information, e.g. "100654" gives "Alabama A & M University."

### State Search

This will open up a popup menu where you can choose from any of the 50 states and Puerto-Rico. This will show all colleges from the selected state.

## Favorites 

After adding favorites from any of the search menus, you can view them by clicking the "View Favorites" button. From here, you can look at all of the college's information you were able to view in the search menus.

You may delete a college from your favorites by clicking on it and then clicking the "delete from favorites" button.
___
CSE248 Final Project - Jackson Ludwig
