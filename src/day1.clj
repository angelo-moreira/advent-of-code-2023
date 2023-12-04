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

(defn char_to_num_back [find, num_char, num]
  (let [i (str/last-index-of find num_char)]
    (if (nil? i)
      nil
      {:word find
       :replace_word num_char
       :replace_with num
       :found_at i
       :found_until (+ i (count num_char))})))

(defn char_to_num_forward [find, num_char, num]
  (let [i (str/index-of find num_char)]
    (if (nil? i)
      nil
      {:word find
       :replace_word num_char
       :replace_with num
       :found_at i
       :found_until (+ i (count num_char))})))

(def translations [["one"   "1"]
                   ["two"   "2"]
                   ["three" "3"]
                   ["four" "4"]
                   ["five" "5"]
                   ["six" "6"]
                   ["seven" "7"]
                   ["eight" "8"]
                   ["nine" "9"]])

(defn fill_indexes [x]
  (flatten
   (merge
    (remove nil? (map (fn [[k v]] (char_to_num_back x k v)) translations))
    (remove nil? (map (fn [[k v]] (char_to_num_forward x k v)) translations)))))

(fill_indexes "fourzhpnphmq52r813four")

(defn find_words [word]
  (let [filled (remove (fn [x] (nil? x)) (fill_indexes word))]
    (if (empty? filled)
      [{:word word
        :replace_word ""
        :replace_with ""
        :found_at 0
        :found_until 0}]
      filled)))

(defn sort_indexes [word]
  (sort-by (fn [x] (x :found_at)) word))

(defn wtf [word]
  (str/join
   ""
   [(subs (word :word) 0 (word :found_at))
    (word :replace_with)
    (subs (word :word) (word :found_until) (count (word :word)))]))


(defn change_word [word_list]
  (str/join
   ""
   (map wtf word_list)))

(def
  words
  (load_file "resources/day1_2_prod.txt"))

(->>
 words
 (map find_words)
 (map sort_indexes)
 (map change_word)
 (strs_to_numbers)
 (list_to_two_digits)
 (list_to_ints)
 (reduce +))
