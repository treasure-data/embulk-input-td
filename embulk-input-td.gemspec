
Gem::Specification.new do |spec|
  spec.name          = "embulk-input-td"
  spec.version       = "0.2.3"
  spec.authors       = ["Muga Nishizawa"]
  spec.summary       = %[Td input plugin for Embulk]
  spec.description   = %[Loads records from Td.]
  spec.email         = ["muga.nishizawa@gmail.com"]
  spec.licenses      = ["Apache 2.0"]
  spec.homepage      = "https://github.com/muga/embulk-input-td"

  spec.files         = `git ls-files`.split("\n") + Dir["classpath/*.jar"]
  spec.test_files    = spec.files.grep(%r"^(test|spec)/")
  spec.require_paths = ["lib"]

  spec.add_development_dependency 'bundler', ['~> 1.0']
  spec.add_development_dependency 'rake', ['>= 10.0']
end
