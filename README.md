# marcus-hateos-fzf-prototype

## Prerequesites

Please install `fzf`, `bb` and `clj`.

## Usage

First, start the graph provider.

    (cd graph/ && clj -X:serve)

Then, run the UI from a different terminal:

    (cd ui/ && bb -X:tui)
