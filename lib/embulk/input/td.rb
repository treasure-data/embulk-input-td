Embulk::JavaPlugin.register_input(
  "td", "org.embulk.input.td.TdInputPlugin",
  File.expand_path('../../../../classpath', __FILE__))
