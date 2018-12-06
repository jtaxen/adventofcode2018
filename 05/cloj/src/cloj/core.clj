(ns cloj.core
  (:gen-class))

(defn readString []
  (with-open [rdr (clojure.java.io/reader "input.txt")]
    (reduce conj [] (line-seq rdr))))

(def polymer
  (first (readString)))

(defn alternatingCase?
  [s]
  (let [a (first s) b (second s)]
    (and (or (and (Character/isUpperCase a) (Character/isLowerCase b))
             (and (Character/isLowerCase a) (Character/isUpperCase b)))
         (= (Character/toUpperCase a) (Character/toUpperCase b)))))

(def alphabet
  (map char (range (int \a) (inc (int \z)))))

(def letterPairs
    (loop [r alphabet
           acc []]
      (if (empty? r)
        acc
        (let [a (first r)
              A (Character/toUpperCase a)]
          (recur (rest r) (conj acc (apply str [a A]) (apply str [A a])))))))

(defn destructOnce
  [polymer]
  (reduce #(clojure.string/replace %1 %2 "") polymer letterPairs))

(defn destruct
  [polymer]
  (loop [poly polymer]
    (let [newP (destructOnce poly)]
      (if (= poly newP)
        newP
        (recur newP)))))


(defn removeLetter
  [c poly]
  (let [C (Character/toUpperCase c)]
    (clojure.string/replace (clojure.string/replace poly (str C) "") (str c) "")))

(defn removeAndDestruct
  [polymer]
  (loop [alph alphabet
         acc {}]
    (if (empty? alph)
      acc
      (let [p (destruct (removeLetter (first alph) polymer))]
        (recur (rest alph) (assoc acc (keyword (str (first alph))) (count p)))))))



(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (do
    ;(println (count (destruct polymer)))
    (->> (removeAndDestruct polymer)
         (vals)
         (reduce min)
         (println))))
