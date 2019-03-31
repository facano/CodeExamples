// ---- Closure ----

// In Closure the function definition is saved with variables in his scope

var addFactory = function(x){
  // Implicit declaration of var x, saved in clouse of returned function
  return function(y){
    return x + y;
  }
}

var addTenTo = addFactory(10);
var result = addTenTo(5);

console.log("The result is " + result); // The result is 15