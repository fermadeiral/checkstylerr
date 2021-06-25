/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2013, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jboss.as.controller.access;

/**
 * Policy for combining multiple permissions.
 */
public enum CombinationPolicy {

    /** If multiple permissions for the same action exist, if any of them allow the action
     * the action should be allowed. */
    PERMISSIVE("permissive"),
//    /** If multiple permissions with the same action exist, the action should be allowed only if all of them
//     * allow the action. */
//    RESTRICTIVE,
    /** If multiple permissions for the same action exist an exception should be thrown and the action should not
     * be allowed. */
    REJECTING("rejecting");

    private final String toString;

    private CombinationPolicy(String toString) {
        this.toString = toString;
    }

    @Override
    public String toString() {
        return toString;
    }
}
