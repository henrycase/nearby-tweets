(ns nearby_tweets.twitter
  (:require [twitter-streaming-client.core :as client]
            [twitter.oauth :as oauth]))

(def consumer-key (System/getenv "NEARBY_CONSUMER_KEY"))
(def consumer-secret (System/getenv "NEARBY_CONSUMER_SECRET"))
(def user-access-token (System/getenv "NEARBY_ACCESS_TOKEN"))
(def user-access-token-secret (System/getenv "NEARBY_ACCESS_SECRET"))

(def creds (oauth/make-oauth-creds consumer-key
                                   consumer-secret
                                   user-access-token
                                   user-access-token-secret))

;; create the client with a twitter.api streaming method and params of
;; your choice

(defn get-stream
  [southwest northeast]
  (let [bounds (flatten (vector southwest northeast))]
    (fn [] (client/create-twitter-stream
            twitter.api.streaming/statuses-filter
            :oauth-creds creds
            :params {:locations bounds}))))
