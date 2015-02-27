Building
========
This page provides instructions on how to build the Boundary Event SDK.

Prerequisites
-------------
* Maven 3.2.1 or later
* Java JDK 1.7
* Bash 3.2.x or later
* gcc 4.2.1 or later
* Git 1.7.1 or later


Instructions
------------


### Building the software

#### Obtaining the Software
All software is stored in public GitHub repositories and to be cloned.

1. Clone the Boundary Event SDK distribution:

    ```$ git clone https://github.com/boundary/boundary-event-sdk```

2. Clone the dependent libraries:
    ```$ git clone https://github.com/boundary/boundary-camel-components```
    ```$ git clone https://github.com/boundary/boundary-plugin-framework-java```

3. Build the dependent libraries
    ```$ mvn install -DskipTests```

2. Change directory to the cloned repository

    ```$ cd boundary-event-sdk```

3. Install additional components (SNMP4J) to build the distribution by running:

    ```$ bash setup.sh```

4. Source the environment

    ```$ source env.sh```

5. Build and package the distribution

    ```$ mvn package```

### Running the Boundary Event SDK in development

### Building the documentation

1. Issue command to create the documentation:

    ```$ mvn site```

2. Start up the site:

    ```$ mvn site:run``` 

3. Documentation can be viewed from your local browser [http://localhost:8080](http://localhost:8080)

