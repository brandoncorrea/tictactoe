(ns tic-tac-toe.player.player)

(defn ->player
  ([type token] {:type type :token token})
  ([type token opponent]
   (assoc (->player type token) :opponent opponent)))

(defmulti next-move :type)
