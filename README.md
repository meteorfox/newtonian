# Newtonian

Particle System in Clojure using Quil.

Still work in progress.

Based on https://github.com/jsoverson/JavaScript-Particle-System

## Usage

	lein deps
	lein run

or,

	For interactive mode:

	lein deps
	lein repl


	(use 'newtonian.particle-system)
	(use 'newtonian.utils)
	(import 'newtonian.utils.Vector2D)

	;; Example

	;; To add repelling field, use negative mass

	(add-field (Vector2D. 280.0 285.0) -100.0)

	;; for attracting field, use positive mass

	(add-field (Vector2D. 280.0 285.0) 100.0)

	;; To remove last field added
	(swap! fields pop)

	;; To add particle emitter
	(add-emitter (Vector2D. 330.0 280.0) (Vector2D. 1.5 1.0))

	;; To remove last particle emitter added
	(swap! emitters pop)



