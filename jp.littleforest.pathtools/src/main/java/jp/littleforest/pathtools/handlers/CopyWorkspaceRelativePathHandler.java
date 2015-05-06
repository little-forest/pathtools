/******************************************************************************
 * Copyright (c) 2011-2012 Yusuke Komori. All rights reserved.
 *
 * This program and the accompanying materials are made available under
 * the terms of the Eclipse Public License v1.0 which accompanies
 * this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *****************************************************************************/
package jp.littleforest.pathtools.handlers;

import static jp.littleforest.pathtools.Constants.*;

import java.util.List;

import org.eclipse.core.resources.IResource;

/**
 * 指定されたリソースのワークスペース内相対パスをクリップボードにコピーするハンドラです。<br />
 * 
 * @author y-komori
 */
public class CopyWorkspaceRelativePathHandler extends AbstractPathHandler {
    /* (non-Javadoc)
     * @see jp.littleforest.pathtools.handlers.AbstractPathHandler#getPath(java.util.List)
     */
    @Override
    protected String getPath(List<IResource> resources) {
        StringBuilder buf = new StringBuilder(128);
        for (IResource resource : resources) {
            String workspacePath = resource.getWorkspace().getRoot().getLocation()
                    .toPortableString();
            String resourcePath = resource.getLocation().toPortableString();
            String relPath = resourcePath.substring(workspacePath.length());
            buf.append(relPath);
            buf.append(SEP);
        }
        return buf.toString();
    }
}
