(ns tic-tac-toe.gui.pages.page)

(defmulti render-page :page)
(defmulti update-page :page)
