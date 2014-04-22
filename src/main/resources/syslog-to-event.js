/**
 * 
 */


// Extract the body from the request message
var event = request.headers.event
event = request.body
var syslog = request.headers["syslog"];


event.putTag("red");
event.putTag("green");
event.putTag("blue");
event.putProperty("hello","world");
var aList = new Array();
aList.push("yellow");
aList.push("magenta");
aList.push("cyan");

event.putProperty("mylist",aList);

var aSongList = new Array();
aSongList.push("Red Barchetta");
aSongList.push("Freewill");
aSongList.push("La Villa Strangiato");

event.source.ref = "localhost";
// event.source.type = "host";
//event.source.putProperty("song_list",aSongList);
event


