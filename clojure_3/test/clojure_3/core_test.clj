(ns clojure-3.core-test
  (:require [clojure.test :refer :all]
            [clojure-3.core :refer :all]))

(deftest solution-test
  (testing "test"
    (is (= (filter zero? (range 50000)) (parallel_filter zero? (range 50000))))
    (is (= (filter odd? (range 50000)) (parallel_filter odd? (range 50000))))
    (is (= (filter even? (range 50000)) (parallel_filter even? (range 50000))))))

(take 2000 (parallel_filter odd? (range)))
(time (doall (parallel_filter odd? (range 1000000))))
(time (doall (filter odd? (range 1000000))))
