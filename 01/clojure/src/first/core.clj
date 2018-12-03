(ns first.core
  (:gen-class))

(defn readLines []
  (with-open [rdr (clojure.java.io/reader "input.txt")]
    (reduce conj [] (line-seq rdr))))

(defn parseInt [s]
  (if (.contains s "+")
    (Integer/parseInt (re-find #"\d+" s))
    (Integer/parseInt s)))

(def instructions
  (map parseInt (readLines)))

(def sumOfInstructions
  (reduce + instructions))

(defn look
  ([inst]
   (look inst 0 [0]))
  ([inst acc passed]
   (let [nextAcc (+ acc (first inst))]
     (do
       (if (some #{nextAcc} passed)
         nextAcc
         (recur (rest inst) nextAcc (conj passed nextAcc)))))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println (look (cycle instructions))))
