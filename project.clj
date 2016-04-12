(def ks-version "1.3.0")
(def tk-version "1.3.1")
(def tk-jetty-version "1.5.5")

(defproject puppetlabs/jruby-utils "0.1.0-SNAPSHOT"
  :description "A library for working with JRuby"
  :url "https://github.com/puppetlabs/jruby-utils"
  :license {:name "Apache License, Version 2.0"
            :url "http://www.apache.org/licenses/LICENSE-2.0"}

  :pedantic? :abort

  :source-paths ["src/clj"]
  :java-source-paths ["src/java"]
  :test-paths ["test/unit" "test/integration"]
  :resource-paths ["resources" "src/ruby"]


  :dependencies [[org.clojure/clojure "1.7.0"]

                 ;; begin version conflict resolution dependencies

                 ;; TODO: get rid of as many of these as possible
                 [clj-time "0.11.0"]
                 [org.clojure/tools.reader "1.0.0-alpha1"]
                 [puppetlabs/typesafe-config "0.1.5"]
                 [org.clojure/tools.macro "0.1.5"]
                 [com.fasterxml.jackson.core/jackson-core "2.5.4"]
                 ;; end version conflict resolution dependencies

                 [org.jruby/jruby-core "1.7.20.1"
                  :exclusions [com.github.jnr/jffi com.github.jnr/jnr-x86asm]]
                 ;; jffi and jnr-x86asm are explicit dependencies because,
                 ;; icn JRuby's poms, they are defined using version ranges,
                 ;; and :pedantic? :abort won't tolerate this.
                 [com.github.jnr/jffi "1.2.9"]
                 [com.github.jnr/jffi "1.2.9" :classifier "native"]
                 [com.github.jnr/jnr-x86asm "1.0.2"]
                 ;; NOTE: jruby-stdlib packages some unexpected things inside
                 ;; of its jar; please read the detailed notes above the
                 ;; 'uberjar-exclusions' example toward the end of this file.
                 [org.jruby/jruby-stdlib "1.7.20.1"]


                 ;;TODO GET RID OF AS MUCH OF THIS AS POSSIBLE

                 [cheshire "5.3.1"]
                 [slingshot "0.10.3"]
                 [clj-yaml "0.4.0" :exclusions [org.yaml/snakeyaml]]
                 [commons-lang "2.6"]
                 [commons-io "2.4"]
                 [clj-time "0.11.0"]
                 [prismatic/schema "1.0.4"]
                 [me.raynes/fs "1.4.6"]
                 [liberator "0.12.0"]
                 [org.apache.commons/commons-exec "1.3"]


                 [puppetlabs/trapperkeeper ~tk-version]
                 [puppetlabs/trapperkeeper-authorization "0.5.0"]
                 [puppetlabs/kitchensink ~ks-version]
                 [puppetlabs/ssl-utils "0.8.1"]
                 [puppetlabs/dujour-version-check "0.1.2" :exclusions [org.clojure/tools.logging]]
                 [puppetlabs/http-client "0.5.0"]
                 [puppetlabs/comidi "0.3.1"]

                 ]

  :deploy-repositories [["releases" {:url "https://clojars.org/repo"
                                     :username :env/clojars_jenkins_username
                                     :password :env/clojars_jenkins_password
                                     :sign-releases false}]]

  ;; By declaring a classifier here and a corresponding profile below we'll get an additional jar
  ;; during `lein jar` that has all the code in the test/ directory. Downstream projects can then
  ;; depend on this test jar using a :classifier in their :dependencies to reuse the test utility
  ;; code that we have.
  :classifiers [["test" :testutils]]

  :profiles {:dev {:dependencies  [[puppetlabs/kitchensink ~ks-version :classifier "test" :scope "test"]
                                   [puppetlabs/trapperkeeper ~tk-version :classifier "test" :scope "test"]
                                   [puppetlabs/trapperkeeper-webserver-jetty9 ~tk-jetty-version]
                                   [puppetlabs/trapperkeeper-webserver-jetty9 ~tk-jetty-version :classifier "test"]]}
             :testutils {:source-paths ^:replace ["test/unit" "test/integration"]}}
  )
