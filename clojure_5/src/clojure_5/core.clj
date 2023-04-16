(ns clojure-5.core)

(def transaction_restarts
  (atom 0))

(def add
  (fn [x] (+ x 1)))

(def forks
  (map (fn [_] (ref 0)) (range)))

(def philosopher
  (fn [i left_fork right_fork thinking_length dining_length dining_number]
    (let [sleep (fn [i duration]
                  (do
                    (println "Philosopher " i " sleep")
                    (Thread/sleep duration))),
          eat (fn [i left_fork right_fork duration]
                  (dosync
                    (swap! transaction_restarts add)
                    (println "Philosopher " i " begin eat")
                    (alter left_fork add)
                    (println "Philosopher " i " take left fork")
                    (alter right_fork add)
                    (println "Philosopher " i " take right fork")
                    (Thread/sleep duration)
                    (println "Philosopher " i " finish eat")
                    (swap! transaction_restarts add)))]
      (new Thread (fn []
                    (doseq [_ (range dining_number)]
                      (do
                        (sleep i thinking_length)
                        (eat i left_fork right_fork dining_length))))))))


(defn run_philosophers
  [philosophers_number thinking_length dining_length dining_number]
  (let [philosophers (map (fn [i] (philosopher i (nth forks i) (nth forks (mod (+ i 1) philosophers_number)) thinking_length dining_length dining_number)) (range philosophers_number))]
    (do
      (doall (map (fn [p] (.start p)) philosophers))
      (doall (map (fn [p] (.join p)) philosophers)))))

(time
  (run_philosophers 5 500 500 5))

(println "transaction restarts:")
(println  @transaction_restarts)
