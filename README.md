# marcus-hateos-fzf-prototype

## Prerequesites

Please install `fzf`, `bb` and `clj` and a Java version (latest LTS is a good choice).

| tool     | where                                      |
|----------|--------------------------------------------|
| fzf      | https://github.com/junegunn/fzf            |
| babashka | https://babashka.org/                      |
| clojure  | https://clojure.org/guides/install_clojure |
| java     | https://adoptium.net/                      |

## Usage

First, start the graph provider.

    (cd graph/ && clj -X graph/start)

Then run the UI in a different terminal

    (cd ui/ && bb -x ui/browse)
