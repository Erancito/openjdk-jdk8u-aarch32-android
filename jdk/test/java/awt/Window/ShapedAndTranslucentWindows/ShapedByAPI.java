/*
 * Copyright (c) 2010, 2014, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

import java.awt.*;


/*
 * @test
 * @summary Check if dynamically shaped window is moved and resized
 *          using API correctly
 * @author Dmitriy Ermashov (dmitriy.ermashov@oracle.com)
 * @library ../../../../lib/testlibrary
 * @run main ShapedByAPI
 */
public class ShapedByAPI extends Common {

    public static void main(String[] args) throws Exception {
        if (checkTranslucencyMode(GraphicsDevice.WindowTranslucency.PERPIXEL_TRANSPARENT))
            for (Class<Window> windowClass: WINDOWS_TO_TEST){
                new ShapedByAPI(windowClass).doTest();
            }
    }

    public ShapedByAPI(Class windowClass) throws Exception{
        super(windowClass);
    }
    public void applyShape(){ applyDynamicShape(); }

    public void doTest() throws Exception{
        super.doTest();

        checkDynamicShape();

        EventQueue.invokeAndWait(() -> {
            Point location = window.getLocationOnScreen();
            location.translate(random.nextInt(dl), random.nextInt(dl));
            window.setLocation(location);
        });
        robot.waitForIdle(delay);
        checkDynamicShape();

        EventQueue.invokeAndWait(() -> {
            Dimension size = window.getSize();
            window.setSize(size.width+random.nextInt(2*dl)-dl, size.height+random.nextInt(2*dl)-dl);
        });
        robot.waitForIdle(delay);
        checkDynamicShape();

        dispose();
    }
}
