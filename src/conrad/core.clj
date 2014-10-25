(ns conrad.core
  (:require [clojure.java.io :as io]
            [clojure.core.async :as async]
            [clojure.tools.analyzer.jvm :as ana.jvm]
            [clojure.tools.namespace.file :as ns-file]
            [taoensso.timbre :as timbre :refer [info spy]])
  (:import [java.io File]
           [clojure.lang LineNumberingPushbackReader]))

(defn trailing-whitespace?
  "True if the line has a whitespace before a newline. False otherwise."
  [line]
  (not (nil? (re-find #"\ $" line))))

(defn long-line?
  "True if the line exceeds the passed in length. False otherwise."
  [line]
  (not (nil? (re-find #"^.{80,}" line))))

(defn long-lines
  "Given a file, returns a vector of lines deemed too long."
  [file max-line-width]
  (with-open [reader (io/reader file)]
    (filterv long-line? (line-seq reader))))

(defn trailing-whitespace
  "Given a file, returns a vector of lines with a whitespace character
  at the end of the line."
  [file]
  (with-open [reader (io/reader file)]
    (filterv trailing-whitespace? (line-seq reader))))

(defn empty-doc-string?
  [funct]
  (= "" (get (meta funct) :doc)))

(defn missing-doc-string?
  ""
  [funct]
  (when (nil? (get (meta funct) :doc))))

;(defn load-ns
;  ""
;  [file]
;  (with-open [reader (io/reader file)]
;    (ns-file/read-file-ns-decl reader)))
; Look into how kibit.check/read-file does this. Seems promising.
;

(defn check-files
  ""
  [files]
  (doseq [file (filter #(and (.isFile %)
                             (.endsWith (.getPath %) "clj"))
                       (file-seq files))]
    (info (.getPath file))
    (load-ns file)
    ;   (long-lines file 80)
    ;  (trailing-whitespace)
    ; (missing-doc-string?)
    ;(empty-doc-string?)
    )
  )

(defn correct-path
  "Make sure a string representing a path has the correct path
  separators. This is only needed for compliance with Windows,
  generally."
  [path]
  (.getPath ^File (io/file path)))
