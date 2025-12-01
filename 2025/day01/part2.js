import {createReadStream} from "node:fs";
import readline from "node:readline";

const rl = readline.createInterface({
    input: createReadStream(new URL("./input.txt", import.meta.url), {
        encoding: "utf-8"
    }),
    crlfDelay: Infinity
});

let pos = 50;
let count = 0;

rl.on("line", (line) => {
    const trimmed = line.trim();

    if (trimmed.length === 0) {
        return;
    }

    const dir = trimmed[0];
    const dist = Number(trimmed.slice(1));

    if (Number.isNaN(dist)) {
        rl.close();
        throw new Error(`Ungültige Zahl in Zeile: "${trimmed}"`);
    }

    if (dist < 0) {
        rl.close();
        throw new Error(`Negative Drehung nicht erlaubt in Zeile: "${trimmed}"`);
    }

    let stepDirection;

    if (dir === "L") {
        stepDirection = -1;
    } else if (dir === "R") {
        stepDirection = 1;
    } else {
        rl.close();
        throw new Error(
            `Ungültige Drehrichtung "${dir}" in Zeile: "${trimmed}". Erlaubt sind nur "L" oder "R".`
        );
    }

    // Anzahl der Klicks berechnen, die auf 0 landen
    if (dist > 0) {
        let firstHitIndex;

        if (stepDirection === 1) {
            // nach rechts
            if (pos === 0) {
                firstHitIndex = 100;
            } else {
                firstHitIndex = 100 - pos;
            }
        } else {
            // nach links
            if (pos === 0) {
                firstHitIndex = 100;
            } else {
                firstHitIndex = pos;
            }
        }

        if (firstHitIndex >= 1 && firstHitIndex <= dist) {
            const hits = Math.floor((dist - firstHitIndex) / 100) + 1;
            count = count + hits;
        }
    }

    // Endposition aktualisieren
    pos = pos + stepDirection * dist;
    pos = ((pos % 100) + 100) % 100;
});

rl.on("close", () => {
    console.log(count);
});

rl.on("error", (err) => {
    console.error("Fehler:", err.message);
    process.exit(1);
});
