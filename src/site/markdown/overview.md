Overview
========

Boundary Event SDK provides a framework for introducing foreign messages and events into the Boundary platform. It has been developed using the open source framework [Apache Camel](http://camel.apache.com).

A [route](http://camel.apache.org/routes.html) in Apache Camel is a series of message processing steps which are loosely coupled.
The Boundary Event SDK implementation consists of defining _route builders_ that convert foreign messages to Boundary events.
One route communicates with the foreign system using its native communication protocol (Sockets, REST, SOAP,etc) and
translates the foreign system message into a Boundary _RawEvent_ object. While another route recieves the RawEvent object where by it is translated into a _JSON_ document and then sent the Boundary Event API via a [REST](http://en.wikipedia.org/wiki/Representational_state_transfer) call.

Two routes: one communicating with the foreign source and the other communicating to the Boundary Event API work cooperatively as an _Event Adapter_. This separation of concerns allows the creation of new Event Adapters in a rapid fashion since half the integration, posting of events to the Boundary Event API is already developed and tested.

The diagram belows shows a graphical representation of the flow of data from the managed nodes via foreign message protocols (SNMP trap PDUs, and Syslog protocol), through
the Boundary Event SDK and ending at the [Boundary Event APIs](https://app.boundary.com/docs/events_api). The Boundary Event SDK hides the need to have deep technical knowledge
of REST or JSON to run the pre-built adapters, since the pre-built adapters are configured in a declarative fashion using an XML file. Speciallized event adapters can be created
but this requires that you have some knowledge of programming in Java, or Python.


![Boundary SDK Data Flow](images/besdk-data-flow.png "Boundary SDK Data Flow")


