(ns graph)

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
                         {:uri "/deployments/def"}]}}
