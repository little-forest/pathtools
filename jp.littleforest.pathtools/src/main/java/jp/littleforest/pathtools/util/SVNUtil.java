/******************************************************************************
 * Copyright (c) 2011-2016 Yusuke Komori. All rights reserved.
 *
 * This program and the accompanying materials are made available under
 * the terms of the Eclipse Public License v1.0 which accompanies
 * this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *****************************************************************************/

package jp.littleforest.pathtools.util;

import java.util.Optional;

import org.eclipse.core.resources.IResource;
import org.eclipse.team.svn.core.resource.ILocalResource;
import org.eclipse.team.svn.core.svnstorage.SVNRemoteStorage;

/**
 * SVNに関する情報を取得するためのユーティリティです。<br>
 *
 * @author y-komori
 */
public class SVNUtil {
    private static final String SVN_REMOTE_STRAGE_CLASS = "org.eclipse.team.svn.core.svnstorage.SVNRemoteStorage";
    private static final String FORMAT = "{%d} ";

    private SVNUtil() {
    }

    public static Optional<Long> getRevision(Object element) {
        if (element instanceof IResource) {
            return getRevision((IResource) element);
        } else {
            return Optional.empty();
        }
    }

    public static Optional<Long> getRevision(IResource resource) {
        SVNRemoteStorage storage = getSVNRemoteStorate();
        if (storage == null) {
            return Optional.empty();
        }

        ILocalResource local = storage.asLocalResource(resource);
        if (local != null) {
            long rev = local.getRevision();
            if (rev >= 0) {
                return Optional.of(local.getRevision());
            }
        }
        return Optional.empty();
    }

    public static String getRevisionString(IResource resource) {
        StringBuilder buf = new StringBuilder();
        getRevision(resource).ifPresent(r -> buf.append(String.format(FORMAT, r)));
        return buf.toString();
    }

    public static String getRevisionString(Object element) {
        StringBuilder buf = new StringBuilder();
        getRevision(element).ifPresent(r -> buf.append(String.format(FORMAT, r)));
        return buf.toString();
    }

    private static SVNRemoteStorage getSVNRemoteStorate() {
        try {
            Class.forName(SVN_REMOTE_STRAGE_CLASS);
            return SVNRemoteStorage.instance();
        } catch (ClassNotFoundException e) {
            return null;
        }
    }
}
