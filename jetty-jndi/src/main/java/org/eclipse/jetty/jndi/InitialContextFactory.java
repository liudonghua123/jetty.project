//
// ========================================================================
// Copyright (c) 1995-2020 Mort Bay Consulting Pty Ltd and others.
//
// This program and the accompanying materials are made available under
// the terms of the Eclipse Public License 2.0 which is available at
// https://www.eclipse.org/legal/epl-2.0
//
// This Source Code may also be made available under the following
// Secondary Licenses when the conditions for such availability set
// forth in the Eclipse Public License, v. 2.0 are satisfied:
// the Apache License v2.0 which is available at
// https://www.apache.org/licenses/LICENSE-2.0
//
// SPDX-License-Identifier: EPL-2.0 OR Apache-2.0
// ========================================================================
//

package org.eclipse.jetty.jndi;

import java.util.Hashtable;
import java.util.Properties;
import javax.naming.CompoundName;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameParser;
import javax.naming.NamingException;

import org.eclipse.jetty.jndi.local.localContextRoot;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/**
 * InitialContextFactory.java
 *
 * Factory for the default InitialContext.
 * Created: Tue Jul  1 19:08:08 2003
 *
 * @version 1.0
 */
public class InitialContextFactory implements javax.naming.spi.InitialContextFactory
{
    private static final Logger LOG = Log.getLogger(InitialContextFactory.class);

    public static class DefaultParser implements NameParser
    {
        static Properties syntax = new Properties();

        static
        {
            syntax.put("jndi.syntax.direction", "left_to_right");
            syntax.put("jndi.syntax.separator", "/");
            syntax.put("jndi.syntax.ignorecase", "false");
        }

        @Override
        public Name parse(String name)
            throws NamingException
        {
            return new CompoundName(name, syntax);
        }
    }

    ;

    /**
     * Get Context that has access to default Namespace.
     * This method won't be called if a name URL beginning
     * with java: is passed to an InitialContext.
     *
     * @param env a <code>Hashtable</code> value
     * @return a <code>Context</code> value
     * @see org.eclipse.jetty.jndi.java.javaURLContextFactory
     */
    @Override
    public Context getInitialContext(Hashtable env)
    {
        if (LOG.isDebugEnabled())
            LOG.debug("InitialContextFactory.getInitialContext()");

        Context ctx = new localContextRoot(env);
        if (LOG.isDebugEnabled())
            LOG.debug("Created initial context delegate for local namespace:" + ctx);

        return ctx;
    }
}
