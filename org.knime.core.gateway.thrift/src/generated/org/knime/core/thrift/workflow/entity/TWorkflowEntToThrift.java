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

import java.util.List;
import java.util.Map;
import org.knime.core.gateway.v0.workflow.entity.ConnectionEnt;
import org.knime.core.gateway.v0.workflow.entity.NodeEnt;
import org.knime.core.gateway.v0.workflow.entity.MetaPortEnt;
import org.knime.core.gateway.v0.workflow.entity.EntityID;
import org.knime.core.gateway.v0.workflow.entity.JobManagerEnt;
import org.knime.core.gateway.v0.workflow.entity.NodeMessageEnt;
import org.knime.core.gateway.v0.workflow.entity.NodeInPortEnt;
import org.knime.core.gateway.v0.workflow.entity.NodeOutPortEnt;
import org.knime.core.gateway.v0.workflow.entity.BoundsEnt;
import org.knime.core.gateway.v0.workflow.entity.NodeAnnotationEnt;
import java.util.List;

import org.knime.core.gateway.v0.workflow.entity.WorkflowEnt;
import org.knime.core.gateway.v0.workflow.entity.builder.WorkflowEntBuilder;

import org.knime.core.thrift.workflow.entity.TWorkflowEntFromThrift.TWorkflowEntBuilderFromThrift;
import org.knime.core.gateway.v0.workflow.entity.builder.GatewayEntityBuilder;

import java.util.stream.Collectors;
import java.util.HashMap;


/**
 *
 * @author Martin Horn, University of Konstanz
 */
public class TWorkflowEntToThrift extends TWorkflowEnt {

	private final WorkflowEnt m_e;
	
	public TWorkflowEntToThrift(final WorkflowEnt e) {
		m_e = e;
	}

	@Override
    public Map<String, TNodeEnt> getNodes() {
        	//TODO support non-primitive keys
    	Map<String, TNodeEnt> res = new HashMap<>();
        m_e.getNodes().entrySet().stream().forEach(e -> res.put(e.getKey(), new TNodeEntToThrift(e.getValue())));
        return res;
        }
    
	@Override
    public List<TConnectionEnt> getConnections() {
        	return m_e.getConnections().stream().map(l -> new TConnectionEntToThrift(l)).collect(Collectors.toList());
        }
    
	@Override
    public List<TMetaPortEnt> getMetaInPorts() {
        	return m_e.getMetaInPorts().stream().map(l -> new TMetaPortEntToThrift(l)).collect(Collectors.toList());
        }
    
	@Override
    public List<TMetaPortEnt> getMetaOutPorts() {
        	return m_e.getMetaOutPorts().stream().map(l -> new TMetaPortEntToThrift(l)).collect(Collectors.toList());
        }
    
	@Override
    public TEntityID getParent() {
            return new TEntityIDToThrift(m_e.getParent());
        }
    
	@Override
    public TJobManagerEnt getJobManager() {
            return new TJobManagerEntToThrift(m_e.getJobManager());
        }
    
	@Override
    public TNodeMessageEnt getNodeMessage() {
            return new TNodeMessageEntToThrift(m_e.getNodeMessage());
        }
    
	@Override
    public List<TNodeInPortEnt> getInPorts() {
        	return m_e.getInPorts().stream().map(l -> new TNodeInPortEntToThrift(l)).collect(Collectors.toList());
        }
    
	@Override
    public List<TNodeOutPortEnt> getOutPorts() {
        	return m_e.getOutPorts().stream().map(l -> new TNodeOutPortEntToThrift(l)).collect(Collectors.toList());
        }
    
	@Override
    public String getName() {
        	return m_e.getName();
        }
    
	@Override
    public String getNodeID() {
        	return m_e.getNodeID();
        }
    
	@Override
    public String getNodeTypeID() {
        	return m_e.getNodeTypeID();
        }
    
	@Override
    public String getNodeType() {
        	return m_e.getNodeType();
        }
    
	@Override
    public TBoundsEnt getBounds() {
            return new TBoundsEntToThrift(m_e.getBounds());
        }
    
	@Override
    public boolean getIsDeletable() {
        	return m_e.getIsDeletable();
        }
    
	@Override
    public String getNodeState() {
        	return m_e.getNodeState();
        }
    
	@Override
    public boolean getHasDialog() {
        	return m_e.getHasDialog();
        }
    
	@Override
    public TNodeAnnotationEnt getNodeAnnotation() {
            return new TNodeAnnotationEntToThrift(m_e.getNodeAnnotation());
        }
    

	@Override
	public String toString() {
	    return m_e.toString();
	}

    public static class TWorkflowEntBuilderToThrift extends TWorkflowEntBuilder {
    
    	private WorkflowEntBuilder m_b;
    	
