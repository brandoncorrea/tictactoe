(ns tic-tac-toe.ui.user-interface)

(defprotocol UserInterface
  (show-title [this])
  (show-instructions [this])
  (show-results [this board])
  (request-game-mode [this])
  (request-difficulty [this])
  (request-move [this board player]))
