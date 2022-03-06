Galie Ana Maria 

Link Project Github: 
https://github.com/GalieAnaMaria/OOP.annualSantaClaus.git

_._._._[[The design underlining concept]]_._._._

The test files will be handled by the main method and 
the Service class which has the underlining purpose of 
distributing the input data into objects and ArrayLists.

Multiple classes have been made for a better distinction 
regarding from where the data is taken and where it will be
restored in. (Additional motive: easier debugging)


._._._._.[Additions & General tehnical functionality]._._._._.:

________[Design Pattern]______:

[SINGLETON]: mainly used for ensuring only one database will be created
		per running test to limit the malfunctioning scenarios

________[GENERAL Functionality]______:

Class:[Service]: The principal class where the program starts to take in
		the information inside json files and stores it into the
		to database. Finally, it will be sent into the Menagement class

Class:[Menagement]: Handles all the operations regarding the data getting
		    updated every year, via annualChanges.
		    Yearly stages will be stored into specifically helper made \
	            classes which will represent overall an Arraylist of Arraylists.
		    
			Aka: Arraylist of annualChanges which each will 
			     contain an ArrayList made out of the children's
		             data from that year post updates

Class:[WriteData]: This class handles the display aspect of the program where
		   the information altered in Menagement will be written in
		   json files for output.
			
		   >PrettyPrinter from Jackson has been used for file readability.
		   

_._._._._._._._.[[EXTRA explanations)]]_._._._._._._._.

Class:[Menagement]:

Across the method, the list have been checked for duplicates and 
	proper ascending order according to specific criteria for
	a smooth flow of the debugging

Method:[annualChangesManagement]_____________:

    Description: Across the method we handle:
		(1) The kid information using only the raw data
			from the input
		(2) The annual changes over the years which alter the
			contents of the objects	

		All of these are stored in an object from AnnualChildren.
		Main reason for this approach was easier debugging and 
		also helped with the shorter/easier display of the 
		results at the end of a test.
	
	Each year consists of:
	(1) Increasing the age of the kids
	(2) Adding new kids
	(3) Updating the children's data
	(4) Adding new gifts to santa's list
	(5) Changing santa's budget
	(6) Eliminating the young adults
    	(7) Calculating every kid's average
	(8) Allocating their budget
	(9) Giving the kids the presents in the prefered order
	(10) Saving the year's state of data for display