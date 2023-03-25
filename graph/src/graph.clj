(ns graph
  (:require
   [org.httpkit.server :as httpkit]
   [cheshire.core :as json]))

;; graph

(def graph
  {"/apps/abc" {:uri "/apps/abc"
                :title "Unicad Discovery"
                :links [{:uri "/deployments/def"}
                        {:uri "/builds/123"}]}
   "/deployments/def" {:uri "/deployments/def"
                       :title "Unicad Discovery: deployment 1"
                       :links [{:uri "/apps/abc"}
                               {:uri "/builds/123"}]}
   "/builds/123"  {:uri "/builds/123"
                   :title "Unicad Discovery: build 1"
                   :links [{:uri "/apps/abc"}
                           {:uri "/deployments/def"}]}})
;; Serve & browse the graph

(defn links [graph]
  (for [k (keys graph) ]
    {:uri k}))

(defn ok [body]
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body (json/generate-string body)})

(defn not-found [body]
  {:status 404
   :headers {"Content-Type" "application/json"}
   :body (json/generate-string body)})

(defonce x (atom nil))

@x


(defn handle [{:keys [uri] :as req}]
  (reset! x req)
  (cond
    (= uri "/")
    (ok {:uri "/"
         :links (links graph)})

    (contains? graph uri)
    (ok (get graph uri))

    :else
    (not-found {:uri uri
                :links (links graph)})))

;; REPL helpers / boilerplate

(defonce server (atom nil))
(def port 7300)
(defn stop-server [stop-fn] (when stop-fn (stop-fn)) nil)
(defn stop [] (swap! server stop-server))
(defn start [_opts]
  (swap! server (fn [old-server]
                  (stop-server old-server)
                  (println (str "graph running: http://localhost:" port))
                  (httpkit/run-server #'handle {:port port}))))
