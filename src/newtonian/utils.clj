(ns newtonian.utils)

(defprotocol Arithmetic
  "Basic arithmetic operations"
  (add [a b] "Addition")
  (sub [a b] "Substraction")
  (mul [v s] "Scalar Multiplication")
  (div [v s] "Scalar Division"))

(defprotocol VectorMath
  "Basic vector math"
  (cross [a b] "Cross Product")
  (dot [a b] "Dot Product")
  (mag [this] "Magnitude")
  (unit [this] "Normalized vector")
  (angle [this] "Angle"))

(defprotocol Randomness
  "Randomness for Jittering"
  (jitter [this jitter-amount]))

(defrecord Vector2D [#^double x  #^double y]
  Arithmetic
  (add [a b]
    (Vector2D. (+ (:x a) (:x b)) (+ (:y a) (:y b))))
  (sub [a b]
    (Vector2D. (- (:x a) (:x b)) (- (:y a) (:y b))))
  (mul [v s]
    (Vector2D. (* (:x v) s) (* (:y v) s)))
  (div [v s]
    (Vector2D. (/ (:x v) s) (/ (:y v) s)))
  VectorMath
  (cross [a b]
    (- (* (:x a) (:y b)) (* (:y a) (:x b))))
  (dot [a b]
    (+ (* (:x a) (:x b)) (* (:y a) (:y b))))
  (mag [this]
    (Math/sqrt (+ (Math/pow (:x this) 2.0) (Math/pow (:y this) 2.0))))
  (unit [this]
    (let [magnitude (mag this)]
      (Vector2D. (/ (:x this) magnitude) (/ (:y this) magnitude))))
  (angle [this]
    (let [x-comp (:x this) y-comp (:y this)]
      (cond
        (and (zero? x-comp) (zero? y-comp)) (rand (* 2.0 Math/PI))
        (and (pos? x-comp) (zero? y-comp)) 0.0
        (and (neg? x-comp) (zero? y-comp)) Math/PI
        (and (zero? x-comp) (pos? y-comp)) (/ Math/PI 2.0)
        (and (zero? x-comp) (neg? y-comp)) (/ (* 3.0 Math/PI) 2.0)
        :else (let [pos-x-comp (pos? x-comp)
                    pos-y-comp (pos? y-comp)
                    [offset ratio] (if pos-x-comp ;; then
                                     (if pos-y-comp ;; then
                                       [0.0 (/ y-comp x-comp)] ;; else
                                       [(/ (* Math/PI 3.0) 2.0) (/ x-comp y-comp)])
                                     ;;else
                                     (if pos-y-comp ;; then
                                       [(/ Math/PI 2.0) (/ x-comp y-comp)] ;;else
                                       [Math/PI (/ y-comp x-comp)]))]
                (+ (Math/atan (Math/abs ratio)) offset)))))
  Randomness
  (jitter [this jitter-amount]
    (Vector2D. (+ (:x this) (* (:x this) jitter-amount (rand)))
               (+ (:y this) (* (:y this) jitter-amount (rand))))))


(defn mk-vec [#^double magnitude #^double orientation]
  (Vector2D. (* magnitude (Math/cos orientation))
             (* magnitude (Math/sin orientation))))
