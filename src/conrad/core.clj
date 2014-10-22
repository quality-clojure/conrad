(ns conrad.core
  (:require [taoensso.timbre :as timbre :refer [info spy]]
            [clojure.java.io :as io]
            [clojure.core.async :as async])
  (:import java.io.File))

(defn has-trailing-whitespace?
  "True if the line has a whitespace before a newline. False otherwise."
  [line]
  (not (nil? (re-find #"\ $" line))))

(defn has-long-line?
  "True if the line exceeds the passed in length. False otherwise."
  [line]
  (not (nil? (re-find #"^.{80,}" line))))

(defn long-lines
  "Given a file, returns a vector of lines deemed too long."
  [file max-line-width]
  (with-open [reader (io/reader file)]
    (filterv has-long-line? (line-seq reader))))

(defn trailing-whitespace
  "Given a file, returns a vector of lines with a whitespace character
  at the end of the line."
  [file]
  (with-open [reader (io/reader file)]
    (filterv has-trailing-whitespace? (line-seq reader))))

(defn missing-doc-string
  ""
  [file]
  (1))

(defn empty-doc-string
  ""
  [file]
  (1))

(defn check-files
  "Reporter is used to deal with the output as it arises."
  [files reporter]
  (doseq [file (filter #(and (.isFile %)
                             (.endsWith (.getPath %) "clj"))
                       (file-seq files))]
    (info (.getPath file))
    (reporter (long-lines file 80))
    (reporter (trailing-whitespace))
    (reporter (missing-doc-string))
    (reporter (empty-doc-string))))
