(ns clojure-2.core)


(defn multiples-of
  [p]
  (fn [x] (= 0 (mod x p))))

(defn sieve
  [[x & xs]]
  (cons x (lazy-seq (sieve (remove (multiples-of x) xs)))))

(def primes
  (sieve (drop 2 (range))))
