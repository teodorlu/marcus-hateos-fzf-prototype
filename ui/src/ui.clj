(ns ui
  (:require
   [babashka.process :as process]
   [clojure.edn :as edn]
   [clojure.string :as str]
   [cheshire.core :as json]
   [babashka.http-client :as http]
   [clojure.pprint :as pprint :refer [pprint]]))

(defn fzf
  "Choose a thing with fzf

  (fzf [\"apples\" \"pears\" \"pizza\")
  ;; => \"apples\"     ; depending on the user's choice!

  returns nil on failure."
  [choices]
  (let [fzf-result (process/shell {:out :string
                                   :in (str/join "\n" choices)
                                   :continue true}
                                  "fzf")]
    (when (= 0 (:exit fzf-result))
      (str/trim (:out fzf-result)))))

(defn less [s]
  (process/shell {:in s} "less"))

(defn edn-parse-orelse [s orelse]
  (try (edn/read-string s)
       (catch Exception _ orelse)))

(defn fzf-edn [choices default]
  (let [next (fzf (map pr-str choices))]
    (edn-parse-orelse next default)))

(defn walk-show-loop-with-exit
  [start show next-loc quit?]
  (loop [loc start]
    (show loc)
    (when-let [next-loc (fzf-edn (next-loc loc) nil)]
      (when-not (quit? next-loc)
        (recur next-loc)))))

(defn graph-get [uri]
  (json/parse-string (:body (http/get (str "http://localhost:7300" uri)
                                      {:throw false}))
                     true))

(defn pprint-str [x]
  (with-out-str (pprint x)))

(defn prepend-quit [xs]
  (into [:quit] xs))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defn browse [_opts]
  (let [start {:uri "/"}
        show (comp less pprint-str graph-get :uri)
        next-loc (comp prepend-quit :links graph-get :uri)
        quit? (fn [loc] (= :quit loc))]
    (walk-show-loop-with-exit start show next-loc quit?)))
