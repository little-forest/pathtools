/******************************************************************************
 * Copyright (c) 2011-2015 Yusuke Komori. All rights reserved.
 *
 * This program and the accompanying materials are made available under
 * the terms of the Eclipse Public License v1.0 which accompanies
 * this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *****************************************************************************/

package jp.littleforest.pathtools.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import jp.littleforest.pathtools.PathToolsPlugin;
import jp.littleforest.pathtools.util.EclipseFontUtil;

/**
 * @author y-komori
 *
 */
public class DecreaseFontHandler extends AbstractHandler {
    @Override
    public Object execute(ExecutionEvent arg0) throws ExecutionException {
        PathToolsPlugin.logInfo("DecreaseFontHandler:execute");
        EclipseFontUtil.decreaseFont();
        return null;
    }

}
