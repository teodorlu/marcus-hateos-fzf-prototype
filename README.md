# marcus-hateos-fzf-prototype

## Prerequesites

Please install `fzf`, `bb` and `clj` and a Java version (latest LTS is a good choice).

## Usage

First, start the graph provider.

    (cd graph/ && clj -X:serve)

Then run the UI in a different terminal

    (cd ui/ && bb -x ui/browse)
