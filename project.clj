(defproject    superstructor/re-frame-fetch-fx "lein-git-inject/version"
  :description "A re-frame effects handler for fetching resources (including across the network)."
  :url         "https://github.com/superstructor/re-frame-fetch-fx.git"
  :license     {:name "MIT"}

  :dependencies [[org.clojure/clojure       "1.10.3"   :scope "provided"]
                 [org.clojure/clojurescript "1.10.844" :scope "provided"
                  :exclusions [com.google.javascript/closure-compiler-unshaded
                               org.clojure/google-closure-library
                               org.clojure/google-closure-library-third-party]]
                 [thheller/shadow-cljs      "2.12.4"   :scope "provided"]
                 [re-frame                  "1.2.0"    :scope "provided"]]

  :profiles {:dev {:dependencies [[binaryage/devtools "1.0.3"]]}}

  :plugins      [[day8/lein-git-inject "0.0.14"]
                 [lein-shadow          "0.3.1"]
                 [lein-ancient         "0.6.15"]
                 [lein-shell           "0.5.0"]]

  :middleware   [leiningen.git-inject/middleware]

  :jvm-opts ["-Xmx2g"]

  :source-paths   ["src"]
  :test-paths     ["test"]
  :resource-paths ["run/resources"]

  :shadow-cljs {:nrepl  {:port 8777}

                :builds {:browser-test
                         {:target    :browser-test
                          :ns-regexp "-test$"
                          :test-dir  "target/test"
                          :devtools  {:http-port  8021
                                      :http-root "target/test"}}

                         :karma-test
                         {:target    :karma
                          :ns-regexp "-test$"
                          :output-to "target/karma-test.js"}}}

  :shell   {:commands {"karma" {:windows         ["cmd" "/c" "karma"]
                                :default-command "karma"}
                       "open"  {:windows         ["cmd" "/c" "start"]
                                :macosx          "open"
                                :linux           "xdg-open"}}}

  :aliases {"watch" ["with-profile" "dev" "do"
                     ["clean"]
                     ["shadow" "watch" "browser-test" "karma-test"]]

            "ci"    ["do"
                     ["clean"]
                     ["shadow" "compile" "karma-test"]
                     ["shell" "karma" "start" "--single-run" "--reporters" "junit,dots"]]}

  :deploy-repositories [["clojars" {:sign-releases false
                                    :url           "https://clojars.org/repo"
                                    :username      :env/CLOJARS_USERNAME
                                    :password      :env/CLOJARS_TOKEN}]]

  :release-tasks [["deploy" "clojars"]]

  :clean-targets [:target-path
                  "shadow-cljs.edn"
                  "package.json"
                  "package-lock.json"
                  "resources/public/js/test"])





