# Gobang AI (Five‑in‑a‑Row)

**A console‑based Gomoku engine written in Java 17, featuring an emoji board, human‑vs‑human play and a pluggable minimax AI.**

<p align="center">
  <img src="https://raw.githubusercontent.com/your‑user/your‑repo/main/docs/board_sample.png" width="600" alt="Sample board screenshot"/>
</p>

---

## Features

| Module              | Highlights                                                                                                                                                       |
| ------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Game core**       | 13 × 13 board, constant‑time victory detector scanning eight directions【0†file0†L24-L31】【0†file6†L35-L48】                                                        |
| **Minimax AI**      | Depth‑limited minimax with alpha–beta pruning and heuristic evaluation (threat pairs, open threes, etc.). Switch `opponent` to `0` for Human vs AI, `1` for PvP. |
| **Rich console UI** | Emoji pieces ⚪ / ⚫ and yellow background squares 🟡 for empty cells for a clean visual grid. Screen is cleared and redrawn each turn【0†file6†L62-L70】.           |
| **Move history**    | Each `Player` object stores every coordinate you played and can replay them on demand【0†file3†L33-L38】.                                                          |
| **Stateless API**   | Pure Java classes (`Game`, `Gobang`, `PlayerGame`, `AIEngine`) so you can embed the engine in GUI or server code.                                                |

---

## Project structure

```
src/
 ├── Main.java         // entry‑point & CLI menu
 ├── Gobang.java       // game wrapper – sets board size & mode
 ├── Game.java         // core rules, board printing, win detection
 ├── AIEngine.java     // minimax + evaluation (coming soon)
 ├── Player.java       // player model (human or bot)
 └── ...
```

---

## Getting started

1. **Clone & build**

```bash
$ git clone https://github.com/YOUR_USER/5inARowAI.git
$ cd 5inARowAI/src
$ javac *.java   # requires JDK 11+
$ java Main
```

2. **Choose a mode**

   * `1` – Human vs Human (default)
   * `2` – Human vs AI *(coming soon)*

3. **Enter moves** using *row* and *column* letters separated by a space. Example: `g j` places on row `g`, column `j`.

---

## AI overview

The bot examines the move tree up to a configurable depth and scores leaf positions with a weighted sum of:

* **Open four / three / two** patterns
* **Win / block win** immediacy
* **Center control** bonus

Alpha–beta pruning skips branches that cannot improve the current best score, allowing deeper look‑ahead without timeouts. A Zobrist hash + `TranspositionTable` (future work) will cache evaluations.

---

## Roadmap

* [ ] Finish `AIEngine` integration & difficulty levels
* [ ] Add PGN‑like game export
* [ ] Swing / JavaFX GUI
* [ ] Unit tests with JUnit
* [ ] Continuous Integration (GitHub Actions)

---

## Contributing

Pull requests are welcome! Please open an issue first to discuss major changes. Make sure `./gradlew test` passes and follow the established code‑style.

---

## License

This project is licensed under the MIT License – see [`LICENSE`](LICENSE) for details.
