// ---- Cohercion ----
// Unexcepted cast of variables
// - String and numbers
console.log(10 + "2"); // 102, + try to concat, cast to string
console.log(10 - "2"); // 8, - try to substract, cast to number
console.log(["one", "two"] + ["three", "four"]); // one,twothree,four
console.log(["5"] - ["2"]); // 3
console.log(["Five"] - ["Two"]); // NaN

// - Equality and boolean
console.log(5 == "5"); // true, == allows to cast before compare
console.log(5 === "5"); // false,  === is strict, dont allow cast (is like comparing type)
var a = "foo", b = null;
console.log(a && b); // null
console.log(a || b); // foo
b = b || "dummy";
console.log(b); // dummy


// ---- Hoisting ----
// Move declaration of functions and vars (not the assignments of its) to top of scope
hoisted(); // Hoisting this function
function hoisted(){
  console.log('Hoisting this function');
}
console.log(hoistedVar) // undefined, var is declared, but not assigned yet
var hoistedVar = "Is hoisted?";

// ---- Scope ----
// var is function scope.
// If var is not used, reference is searched in the stack until global scope, and declared global
+function(){
  if(true){
    var funcVar;
    let scopeVar;
  }
  funcVar = "foo var";
  scopeVar = "foo let"; // create global var, becase let is defined in a not reachable block
  console.log(funcVar); // foo
  console.log(scopeVar); // foo
}();
console.log(typeof funcVar); // undefined
console.log(scopeVar); // foo