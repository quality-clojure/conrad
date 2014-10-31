(ns conrad.analyze
  (:require [clojure.java.io :as io]
            [clojure.tools.analyzer.jvm :as ana.jvm]
            [clojure.tools.analyzer.ast :as ast]
            [taoensso.timbre :as timbre :refer [info spy]])
  (:import  [clojure.lang LineNumberingPushbackReader]))

(defn doc-string
  "Given a form, such as a function, attempt to retrieve the documentation. Nil otherwise."
  [form]
  (get-in (ana.jvm/analyze' 'form) [:meta :doc]))

(defn children
  "Return the children of a node from the AST."
  [node]
  (ast/children (ana.jvm/analyze-ns node)))

;(defn forms
;  ""
;  [name-space]
;  (get-in (children name-space) [:something :something])



;; Temporarily kidnapped from kibit to try and figure namespace loading out.
(defn- careful-refer
  "Refers into the provided namespace all public vars from clojure.core
  except for those that would clobber any existing interned vars in that
  namespace. This is needed to ensure that symbols read within syntax-quote
  end up being fully-qualified to clojure.core as appropriate, and only
  to *ns* if they're not available there. AFAICT, this will work for all
  symbols in syntax-quote except for those referring to vars that are referred
  into the namespace."
  [ns]
  (binding [*ns* ns]
    (refer 'clojure.core :exclude (or (keys (ns-interns ns)) ())))
  ns)

;; Temporarily kidnapped from kibit to try and figure namespace loading out.
(def eof (Object.))

;; Temporarily kidnapped from kibit to try and figure namespace loading out.
(defn read-file
  "Generate a lazy sequence of top level forms from a
  LineNumberingPushbackReader"
  [^LineNumberingPushbackReader r init-ns]
  (let [do-read (fn do-read [ns]
                  (lazy-seq
                   (let [form (binding [*ns* ns]
                                (read r false eof))
                         [ns? new-ns k] (when (sequential? form) form)
                         ns (if (and (symbol? new-ns)
                                     (or (= ns? 'ns) (= ns? 'in-ns)))
                              (careful-refer (create-ns new-ns))
                              ns)]
                     (when-not (= form eof)
                       (cons form (do-read ns))))))]
    (do-read (careful-refer (create-ns init-ns)))))
