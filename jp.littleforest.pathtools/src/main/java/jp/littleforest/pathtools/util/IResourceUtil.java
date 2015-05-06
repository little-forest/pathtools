/******************************************************************************
 * Copyright (c) 2011-2012 Yusuke Komori. All rights reserved.
 *
 * This program and the accompanying materials are made available under
 * the terms of the Eclipse Public License v1.0 which accompanies
 * this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *****************************************************************************/
package jp.littleforest.pathtools.util;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;

/**
 * Eclipse 上のリソースを扱うためのユーティリティクラスです。<br />
 *
 * @author y-komori
 */
public class IResourceUtil {
    private IResourceUtil() {

    }

    public static final <T extends IAdaptable> T getAdaptable(IAdaptable adaptable, Class<T> clazz) {
        return clazz.cast(adaptable.getAdapter(clazz));

    }

    public static final String toOSPath(IResource resource) {
        return resource.getLocation().toOSString();
    }

    public static final IWorkspaceRoot getWorkspaceRoot() {
        return ResourcesPlugin.getWorkspace().getRoot();
    }
}
