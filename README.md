# Newtonian

Particle System in Clojure using Quil.

![Particles](http://img402.imageshack.us/img402/1473/screenshotfrom201208272.png)

Still work in progress.

Based on https://github.com/jsoverson/JavaScript-Particle-System


# Live performance

http://www.youtube.com/watch?v=xiqWclsXdcc


## Usage

	lein deps
	lein run

or,

	For interactive mode:

	lein deps
	lein repl (or if in Emacs, nrepl-jack-in, or clojure-jack-in)


	(use 'newtonian.particle-system)
	(require 'newtonian.utils)
	(import 'newtonian.utils.Vector2D)

        ;; Start 

	(-main)

	;; Things to play with

	;; To add repelling field, use negative mass

	(add-field (Vector2D. 280.0 285.0) -100.0)

	;; for attracting field, use positive mass

	(add-field (Vector2D. 280.0 285.0) 500.0)

	;; To remove last field added
	(swap! fields pop)

	;; To add particle emitter
	(add-emitter (Vector2D. 330.0 280.0) (Vector2D. 1.5 1.0))

	;; To remove last particle emitter added
	(swap! emitters pop)

## TODO

* Drag-and-drop support for Paritcle Emitters and Fields
* Dynamic sizing and coloring of particles with GUI
* Dynamic force field adjustment with GUI.
* Improve documentation
* Settings to change frame rate, window size, renderer, etc
* Preferences to change background color, number of particles, color of fields and emitters.
* Collapse particle system state in a map, to allow multiple particles systems. ???
* ~~Aggregate force of all fields in particles, right now one field influence at a time~~
