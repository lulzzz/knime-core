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
 */
package org.knime.core.thrift.workflow.entity;

import org.knime.core.gateway.v0.workflow.entity.EntityID;
import org.knime.core.gateway.v0.workflow.entity.JobManagerEnt;
import org.knime.core.gateway.v0.workflow.entity.NodeMessageEnt;
import org.knime.core.gateway.v0.workflow.entity.NodeInPortEnt;
import org.knime.core.gateway.v0.workflow.entity.NodeOutPortEnt;
import org.knime.core.gateway.v0.workflow.entity.BoundsEnt;
import org.knime.core.gateway.v0.workflow.entity.NodeAnnotationEnt;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.facebook.swift.codec.ThriftConstructor;
import com.facebook.swift.codec.ThriftField;
import com.facebook.swift.codec.ThriftStruct;

import org.knime.core.gateway.v0.workflow.entity.NodeEnt;
import org.knime.core.gateway.v0.workflow.entity.builder.NodeEntBuilder;

import org.knime.core.thrift.workflow.entity.TNodeEnt.TNodeEntBuilder;
import org.knime.core.thrift.workflow.entity.TNodeEntFromThrift.TNodeEntBuilderFromThrift;
import org.knime.core.thrift.TEntityBuilderFactory.ThriftEntityBuilder;
import org.knime.core.gateway.v0.workflow.entity.builder.GatewayEntityBuilder;


/**
 *
 * @author Martin Horn, University of Konstanz
 */
@ThriftStruct(builder = TNodeEntBuilder.class)
public class TNodeEnt {



	private TEntityID m_Parent;
	private TJobManagerEnt m_JobManager;
	private TNodeMessageEnt m_NodeMessage;
	private List<TNodeInPortEnt> m_InPorts;
	private List<TNodeOutPortEnt> m_OutPorts;
	private String m_Name;
	private String m_NodeID;
	private String m_NodeTypeID;
	private String m_NodeType;
	private TBoundsEnt m_Bounds;
	private boolean m_IsDeletable;
	private String m_NodeState;
	private boolean m_HasDialog;
	private TNodeAnnotationEnt m_NodeAnnotation;

    /**
     * @param builder
     */
    private TNodeEnt(final TNodeEntBuilder builder) {
		m_Parent = builder.m_Parent;
		m_JobManager = builder.m_JobManager;
		m_NodeMessage = builder.m_NodeMessage;
		m_InPorts = builder.m_InPorts;
		m_OutPorts = builder.m_OutPorts;
		m_Name = builder.m_Name;
		m_NodeID = builder.m_NodeID;
		m_NodeTypeID = builder.m_NodeTypeID;
		m_NodeType = builder.m_NodeType;
		m_Bounds = builder.m_Bounds;
		m_IsDeletable = builder.m_IsDeletable;
		m_NodeState = builder.m_NodeState;
		m_HasDialog = builder.m_HasDialog;
		m_NodeAnnotation = builder.m_NodeAnnotation;
    }
    
    protected TNodeEnt() {
    	//
    }

    @ThriftField(1)
    public TEntityID getParent() {
        return m_Parent;
    }
    
    @ThriftField(2)
    public TJobManagerEnt getJobManager() {
        return m_JobManager;
    }
    
    @ThriftField(3)
    public TNodeMessageEnt getNodeMessage() {
        return m_NodeMessage;
    }
    
    @ThriftField(4)
    public List<TNodeInPortEnt> getInPorts() {
        return m_InPorts;
    }
    
    @ThriftField(5)
    public List<TNodeOutPortEnt> getOutPorts() {
        return m_OutPorts;
    }
    
    @ThriftField(6)
    public String getName() {
        return m_Name;
    }
    
    @ThriftField(7)
    public String getNodeID() {
        return m_NodeID;
    }
    
    @ThriftField(8)
    public String getNodeTypeID() {
        return m_NodeTypeID;
    }
    
    @ThriftField(9)
    public String getNodeType() {
        return m_NodeType;
    }
    
