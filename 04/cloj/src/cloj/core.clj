(ns cloj.core
  (:gen-class))

(defn readLines []
  (sort < (with-open [rdr (clojure.java.io/reader "input.txt")]
            (reduce conj [] (line-seq rdr)))))

(defn getGuardList
  [instructions]
  (->> (map (fn [s]
              (let [match (re-find #"Guard #(\d+)" s)]
                (if (some? match)
                  (Integer. (last match))
                  nil)))
            instructions)
       (filter #(some? %))
       (distinct)
       (sort <)))

(defn getMinutes [s]
  (println "here")
  (Integer. (second (re-find #"\d{2}:(\d{2})" s))))

(defn updateSchedule
  [schedule guard fallsAsleep wakesUp]
  (println "there")
  (aset schedule guard (let [sch (nth schedule guard)]
                         (loop [sched sch
                                index (- fallsAsleep 1)]
                           (if (= wakesUp index)
                             sched
                             (do
                               (aset sched index (+ (aget sched index) 1))
                               (recur sched (+ index 1))))))))

(defn getSleepSchedule
  [instructions guards]
  (let [schedule (make-array Integer/TYPE (count guards) 60)]
    (loop [inst instructions
           sched schedule
           guardId 0
           fallsAsleep 0]
      (println (count inst))
      (let [currentInst (first inst)
            reGuardId (re-find #"Guard #(\d+)" currentInst)]
        (cond
          (nil? inst) sched
          (some? reGuardId)
          (recur (rest inst)
                 sched
                 (Integer. (second reGuardId))
                 fallsAsleep)
          (clojure.string/includes? currentInst "falls asleep")
          (recur (rest inst)
                 sched
                 guardId
                 (getMinutes currentInst))
          (clojure.string/includes? currentInst "wakes up")
          (recur (rest inst)
                 (updateSchedule sched guardId fallsAsleep (getMinutes currentInst))
                 guardId
                 fallsAsleep))))))



(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
