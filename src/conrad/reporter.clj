(ns conrad.reporter)

(defn sample-cli-reporter
  ""
  [check-map]
  (let [{:keys [file line content]} check-map]
    (println file " " line " " content)))
