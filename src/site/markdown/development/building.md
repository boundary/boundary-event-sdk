Building
========
This page provides instructions on how to build the Boundary Event SDK.

Prerequisites
-------------
* Maven 3.2.1 or later
* Java JDK 1.7
* gcc 4.2.1 or later
* Git 1.7.1 or later


Instructions
------------

1. Clone the distribution

    ```$ git clone XXX```    

2. Change directory to the cloned repository

    ```$ cd boundary-event-sdk```

3. Install additional components to build the distribution by running:

    ```$ bash setup.sh```

4. Source the environment

    ```$ source env.sh```

5. Build and package the distribution

    ```$ mvn assembly:assembly```
