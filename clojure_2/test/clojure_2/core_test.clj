(ns clojure-2.core-test
  (:require [clojure.test :refer :all]
            [clojure-2.core :refer :all]))

(deftest solution-test
  (testing "test"
    (is (= (nth primes 0) 2))
    (is (= (nth primes 1) 3))
    (is (= (nth primes 2) 5))
    (is (= (nth primes 999) 7919))
    (is (= (nth primes 1000) 7927))))