    	public TWorkflowEntBuilderToThrift(final WorkflowEntBuilder b) {
    		m_b = b;
    	}

    
    	@Override
        public TWorkflowEnt build() {
            return new TWorkflowEntToThrift(m_b.build());
        }
        
        @Override
        public GatewayEntityBuilder<WorkflowEnt> wrap() {
            return new TWorkflowEntBuilderFromThrift(this);
        }

		@Override
        public TWorkflowEntBuilderToThrift setNodes(final Map<String, TNodeEnt> Nodes) {
					//TODO support non-primitive keys
			Map<String, NodeEnt> map = new HashMap<>();
		    Nodes.entrySet().forEach(e -> map.put(e.getKey(), new TNodeEntFromThrift(e.getValue())));
			m_b.setNodes(map);
		            return this;
        }
        
		@Override
        public TWorkflowEntBuilderToThrift setConnections(final List<TConnectionEnt> Connections) {
					m_b.setConnections(Connections.stream().map(e -> new TConnectionEntFromThrift(e)).collect(Collectors.toList()));
		            return this;
        }
        
		@Override
        public TWorkflowEntBuilderToThrift setMetaInPorts(final List<TMetaPortEnt> MetaInPorts) {
					m_b.setMetaInPorts(MetaInPorts.stream().map(e -> new TMetaPortEntFromThrift(e)).collect(Collectors.toList()));
		            return this;
        }
        
		@Override
        public TWorkflowEntBuilderToThrift setMetaOutPorts(final List<TMetaPortEnt> MetaOutPorts) {
					m_b.setMetaOutPorts(MetaOutPorts.stream().map(e -> new TMetaPortEntFromThrift(e)).collect(Collectors.toList()));
		            return this;
        }
        
		@Override
        public TWorkflowEntBuilderToThrift setParent(final TEntityID Parent) {
					m_b.setParent(new TEntityIDFromThrift(Parent));
		            return this;
        }
        
		@Override
        public TWorkflowEntBuilderToThrift setJobManager(final TJobManagerEnt JobManager) {
					m_b.setJobManager(new TJobManagerEntFromThrift(JobManager));
		            return this;
        }
        
		@Override
        public TWorkflowEntBuilderToThrift setNodeMessage(final TNodeMessageEnt NodeMessage) {
					m_b.setNodeMessage(new TNodeMessageEntFromThrift(NodeMessage));
		            return this;
        }
        
		@Override
        public TWorkflowEntBuilderToThrift setInPorts(final List<TNodeInPortEnt> InPorts) {
					m_b.setInPorts(InPorts.stream().map(e -> new TNodeInPortEntFromThrift(e)).collect(Collectors.toList()));
		            return this;
        }
        
		@Override
        public TWorkflowEntBuilderToThrift setOutPorts(final List<TNodeOutPortEnt> OutPorts) {
					m_b.setOutPorts(OutPorts.stream().map(e -> new TNodeOutPortEntFromThrift(e)).collect(Collectors.toList()));
		            return this;
        }
        
		@Override
        public TWorkflowEntBuilderToThrift setName(final String Name) {
					m_b.setName(Name);
		            return this;
        }
        
		@Override
        public TWorkflowEntBuilderToThrift setNodeID(final String NodeID) {
					m_b.setNodeID(NodeID);
		            return this;
        }
        
		@Override
        public TWorkflowEntBuilderToThrift setNodeTypeID(final String NodeTypeID) {
					m_b.setNodeTypeID(NodeTypeID);
		            return this;
        }
        
		@Override
        public TWorkflowEntBuilderToThrift setNodeType(final String NodeType) {
					m_b.setNodeType(NodeType);
		            return this;
        }
        
		@Override
        public TWorkflowEntBuilderToThrift setBounds(final TBoundsEnt Bounds) {
					m_b.setBounds(new TBoundsEntFromThrift(Bounds));
		            return this;
        }
        
		@Override
        public TWorkflowEntBuilderToThrift setIsDeletable(final boolean IsDeletable) {
					m_b.setIsDeletable(IsDeletable);
		            return this;
        }
        
		@Override
        public TWorkflowEntBuilderToThrift setNodeState(final String NodeState) {
					m_b.setNodeState(NodeState);
		            return this;
        }
        
		@Override
        public TWorkflowEntBuilderToThrift setHasDialog(final boolean HasDialog) {
					m_b.setHasDialog(HasDialog);
		            return this;
        }
        
		@Override
        public TWorkflowEntBuilderToThrift setNodeAnnotation(final TNodeAnnotationEnt NodeAnnotation) {
					m_b.setNodeAnnotation(new TNodeAnnotationEntFromThrift(NodeAnnotation));
		            return this;
        }
        
    }

}
