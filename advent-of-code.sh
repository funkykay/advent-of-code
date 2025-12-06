#!/usr/bin/env bash

set -e

if [ $# -ne 1 ]; then
    echo "Usage: $0 <script-datei>"
    exit 1
fi

SCRIPT_PATH="$1"

if [ ! -f "$SCRIPT_PATH" ]; then
    echo "Datei nicht gefunden: $SCRIPT_PATH"
    exit 1
fi

EXT="${SCRIPT_PATH##*.}"

run_js() {
    if ! command -v nvm >/dev/null 2>&1; then
        if [ -s "$HOME/.nvm/nvm.sh" ]; then
            . "$HOME/.nvm/nvm.sh"
        fi
    fi

    if ! command -v nvm >/dev/null 2>&1; then
        echo "nvm nicht verfügbar"
        exit 1
    fi

    NODE_VERSION="$(cat .nvmrc | tr -d '[:space:]')"
    nvm use "$NODE_VERSION" >/dev/null

    node "$SCRIPT_PATH"
}

run_py() {
    if ! command -v pyenv >/dev/null 2>&1; then
        echo "pyenv nicht verfügbar"
        exit 1
    fi

    PY_VERSION="$(cat .python-version | tr -d '[:space:]')"
    PYENV_VERSION="$PY_VERSION" python "$SCRIPT_PATH"
}

run_go() {
    if ! command -v goenv >/dev/null 2>&1; then
        echo "goenv nicht verfügbar"
        exit 1
    fi

    GO_VERSION="$(cat .go-version | tr -d '[:space:]')"
    eval "$(goenv init -)"
    export GOENV_VERSION="$GO_VERSION"

    go run "$SCRIPT_PATH"
}

run_java() {
    if [ ! -f .sdkmanrc ]; then
        echo ".sdkmanrc nicht gefunden"
        exit 1
    fi

    SDKMAN_DIR="${SDKMAN_DIR:-$HOME/.sdkman}"
    if [ -s "$SDKMAN_DIR/bin/sdkman-init.sh" ]; then
        . "$SDKMAN_DIR/bin/sdkman-init.sh"
    else
        echo "SDKMAN nicht verfügbar"
        exit 1
    fi

    # java-Version aus .sdkmanrc lesen, z.B. java=25.0.1-sem
    JAVA_VERSION="$(grep -E '^[[:space:]]*java=' .sdkmanrc | tail -n 1 | cut -d'=' -f2- | tr -d '[:space:]')"

    if [ -z "$JAVA_VERSION" ]; then
        echo "Keine java-Version in .sdkmanrc gefunden"
        exit 1
    fi

    sdk use java "$JAVA_VERSION" >/dev/null
    java "$SCRIPT_PATH"
}

case "$EXT" in
    js)   run_js ;;
    mjs)  run_js ;;
    py)   run_py ;;
    go)   run_go ;;
    java) run_java ;;
    *)
        echo ".$EXT wird nicht unterstützt (js mjs py go java)"
        exit 1
        ;;
esac
