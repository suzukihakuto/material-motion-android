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
package com.google.android.material.motion.tweens;

import android.animation.TypeEvaluator;
import android.util.Property;

import com.google.android.material.motion.MotionRuntime;
import com.google.android.material.motion.interactions.Tween;

public final class MaterialAnimator {

  public static <O> MaterialAnimator ofFloat(
    O target,
    Property<O, Float> property,
    Float... values) {
    return new MaterialAnimator(
      new Tween<>(
        target,
        property,
        null,
        values,
        null,
        null,
        null,
        null,
        null,
        null,
        null));
  }

  public static <O, T> MaterialAnimator ofObject(
    O target,
    Property<O, T> property,
    TypeEvaluator<T> evaluator,
    T... values) {
    return new MaterialAnimator(
      new Tween<>(
        target,
        property,
        evaluator,
        values,
        null,
        null,
        null,
        null,
        null,
        null,
        null));
  }

  public final Tween<?, ?> tween;

  private MaterialAnimator(Tween<?, ?> tween) {
    this.tween = tween;
  }

  public void setEvaluator(TypeEvaluator evaluator) {
    //noinspection unchecked
    tween.evaluator.write(evaluator);
  }

  public void setFloatValues(Float... values) {
    //noinspection unchecked
    ((Tween<?, Float>) tween).values.write(values);
  }

  @SafeVarargs
  public final <T> void setObjectValues(T... values) {
    //noinspection unchecked
    ((Tween<?, T>) tween).values.write(values);
  }

  public void setDuration(long duration) {
    //noinspection unchecked
    tween.duration.write(duration);
  }

  public <O> void start(MotionRuntime runtime) {
    //noinspection unchecked
    runtime.addInteraction(((Tween<O, ?>) tween), (O) tween.target);
  }
}
