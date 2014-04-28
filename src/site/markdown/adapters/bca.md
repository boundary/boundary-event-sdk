Boundary Communication Adapter
==============================

he Boundary Communication Adapter (BCA) leverages Camel and the Boundary Event API for remotely checking for service availability, this could be applied to ServiceMesh, PLUMgrid, Puppet, or anything else that relies on TCP for communication.

The BCA consists of a timer for scheduling frequency of checks, this could be set as often as one second. The BCA makes a connect to a user define IP and port and if the connection fails an exception is posted to the Boundary Event API.

Events posted to Boundary are checked for duplication - the user controls where this occurs if at all - and are then indexed for search and enriched with application and group membership details where appropriate. In addition to accessing events via the Boundary Event Console, users may also visualize these in dashboards, set notification rules based on conditions, or even send them to incident management tools.

The BCA is open source and relies on Boundaries public API\'s so users may easily modify or extend it beyond its current capabilities if needed.



Parameters
----------
