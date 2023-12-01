(ns day1
  (:gen-class)
  (:require
   [clojure.string :as str]))

(defn load_file [file]
  (->
   (slurp file)
   (str/split-lines)))

(defn get_numbers [str_with_nums]
  (str/split str_with_nums #"[^0-9]"))

(defn remove_blanks [list_with_blanks]
  (remove str/blank? list_with_blanks))

(defn str_to_number [str_with_nums]
  (->
   str_with_nums
   get_numbers
   remove_blanks
   str/join))

(defn strs_to_numbers [list_strings]
  (map
   str_to_number list_strings))

(defn to_two_digits [long_number]
  (str (first long_number) (last long_number)))

(defn list_to_two_digits [list_of_numbers]
  (map to_two_digits list_of_numbers))

(defn list_to_ints [list_of_numbers]
  (map
   (fn [num] (Integer/parseInt num))
   list_of_numbers))

(defn day1_1 []
  (reduce
   +
   (->
    (load_file "resources/day1_1_prod.txt")
    strs_to_numbers
    list_to_two_digits
    list_to_ints)))

;; (day1_1)
