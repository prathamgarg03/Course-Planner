# Course Planner

This project is a Course Planner application developed using Java and Spring Boot. It provides a RESTful API to manage and retrieve information about departments, courses, course offerings, and watchers.

## Technologies Used

- Java 17
- Spring Boot 3.0.0
- Gradle
- JUnit

## Features

- Get information about the application
- Dump the current model
- Get a list of all departments
- Get a list of all courses in a department
- Get a list of all offerings of a course in a department
- Get a list of all sections of an offering of a course in a department
- Get a graph of students per semester for a department
- Add a new offering
- Get a list of all watchers
- Add a new watcher
- Get information about a watcher
- Delete a watcher

## Setup

1. Clone the repository
2. Navigate to the project directory
3. Run `./gradlew bootRun` to start the application

## API Endpoints

- `GET /api/about`: Returns information about the application
- `GET /api/dump-model`: Dumps the current model
- `GET /api/departments`: Returns a list of all departments
- `GET /api/departments/{deptId}/courses`: Returns a list of all courses in a department
- `GET /api/departments/{deptId}/courses/{courseID}/offerings`: Returns a list of all offerings of a course in a department
- `GET /api/departments/{deptId}/courses/{courseId}/offerings/{offeringId}`: Returns a list of all sections of an offering of a course in a department
- `GET /api/stats/students-per-semester`: Returns a graph of students per semester for a department
- `POST /api/addoffering`: Adds a new offering
- `GET /api/watchers`: Returns a list of all watchers
- `POST /api/watchers`: Adds a new watcher
- `GET /api/watchers/{watcherID}`: Returns information about a watcher
- `DELETE /api/watchers/{watcherID}`: Deletes a watcher

## Testing

Run `./gradlew test` to execute the test cases.

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.
