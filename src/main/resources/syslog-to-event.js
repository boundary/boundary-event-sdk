/**
 * 
 */


// Extract the body from the request message
var event = request.event
event = headers.get('event');
var syslog = headers.get('syslog');


event.addTag("red");
event.addTag("green");
event.addTag("blue");
event.addProperty("hello","world");
var aList = new Array();
aList.push("yellow");
aList.push("magenta");
aList.push("cyan");

event.addProperty("mylist",aList);

var aSongList = new Array();
aSongList.push("Red Barchetta");
aSongList.push("Freewill");
aSongList.push("La Villa Strangiato");

event.source.ref = "localhost";
event.source.type = "host";
event.source.addProperty("song_list",aSongList);
event


