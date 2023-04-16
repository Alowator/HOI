(ns clojure-1.core)


(defn repeat_n_times
  [xs n]
  (reduce
    (fn [x1 _] (conj x1 xs))
    [xs]
    (range (- n 1))))

(defn check_last_two_chars
  [x]
  (not (= (first (take-last 2 x)) (last (take-last 2 x)))))

(defn smart_concat
  [xs x]
  (filter check_last_two_chars (map (fn [y] (str x y)) xs)))

(defn pairwise
  [x1 x2] (reduce (fn [r1 r2] (concat r1 r2)) (map (fn [x] (smart_concat x2 x)) x1)))

(defn f
  [xs n]
  (reduce
    pairwise
    (repeat_n_times xs n)))
