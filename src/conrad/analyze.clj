(ns conrad.analyze
  (:require [clojure.java.io :as io]
            [clojure.tools.analyzer.jvm :as ana.jvm]
            [clojure.tools.analyzer.ast :as ast]
            [taoensso.timbre :as timbre :refer [info spy]]))

(defn doc-string
  "Given a form, such as a function, attempt to retrieve the documentation. Nil otherwise."
  [form]
  (get-in (ana.jvm/analyze' 'form) [:meta :doc]))

(defn children
  "Return the children of a node from the AST."
  [node]
  (ast/children (ana.jvm/analyze-ns x)))

;(defn forms
;  ""
;  [name-space]
;  (get-in (children name-space) [:something :something])
