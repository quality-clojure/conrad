(ns conrad.core
  (:require [taoensso.timbre :as timbre :refer [info spy]]
            [clojure.java.io :as io])
  (:import java.io.File))

;; All should return something like a vector of maps containing
;; :file, :line, :content that the reporter will use to do various things.
(defn long-lines
  ""
  [file max-line-length]
  (1))

(defn trailing-whitespace
  ""
  [file]
  (1))

(defn missing-doc-string
  ""
  [file]
  (1))

(defn empty-doc-string
  ""
  [file]
  (1))

(defn check-files
  ""
  [files reporter]
  (doseq [file (filter #(and (.isFile %)
                             (.endsWith (.getPath %) "clj"))
                       (file-seq files))]
    (info (.getPath file))
    (reporter (long-lines file 80))
    (reporter (trailing-whitespace))
    (reporter (missing-doc-string))
    (reporter (empty-doc-string))))
