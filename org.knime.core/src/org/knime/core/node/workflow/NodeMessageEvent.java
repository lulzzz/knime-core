/* -------------------------------------------------------------------
 * This source code, its documentation and all appendant files
 * are protected by copyright law. All rights reserved.
 *
 * Copyright, 2003 - 2006
 * Universitaet Konstanz, Germany.
 * Lehrstuhl fuer Angewandte Informatik
 * Prof. Dr. Michael R. Berthold
 *
 * You may not modify, publish, transmit, transfer or sell, reproduce,
 * create derivative works from, distribute, perform, display, or in
 * any way exploit any of the content, in whole or in part, except as
 * otherwise expressly permitted in writing by the copyright owner.
 * -------------------------------------------------------------------
 *
 * History
 *   20.09.2007 (Fabian Dill): created
 */
package org.knime.core.node.workflow;

import java.util.EventObject;

/**
 *
 * @author Fabian Dill, University of Konstanz
 */
public class NodeMessageEvent extends EventObject {

    private final NodeMessage m_nodeMessage;

    /**
     *
     * @param nodeId id of the source node
     * @param message the message object (type, message)
     */
    public NodeMessageEvent(final NodeID nodeId, final NodeMessage message) {
        super(nodeId);
        m_nodeMessage = message;
    }

    /**
     *
     * @return the node message object (type, message)
     */
    public NodeMessage getMessage() {
        return m_nodeMessage;
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public NodeID getSource() {
        return (NodeID)super.getSource();
    }



}
