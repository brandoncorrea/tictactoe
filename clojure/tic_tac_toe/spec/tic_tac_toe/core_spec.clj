(ns tic-tac-toe.core-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.core :refer :all]))

(describe "ui-names"
  (it "console arguments mapped to namespace and function names"
    (should= {"--console" ['tic-tac-toe.console.core '-main]
              "--desktop" ['tic-tac-toe.desktop.core '-main]
              "--web"     ['tic-tac-toe.web.core '-main]}
             ui-names)))

(describe "print-err"
  (for [arg [nil "" "abcd"]]
    (it (format "writes invalid argument to console: %s" arg)
      (should= (str "Invalid argument: "
                    (if (nil? arg) "nil" arg)
                    "\nOptions:\n"
                    "  --console\n"
                    "  --desktop\n"
                    "  --web\n")
               (with-out-str (print-err arg))))))