(ns day2
  (:gen-class)
  (:require
   [clojure.string :as str]))

(def game_limit
  {:red 12
   :green 13
   :blue 14})

(defn get_id [game]
  (->
   (str/split game #"Game ")
   (second)
   (str/split #":")
   (first)
   Integer/parseInt))

(defn remove_id [game]
  (->
   (str/split game #": ")
   (second)))

(defn colour_found? [str_colour colour]
  (->
   (re-pattern colour)
   (re-find str_colour)))

(defn str_to_int [colour_list colour]
  (->
   colour_list
   first
   (str/split (re-pattern colour))
   first
   str/trim
   Integer/parseInt))

(defn get_colour [set_colours, colour]
  (let [colour_str (filter #(colour_found? %1 colour) set_colours)]
    (if (empty? colour_str)
      0
      (str_to_int colour_str colour))))

(defn split_to_colours [game_set]
  (str/split game_set #","))

(defn create_sets [game]
  (->>
   (str/split game #"; ")
   (map (fn [x] (str x ",")))
   (map split_to_colours)
   (map (fn [game_sets]
          {:blue (get_colour game_sets "blue")
           :red (get_colour game_sets "red")
           :green (get_colour game_sets "green")}))))

(defn create_game [game]
  {:id (get_id game)
   :sets (->
          (remove_id game)
          (create_sets))})

(defn valid_game? [game]
  (->>
   game :sets
   (every? (fn [colour_set] (and
                             (>= (game_limit :red) (colour_set :red))
                             (>= (game_limit :green) (colour_set :green))
                             (>= (game_limit :blue) (colour_set :blue)))))))

(defn count_ids [games]
  (->>
   (map #(%1 :id) games)
   (reduce +)))

(defn load_file [file]
  (->
   (slurp file)
   (str/split-lines)))

(defn day2_1 [file]
  (->>
   (load_file file)
   (map create_game)
   (filter valid_game?)
   count_ids))

(day2_1 "resources/day2_1_prod.txt")