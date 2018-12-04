(ns cloj.core
  (:gen-class))

(defn readLines []
  (with-open [rdr (clojure.java.io/reader "input.txt")]
    (reduce conj [] (line-seq rdr))))

(def instructions (sort (readLines)))

(defn splitString [s g]
  (let [dateTime (re-find #"(\d{4}(-\d{2}){2})\s(\d{2}:\d{2})" s)
        date (second dateTime)
        dTime (last dateTime)
        minute (Integer. (second (clojure.string/split dTime #":")))
        guard (second (re-find #"Guard #(\d+) begins shift" s))
        fallsAsleep (some? (re-find #"falls asleep" s))
        wakesUp (some? (re-find #"wakes up" s))]
    {:guard (if (some? guard) (Integer. guard) g)
     :minute minute
     :fallsAsleep fallsAsleep
     :wakesUp wakesUp}))

(defn readInstructions [s]
  (let [firstGuard (:guard (splitString (first s) -1))]
    (loop [ln s
           guard firstGuard
           results '()]
      (if (empty? ln)
        results
        (let [currentLine (splitString (first ln) firstGuard)
              currentGuard (:guard currentLine)]
          (recur (rest ln)
                 currentGuard
                 (conj results currentLine)))))))

(def sortedInstructions
  (readInstructions instructions))

(def guardList
  (distinct (map #(:guard %) sortedInstructions)))

(def countSleepingMinutes
  [glist instList]
  (loop [il instList]
    (let [inst (first il)]
      (



(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
