/******************************************************************************
 * Copyright (c) 2011-2017 Yusuke Komori. All rights reserved.
 *
 * This program and the accompanying materials are made available under
 * the terms of the Eclipse Public License v1.0 which accompanies
 * this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *****************************************************************************/

package jp.littleforest.pathtools;

import org.eclipse.osgi.util.NLS;

/**
 * @author y-komori
 *
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME = "jp.littleforest.pathtools.messages"; //$NON-NLS-1$

    public static String PATHTOOLS_DISPLAY_OPEN_IN_MENU;

    public static String PATHTOOLS_OPEN_FOLDER_CMD;

    public static String PATHTOOLS_OPEN_PROMPT_CMD;

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
