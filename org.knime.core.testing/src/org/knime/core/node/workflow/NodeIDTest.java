/*
 * ------------------------------------------------------------------------
 *
 *  Copyright by KNIME GmbH, Konstanz, Germany
 *  Website: http://www.knime.org; Email: contact@knime.org
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License, Version 3, as
 *  published by the Free Software Foundation.
 *
 *  This program is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, see <http://www.gnu.org/licenses>.
 *
 *  Additional permission under GNU GPL version 3 section 7:
 *
 *  KNIME interoperates with ECLIPSE solely via ECLIPSE's plug-in APIs.
 *  Hence, KNIME and ECLIPSE are both independent programs and are not
 *  derived from each other. Should, however, the interpretation of the
 *  GNU GPL Version 3 ("License") under any applicable laws result in
 *  KNIME and ECLIPSE being a combined program, KNIME GMBH herewith grants
 *  you the additional permission to use and propagate KNIME together with
 *  ECLIPSE with only the license terms in place for ECLIPSE applying to
 *  ECLIPSE and the GNU GPL Version 3 applying for KNIME, provided the
 *  license terms of ECLIPSE themselves allow for the respective use and
 *  propagation of ECLIPSE together with KNIME.
 *
 *  Additional permission relating to nodes for KNIME that extend the Node
 *  Extension (and in particular that are based on subclasses of NodeModel,
 *  NodeDialog, and NodeView) and that only interoperate with KNIME through
 *  standard APIs ("Nodes"):
 *  Nodes are deemed to be separate and independent programs and to not be
 *  covered works.  Notwithstanding anything to the contrary in the
 *  License, the License does not apply to Nodes, you are not required to
 *  license Nodes under the License, and you are granted a license to
 *  prepare and propagate Nodes, in each case even if such Nodes are
 *  propagated with or for interoperation with KNIME.  The owner of a Node
 *  may freely choose the license terms applicable to such Node, including
 *  when such Node is propagated with or for interoperation with KNIME.
 * ---------------------------------------------------------------------
 *
 * History
 *   16.08.2016 (thor): created
 */
package org.knime.core.node.workflow;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;
import org.knime.core.node.workflow.NodeID.NodeIDSuffix;

/**
 * Check is the {@link NodeID} class works as expected.
 *
 * @author Thorsten Meinl, KNIME.com, Zurich, Switzerland
 */
public class NodeIDTest {
    /**
     * Checks if combining to node IDs works as expected.
     */
    @Test
    public void testCombine() {
        NodeID p = NodeID.fromString("0:1");
        NodeIDSuffix s = NodeIDSuffix.fromString("2:3");
        assertThat("Unexpected combined NodeID", s.prependParent(p).toString(), is("0:1:2:3"));

        p = NodeID.fromString("0:1");
        s = NodeIDSuffix.fromString("2");
        assertThat("Unexpected combined NodeID", s.prependParent(p).toString(), is("0:1:2"));

        p = NodeID.fromString("0");
        s = NodeIDSuffix.fromString("1");
        assertThat("Unexpected combined NodeID", s.prependParent(p).toString(), is("0:1"));

        p = NodeID.ROOTID;
        s = NodeIDSuffix.fromString("0");
        assertThat("Unexpected combined NodeID", s.prependParent(p).toString(), is("0"));
    }

    /** Test {@link NodeID#isRoot()}. */
    @Test
    public void testIsRoot() {
        assertThat("Expected root to be root", NodeID.ROOTID.isRoot(), is(true));
        assertThat("Expected child to be not root", NodeID.ROOTID.createChild(2).isRoot(), is(false));

        NodeID root = NodeID.ROOTID;
        NodeID rootClone = NodeID.fromString(root.toString());
        assertThat("Expected root-clone to be root", rootClone.isRoot(), is(true));

        NodeID serializationClone = SerializationUtils.clone(root);
        assertThat("Expected serialization-clone to be root", serializationClone.isRoot(), is(true));

    }

}
