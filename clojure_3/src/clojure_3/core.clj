(ns clojure-3.core)


(defn parallel_filter_task [pred coll]
  (let
    [block-size 10000]
    (->> (partition-all block-size coll)
         (map (fn [x] (future (doall (filter pred x)))))
         (doall)
         (map deref)
         (flatten)
         )))


(defn parallel_filter [func seq]
  (flatten (map (fn [x] (parallel_filter_task func x)) (partition-all 2000000 seq))))
