;; This Source Code Form is subject to the terms of the Mozilla Public
;; License, v. 2.0. If a copy of the MPL was not distributed with this
;; file, You can obtain one at http://mozilla.org/MPL/2.0/.
;;
;; Copyright (c) UXBOX Labs SL

(ns app.common.geom.shapes.corners)

(defn- truncate-side
  [shape ra-attr rb-attr dimension-attr]
  (let [ra        (ra-attr shape)
        rb        (rb-attr shape)
        dimension (dimension-attr shape)]
    (if (<= (+ ra rb) dimension)
      [ra rb]
      [(/ (* ra dimension) (+ ra rb))
       (/ (* rb dimension) (+ ra rb))])))

(defn shape-corners-1
  [{:keys [width height] :as shape}]
  (let [max-radius (/ (min width height) 2)]
    (min (:rx shape 0) max-radius)))

(defn shape-corners-4
  [shape]
  (let [[r-top-left r-top-right]
        (truncate-side shape :r1 :r2 :width)

        [r-right-top r-right-bottom]
        (truncate-side shape :r2 :r3 :height)

        [r-bottom-right r-bottom-left]
        (truncate-side shape :r3 :r4 :width)

        [r-left-bottom r-left-top]
        (truncate-side shape :r4 :r1 :height)]

    [(min r-top-left r-left-top)
     (min r-top-right r-right-top)
     (min r-right-bottom r-bottom-right)
     (min r-bottom-left r-left-bottom)]))
