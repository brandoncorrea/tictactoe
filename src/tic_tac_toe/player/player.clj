(ns tic-tac-toe.player.player)

(defn ->player [token type]
  {:token token :type type})

(defmulti next-move :type)
