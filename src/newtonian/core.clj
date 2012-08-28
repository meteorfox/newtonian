(ns newtonian.core
  (:require [newtonian.particle-system :as newt]
            [newtonian.utils :as utils])
  (:import newtonian.utils.Vector2D)
  (:use quil.core)
  (:gen-class))


(defn setup []
  (frame-rate 60)
  (background 8)
  (reset! newt/fields [])
  (reset! newt/particles [])
  (reset! newt/emitters [])
  (newt/add-emitter (Vector2D. 330.0 280.0) (Vector2D. 1.5 1.0))
  (newt/add-field (Vector2D. 380.0 285.0) 5000.0))

(defn before-update [])

(defn update []
  (newt/add-new-particles)
  (newt/update-particles 800 600))

(defn draw-field [{:keys [position]}]
  (let [x (:x position)
        y (:y position)]
    (no-stroke)
    (fill-int (color 255 64 64))
    (ellipse x y 10.0 10.0)))

(defn draw-particle [{:keys [position velocity accel]}]
  (let [x (:x position)
        y (:y position)
        a (utils/mag accel)
        s (utils/mag velocity)]
    (no-stroke)
    (fill-int (color
               166
               (/ (* 220 0.5) s)
               (* 76 s)
               255))
    (ellipse x y 4 4)))

(defn draw []
  (background 8)
  (let [particles @newt/particles
        fields @newt/fields]
   (doseq [p particles]
     (draw-particle p))
   (doseq [f fields]
     (draw-field f))))

(defn after-draw [])

(defn main-loop []
  (before-update)
  (update)
  (draw)
  (after-draw))


(defn newtonian []
  (defsketch newtonian-particles
    :title "Newtonian Particles"
    :setup setup
    :draw main-loop
    :size [800 600]
    :renderer :p2d))

(defn -main [& args]
  (newtonian))
