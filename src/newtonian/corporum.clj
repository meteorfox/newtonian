(ns newtonian.corporum
  (:require [newtonian.utils :as utils])
  (:import [newtonian.utils Vector2D]))

(defprotocol Emission
  (emit [this] "Emits a Particle with the ParticleEmitter attributes"))

(defprotocol Kinematics
  "Movements of bodies"
  (move [b] [b p] "Moves body one time step"))

(defprotocol FieldForce
  "Force influence by the field"
  (influence [field particle] "Field's influence on a particle"))

(defrecord Particle [#^Vector2D position #^Vector2D velocity #^Vector2D accel
                     #^int age #^int death])

(defrecord ParticleEmitter [#^Vector2D position #^Vector2D velocity
                            #^int size #^int life #^double spread #^int emission-rate])

(defrecord Field [#^Vector2D position #^int size #^double mass])

(extend-protocol Emission
  ParticleEmitter
  (emit [this]
    (Particle. (:position this)
               (utils/mk-vec (utils/mag (:velocity this))
                             (+ (utils/angle (:velocity this))
                                (- (:spread this)
                                   (* (rand) (:spread this) 2.0))))
               (Vector2D. 0.0 0.0)
               0 (:life this))))

(extend-protocol Kinematics
  Particle
  (move [b]
    (let [vel (utils/add (:velocity b) (:accel b))
          pos (utils/add (:position b) (:velocity b))]
      (assoc b :velocity vel :position pos)))
  ParticleEmitter
  (move [b p]
    (ParticleEmitter. p (:velocity b) (:size b)
                      (:life b) (:spread b) (:emission-rate b))))


(extend-protocol FieldForce
  Particle
  (influence [p f]
    (let [dist (utils/sub (:position f) (:position p))
          x-comp (:x dist)
          y-comp (:y dist)
          field-mass (:mass f)
          old-accel (:accel p)
          force (/ field-mass
                   (Math/pow
                    (+ (* x-comp x-comp) (/ field-mass 2.0)
                       (* y-comp y-comp) (/ field-mass 2.0))
                    1.5))]
      (assoc p :accel (Vector2D. (+ (:x old-accel) (* x-comp force))
                                 (+ (:y old-accel) (* y-comp force))))))
  Field
  (influence [f p]
    (influence p f)))

(defn mk-particle-emitter [#^Vector2D position #^Vector2D velocity]
  (ParticleEmitter. position velocity 8 -1 (/ Math/PI 32) 4))
