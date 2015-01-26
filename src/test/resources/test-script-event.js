// Extract our object
var event = request.getBody()

// Date
var builder = new java.util.Calendar.Builder();
builder.setDate(2010,5,20);
builder.setTimeOfDay(13,0,0,0);

event.date = builder.build().getTime();

// String
event.string = "Hello World!";

// int
event.integer = 37;

// double
event.decimal = 9.81;

// Map
var map = new java.util.HashMap();
map.put("red","one");
map.put("green","two");
map.put("blue","three");

event.setMap(map);

// List
var list = new java.util.ArrayList();
list.add("alex");
list.add("geddy");
list.add("neal");

event.setList(list);

// Enum
var ScriptEnum = Java.type('com.boundary.sdk.event.script.ScriptEnum');
event.setEnumeration(ScriptEnum.YELLOW);

// Object
var object = new com.boundary.sdk.event.script.ScriptObject();
object.setDate(builder.build().getTime());
object.setString("Goodbye");
object.setList(list);
object.setMap(map);

event.setObject(object);

result = event;