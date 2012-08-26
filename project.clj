(defproject newtonian "0.1.0-SNAPSHOT"
  :description "Particles System"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [quil "1.6.0"]]
  :dev-dependencies [[swank-cdt "1.4.0"]]
  :extra-classpath-dirs ["/usr/lib/jvm/java-7-oracle/lib/tools.jar"]
  :main newtonian.core)
