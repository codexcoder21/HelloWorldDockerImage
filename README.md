# HelloWorldDockerImage

This project builds a simple "Hello World" Java application and packages it as a Docker image using Jib.

## Building the Docker Image

To build the Docker image and load it into your local Docker daemon, navigate to the `HelloWorldDockerImage` directory and run the following command:

```bash
./gradlew jibDockerBuild
```

This will create a Docker image named `hello-world-docker-image:latest`.

## Running the Docker Image Locally

Once the Docker image is built, you can run it using Docker. Make sure Docker is running on your system.

To run the image and map the container's port 8080 to your host's port 8080:

```bash
docker run -p 8080:8080 hello-world-docker-image:latest
```

To specify a different port, you can use the `PORT` environment variable. For example, to run the server on port 9000:

```bash
docker run -p 9000:9000 -e PORT=9000 hello-world-docker-image:latest
```

After running the command, you should be able to access the "Hello World" message by opening your web browser and navigating to `http://localhost:8080` (or the specified port, e.g., `http://localhost:9000`).

## Running Tests

To run all unit and integration tests for the project, navigate to the `HelloWorldDockerImage` directory and execute:

```bash
./gradlew test
```

This will execute both the `HelloWorldServerTest` (unit test) and `HelloWorldServerContainerTest` (integration test that runs the Docker image).