/*
 * Copyright 2017-present The Material Motion Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.android.material.motion.interactions;

import android.view.View;

import com.google.android.material.motion.ConstraintApplicator;
import com.google.android.material.motion.Interaction;
import com.google.android.material.motion.MotionObservable;
import com.google.android.material.motion.MotionRuntime;
import com.google.android.material.motion.gestures.GestureInteraction;
import com.google.android.material.motion.gestures.GestureRecognizer;
import com.google.android.material.motion.properties.ViewProperties;

import static com.google.android.material.motion.gestures.GestureRecognizer.CHANGED;
import static com.google.android.material.motion.operators.Anchored.anchored;
import static com.google.android.material.motion.operators.OnRecognitionState.onRecognitionState;
import static com.google.android.material.motion.operators.Pivot.pivot;

public class AdjustsAnchorPoint extends Interaction<View, Void> {

  private final Pinchable pinchable;
  private final Rotatable rotatable;

  public AdjustsAnchorPoint(Pinchable pinchable, Rotatable rotatable) {
    this.pinchable = pinchable;
    this.rotatable = rotatable;
  }

  @Override
  public void apply(MotionRuntime runtime, View target, ConstraintApplicator<Void> constraints) {
    apply(runtime, target, pinchable);
    apply(runtime, target, rotatable);
  }

  private void apply(
    MotionRuntime runtime,
    View target,
    GestureInteraction<? extends GestureRecognizer, ?> interaction) {
    MotionObservable<? extends GestureRecognizer> gestureStream =
      interaction.gestureStream.compose(onRecognitionState(CHANGED));

    runtime.write(gestureStream.compose(pivot()), target, ViewProperties.PIVOT);
    runtime.write(gestureStream.compose(anchored(target)), target, ViewProperties.ANCHOR_POINT_ADJUSTMENT);
  }
}
