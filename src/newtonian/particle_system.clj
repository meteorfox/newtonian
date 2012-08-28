(ns newtonian.particle-system
  (:require [newtonian.corporum :as corporum]
            [newtonian.utils :as utils])
  (:import (newtonian.corporum ParticleEmitter Particle Field)
           (newtonian.utils Vector2D)))

(def max-particles (atom 2000))

(def draw-options (atom {:objects true
                         :accelerations false
                         :velocities false
                         :particles true}) )

(def particles (atom []))

(def emitters (atom []))

(def fields (atom []))


(defn add-emitter [#^Vector2D coord #^Vector2D velocity]
  (swap! emitters conj (corporum/mk-particle-emitter coord velocity)))

(defn add-field [#^Vector2D coord #^double mass]
  (swap! fields conj (Field. coord 8 mass)))

(defn add-new-particles []
  (if (< (count @particles) @max-particles)
    (let [emitters @emitters
          new-particles (for [emitter emitters
                              _ (range (:emission-rate emitter))]
                          (corporum/emit emitter))]
      (swap! particles into new-particles))))

(defn submit-to-fields [p fields]
  (reduce corporum/influence
          (assoc p :accel (Vector2D. 0.0 0.0))
          fields))

(defn out-of-bounds? [p x-bounds y-bounds]
  (let [position (:position p)
        x (:x position)
        y (:y position)]
    (or (neg? x) (> x x-bounds) (neg? y) (> y y-bounds))))


(defn update-particle* [p]
  (let [flds @fields]
    (->> (submit-to-fields p @fields)
         (corporum/move))))

(defn update-particles*
  "Updates each particle new position, and removes 'dead' and/or
   out of bounds particles"
  [old-particles x-bounds y-bounds]
  (let [updated-particles (for [p old-particles :let [p (assoc p :age (inc (:age p)))]
                                :when (or (<= (:death p) 0)
                                          (< (:age p) (:death p)))]
                            (update-particle* p))]
    (remove #(out-of-bounds? % x-bounds y-bounds) updated-particles)))

(defn update-particles [x-bounds y-bounds]
  (swap! particles update-particles* x-bounds y-bounds))

(defn on-before-update [])

(defn on-update [])

(defn on-draw [])

(defn on-after-draw [])
