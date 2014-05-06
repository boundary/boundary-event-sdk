Overview
========

Boundary Event SDK (BESDK) provides a framework for introducing foreign events into the Boundary platform. It has been developed using the open source framework [Apache Camel](http://camel.apache.com).

A [route](http://camel.apache.org/routes.html) in Apache Camel is a series of message processing steps which are loosely coupled. The Boundary Event SDK implementation
consists of defining _route builders_ that convert foreign messages to Boundary events. Using the route builders one can declaratively define routes and logically
connect them to accomplish the tasks of converting foreign messages to events.

The diagram belows shows a graphical representation of the flow of data from the managed nodes via foreign message protocols (SNMP trap PDUs, and Syslog protocol), through
the Boundary Event SDK and ending at the [Boundary Event APIs](https://app.boundary.com/docs/events_api). The Boundary Event SDK hides the need to have deep technical knowledge
of REST or JSON to run the pre-built adapters, since the pre-built adapters are configured in a declarative fashion using an XML file. Speciallized event adapters can be created
but this requires that you have some knowledge of programming in Java, or Python.


![Boundary SDK Data Flow](images/besdk-data-flow.png "Boundary SDK Data Flow")


