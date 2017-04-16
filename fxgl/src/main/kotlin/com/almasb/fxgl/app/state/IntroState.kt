/*
 * The MIT License (MIT)
 *
 * FXGL - JavaFX Game Library
 *
 * Copyright (c) 2015-2017 AlmasB (almaslvl@gmail.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.almasb.fxgl.app.state

import com.almasb.fxgl.app.ApplicationState
import com.almasb.fxgl.app.FXGL
import com.almasb.fxgl.core.event.Subscriber
import com.almasb.fxgl.scene.IntroScene
import com.almasb.fxgl.scene.intro.FXGLIntroScene
import com.almasb.fxgl.scene.intro.IntroFinishedEvent

/**
 *
 *
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
object IntroState : AbstractAppState(FXGLIntroScene()) {

    private lateinit var introFinishedSubscriber: Subscriber

    override fun onEnter(prevState: State) {
        when(prevState) {
            is StartupState -> {
                introFinishedSubscriber = FXGL.getEventBus().addEventHandler(IntroFinishedEvent.ANY, { onIntroFinished() })

                (scene as IntroScene).startIntro()
            }

            else -> throw IllegalArgumentException("Entered IntroState from illegal state: $prevState")
        }
    }

    override fun onExit() {
        introFinishedSubscriber.unsubscribe()
    }

    private fun onIntroFinished() {
        if (FXGL.getSettings().isMenuEnabled) {
            FXGL.getApp().setState(ApplicationState.MAIN_MENU)
        } else {
            FXGL.getApp().startNewGame()
        }
    }

    override fun onUpdate(tpf: Double) {

    }
}