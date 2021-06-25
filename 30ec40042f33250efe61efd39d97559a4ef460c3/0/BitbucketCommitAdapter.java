/*
 * BitbucketCommitAdapter.java
 * Copyright (C) 2020 Kaz Nishimura
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */

package org.vx68k.bitbucket.client.adapter;

import javax.json.bind.adapter.JsonbAdapter;
import org.vx68k.bitbucket.BitbucketCommit;
import org.vx68k.bitbucket.client.internal.ClientCommit;

/**
 * Adapter for {@link BitbucketCommit}.
 *
 * @author Kaz Nishimura
 * @since 6.0
 */
public class BitbucketCommitAdapter
    implements JsonbAdapter<BitbucketCommit, ClientCommit>
{
    @Override
    public final ClientCommit adaptToJson(final BitbucketCommit commit)
    {
        if (commit instanceof ClientCommit) {
            return (ClientCommit) commit;
        }
        return new ClientCommit((ClientCommit)commit);
    }

    @Override
    public final BitbucketCommit adaptFromJson(final ClientCommit commit)
    {
        return commit;
    }
}