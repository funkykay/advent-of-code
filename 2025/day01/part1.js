import { createReadStream } from "node:fs";
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

    if (dir === "L") {
        pos = pos - dist;
    } else if (dir === "R") {
        pos = pos + dist;
    } else {
        rl.close();
        throw new Error(`Ungültige Drehrichtung "${dir}" in Zeile: "${trimmed}". Erlaubt sind nur "L" oder "R".`);
    }

    pos = ((pos % 100) + 100) % 100;

    if (pos === 0) {
        count = count + 1;
    }
});

rl.on("close", () => {
    console.log(count);
});

rl.on("error", (err) => {
    console.error("Fehler:", err.message);
    process.exit(1);
});
