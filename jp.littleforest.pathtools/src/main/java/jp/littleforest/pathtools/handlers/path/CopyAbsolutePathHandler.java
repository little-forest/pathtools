/******************************************************************************
 * Copyright (c) 2011-2012 Yusuke Komori. All rights reserved.
 *
 * This program and the accompanying materials are made available under
 * the terms of the Eclipse Public License v1.0 which accompanies
 * this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *****************************************************************************/
package jp.littleforest.pathtools.handlers.path;

import static jp.littleforest.pathtools.Constants.*;

import java.util.List;

import org.eclipse.core.resources.IResource;

/**
 * 絶対パスをクリップボードにコピーするためのハンドラです。<br />
 * 
 * @author y-komori
 */
public class CopyAbsolutePathHandler extends AbstractPathHandler {
    /* (non-Javadoc)
     * @see jp.littleforest.pathtools.handlers.AbstractPathHandler#getPath(java.util.List)
     */
    @Override
    protected String getPath(List<IResource> resources) {
        StringBuilder buf = new StringBuilder(128);
        for (IResource resource : resources) {
            String path = resource.getLocation().toOSString();
            buf.append(path);
            buf.append(SEP);
        }
        return buf.toString();
    }

    protected String getResourcePath(List<IResource> resources) {
        StringBuilder buf = new StringBuilder(128);
        for (IResource resource : resources) {
            String path = resource.getLocation().toOSString();
            buf.append(path);
            buf.append(SEP);
        }
        return buf.toString();
    }
}
