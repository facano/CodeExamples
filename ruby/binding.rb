#### Binding
# Associate a context with their vars and methods, for eval its in other places
# Objects of class Binding can get using method Kernel#binding
# https://ruby-doc.org/core-2.2.0/Binding.html

# Example: Basic implementation of templating view engine
class ERB
  def initialize(content)
    @content = content
  end
  def result(context)
    puts "Debug: #{context.local_variables}" # just show local var, not instance var
    @content.gsub(/<%=(.+)%>/).each do
      context.eval($1)
    end
  end
end
module ViewContext
  def template
    return "<div>This is my template of the app <%= @app_name %></div>" # simulates opened from file
  end
  def render(context)
    view = ERB.new(template)
    puts view.result(context)
  end
end
class Controller
  include ViewContext
  def action
    local_var = "local var"
    @app_name = "Example Binding :D" # simulates a instance var in controller
    render(binding())
  end
end

# Router call action in controller:
Controller.new.action
# => Debug: [:local_var]
# => <div>This is my template of the app Example Binding app</div>