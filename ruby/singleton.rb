#### Singleton
# Lib that control nº instance of class to 1 (Singleton Pattern)

require 'singleton'

class Config
  include Singleton

  attr_accessor :version, :state
end

config = Config.instance # => #<Config:0x00007fc25a1b8d10>

begin
  config_2 = Config.new # => private method `new' called for Config:Class
rescue Exception => e
  puts e
end
config_2 = Config.instance # => #<Config:0x00007fc25a1b8d10>

puts config == config_2 # => true