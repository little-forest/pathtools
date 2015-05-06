/******************************************************************************
 * Copyright (c) 2011-2012 Yusuke Komori. All rights reserved.
 *
 * This program and the accompanying materials are made available under
 * the terms of the Eclipse Public License v1.0 which accompanies
 * this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *****************************************************************************/
package jp.littleforest.pathtools.util;

import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Display;

/**
 * @author y-komori
 *
 */
public class SWTUtil {
    private SWTUtil() {

    }

    /**
     * 指定された文字列をクリップボードへコピーします。<br />
     * 
     * @param text
     *            文字列
     */
    public static void copyToClipboard(String text) {
        if (text == null) {
            return;
        }
        Display display = Display.getCurrent();
        if (display != null) {
            Clipboard clipboard = new Clipboard(display);
            clipboard.setContents(new Object[] { text },
                    new Transfer[] { TextTransfer.getInstance() });
        }
    }
}
