######## Procs
# Procs can stored in objects, dont validate params and end method caller if invoque return
puts "[PROCS]"
def my_proc_method(value_clousure)
  my_proc = Proc.new { "returning a string directly with a var #{value_clousure}" }
  return my_proc
end
def my_proc_method_return
  my_proc = Proc.new { return "returning a value in to the caller of my_proc_method_return" }
  my_proc.call
  return "I guess this code is not reachable"
end

puts my_proc_method("in clousure").call # => "returning a string directly with a var in clousure"
puts my_proc_method_return # => "returning a value in to the caller of my_proc_method_return"


######## Lamdas
# Lamdas can stored in objects, validate params and return value to method if invoque return
puts "\n[LAMBDAS]"
def my_lamda_method(value_clousure)
  my_lambda = ->{ "returning a string directly with a var #{value_clousure}" }
  return my_lambda
end
def my_lamda_method_return
  my_lambda = lambda { return "returning a value in to the my_lambda var in my_lamda_method_return" }
  my_lambda.call
  return "This time this code is reachable"
end
puts my_lamda_method("in clousure").call # => "returning a string directly with a var in clousure"
puts my_lamda_method_return # => "This time this code is reachable"

######## Blocks
# Block cant stored in var, only can pass one to method for call in yield. Return inside block can return receiver of method call
puts "\n[BLOCKS]"
def block_method(param, &block)
  return_value = block.call(param)
  puts block.class # Proc
  puts return_value
end
def block_yield(param1)
  if block_given?
    return_value = yield(param1)
    puts return_value
  else
    puts "No block was given"
  end
end
block_method("param") { |arg| puts "Block using like a Proc with arg #{arg}";
   "returning a val from block in last line" }
# => "Block using like a Proc with arg param"
# => "Proc"
# => "returning a val from block in last line"

def method_wrapper
  block_yield("yield keyword") do |arg|
    puts "Block using #{arg} for call"
    return "returning a val from yielded block in last line with explicit return"
  end
  print "I guess this code is not reachable"
end
puts method_wrapper
# => "Block using yield keyword for call"
# => "returning a val from yielded block in last line with explicit return"

#Scope of vars
# Scope is at level of method Scope / or lamda, proc, etc
if true
  inner_if = "Inner if"
end
begin
  inner_begin = "Inner begin"
end
def inner_method
  inner_method_var = "Inner method"
end
puts inner_if # => "Inner if"
puts inner_begin # => "Inner begin"
puts inner_method_var rescue nil # =>  undefined local variable or method `inner_method_var'