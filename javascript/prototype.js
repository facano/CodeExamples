// ---- Prototype ----
// Is an object that is delegated a property when it is not found in current object => chain of prototypes

// Class definition (1)
function Animal(name){ // constructor
  this.name = name;
  this.mammal = false;
  this.eat = function(){
    console.log(this.name + " is eating");
  };
}
var myPet = new Animal("Rocky");
myPet.eat();
Animal.prototype.isMammal = function(){
  console.log(this.name + " is" + (this.mammal ? "" : " not") + " a mammal");
};
myPet.isMammal();

// "Class" definition (2)
var animalClass = {
  name: "RockyJr", // <-- hardcoded class property, not constructor for set
  mammal: false,
  eat: function(){
    console.log(this.name + " is eating");
  },
  isMammal: function(){
    console.log(this.name + " is" + (this.mammal ? "" : " not") + " a mammal");
  }
}
var mySecondPet = Object.create(animalClass); // there is not constructor to call => use "create"
mySecondPet.eat();
mySecondPet.isMammal();


// Inheritance
function Cat(name){
  Animal.call(this, name); // like "super" call in other languages, use explicit bind
  this.mammal = true;
}
Cat.prototype = Object.create(Animal.prototype); // "create" not call constructor, instead of "new"
Cat.prototype.constructor = Cat; // before it, constructor is Animal
myCat = new Cat("Garfield");
myCat.eat();
myCat.isMammal();