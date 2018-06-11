# Java Backend Coding Test

The goal of this exercise is to implement a service that implements a basic shopping cart, modelled as a REST API. 

The functionality that should be implemented is described below.

### Layout

Use the scaffolding already provided to start your service. The project is built as a
 [Maven](https://maven.apache.org/) project, using [SparkJava](http://sparkjava.com/).

Unit tests are set up to run with [JUnit 5](https://junit.org) and integration tests with 
 [Cucumber](https://cucumber.io/), although feel free to switch to other implementations if you are more comfortable 
 with those.

### Running
ApplicationMain is the service's entry point, if you ran its main method with your IDE, the server should start on 
``http://localhost:4567``.

The service can also be built by running the ``mvn package`` command, it will generate a standalone artifact suitable
 for running independently at ``target/backend-coding-test-0.0.1-SNAPSHOT.jar``. When executed, it will start in the 
 same way as above.

### Exercise

Given the existing structure, design a shopping cart REST API and implement a Java service that implements that 
 API. The API should allow for the following functionality:
* Add items to a cart
* Get a list of items currently in the cart
* Clear the cart from all the items

Don't be tempted to over-implement in order to fill in the gaps of basic cart functionality that isn't being asked for, 
 but make sure your design does not lock you out of being able to add more endpoints that would implement additional 
 basic cart manipulation. If your design relies on those other endpoints also being implemented for your API to make 
 sense as a whole, feel free to write that down in your notes.

### Implementation notes
				
For the sake of the exercise, no data persistence to an external data source needs to be taken into consideration,
 suitable in-memory representation(s) are enough.
 
If necessary for your design, when referring to a product, you can assume that each of those products can be referred
 to individually by an opaque identifier with the format of your choosing, and that the source of those products and 
 identifiers would be fully contained within another service, which is not provided and does not have to be 
 implemented for the sake of this exercise.

### Timing

The recommended time to spend on this task is 2 to 3 hours.

### What to submit

Ensure that all your application builds correctly and all its tests are passing before submission.

Place a README file in the root folder of your project, using any notes that you feel are necessary for another person 
 reviewing to be able to successfully run your example and assess it properly. In particular, if you make changes to 
 the build and running process, provide instructions on how to run your solution.

ZIP up the solved exercise and submit it back via the channel that was communicated to you.
