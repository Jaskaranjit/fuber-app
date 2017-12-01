# fuber-app

## Modelling Problem:
1. You are the proprietor of füber, an on call taxi service.
2. You have a fleet of cabs at your disposal, and each cab has a location, determined by it’s latitude and longitude.
3. A customer can call one of your taxis by providing their location, and you must assign the nearest taxi to the customer.
4. Some customers are particular that they only ride around in pink cars, for hipster reasons. You must support this ability.
5. When the cab is assigned to the customer, it can no longer pick up any other customers
6. If there are no taxis available, you reject the customer's request.
7. The customer ends the ride at some location. The cab waits around outside the customer’s house, and is available to be assigned to another customer.

## Extra Credit:
1. When the customer ends the ride, log the total amount the customer owes
2. The price is 1 dogecoin per minute, and 2 dogecoin per kilometer. Pink cars cost an additional 5 dogecoin.
3. HTML front end showing me all the cars available

## Pre-requisites:
1. java 1.7 or higher
2. maven3
3. mongoDB

## Compile and run
To compile and run the application:
	### Build the application using the following command
	```
	mvn clean install -DskipTests
	```

	### To run the tests:
	```
	mvn test
	```

	### To start the app:
	```
	java -jar target/fuber-app-1.0-SNAPSHOT.jar 
	```

	### The swagger UI will be available at:
	```
	http://localhost:8507/fuber/docs/
	```

## To Restore the database
The sample data is provided in the resources and shell script is also provided to restore the database.

	### To restore the database:
	```
	sh restoreDatabase.sh
	```






	

