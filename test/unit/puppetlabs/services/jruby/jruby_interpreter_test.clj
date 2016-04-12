(ns puppetlabs.services.jruby.jruby-interpreter-test
  (:require [clojure.test :refer :all]
            [puppetlabs.services.jruby.jruby-testutils :as jruby-testutils]
            [puppetlabs.services.jruby.jruby-puppet-internal :as jruby-internal]
            [puppetlabs.puppetserver.testutils :as testutils]))

(use-fixtures :once
              (testutils/with-puppet-conf
                "./dev-resources/puppetlabs/services/jruby/jruby_interpreter_test/puppet.conf"))

(deftest jruby-env-vars
  (testing "the environment used by the JRuby interpreters"
    (let [jruby-interpreter (jruby-internal/create-scripting-container
                              jruby-testutils/ruby-load-path
                              jruby-testutils/gem-home
                              jruby-testutils/compile-mode)
          jruby-env (.runScriptlet jruby-interpreter "ENV")]

      ; $HOME and $PATH are left in by `jruby-puppet-env`
      (is (= #{"HOME" "PATH" "GEM_HOME" "JARS_NO_REQUIRE" "JARS_REQUIRE"}
            (set (keys jruby-env)))))))
