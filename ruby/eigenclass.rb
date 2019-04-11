###### Eigenclass
# Anonymous class that Ruby creates and inserts into the inheritance hierarchy to hold the class methods (thus not interfering with the instances that are created from the class).
# -- Mark McDonnell Senior Software Engineer
# Eigen allow us to control scope of vars and methods in a special way

class Animal
  @@class_var = "My class variable for an Animal"
  @instance_var = "My instance variable for an Animal used as class_var"

  def initialize(name)
    @name = name
  end

  def self.class_var
    puts @@class_var
  end

  def eigenclass
    class << self # self is the class itself, Animal in this case
      self
    end
  end

  class << self # Eigenclass, encapsulates var in inheritance
    attr_accessor :instance_var # apply instance modifier like 'attr_accessor' for class vars in eigenclass
    def instance_var_val
      puts @instance_var
    end

    private # apply instance modifier like 'private' for class vars in eigenclass
    def private_class_method
      "I'm private"
    end
  end

end

class Cat < Animal
  @@class_var = "My class variable for a Cat" # override value for superclass variable
  @instance_var = "My instance variable for a Cat used as class_var"
end


puts "Animal class:"
Animal.class_var # => My class variable for a Cat
Animal.instance_var_val # => My instance variable for an Animal used as class_var
puts Animal.instance_var

puts "Cat class:"
Cat.class_var # => My class variable for a Cat
Cat.instance_var_val # => My instance variable for a Cat used as class_var
puts Cat.instance_var

begin
  Animal.private_class_method # => private method `private_class_method' called for Animal:Class (NoMethodError)
rescue Exception => e
  puts e
end

my_pet_1 = Cat.new("First pet")
my_pet_2 = Cat.new("Second pet")
# Each object have an unique and different eigenclass
puts my_pet_1.eigenclass # => #<Class:#<Cat:0x007fdbc593ba40>>
puts my_pet_2.eigenclass # => #<Class:#<Cat:0x007fdbc593b9f0>>
# Eigenclass is a subclass of object's Class
puts my_pet_1.eigenclass.superclass # => Cat

######## Post in https://stackoverflow.com/a/1631005
str = "abc"
other_str = "def"

class << str # using 'self' for specific instance
  def append_d
    return self + "d"
  end
end

puts str.append_d # => "abcd"
puts other_str.append_d # => raises an exception, 'append_d' is not defined on other_str
