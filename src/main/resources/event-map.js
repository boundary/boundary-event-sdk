/**
 * 
 */


// Extract the body from the request message
var event = request.body;



event.name = "hello";
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
// event.source.type = "host";
event.source.addProperty("song_list",aSongList);
event



//event.addProp("hello", "world");
//ArrayList<String> aList = new ArrayList<String>();
//aList.add("yellow");
//aList.add("magenta");
//aList.add("cyan");
//
//event.addProp("mylist",aList);
//
//ArrayList<String> aSongList = new ArrayList<String>();
//aSongList.add("Red Barchetta");
//aSongList.add("Free Will");
//aSongList.add("La Villa Strangiato");
//
//event.getSource().setRef("localhost").setType("host");
//event.getSource().addProp("song_list", aSongList);

