// ---- This keyword ----

// Implicit Binding
// - Bind to object that "call" the function
var person = {
  name: "Felipe",
  greet: function(){
    console.log('Hello, my name is ' + this.name)
  }
}
person.greet(); // Hello, my name is Felipe

// - Now, changing the place for call function, change bind of this
window.name="External Name"; // only works in a browser
var external_greet = person.greet;
external_greet(); // Hello, my name is External Name


// Explicit Binding
// - call and apply
external_greet.call(person, "arg1", "arg2"); // Hello, my name is Felipe
external_greet.apply(person, ["arg1", "arg2"]); // Hello, my name is Felipe
// - bind
external_greet.bind(person);
external_greet(); // Hello, my name is Felipe