    @ThriftField(10)
    public TBoundsEnt getBounds() {
        return m_Bounds;
    }
    
    @ThriftField(11)
    public boolean getIsDeletable() {
        return m_IsDeletable;
    }
    
    @ThriftField(12)
    public String getNodeState() {
        return m_NodeState;
    }
    
    @ThriftField(13)
    public boolean getHasDialog() {
        return m_HasDialog;
    }
    
    @ThriftField(14)
    public TNodeAnnotationEnt getNodeAnnotation() {
        return m_NodeAnnotation;
    }
    

	@Override
	public String toString() {
	    return ToStringBuilder.reflectionToString(this);
	}

	public static TNodeEntBuilder builder() {
		return new TNodeEntBuilder();
	}
	
    public static class TNodeEntBuilder implements ThriftEntityBuilder<NodeEnt> {
    
		private TEntityID m_Parent;
		private TJobManagerEnt m_JobManager;
		private TNodeMessageEnt m_NodeMessage;
		private List<TNodeInPortEnt> m_InPorts;
		private List<TNodeOutPortEnt> m_OutPorts;
		private String m_Name;
		private String m_NodeID;
		private String m_NodeTypeID;
		private String m_NodeType;
		private TBoundsEnt m_Bounds;
		private boolean m_IsDeletable;
		private String m_NodeState;
		private boolean m_HasDialog;
		private TNodeAnnotationEnt m_NodeAnnotation;

        @ThriftConstructor
        public TNodeEnt build() {
            return new TNodeEnt(this);
        }
        
        @Override
        public GatewayEntityBuilder<NodeEnt> wrap() {
            return new TNodeEntBuilderFromThrift(this);
        }

        @ThriftField
        public TNodeEntBuilder setParent(final TEntityID Parent) {
			m_Parent = Parent;			
            return this;
        }
        
        @ThriftField
        public TNodeEntBuilder setJobManager(final TJobManagerEnt JobManager) {
			m_JobManager = JobManager;			
            return this;
        }
        
        @ThriftField
        public TNodeEntBuilder setNodeMessage(final TNodeMessageEnt NodeMessage) {
			m_NodeMessage = NodeMessage;			
            return this;
        }
        
        @ThriftField
        public TNodeEntBuilder setInPorts(final List<TNodeInPortEnt> InPorts) {
			m_InPorts = InPorts;			
            return this;
        }
        
        @ThriftField
        public TNodeEntBuilder setOutPorts(final List<TNodeOutPortEnt> OutPorts) {
			m_OutPorts = OutPorts;			
            return this;
        }
        
        @ThriftField
        public TNodeEntBuilder setName(final String Name) {
			m_Name = Name;			
            return this;
        }
        
        @ThriftField
        public TNodeEntBuilder setNodeID(final String NodeID) {
			m_NodeID = NodeID;			
            return this;
        }
        
        @ThriftField
        public TNodeEntBuilder setNodeTypeID(final String NodeTypeID) {
			m_NodeTypeID = NodeTypeID;			
            return this;
        }
        
        @ThriftField
        public TNodeEntBuilder setNodeType(final String NodeType) {
			m_NodeType = NodeType;			
            return this;
        }
        
        @ThriftField
        public TNodeEntBuilder setBounds(final TBoundsEnt Bounds) {
			m_Bounds = Bounds;			
            return this;
        }
        
        @ThriftField
        public TNodeEntBuilder setIsDeletable(final boolean IsDeletable) {
			m_IsDeletable = IsDeletable;			
            return this;
        }
        
        @ThriftField
        public TNodeEntBuilder setNodeState(final String NodeState) {
			m_NodeState = NodeState;			
            return this;
        }
        
        @ThriftField
        public TNodeEntBuilder setHasDialog(final boolean HasDialog) {
			m_HasDialog = HasDialog;			
            return this;
        }
        
        @ThriftField
        public TNodeEntBuilder setNodeAnnotation(final TNodeAnnotationEnt NodeAnnotation) {
			m_NodeAnnotation = NodeAnnotation;			
            return this;
        }
        
    }

}
